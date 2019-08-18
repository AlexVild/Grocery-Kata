package avild.grocerykata
// Class performs CRUD operations on our grocery Inventory
class Inventory {
    public ArrayList<GroceryItem> itemsInInventory

    Inventory() {
        itemsInInventory = new ArrayList<GroceryItem>()
    }

    void addItem(GroceryItem item) {
        itemsInInventory.push(item)
    }

    void removeItem(String query) {
        itemsInInventory.removeAll { it.name.equalsIgnoreCase(query) }
    }

    GroceryItem queryForItem(String query) {
        GroceryItem item = itemsInInventory.find { it.name.equalsIgnoreCase(query.trim()) }
        if(item) {
            return item
        } else {
            throw new NoSuchFieldException("${query} does not exist in the inventory")
        }
    }

    void updateItemPrice(String query, int updatedPrice) {
        itemsInInventory.collect { items ->
            if(items.name.equalsIgnoreCase(query)) {
                items.price = updatedPrice
            }
            return items
        }
    }

    void clearInventory() {
        itemsInInventory.clear()
    }
}
