package avild.grocerykata.specials

import spock.lang.Specification

class AmountSpecialSpec extends Specification {
    def "AmountSpecial() constructor populates fields"() {
        when:
        AmountSpecial special = new AmountSpecial("chips", 3, 5, 6)

        then:
        special.itemName == "chips"
        special.triggerAmount == 3
        special.newPrice == 5
        special.limit == 6
    }
}
