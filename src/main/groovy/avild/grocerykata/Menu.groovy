package avild.grocerykata

class Menu {
    static void displayMainMenu() {
        print (
                '''
                Main Menu:/n
                [1] Modify inventory\n
                [2] Check customer out\n
                [3] Quit\n
                \n
                Enter [num]: 
                '''
        )
    }

    static void displayInventoryMenu() {
        print (
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
                '''
        )
    }

    static void displaySpecialMenu() {
        print (
                '''
                Main Menu > Inventory Menu > Special Management:/n
                [1] Add Amount Special (n for m at $x)\n
                [2] Add Percent Special (buy n get m at x%)\n
                [3] Add Equal or Lesser Value Special (buy m pounds, get equal or lesser for x%)\n
                [4] Remove Special\n
                [5] Inventory Management\n
                \n
                Enter [num]: 
                '''
        )
    }

}
