package avild.grocerykata.specials

import spock.lang.Specification

class PercentageSpecialSpec extends Specification {
    def "PercentageSpecial() constructor populates fields"() {
        when:
        PercentageSpecial special = new PercentageSpecial("chips", 1, 1.0, 0)

        then:
        special.itemName == "chips"
        special.percentOff == 1.0
        special.specialAmount == 1
        special.limit == 0
    }
}
