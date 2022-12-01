
TO RUN THE JAR FILE, FIRST ENSURE DEPENDENCIES

1)You will need to put files named Stocks.xml and stocks2.json  in the same folder as the jar file
    for it to run.Please use the same files as provided with name convention.
2) Then you will need to run the submitted jar file.

LIST OF THIRD PARTY LIBRARIES
- JSON SIMPLE
    The library is licensed under the  Apache License 2.0
    LINK FOR THE LICENSE - https://www.apache.org/licenses/LICENSE-2.0.txt
    SETUP for IntelliJ
        1:Open project Structure
        2: Go to Modules Tab on the right
        3: The src and test folder should be visible
        4: Next Select Dependencies it should show JDK
        5: Add new module by pressing the add button
        6: Select the jar in the res folder path - ./libraries/json-simple-1.1.1.jar
        7: The jar should be selected and added to your project now along with your JDK.


FURTHER INSTRUCTIONS TO CREATE INFLEXIBLE PORTFOLIO WITH 3 STOCKS

3)After running the program you will see Main Menu 10 options in it.
4) Select the create portfolio object by entering the value 3 from System.in
5) It will prompt the user to enter the portfolio name, give a name for example "investment portfolio".
6) It will prompt the user to enter the number of stocks you want to add to the portfolio.
7) Enter 3 ,since you want to add 3 stocks
8) Then you can enter a valid stock ticker
9) Then you have to enter the quantity of that ticker you want to add.
10) Follow steps 8 ,9 for 2 more times
11)After creation it will prompt with Done Succesfully.
12)Press the Enter key to go back to the main menu.
13)If invalid input is entered it will show invalid input message and prompt to press
    enter, and it will take you back to main menu.

Invalid inputs include invalid tickers, which don't represent any stocks, invalid quantities.

The program supports for a reasonable integer quantity of stocks, since in the real world scenario,users don't have big number quantity of stocks.
If invalid input is given you will need to repeat steps 4-11, for creating another portfolio repeat steps
4-11 , make sure to give a new portfolio name and enter a valid integer.


FURTHER INSTRUCTIONS TO CREATE FLEXIBLE PORTFOLIO WITH 3 STOCKS

The program is designed in a way such that you need to name a
    flexible portfolio then make buy transactions.
1)Select option 6 Create Flexible Portfolio in the MENU
2)Give a name for a flexible portfolio like "another portfolio",
    make sure the name is unique and is not present as a flexible or inflexible portfolio name.
3)After this a portfolio will be succesfully created.
4)To add 3 stocks you will need to select option 7.Buy or Sell a stock in Flexible Portfolio
5)Select 1 for a buy,after that you will be asked to enter the name of the portfolio,
    we named the portfolio as "another portfolio" we enter that.
6)Next step is to enter a valid ticker, this will be the first stock you are adding to the portfolio.
7) Next enter the quantity you want to buy.
8)Enter the commission fee you paid for this transaction.
9)Enter the date of the buy, when you bought the stock, make sure the date is a valid working day for that ticker.
10)If all values are valid it will show a success message.
11)Next it will take you back to the main menu.
12)Repeat steps 4-11 for 2 more times.

Invalid input error will be shown if the date isn't in the correct format,or it's a non-working date or
    the date is a date when that ticker wasn't even listed and if the ticker isn't a valid ticker.



TO QUERY THE PORTFOLIO FOR A PARTICULAR DATE

1)Choose option 4 by entering 4
2)It will prompt the user to enter the portfolio name, give an existing portfolio name.
3)Then it will ask you to enter date in YYYY-MM-DD format like 2022-02-14, make sure to give the date in the correct format.
4)Press enter , it will show the valuation of the portfolio you queried for.

In case an invalid input is given like non-existing portfolio,
 or invalid date it will prompt the message and take you back to the main menu.

IMP: For any dates where it was a non-business day/non-working day like weekends the output will be stock data can't be found.

THE OTHER MENU OPTIONS

1)Show all portfolios
This shows all the names of the portfolios present of the investor.

2)Show a particular portfolio composition
It will ask the options whether you want to show a flexible or an inflexible portfolio.
For inflexible portfolio the program prompts the user to
    enter the name of the portfolio of which they want to see the composition.
For flexible portfolio it will further ask an input date on when you want to see the composition.
This option displays the stocks and the quantity present in a portfolio.
Invalid names and Invalid date format will show an error message.
After entering a valid portfolio name it will display, the stocks and their respective quantities in that portfolio.

3) Create portfolio
This option creates only an inflexible portfolio *IMP*
To create an inflexible portfolio as per the steps given above

4)Show a particular portfolio value
This displays the total value of a portfolio on a given date, as steps given above.

5)Load portfolios form a file
The user can change the file in which he has given his portfolios.
If the user wishes to change his flexible portfolios then he should change only the .json file.
If the user wishes to change his inflexible portfolios then he should change only the .xml file.
The file needs to be put in the same directory as the jar file before running.
The file should have the correct format, and it should be in either .xml format or .json for it to switch successfully.
After entering 5 option, the user will be prompted to enter the file name,then
    the user needs to enter just the file name with .xml(like Stocks.xml ) or .json(like stocks2.json)
     extension in the program to switch the files.
The file needs to have the correct structure inside as given in the other .xml file for it to work correctly.
The same rule as above applies for the .json file.
If invalid files or incorrect formats have been passed it will show an error message.


6)Create Flexible Portfolio
As shown above, follow the steps to create a flexible portfolio.

7)Buy or Sell a stock in Flexible Portfolio
The buy or sell a stock option is valid only for flexible portfolios.
After choosing the 7 option it will ask the user whether the user wants to buy or sell a stock.
Enter 1 or 2 according to what you want to do.
After that it will ask you to enter the name of the portfolio where you want to perform the transaction.
Post that enter the stock ticker,quantity,date of the transaction and the commission the user paid for that
    transaction,if the transaction is a success, success message will be prompted on the screen.
Invalid inputs in general include invalid ticker,date,quantity,commission fees,invalid name of portfolio.
For the sell option in particular, a sell can't occur if the quantity is insufficient
     in the portfolio currently or on the date of the transaction. Also without a buy of that ticker a sell can't happen.



8)Get cost basis of a flexible portfolio
The option computes the cost basis of a flexible portfolio only.
The cost basis needs the user to enter the date and the name of the portfolio.
The cost basis includes the cost of buying the stock and the commission fees the user has paid so far for
    all transactions which include buy and sell.

9)Graph
The option plots the graph of a portfolio from a particular date to a particular date.
The start date is the beginning date and the end date is the end date.
The graph plots the dates and the number of stars next to it.
The graph is plot in relative to a base number and each star indicates another number.
So the total value in the graph for a timestamp = relative amount + noOfStars*(one star value).
The graph will have time stamps as dates in all cases.
When the graph is plotted, if it's a daily graph, it will show all dates as timestamps from the beginning till end date.
If it's a monthly or above graph it will show the end of that month date as time stamps and add years.

If range includes a date nearby, for monthly and all ranges above it will also add the last date in the range.

Invalid inputs include, future dates, start date greater than or equal to end date.






