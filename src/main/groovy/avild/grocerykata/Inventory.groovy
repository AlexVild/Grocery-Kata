package avild.grocerykata
// Class performs CRUD operations on our grocery Inventory
class Inventory {
    public ArrayList<GroceryItem> itemsInInventory

    Inventory() {
        itemsInInventory = new ArrayList<GroceryItem>()
    }

    void addItem(GroceryItem item) {
        this.itemsInInventory.push(item)
    }

    void removeItem(String query) {
        itemsInInventory.removeAll { it.name.equalsIgnoreCase(query) }
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
        this.itemsInInventory.clear()
    }
}
