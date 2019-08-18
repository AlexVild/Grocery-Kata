package avild.grocerykata

class GroceryItem {
    String name
    int price
    int markdownPrice
    long weight

    GroceryItem(String name, int price, int markdownPrice, long weight) {
        this.name = name
        this.price = price
        this.markdownPrice = markdownPrice
        this.weight = weight
    }
}
