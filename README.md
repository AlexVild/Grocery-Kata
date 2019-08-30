## What have we here?

This consists of just the class files responsible for the completion programming exercise, and
the tests that verify this completion.

To run the tests, simply clone this project and run:

`mvn clean install` 

To compile and build the project and its target files. To run the test suite, run

`mvn test`

## Why Like This Though
The way I designed this was as if someone was going to create their own GUI and be able to use my inventory
management system to add items to a grocery store's inventory. The `inventory` object contains the functions
that can be hooked into to manage the store's actual stock of items, the stores ongoing specials, and
manage prices of the items in stock.

The `GroceryScanner` object exists for the cashier - it is what they would use to perform ring out
operations when a customer is going through their aisle. It allows them to do things such as ring
items, output the price (with regard to specials and markdowns), weigh items, and remove items from
check out.

## Neat

Email me if you run into any problems getting it to build!

ajv2324@gmail.com