package avild.grocerykata

import spock.lang.Specification

class GroceryItemSpec extends Specification {
    def "constructor for Grocery Item correctly sets defaults"() {
        when:
        GroceryItem item = new GroceryItem(name: "apple", price: 299)

        then:
        item.name == "apple"
        item.price == 299
        item.markdownPrice == null
        !item.pricedByWeight
    }
}
