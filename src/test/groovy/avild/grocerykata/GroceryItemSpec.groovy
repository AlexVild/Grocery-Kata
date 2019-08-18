package avild.grocerykata

import spock.lang.Specification

class GroceryItemSpec extends Specification {
    def "constructor for Grocery Item correctly creates a new Grocery Item"() {
        when:
        GroceryItem item = new GroceryItem("apple", 299, 0, 1.5)

        then:
        item.name == "apple"
        item.price == 299
        item.markdownPrice == 0
        item.weight == 1.5
    }
}
