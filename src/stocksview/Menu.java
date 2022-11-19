package stocksview;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * The interface specifies the features of Menu view for text based program.
 *
 * <p>UPDATES :
 * Added new method showCostOfPortfolio which shows cost basis for a flexible portfolio.
 * Added new method plot which handles the plotting of the graph
 * when a map of dates and values is passed.
 * Removed show list of stocks methods, since now we are extending the design to many stocks,
 * thus we don't need to show the list of all valid tickers as the list is extensive.</p>
 */
public interface Menu {

  /**
   * The method displays the options that user can choose from, like create portfolio, load
   * portfolio, show all the portfolios, load portfolio from a file with respective integers
   * to choose from.
   */
  void showMenu();

  /**
   * The method displays an input error message when an invalid input is entered by the user.
   */
  void showInputError();

  /**
   * The method displays the message prompting the user to enter the portfolio name.
   */
  void showSinglePortfolioMenu();

  /**
   * The method displays all portfolio names of the user.
   *
   * @param names the list containing of all portfolio names
   */
  void showAllPortfolioNames(List<String> names);

  /**
   * The method displays the stock tickers and their quantities that are passed in as a map of keys
   * as stock tickers and values as quantity of the particular stock.
   *
   * @param stocks the map where it the stock tickers and its quantities
   */
  void showStocks(Map<String, Integer> stocks);


  /**
   * The method displays the error message when a particular portfolio is not found.
   */
  void showPortfolioNameError();

  /**
   * The method displays the total value of the portfolio that is passed as a double in a readable
   * format by also specifying the date on which the value is retrieved.
   *
   * @param value the double value that is passed as the portfolio value
   * @param date  the date on which the portfolio value is generated
   */
  void showValueOfPortfolio(double value, String date);

  /**
   * The method displays the message of prompting the user to enter to go back to the main menu.
   */
  void goBackMessage();

  /**
   * The method displays a success message to indicate a successful operation.
   */
  void successMessage();

  /**
   * The method sets the output stream of the view so as the view displays output to that stream.
   *
   * @param out PrintStream that the output should be returned
   */
  void setStream(PrintStream out);

  /**
   * The method displays the message that is passed as a string onto the output stream.
   *
   * @param s the message in the form of a string that is to be displayed
   */
  void showMessage(String s);

  /**
   * The method displays the double value saying that the is the cost on the particular date
   * that is passed to it.
   *
   * @param value the cost that is passed to display it
   * @param date the date on which the value is generated
   */
  void showCostOfPortfolio(double value, String date);

  /**
   * The method plots the graph specifying the value attached to a particular time state.
   *
   * @param cost the map containing the value attached to the specific time state
   */
  void plot(Map<String, Double> cost);
}
