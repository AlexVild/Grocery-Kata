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
        GroceryItem queriedItem = inventory.queryForItem(itemName)
        int priceOfItem = markdownPrice ? markdownPrice : queriedItem.price

        itemsRangUp.push(itemName)
        sum += (priceOfItem.toFloat() * weight).toInteger()
    }

    void removeLastScannedItem() {
        String itemToRemove = itemsRangUp.head()
        int priceOfItem = inventory.queryForItem(itemToRemove).price

        itemsRangUp.pop() // remove the item from our rang up list
        sum -= priceOfItem
    }

    void removeItem(String item) {
        int priceOfItem = inventory.queryForItem(item).price
        itemsRangUp.remove(itemsRangUp.indexOf(item))
        sum -= priceOfItem
    }

    String getFormattedSum() {
        return "\$${sum / 100.0}"
    }
}
