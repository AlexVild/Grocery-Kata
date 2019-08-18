package avild.grocerykata

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    public Inventory inventory
    public float sum = 0.0

    void ringItem(String itemName) {
        String trimmedQueryTerm = itemName.trim()

        GroceryItem queriedItem = this.inventory.itemsInInventory.find {
            it.name.equalsIgnoreCase(trimmedQueryTerm)
        }

        float priceOfItem = queriedItem.price
        this.sum += priceOfItem
    }
}
