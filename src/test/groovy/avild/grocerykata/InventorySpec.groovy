package avild.grocerykata

import spock.lang.Specification

class InventorySpec extends Specification {
    Inventory mockInventory

    def setup() {
        mockInventory = new Inventory()
    }

    def "addItem adds a new grocery item to the store's inventory"() {
        given:
        GroceryItem apple = new GroceryItem("apple", 299, 0, 5)

        when:
        mockInventory.addItem(apple)

        then:
        mockInventory.inventory.size() == 1
        mockInventory.inventory[0].name == "apple"
    }
}
