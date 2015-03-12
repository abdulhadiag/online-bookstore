# css490-project
Implement a Bookstore using Servlets/JSP/Tomcat.

- JavaBeans for all tables
- Action classes (Servlets, DB representation, DB Interface)
- Complete JSPages:
  * BookListing
  * CustomerOverview
  * SearchResults
  * AdminOverview
  * CartOverview
  * Conf_UserSignup
  * Conf_BookSubmit [Upload of new book is working, adds authors as well]
  * Transaction
  * UpdateInvent
- I updated the SignUp.jsp to act as a simple example of how the data flows in that situation. You should be able to go to Simple Sign up from the home page, enter in account details, and then submit. This will create a new user in the DB. Obviously we need some more data validation, etc. but it is a quick demo. I also added more to the Database Test page linked from index.jsp. This just shows some examples of how our DAO objects interact with the database. My hope was to build out enough so that you guys could follow the code and see how data flows in case you have time to build out more functionality. See you when I get back!
- Model Classes:
  * User - Details of a user [DONE]
  * Book - A book with its data and a list of authors, genres, and reviews. Includes publisher. [DONE]
  * Author - Details about an author and a list of their works. [DONE]
  * Transaction - Details about a transaction. [DONE -Abdul]
  * Genre - Simple genre bean. [DONE -Aaron]
  * Review - A rating and written review for a specific user/isbn combo. [DONE -Aaron]
  * Cart - Represents the contents of a cart [DONE]
  * LineItem - One book in a cart/transaction, with quantity [DONE] 
- Database Access Objects (DAO)
  * UserDB [DONE]
  * BookDB [Pretty far along. We should probably add more functionality for interacting with other classes]
  * AuthorDB [DONE -Abdul]
  * GenreDB [DONE -Aaron]
  * TransactionDB [DONE -Abdul]
  * ReviewDB [DONE -Aaron]
  * LineItemDB [DONE]
- TODO
  * Review functionality. Post new, and view existing
  * Reports - Reports.jsp
  * Update inventory amounts?
  * Presentation/Report/Documentation
