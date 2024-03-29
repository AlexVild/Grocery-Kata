package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.EqualOrLesserSpecial
import avild.grocerykata.specials.PercentageSpecial
import avild.grocerykata.specials.Special

// Class performs CRUD operations on our grocery Inventory
class Inventory {
    ArrayList<GroceryItem> itemsInInventory = new ArrayList<GroceryItem>()
    ArrayList<Special> currentSpecials = new ArrayList<Special>()

    void addItem(GroceryItem item) throws RuntimeException {
        // ensure the item is not already in the inventory
        if (itemsInInventory.find { it.name.equalsIgnoreCase(item.name.trim()) }) {
            throw new RuntimeException("${item.name} already exists in the inventory")
        } else {
            itemsInInventory.push(item)
        }
    }

    void removeItem(String query) {
        itemsInInventory.removeAll { it.name.equalsIgnoreCase(query) }
    }

    GroceryItem queryForItem(String query) throws RuntimeException {
        GroceryItem item = itemsInInventory.find { it.name.equalsIgnoreCase(query.trim()) }
        if(item) {
            return item
        } else {
            throw new RuntimeException("${query} does not exist in the inventory")
        }
    }

    void updateItemPrice(String query, int updatedPrice) {
        // only update an item if it exists in the first place; else: will throw queryForItem's RuntimeException
        if (queryForItem(query)) {
            itemsInInventory.collect { items ->
                if(items.name.equalsIgnoreCase(query)) {
                    items.price = updatedPrice
                }
                return items
            }
        }
    }

    void addAmountSpecial(String itemName, int triggerAmount, int newPrice, int limit) { // todo one special per item
        if (queryForItem(itemName)) { // ensure the item exists in the inventory
            AmountSpecial newSpecial = new AmountSpecial(itemName: itemName, triggerAmount: triggerAmount, newPrice: newPrice, limit: limit)
            currentSpecials << newSpecial
        }
    }

    void addPercentageSpecial(String itemName, int specialAmount, float percentOff, int limit) {
        if (queryForItem(itemName)) { // ensure the item exists in the inventory
            PercentageSpecial newSpecial = new PercentageSpecial(itemName: itemName, specialAmount: specialAmount, percentOff: percentOff, limit: limit)
            currentSpecials << newSpecial
        }
    }

    void addEqualOrLesserSpecial(String itemName, float triggerWeight, float percentOff, int limit) {
        if (queryForItem(itemName)) { // ensure the item exists in the inventory
            if (queryForItem(itemName).pricedByWeight) { // ensure the item is a weighted item
                EqualOrLesserSpecial newSpecial = new EqualOrLesserSpecial(itemName: itemName, triggerWeight: triggerWeight, percentOff: percentOff, limit: limit)
                currentSpecials << newSpecial
            } else {
                throw new RuntimeException("Item '${itemName}' is not priced by weight")
            }
        }
    }

    void clearInventory() {
        itemsInInventory.clear()
    }
}
