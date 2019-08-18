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

    def "removeItem removes an existing grocery item from the store's inventory, regardless of case"() {
        given:
        mockInventory.inventory = [apple]
        String query = "Apple"

        when:
        mockInventory.removeItem(query)

        then:
        mockInventory.inventory.size() == 0
    }

    def "updatePrice updates the price of an item in the inventory"() {
        given:
        mockInventory.inventory = [apple]
        String query = "apple"
        int newPrice = 99

        when:
        mockInventory.updateItemPrice(query, newPrice)

        then:
        mockInventory.inventory[0].price == 99
    }

    def "clearInventory removes all items from the inventory"() {
        given:
        mockInventory.inventory = [apple, apple, apple]

        when:
        mockInventory.clearInventory()

        then:
        mockInventory.inventory.size() == 0
    }
}
