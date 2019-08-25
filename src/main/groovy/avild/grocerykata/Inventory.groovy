package avild.grocerykata

import avild.grocerykata.specials.Special

// Class performs CRUD operations on our grocery Inventory
class Inventory {
    public ArrayList<GroceryItem> itemsInInventory
    public ArrayList<Special> currentSpecials

    Inventory() {
        itemsInInventory = new ArrayList<GroceryItem>()
        currentSpecials = new ArrayList<Special>()
    }

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

    void clearInventory() {
        itemsInInventory.clear()
    }
}
