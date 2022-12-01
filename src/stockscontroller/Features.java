package stockscontroller;

import java.util.ArrayList;
import java.util.Map;
import stocksview.GraphicalView;

/**
 * The features interfaces specifies the features of the application.
 */
public interface Features {
  /**
   * The method retrieves the list of portfolios from the model for the view to display.
   */
  void showPortfolios();

  /**
   * The set view method is used to set up a graphical view.
   *
   * @param g it takes in a graphical view object
   */
  void setView(GraphicalView g);

  /**
   * The method retrieves an Inflexible Portfolio from the model and passes it to the view
   * for it show.
   */
  void showParticularPortfolio();

  /**
   * The method returns the value of a portfolio from the model for the view to display.
   *
   * @param portfolio the array list consists of the name and date of the portfolio
   */
  void totalPortfolioValue(ArrayList<String> portfolio);

  /**
   * The method shows a flexible portfolio from the model for the view to display.
   *
   * @param portfolio the array list consists of the name and date of the portfolio.
   */
  void showFlexiblePortfolio(ArrayList<String> portfolio);

  /**
   * The method retrieves the cost basis of a particular portfolio.
   *
   * @param portfolio the array list consist of name and date of the portfolio.
   */
  void getCostBasis(ArrayList<String> portfolio);

  /**
   * The method performs a buy transaction on an existing flexible portfolio.
   *
   * @param values the array list consist of the portfolio name,date,ticker,quantity
   *               and commission fees.
   */
  void performBuy(ArrayList<String> values);

  /**
   * The method performs a sell transaction on an existing flexible portfolio.
   *
   * @param values the array list consist of the portfolio name,date,ticker,quantity
   *               and commission fees.
   */
  void performSell(ArrayList<String> values);

  /**
   * The method creates a flexible portfolio.
   */
  void createFlexiblePortfolio();

  /**
   * The method is used to retrieve the values to be plotted for the graph for appropriate dates.
   *
   * @param values the array list consists of name,start date and end date.
   */
  void plotGraph(ArrayList<String> values);

  /**
   * The method invests a fixed amount in an existing flexible portfolio on a particular
   * date.
   *
   * @param formData      the weight composition of stocks
   * @param name          the name of the portfolio
   * @param date          the date of investment
   * @param commissionFee commission fee associated with the transaction
   * @param amount        the total investment amount
   */
  void investFixedAmount(Map<String, Double> formData, String name,
                         String date, Double commissionFee, Double amount);

  /**
   * The methods performs a high level investing strategy on an existing flexible portfolio.
   *
   * @param formData         the weight composition of the stocks.
   * @param portfolioName    the name of the portfolio.
   * @param amount           the amount on investment
   * @param startDate        the start date of the investment
   * @param endDate          end date of the investment
   * @param commissionAmount the commission fees associated with the strategy
   * @param recurrence       days on which the recurrent investments will occur
   */
  void dollarAverageInvesting(Map<String, Double> formData, String portfolioName,
                              Double amount, String startDate, String endDate,
                              Double commissionAmount, String recurrence);

  /**
   * The method changes the file which stores the portfolios of an investor.
   *
   * @param name the name of the file
   */
  void changeFile(String name);
}
