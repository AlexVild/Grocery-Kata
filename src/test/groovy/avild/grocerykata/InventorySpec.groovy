package avild.grocerykata


import spock.lang.Specification

class InventorySpec extends Specification {
    Inventory mockInventory
    GroceryItem apple = new GroceryItem("apple", 299, 0, 5)

    def setup() {
        mockInventory = new Inventory()
    }

    def "Inventory() creates a clear inventory list"() {
        expect:
        mockInventory.itemsInInventory == []
    }

    def "addItem adds a new grocery item to the store's inventory"() {
        when:
        mockInventory.addItem(apple)

        then:
        mockInventory.itemsInInventory.size() == 1
        mockInventory.itemsInInventory[0].name == "apple"
    }

    def "removeItem removes an existing grocery item from the store's inventory, regardless of case"() {
        given:
        mockInventory.itemsInInventory = [apple]
        String query = "Apple"

        when:
        mockInventory.removeItem(query)

        then:
        mockInventory.itemsInInventory.size() == 0
    }

    def "updatePrice updates the price of an item in the inventory"() {
        given:
        mockInventory.itemsInInventory = [apple]
        String query = "apple"
        int newPrice = 99

        when:
        mockInventory.updateItemPrice(query, newPrice)

        then:
        mockInventory.itemsInInventory[0].price == 99
    }

    def "clearInventory removes all items from the inventory"() {
        given:
        mockInventory.itemsInInventory = [apple, apple, apple]

        when:
        mockInventory.clearInventory()

        then:
        mockInventory.itemsInInventory.size() == 0
    }
}
