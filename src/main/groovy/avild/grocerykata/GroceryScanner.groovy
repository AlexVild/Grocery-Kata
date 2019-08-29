package avild.grocerykata

import avild.grocerykata.specials.AmountSpecial
import avild.grocerykata.specials.EqualOrLesserSpecial
import avild.grocerykata.specials.PercentageSpecial
import avild.grocerykata.specials.Special

// This class allows a clerk to ring up items for a customer who's checking out
class GroceryScanner {
    Inventory inventory = new Inventory()
    int sum = 0
    ArrayList<CheckedOutItem> itemsRangUp = new ArrayList<CheckedOutItem>() // stack - head of list should be last item rang up

    boolean isItemRangUp(String itemName) {
        return itemsRangUp.find { it.name == itemName }
    }

    int countTimesItemRangUp(String itemName) {
        return itemsRangUp.count {
            it.name == itemName
        }
    }

    // weight set by default to 1.0 so that calculation is fine with items not paid for by weight
    void ringItem(String itemName, float weight = 1.0, Integer markdownPrice = null) {
        GroceryItem queriedItem = inventory.queryForItem(itemName)
        int priceOfItem = markdownPrice ? markdownPrice : queriedItem.price

        CheckedOutItem checkedOutItem = new CheckedOutItem(name: itemName, weight: weight)
        itemsRangUp.push(checkedOutItem)
        sum += (priceOfItem.toFloat() * weight).toInteger()
    }

    void removeLastScannedItem() {
        String itemToRemove = itemsRangUp.head().name
        int priceOfItem = inventory.queryForItem(itemToRemove).price

        itemsRangUp.pop() // remove the item from our rang up list
        sum -= priceOfItem
    }

    void removeItem(String item) {
        int priceOfItem = inventory.queryForItem(item).price
        int indexToRemove = itemsRangUp.findIndexOf { it.name == item }
        itemsRangUp.removeAt(indexToRemove)
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
                case EqualOrLesserSpecial:
                    amountToDetractFromSum += checkEqualOrLesserSpecial(special as EqualOrLesserSpecial)
                    break
                default:
                    break
            }
        }

        return amountToDetractFromSum
    }

    private int checkPercentSpecial(PercentageSpecial special) {
        int amountSaved = 0
        if(isItemRangUp(special.itemName)) {
            def amountCheckedOut = countTimesItemRangUp(special.itemName)
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
        if(isItemRangUp(special.itemName)) {
            def amountCheckedOut = countTimesItemRangUp(special.itemName)
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

    private int checkEqualOrLesserSpecial(EqualOrLesserSpecial special) {
        int amountSaved = 0
        if(isItemRangUp(special.itemName)) {
            GroceryItem item = inventory.queryForItem(special.itemName)

            float totalWeightCheckedOut = itemsRangUp
                .findAll { it.name.equalsIgnoreCase(special.itemName) }
                .inject(0.0) { result, it -> result + it.weight } as float
            float currentWeight = totalWeightCheckedOut // this will be used as a decrementing value to determine when the special has been used up

            while (currentWeight >= special.triggerWeight) {
                currentWeight -= special.triggerWeight
                if(currentWeight >= special.triggerWeight) {
                    amountSaved = item.price * special.triggerWeight * special.percentOff as int
                    currentWeight -= special.triggerWeight
                } else {
                    amountSaved = item.price * currentWeight * special.percentOff as int
                    currentWeight -= special.triggerWeight
                }
            }
        }
        return amountSaved
    }
}
