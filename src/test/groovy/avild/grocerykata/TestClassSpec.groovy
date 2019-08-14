package avild.grocerykata

import spock.lang.Specification

class TestClassSpec extends Specification{
    def "testing tests"() {
        given:
        int x = 2
        int expected = 2

        when:
        int actual = TestClass.testMethod(x)

        then:
        actual == expected
    }
}
