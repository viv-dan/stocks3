DESIGN OF OUR PROJECT

We have designed our project in a Model � View � Controller Design Pattern. For supporting flexible portfolios, adapter design pattern has been used where the old implementation's changes are very minimal. Three packages have been made as:

1) stocksmodel
2) StockController
3) stocksview

stocksmodel:

Five Interfaces have been created. They are:
a. Investor
b. Portfolio
c. Stock
d. InvestorExtension
e. StockAPI

	Stock Model:
	
	The stock model Interface represents a stock which has been implemented by the StockImpl class. The StockImpl object has a private ticker symbol associated with it and a value based on a certain date. A builder class has also been created inside of the StockImpl model which gives the client a less confusing way to create stockmodel object without having pass all the objects in one go. Instead the client can get the builder and call certain methods to associate ticker symbol to a stock. There are two methods defined in the Stock model interface. They are getSymbol() to get the ticker symbol of the stock and getStockValue(String date) which takes in a date as a string parameter to the stock value on that date. This data is being read from {ticker}.rtf files which should be in the same directory as the .jar file where the {ticker} indicates its own symbol. The Date is scanned in this file and the closing value associated with it is returned.

	Portfolio Model:

	The portfolio model Interface represents a portfolio which has been implemented by a PortfolioImpl class. The PortfolioImpl object has a private name and stock composition(stocks and their quantities) as map. A builder class has also been created inside of the PortfolioImpl model which gives the client a less confusing way to create Portfolio model object without having pass all the objects in one go. This is similar to the stockImpl model class builder. The client can pass the name of the portfolio and the composition as map to the builder and it returns a portfolio model object. The portfolio interface has methods defined as getPortfolioName() which returns the name of the portfolio as a string value and a getStockComposition() portfolio as a map of stocks and their respective quantities. The client is returned a copy of the stocks and quantities map so that even if the client changes something in it, the original object will not change.

	Investor Model:

	The InvestorModel interface represents a model which defines methods for getting data such as creating, retrieving portfolios etc.., The investorModel has been implemented by a class called InvestorImpl. The filename from which the data is retrieved and stored is kept as a private static class variable. The supported stocks in our program are stored in a string array and when the client requests the stocks list, a clone of the object is returned to preserve the original object. A cache system has also been implemented in which there is an ArrayList of portfolios. Every time the user requests to load a portfolio it is read from the file and stored in this arraylist. If the client requests it again the retrieve of the portfolio is done from this array list. The methods that Investor Model interface has are loadAllPortolfioNames() which list the portfolios that are stored in a default file, createPortfolio(String name, Map<String, Integer>) which takes in a portfolio name as a string and a map of stock names as strings and their respective quanitities, loadPortfolio(String name) which retrieves the portolfio from the storage based on the name passed to it, changeReadFile(String filename) which changes the storage from which the program reads portfolios and stocks data, getStocks() which returns the supported stocks by our program, getPortfolioValuation(String name, String date) which returns the total valuation of the portfolio based on the name of the portfolio given and the date on which the portfolio value is required. For each stock in the portfolio the getStockvalue() of the stock is called and multiplied with the quantity associated with it in the map. The total value of all the stocks is returned as a double value.

    InvestorExtension Model:

    The InvestorExtension interface represents a main model with which now the controller communicates with for getting the data such as creating, retrieving, performing transactions, costbasis etc.., This model extends from the InvestorModel and adds some additional methods of createFlexiblePortfolio() for creating a flexible portfolio that can be modified later. It also has methods like createBuyTransaction()/createSellTransaction() for buying or selling stocks in a particular flexible portfolio. getCostBasis() method has also been added for getting the total amount of money invested in the flexible portfolio until a desired date. loadFlexiblePortfolio() method has been added to return the composition of the flexible portfolio until a desired date. This is implemented by InvestorExtensionImpl class which creates a delegate object which represents a InvestorImpl class so as to make use of the previous methods that are already defined. A Cache of flexible portfolios is also created. The flexibile portfolios are being read from a json file. The ChangeReadFile() takes a string and scans it for whether it is a xml or json file. If .xml file is provided the inflexible portfolios storage is changed to the provided one. If .json file is provided the flexible portfolios storage is changes to the provided one.
    The methods LoadAllPortfolioNames() and getPortfolioValuation() have been extended and to support loading of all flexible portfolios and also get their values until that certain date so as to include the changes of the flexible portfolios.

    This investorExtensionImpl acts as a gate keeper to all the models in the model package. It communicates with all the other models that are in the model package. The controller can communicate with this class and no other model classes to get the data it requires.

    StockAPI Model:

    The StockAPI interface represents an API object with which the model gets the data such as closing value of a particular stock and also validating a particular stock. It has been implemented by the StockAPIImpl class. It defines methods like validTicker() which validates a given ticker and getStockClosingByDate() which returns the closing value of the stock on a particular date.

    If we have to add support for some other API the new class can implement the StockAPI interface and have its own implementations. The object of the StockAPI is being used by the Stock object class as it has a static object of type stockAPI. Thus, by doing this it achieves low coupling.


stockscontroller:
	
	One interface has been created in the stockscontroller package.

	Controller:
		
		This interface represents a controller object which has been implemented by the controllerImpl class. It has only one public method called startProgram() which takes in an investorExtension model and a menu (view) object. It only communicates with these two classes to get the data for various operations. It processes the inputs entered by the user and communicates with the investor model object to get various data and it tells the view to display required messages. A scanner class object is created for scanning the inputs entered by the user. The scanner object takes in an input stream such as System.in or a ByteArrayInputStream() depending on the method that the startProgram method is called and the parameters that are passed. It also tells the view to take in an output stream to which the view object writes to.

stocksview:
	
	One interface has been created in the stocksview package.
	
	Menu:
		
		This interface represents a view object which has been implemented by the MenuImpl class. There are various methods which the controller can call the view to write to the output stream. Certain data like portfolio stocks quantities, total value are being given to the menu object by the controller. SetStream(PrintStream out) method in the menu is being used by the controller to set the output stream of the view. Various methods like showMessage(String message), showSuccessMessage(), goBackMessage(), showInputError() are created to show certain predefined messages. showAllPortfolioNames(List<String> l) writes the list of strings in the list to the output stream. showMenu() is the home of the menu object. This method contains the list of options the user can choose from to perform certain operations.  


DESIGN-CHANGES:

MODEL:

1) In the investor interface we have removed the getStocks() method from the previous assignment as now with the integration of the API all the stocks are being supported.
Following the adapter design patter InvestorExtension has been created to use the old implementations and make changes required for the flexible portfolios and also additional methods for the flexible portfolios that have been documented above.

CONTROLLER:

1) Additional switch cases have been added to support buy/sell of stocks in a portfolio, creating a flexible portfolio, getting a costbasis of a portfolio and for displaying the performance of a portfolio based on time range given. No design change has been made in the method signature of the controller but just additional switch cases in the startProgram() method of the controller.

MENU:

1) Two additional methods have been included in the Menu interface to facilitate the plotting of the performance of a portfolio and also for displaying the cost basis of a particular flexible portfolio.

