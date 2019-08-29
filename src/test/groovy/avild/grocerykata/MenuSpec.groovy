package avild.grocerykata

import spock.lang.Specification

class MenuSpec extends Specification {

    def "displays a menu"() {
        given:
        Menu menu = new Menu()
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)

        when:
        menu.displayMenu()

        then:
        buffer.toString().contains("hey")
    }
}
