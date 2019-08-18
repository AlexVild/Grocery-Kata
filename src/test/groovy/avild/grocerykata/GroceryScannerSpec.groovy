package avild.grocerykata

import spock.lang.Specification

class GroceryScannerSpec extends Specification{
    GroceryItem apple = new GroceryItem("apple", 299, 0, 5)
    Inventory mockInventory
    GroceryScanner groceryScanner

    def setup() {
        mockInventory = new Inventory()
        mockInventory.itemsInInventory.push(apple)
        groceryScanner = new GroceryScanner()
        groceryScanner.inventory = mockInventory
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
}
