package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.Special

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

    int runningTotal() {
        return sum - calcAmountSavedFromSpecials()
    }

    private int calcAmountSavedFromSpecials() {
        ArrayList<Special> currentSpecials = this.inventory.currentSpecials
        int amountToDetractFromSum = 0
        currentSpecials.each { Special special ->
            switch (special) {
                case AmountSpecial:
                    amountToDetractFromSum += checkAmountSpecial(special as AmountSpecial)
                    break
                default:
                    break
            }
        }

        return amountToDetractFromSum
    }

    private int checkAmountSpecial(AmountSpecial special) {
        int amountSaved = 0
        if(itemsRangUp.contains(special.itemName)) {
            def amountCheckedOut = itemsRangUp.count(special.itemName)
            if (amountCheckedOut > special.triggerAmount) {
                boolean isLimitExceeded = (special.limit != 0 && amountCheckedOut > special.limit)

                int timesSpecialCanBeUsed = isLimitExceeded ?
                        (special.limit / special.triggerAmount) as int :
                        (amountCheckedOut / special.triggerAmount) as int

                amountSaved = special.newPrice * timesSpecialCanBeUsed
            }
        }
        return amountSaved
    }
}
