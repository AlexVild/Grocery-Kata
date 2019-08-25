package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.AmountSpecialSpec
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
        groceryScanner.itemsRangUp = ["pear"]

        when:
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.itemsRangUp[0] == "apple"
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
        groceryScanner.itemsRangUp = ["pear"]

        when:
        groceryScanner.ringItem("apple", 0.5)

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.itemsRangUp[0] == "apple"
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
        groceryScanner.itemsRangUp = [apple.name, pear.name]
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
        groceryScanner.itemsRangUp = [apple.name, pear.name, pear.name]
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
        groceryScanner.inventory.itemsInInventory = [pear]
        groceryScanner.inventory.currentSpecials = [
                new AmountSpecial(itemName: "pear", triggerAmount: 2, newPrice: 299)
        ]

        when:
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")

        then:
        groceryScanner.runningTotal() == 598
    }

    def "calcAmountSavedFromSpecials will use special limits correctly"() {
        given:
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        groceryScanner.inventory.itemsInInventory = [pear]
        groceryScanner.inventory.currentSpecials = [
                new AmountSpecial(itemName: "pear", triggerAmount: 2, newPrice: 299, limit: 4)
        ]

        when:
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")
        groceryScanner.ringItem("pear")

        then:
        groceryScanner.runningTotal() == 1196
    }
}
