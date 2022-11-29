package stocksview;

import java.util.List;
import java.util.Map;

public interface View {
  /**
   * The method displays the options that user can choose from, like create portfolio, load
   * portfolio, show all the portfolios, load portfolio from a file with respective integers
   * to choose from.
   */
  void showMenu();
  /**
   * The method displays the stock tickers and their quantities that are passed in as a map of keys
   * as stock tickers and values as quantity of the particular stock.
   *
   * @param stocks the map where it the stock tickers and its quantities
   */
  void showStocks(Map<String, Double> stocks);
  /**
   * The method displays all portfolio names of the user.
   *
   * @param names the list containing of all portfolio names
   */
  void showAllPortfolioNames(List<String> names);

  /**
   * The method displays the total value of the portfolio that is passed as a double in a readable
   * format by also specifying the date on which the value is retrieved.
   *
   * @param value the double value that is passed as the portfolio value
   * @param date  the date on which the portfolio value is generated
   */
  void showValueOfPortfolio(double value, String date);
  /**
   * The method displays the double value saying that the is the cost on the particular date
   * that is passed to it.
   *
   * @param value the cost that is passed to display it
   * @param date  the date on which the value is generated
   */
  void showCostOfPortfolio(double value, String date);
  /**
   * The method displays a success message to indicate a successful operation.
   */
  void successMessage();

  /**
   * The method plots the graph specifying the value attached to a particular time state.
   *
   * @param cost the map containing the value attached to the specific time state
   */
  void plot(Map<String, Double> cost);
}
