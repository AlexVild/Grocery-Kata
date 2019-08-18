package avild.grocerykata

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    public Inventory inventory
    public float sum = 0.0
    public ArrayList<String> itemsRangUp // stack - head of list should be last item rang up

    GroceryScanner() {
        itemsRangUp = []
    }

    void ringItem(String itemName) {
        GroceryItem queriedItem = this.inventory.queryForItem(itemName)

        float priceOfItem = queriedItem.price
        this.sum += priceOfItem
    }

    void removeLastScannedItem() {
        String itemToRemove = this.itemsRangUp.head()
        int priceOfItem = this.inventory.queryForItem(itemToRemove).price

        this.itemsRangUp.pop() // remove the item from our rang up list
        this.sum -= priceOfItem
    }
}
