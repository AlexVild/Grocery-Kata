package avild.grocerykata.specials

import spock.lang.Specification

class PercentageSpecialSpec extends Specification {
    def "PercentageSpecial() constructor populates defaults"() {
        when:
        PercentageSpecial special = new PercentageSpecial(itemName: "chips", percentOff: 1.0, specialAmount: 1, triggerAmount: 1)

        then:
        special.itemName == "chips"
        special.percentOff == 1.0 as float
        special.specialAmount == 1
        special.triggerAmount == 1
        special.limit == 0
    }
}
