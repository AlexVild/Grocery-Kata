package avild.grocerykata

import spock.lang.Specification

class MenuSpec extends Specification {

    def "displays the main menu"() {
        given:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)

        when:
        Menu.displayMainMenu()

        then:
        buffer.toString().contains(
                '''
                Main Menu:/n
                [1] Modify inventory\n
                [2] Check customer out\n
                [3] Quit\n
                \n
                Enter [num]: 
                ''')
    }

    def "displays the inventory menu"() {
        given:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)

        when:
        Menu.displayInventoryMenu()

        then:
        buffer.toString().contains(
                '''
                Main Menu > Inventory Menu:/n
                [1] View Inventory\n
                [2] Add Item to Inventory\n
                [3] Update Item Price\n
                [4] Remove Item from Inventory\n
                [5] Manage Specials\n
                [6] Main Menu\n
                \n
                Enter [num]: 
                ''')
    }

    def "displays the special management menu"() {
        given:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)

        when:
        Menu.displaySpecialMenu()

        then:
        buffer.toString().contains(
                '''
                Main Menu > Inventory Menu > Special Management:/n
                [1] Add Amount Special (n for m at $x)\n
                [2] Add Percent Special (buy n get m at x%)\n
                [3] Add Equal or Lesser Value Special (buy m pounds, get equal or lesser for x%)\n
                [4] Remove Special\n
                [5] Inventory Management\n
                \n
                Enter [num]: 
                ''')
    }
}
