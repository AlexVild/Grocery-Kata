package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.PercentageSpecial
import spock.lang.Specification

class InventorySpec extends Specification {
    Inventory mockInventory
    GroceryItem apple = new GroceryItem(name: "apple", price: 299, pricedByWeight: true)

    def setup() {
        mockInventory = new Inventory()
    }

    def "Inventory() creates a clear inventory list and current special list"() {
        expect:
        mockInventory.itemsInInventory == []
        mockInventory.currentSpecials == []
    }

    def "addItem adds a new grocery item to the store's inventory"() {
        when:
        mockInventory.addItem(apple)

        then:
        mockInventory.itemsInInventory.size() == 1
        mockInventory.itemsInInventory[0].name == "apple"
    }

    def "addItem throws a RuntimeException when trying to add an item that already exists"() {
        when:
        mockInventory.addItem(apple)
        mockInventory.addItem(apple)

        then:
        def ex = thrown(RuntimeException)
        ex.message == "${apple.name} already exists in the inventory"
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

    def "updatePrice throws an exception if it can't find the item in the inventory"() {
        given:
        mockInventory.itemsInInventory = [apple]

        when:
        mockInventory.updateItemPrice("pear", 100)

        then:
        def ex = thrown(RuntimeException)
        ex.message == "pear does not exist in the inventory"
    }

    def "clearInventory removes all items from the inventory"() {
        given:
        GroceryItem banana = new GroceryItem(name: "banana", price: 299, pricedByWeight: true)
        GroceryItem pear = new GroceryItem(name: "pear", price: 299)
        mockInventory.itemsInInventory = [apple, pear, banana]

        when:
        mockInventory.clearInventory()

        then:
        mockInventory.itemsInInventory.size() == 0
    }

    def "queryForItem returns a groceryItem in the inventory from a given string"() {
        given:
        mockInventory.itemsInInventory = [apple]

        when:
        final GroceryItem expected = mockInventory.queryForItem("apple")

        then:
        expected == apple
    }

    def "queryForItem returns a Runtime exception when it can't find the item specified"() {
        when:
        String query = "apple"
        mockInventory.queryForItem(query)

        then:
        def ex = thrown(RuntimeException)
        ex.message == "${query} does not exist in the inventory"
    }

    def "addAmountSpecial adds a special for the given item"() {
        given:
        AmountSpecial expectedSpecial = new AmountSpecial(itemName: "chips", triggerAmount: 2, newPrice: 3, limit: 0)
        GroceryItem chips = new GroceryItem(name: "chips", price: 3)
        mockInventory.itemsInInventory = [chips]

        when:
        mockInventory.addAmountSpecial("chips", 2, 3, 0)

        then:
        mockInventory.currentSpecials.size() == 1
        mockInventory.currentSpecials[0].itemName == expectedSpecial.itemName
        mockInventory.currentSpecials[0].triggerAmount == expectedSpecial.triggerAmount
        mockInventory.currentSpecials[0].newPrice == expectedSpecial.newPrice
        mockInventory.currentSpecials[0].limit == expectedSpecial.limit
    }

    def "addAmountSpecial throws an exception if there is no item for which to add a special to"() {
        when:
        mockInventory.addAmountSpecial("water", 2, 3, 0)

        then:
        def ex = thrown(RuntimeException)
        ex.message == "water does not exist in the inventory"
    }

    def "addPercentageSpecial adds a special for the given item"() {
        given:
        PercentageSpecial expectedSpecial = new PercentageSpecial(itemName: "chips", specialAmount: 2, percentOff: 0.5, limit: 0)
        GroceryItem chips = new GroceryItem(name: "chips", price: 3)
        mockInventory.itemsInInventory = [chips]

        when:
        mockInventory.addPercentageSpecial("chips", 2, 0.5, 0)

        then:
        mockInventory.currentSpecials.size() == 1
        mockInventory.currentSpecials[0].itemName == expectedSpecial.itemName
        mockInventory.currentSpecials[0].specialAmount == expectedSpecial.specialAmount
        mockInventory.currentSpecials[0].percentOff == expectedSpecial.percentOff
        mockInventory.currentSpecials[0].limit == expectedSpecial.limit
    }

    def "addPercentageSpecial throws an exception if there is no item for which to add a special to"() {
        when:
        mockInventory.addPercentageSpecial("water", 2, 0.3, 0)

        then:
        def ex = thrown(RuntimeException)
        ex.message == "water does not exist in the inventory"
    }
}
