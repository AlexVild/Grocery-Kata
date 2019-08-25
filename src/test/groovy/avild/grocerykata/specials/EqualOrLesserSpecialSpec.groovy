package avild.grocerykata.specials

import spock.lang.Specification

class EqualOrLesserSpecialSpec extends Specification {
    def "EqualOrLesserSpecial() constructor populates defaults"() {
        when:
        EqualOrLesserSpecial special = new EqualOrLesserSpecial(itemName: "meat", triggerWeight: 2.0, percentOff: 0.5)

        then:
        special.itemName == "meat"
        special.triggerWeight == 2.0
        special.percentOff == 0.5
        special.limit == 0
    }
}
