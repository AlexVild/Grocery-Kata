package avild.grocerykata

import spock.lang.Specification

class GroceryScannerSpec extends Specification{
    GroceryItem apple = new GroceryItem("apple", 299, 0, true)
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
    }

    def "ringItem successfully queries from the Grocery's Inventory and adds the price to the running total"() {
        when:
        groceryScanner.ringItem("apple")
        groceryScanner.ringItem("apple")

        then:
        groceryScanner.sum == 598
    }

    def "ringItem correctly trims and ignores case of a query string"() {
        when:
        groceryScanner.ringItem("   APPLE   ")

        then:
        groceryScanner.sum == 299
    }

    def "removeLastScannedItem removes the last item the user rang up and subtracts the total from the sum"() {
        given:
        GroceryItem pear = new GroceryItem("pear", 299, 0)
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
        GroceryItem pear = new GroceryItem("pear", 299, 0)
        groceryScanner.inventory.itemsInInventory = [apple, pear]
        groceryScanner.itemsRangUp = [apple.name, pear.name, pear.name]
        groceryScanner.sum = apple.price + pear.price + pear.price

        when:
        groceryScanner.removeItem("pear")

        then:
        groceryScanner.itemsRangUp.size() == 2
        groceryScanner.sum == apple.price + pear.price
    }
}
