package avild.grocerykata.specials

class AmountSpecial extends Special {
    public int triggerAmount
    public int newPrice
    public int limit

    AmountSpecial(String itemName, int triggerAmount, int newPrice, int limit = 0) {
        this.itemName = itemName
        this.triggerAmount = triggerAmount
        this.newPrice = newPrice
        this.limit = limit
    }
}
