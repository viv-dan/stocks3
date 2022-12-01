package stocksmodel;

import java.util.Map;

/**
 * The interface extends Investor interface and defines additional methods that are needed to
 * support flexible portfolios.
 */
public interface InvestorExtension extends Investor {

  /**
   * The method creates a flexible portfolio in which buying and selling stocks at a later is done.
   *
   * @param name the name of the portfolio that is to be created
   * @throws RuntimeException is thrown when there is already a portfolio of the name that is passed
   *          already exists or if it is not able to persist the portfolio onto physical storage
   */
  void createFlexiblePortfolio(String name) throws RuntimeException;

  /**
   * The method does a transaction of sell in the given portfolio based on the parameters passed.
   * UPDATE: the quantity data type now has to be double as the user now can sell fractional shares
   *
   * @param portfolio the name of the portfolio in which the sell transaction has to be done
   * @param commissionFeeValue the commission fee which is included for every transaction of sell
   * @param date the date on which the sell transaction has to be performed
   * @param ticker the ticker symbol of the stock on which the sell transaction has to be performed
   * @param quantity the quantity of the stock to be sold
   * @throws RuntimeException is thrown when the portfolio doesn't exist or the stock market is
   *          closed on the particular date that is entered. Exception is also thrown when the
   *          stock quantity is less or not even bought till the date in the portfolio
   */
  void createSellTransaction(String portfolio, double commissionFeeValue, String date,
                             String ticker, Double quantity) throws RuntimeException;

  /**
   * The method does a transaction of buy in the given portfolio based on the parameters passed.
   * UPDATE: the quantity data type now has to be double to facilitate the dollar-cost averaging
   *
   * @param portfolio the name of the portfolio in which the buy transaction has to be done
   * @param commissionFeeValue the commission fee which is included for every transaction of buy
   * @param date the date on which the buy transaction has to be performed
   * @param ticker the ticker symbol of the stock that which has to be bought
   * @param quantity the quantity of the stock to buy
   * @throws RuntimeException is thrown when the portfolio doesn't exist or the stock market is
   *          closed on the particular date that is entered.
   */
  void createBuyTransaction(String portfolio, double commissionFeeValue, String date,
                            String ticker, Double quantity) throws IllegalArgumentException;

  /**
   * The method returns the cost basis(total investment) of the portfolio until the desired date.
   *
   * @param portfolioName the name of the portfolio that the cost basis has to be returned
   * @param date the date until which the cost basis has to be returned
   * @return returns the cost basis of the portfolio in the form of a double
   * @throws IllegalArgumentException is thrown when the portfolio doesn't exist or if it is empty
   */
  Double getCostBasis(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * The method returns the stock composition of the flexible portfolio until the desired date.
   * UPDATE: The return type of the method has been changed from Map(String, Integer) to
   * Map(String, Double) as it has to return the fractional stock quantities.
   *
   * @param name the name of the portfolio that the composition has to be returned
   * @param date the date until which the composition has to be returned
   * @return returns a map of stocks and its respective quantities in the form of a map
   * @throws RuntimeException is thrown when the portfolio doesn't exist
   */
  Map<String, Double> loadFlexiblePortfolio(String name, String date) throws RuntimeException;

}
