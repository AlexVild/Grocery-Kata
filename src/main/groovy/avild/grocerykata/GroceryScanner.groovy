package avild.grocerykata

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    Inventory inventory = new Inventory()
    int sum = 0
    ArrayList<String> itemsRangUp = new ArrayList<String>() // stack - head of list should be last item rang up

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
