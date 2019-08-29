package avild.grocerykata

import spock.lang.Specification

class CheckedOutItemSpec extends Specification {
    def "checkedOutItem defaults are set correctly" () {
        when:
        CheckedOutItem item = new CheckedOutItem(name: "apple")

        then:
        item.name == "apple"
        item.weight == 1.0 as float
    }
}
