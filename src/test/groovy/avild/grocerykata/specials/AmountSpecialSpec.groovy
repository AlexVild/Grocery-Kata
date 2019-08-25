package avild.grocerykata.specials

import spock.lang.Specification

class AmountSpecialSpec extends Specification {
    def "AmountSpecial() constructor populates defaults"() {
        when:
        AmountSpecial special = new AmountSpecial(itemName: "chips", triggerAmount: 3, newPrice: 5)

        then:
        special.itemName == "chips"
        special.triggerAmount == 3
        special.newPrice == 5
        special.limit == 0
    }
}
