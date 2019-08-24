package avild.grocerykata

class GroceryItem {
    String name
    int price
    int markdownPrice
    boolean pricedByWeight = false

    GroceryItem(String name, int price, int markdownPrice, boolean pricedByWeight) {
        this.name = name
        this.price = price
        this.markdownPrice = markdownPrice
        this.pricedByWeight = pricedByWeight
    }

    GroceryItem(String name, int price, int markdownPrice) {
        this.name = name
        this.price = price
        this.markdownPrice = markdownPrice
    }
}
