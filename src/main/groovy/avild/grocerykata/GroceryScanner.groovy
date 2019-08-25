package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.PercentageSpecial
import avild.grocerykata.specials.Special

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    Inventory inventory = new Inventory()
    int sum = 0
    ArrayList<String> itemsRangUp = new ArrayList<String>() // stack - head of list should be last item rang up

    // weight set by default to 1.0 so that calculation is fine with items not paid for by weight
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
                case PercentageSpecial:
                    amountToDetractFromSum += checkPercentSpecial(special as PercentageSpecial)
                    break
                case AmountSpecial:
                    amountToDetractFromSum += checkAmountSpecial(special as AmountSpecial)
                    break
                default:
                    break
            }
        }

        return amountToDetractFromSum
    }

    private int checkPercentSpecial(PercentageSpecial special) {
        int amountSaved = 0
        if(itemsRangUp.contains(special.itemName)) {
            def amountCheckedOut = itemsRangUp.count(special.itemName)
            if (amountCheckedOut >= special.triggerAmount) {
                int regularPriceOfItem = inventory.queryForItem(special.itemName).price
                boolean isLimitExceeded = (special.limit != 0 && amountCheckedOut > special.limit)

                def totalItemsAffected = isLimitExceeded ? special.limit : amountCheckedOut
                int salePrice = regularPriceOfItem * special.percentOff as int
                int itemsAtSalePrice = totalItemsAffected / (special.triggerAmount + special.specialAmount) as int

                amountSaved = itemsAtSalePrice * salePrice
            }
        }
        return amountSaved
    }

    private int checkAmountSpecial(AmountSpecial special) {
        int amountSaved = 0
        if(itemsRangUp.contains(special.itemName)) {
            def amountCheckedOut = itemsRangUp.count(special.itemName)
            if (amountCheckedOut >= special.triggerAmount) {
                int regularPriceOfItem = inventory.queryForItem(special.itemName).price
                boolean isLimitExceeded = (special.limit != 0 && amountCheckedOut > special.limit)
                def limit = isLimitExceeded ? special.limit : amountCheckedOut
                def itemsWithinSpecial = limit / special.triggerAmount
                def itemsNotWithinSpecial = isLimitExceeded ?
                        amountCheckedOut - limit :
                        limit % special.triggerAmount

                def newTotalPrice = (regularPriceOfItem * itemsNotWithinSpecial) + (special.newPrice * itemsWithinSpecial)
                amountSaved = (regularPriceOfItem * amountCheckedOut) - newTotalPrice
            }
        }
        return amountSaved
    }
}
