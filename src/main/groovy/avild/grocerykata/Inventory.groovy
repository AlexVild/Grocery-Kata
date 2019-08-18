package avild.grocerykata

// Class performs CRUD operations on our grocery Inventory
class Inventory {
    public ArrayList<GroceryItem> inventory

    Inventory() {
        inventory = new ArrayList<GroceryItem>()
    }

    void addItem(GroceryItem item) {
        inventory.push(item)
    }
}
