# My Personal Project

- What will the application do?

The application will take orders of drinks.

- Who will use it? 

The café shop owner.

- Why is this project of interest to you?

I think this application can be used very often throughout out daily life.
But I've never considered the way of how it works, that is why I would like to take this chance and create one of my own.

## User Stories
 
- As a user, I want to be able to add order into the order list / drink list.
- As a user, I want to be able to view the list of order in the order list, and also the drink list in the order.
- As a user, I want to be able to mark order that could be delivered (when the bill> 20).
- As a user, I want to be able to remove order from the order list.
- As a user, I want to be able to see the number of orders that are on the waitlist and orders that have been done.
- As a user, I want to be able to modify order, drinks in the order
- As a user, I want to be able to modify address.
- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to be able to load my to-do list from file


## Instructions for Grader
- You can generate the first required event related to adding a drink to a drinkList by clicking the button labelled as 
"Enter" after you chose the drink and size
- You can generate the second required event related to adding drink to a drinkList is that
you can check the drinkList after you add the order by clicking the Jlist on the right-hand side.
When you click a specific order on the list, user will be able to view the drink list in that order.
- You can locate my visual component by my background
- You can save the state of my application by type in date in the Textfield, and click the button labelled as "Save"
- You can reload the state of my application by type in date in the Textfield, and click the button labelled as "Read"

## Phase 4 : Task 2

Mon Nov 21 11:26:25 PST 2022

Order 0 is created

**Add the drink to the order:**

 Mon Nov 21 11:26:25 PST 2022
5COLD_BREW - $5.75 is added to Order 0

Mon Nov 21 11:26:25 PST 2022
The Price is updated to 7.75

**When the total price for the order >= 20:**

 Mon Nov 21 11:26:25 PST 2022
Order 0 is now valid for delivery

**Add the order to the waitingList:**

 Mon Nov 21 11:26:25 PST 2022
Order 0 is added to the waitingList

**Remove the order from the waitingList:**

 Mon Nov 21 11:43:02 PST 2022
Order 6 is removed from the waitingList

**Load orders from Json:**

 Mon Nov 21 11:37:32 PST 2022
Order 0 is loaded from the JsonFile

**Set order to done:**

 Mon Nov 21 11:37:32 PST 2022
Order 0 is done

**Save to Json:**

 Mon Nov 21 11:37:38 PST 2022
Today's orders are saved as JsonFile

## Phase 4 : Task 3

I would refactor TakeOrder and TakeOrderGUI. 

After we learned the composite pattern, it makes much more sense
if I have an abstract class that TakeOrder and TakeOrderGUI could both extend.

These two classes are extremely similar, the differences are some little parts of
implementation, but they still have a lot of common that will definitely make things easier 
if they have a superclass. 
Also, if they have the same superType, I wouldn't
need two different reader and writer classes. I could just take the superType as parameter.


**bold** and *italic* 