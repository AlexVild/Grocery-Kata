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
        String trimmedQueryTerm = itemName.trim()

        GroceryItem queriedItem = this.inventory.itemsInInventory.find {
            it.name.equalsIgnoreCase(trimmedQueryTerm)
        }

        float priceOfItem = queriedItem.price
        this.sum += priceOfItem
    }

    void removeLastScannedItem() {
        String itemToRemove = this.itemsRangUp.head()
        int priceOfItem = this.inventory.itemsInInventory.find {
            it.name.equalsIgnoreCase(itemToRemove)
        }.price // todo make function to query price of item in inventory

        this.itemsRangUp.pop() // remove the item from our rang up list
        this.sum -= priceOfItem
    }
}
