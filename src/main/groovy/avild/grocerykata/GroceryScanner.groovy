package avild.grocerykata

class GroceryScanner {
    public Inventory inventory
    public float sum = 0.0

    void ringItem(String itemName) {
        String trimmedQueryTerm = itemName.trim()
        GroceryItem queriedItem = this.inventory.itemsInInventory.find {it.name.equalsIgnoreCase(trimmedQueryTerm)}
        float priceOfItem = queriedItem.price

        this.sum += priceOfItem
    }
}
