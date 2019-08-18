package avild.grocerykata

import avild.grocerykata.GroceryItem

// Class performs CRUD operations on our grocery Inventory
class Inventory {
    public ArrayList<GroceryItem> inventory

    Inventory() {
        inventory = new ArrayList<GroceryItem>()
    }

    void addItem(GroceryItem item) {
        this.inventory.push(item)
    }

    void removeItem(String query) {
        inventory.removeAll { it.name.equalsIgnoreCase(query) }
    }
}
