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
public interface Menu extends View {
  /**
   * The method displays an input error message when an invalid input is entered by the user.
   */
  void showInputError();
  /**
   * The method displays the message prompting the user to enter the portfolio name.
   */
  void showSinglePortfolioMenu();
  /**
   * The method displays the error message when a particular portfolio is not found.
   */
  void showPortfolioNameError();
  /**
   * The method displays the message of prompting the user to enter to go back to the main menu.
   */
  void goBackMessage();
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


}
