package avild.grocerykata

class GroceryItem {
    String name
    int price
    int markdownPrice
    float weight

    GroceryItem(String name, int price, int markdownPrice, float weight) {
        this.name = name
        this.price = price
        this.markdownPrice = markdownPrice
        this.weight = weight
    }
}
