package avild.grocerykata.specials

class PercentageSpecial {
    public String itemName
    public int specialAmount
    public float percentOff
    public int limit

    PercentageSpecial(String itemName, int specialAmount, float percentOff, int limit = 0) {
        this.itemName = itemName
        this.specialAmount = specialAmount
        this.percentOff = percentOff
        this.limit = limit
    }
}
