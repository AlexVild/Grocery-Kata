package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.EqualOrLesserSpecial
import avild.grocerykata.specials.PercentageSpecial
import spock.lang.Specification

class GroceryScannerSpec extends Specification{
    GroceryItem apple = new GroceryItem(name: "apple", price: 299)
    Inventory mockInventory
    GroceryScanner groceryScanner

    def setup() {
        mockInventory = new Inventory()
        mockInventory.itemsInInventory.push(apple)
        groceryScanner = new GroceryScanner()
        groceryScanner.inventory = mockInventory
    }

    def "GroceryScanner initializes an empty list of things rang up"() {
        expect:
        groceryScanner.itemsRangUp == []
        groceryScanner.sum == 0
    }

    def "ringItem successfully queries from the Grocery's Inventory and adds the price to the running total"() {
        when:
        groceryScanner.ringItem("apple")
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.sum == 598
    }

    def "ringItem adds the newly rang item to the amount of items rang up (pushing to the front of the stack)"() {
        given:
        groceryScanner.itemsRangUp = [new CheckedOutItem(name: "pear", weight: 1.0)]

        when:
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.itemsRangUp[0].name == "apple"
        groceryScanner.itemsRangUp[0].weight == 1.0 as float
    }

    def "ringItem correctly trims and ignores case of a query string"() {
        when:
        groceryScanner.ringItem("   APPLE   ")

        then:
        groceryScanner.sum == 299
    }

    def "ringItem correctly calculates the value of an item by its weight and adds it to the sum" () {
        when:
        groceryScanner.ringItem("apple", 0.5)

        then:
        groceryScanner.sum == 149
    }

    def "ringItem adds the item to the list of items rang up (front of stack)" () {
        given:
        groceryScanner.itemsRangUp = [new CheckedOutItem(name: "pear", weight: 1.0)]

        when:
        groceryScanner.ringItem("apple", 0.5)

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.itemsRangUp[0].name == "apple"
        groceryScanner.itemsRangUp[0].weight == 0.5 as float
    }

    def "ringItem uses a markdown price when available" () {
        when:
        groceryScanner.ringItem("apple", 0.5, 199)

        then:
        groceryScanner.sum == 99
    }

    def "removeLastScannedItem removes the last item the user rang up and subtracts the total from the sum"() {
        given:
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        groceryScanner.inventory.itemsInInventory = [apple, pear]
        groceryScanner.itemsRangUp = [
                new CheckedOutItem(name: "pear", weight: 1.0),
                new CheckedOutItem(name: "apple", weight: 1.0),
        ]
        groceryScanner.sum = apple.price + pear.price

        when:
        groceryScanner.removeLastScannedItem()

        then:
        groceryScanner.itemsRangUp.size() == 1
        groceryScanner.sum == pear.price
    }

    def "removeItem removes a specific item the user rang up based on its string ID" () {
        given:
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        groceryScanner.inventory.itemsInInventory = [apple, pear]
        groceryScanner.itemsRangUp = [
                new CheckedOutItem(name: "apple", weight: 1.0),
                new CheckedOutItem(name: "pear", weight: 1.0),
                new CheckedOutItem(name: "pear", weight: 1.0)
        ]
        groceryScanner.sum = apple.price + pear.price + pear.price

        when:
        groceryScanner.removeItem("pear")

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.sum == apple.price + pear.price
    }

    def "getFormattedSum displays the running sum in dollar format" () {
        given:
        groceryScanner.sum = 398

        when:
        String actualSumString = groceryScanner.getFormattedSum()

        then:
        actualSumString == "\$3.98"
    }

    def "runningTotal returns a running total of the sum of item's cost"() {
        when:
        groceryScanner.ringItem(apple.name)

        then:
        groceryScanner.runningTotal() == 299
    }

    def "calcAmountSavedFromSpecials will evaluate current running specials and detract from the sum"() {
        given:
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        GroceryItem chips = new GroceryItem(name: "chips", price: 400)
        GroceryItem meat = new GroceryItem(name: "meat", price: 100, pricedByWeight: true)
        groceryScanner.inventory.itemsInInventory = [pear, chips, meat]
        groceryScanner.inventory.currentSpecials = [
                new PercentageSpecial(itemName: "pear", triggerAmount: 2, specialAmount: 1, percentOff: 0.5),
                new AmountSpecial(itemName: "chips", triggerAmount: 3, newPrice: 500),
                new EqualOrLesserSpecial(itemName: "meat", triggerWeight: 2.0, percentOff: 0.5),
        ]

        when:
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("meat", 2.0)
        groceryScanner.ringItem("meat", 1.0)

        then:
        groceryScanner.runningTotal() == 1498
    }

    def "removing an item with a special attached to it invalidates the special if it no longer holds"() {
        given:
        GroceryItem chips = new GroceryItem(name: "chips", price: 400)
        groceryScanner.inventory.itemsInInventory = [chips]
        groceryScanner.inventory.currentSpecials = [
                new AmountSpecial(itemName: "chips", triggerAmount: 3, newPrice: 500),
        ]

        when:
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.removeLastScannedItem()
        then:
        groceryScanner.runningTotal() == 800
    }

    def "calcAmountSavedFromSpecials will use special limits correctly"() {
        given:
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        GroceryItem chips = new GroceryItem(name: "chips", price: 400)
        GroceryItem meat = new GroceryItem(name: "meat", price: 100, pricedByWeight: true)
        groceryScanner.inventory.itemsInInventory = [pear, chips, meat]
        groceryScanner.inventory.currentSpecials = [
                new PercentageSpecial(itemName: "pear", triggerAmount: 1, specialAmount: 1, percentOff: 1.0,  limit: 4),
                new AmountSpecial(itemName: "chips", triggerAmount: 2, newPrice: 200, limit: 2),
                new EqualOrLesserSpecial(itemName: "meat", triggerWeight: 2.0, percentOff: 0.5, limit: 1),
        ]

        when:
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("chips")
        groceryScanner.ringItem("meat", 6.0)

        then:
        groceryScanner.runningTotal() == 2796
    }

    def "isItemRangUp returns true when item has been rang up" () {
        when:
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.isItemRangUp("apple")
    }

    def "countTimesItemRangUp returns how many times an item has been rang up" () {
        when:
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.countTimesItemRangUp("apple") == 1
    }
}
