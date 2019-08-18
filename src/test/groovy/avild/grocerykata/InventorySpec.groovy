package avild.grocerykata


import spock.lang.Specification

class InventorySpec extends Specification {
    Inventory mockInventory
    GroceryItem apple = new GroceryItem("apple", 299, 0, 5)

    def setup() {
        mockInventory = new Inventory()
    }

    def "addItem adds a new grocery item to the store's inventory"() {
        when:
        mockInventory.addItem(apple)

        then:
        mockInventory.inventory.size() == 1
        mockInventory.inventory[0].name == "apple"
    }

    def "removeItem removes an existing grocery item from the store's inventory"() {
        given:
        mockInventory.inventory = [apple]
        String query = "Apple"

        when:
        mockInventory.removeItem(query)

        then:
        mockInventory.inventory.size() == 0
    }
}
