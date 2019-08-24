package avild.grocerykata

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    public Inventory inventory
    public int sum = 0
    public ArrayList<String> itemsRangUp // stack - head of list should be last item rang up

    GroceryScanner() {
        itemsRangUp = []
    }

    void ringItem(String itemName, float weight = 1.0, Integer markdownPrice = null) {
        GroceryItem queriedItem = this.inventory.queryForItem(itemName)
        int priceOfItem = markdownPrice ? markdownPrice : queriedItem.price

        this.itemsRangUp.push(itemName)
        this.sum += (priceOfItem.toFloat() * weight).toInteger()
    }

    void removeLastScannedItem() {
        String itemToRemove = this.itemsRangUp.head()
        int priceOfItem = this.inventory.queryForItem(itemToRemove).price

        this.itemsRangUp.pop() // remove the item from our rang up list
        this.sum -= priceOfItem
    }

    void removeItem(String item) {
        int priceOfItem = this.inventory.queryForItem(item).price
        this.itemsRangUp.remove(this.itemsRangUp.indexOf(item))
        this.sum -= priceOfItem
    }

    String getFormattedSum() {
        return "\$${this.sum.toFloat() / 100}"
    }
}
