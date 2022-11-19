package stocksmodel;

/**
 * An interface that specifies the methods of a stock in order to be able to persist and retrieve at
 * any certain time.
 */
interface Stock {

  /**
   * The method returns the ticker symbol of a stock in the form of a string value.
   *
   * @return the ticker symbol of a stock in the form of a string value
   */
  String getSymbol();

  /**
   * The method returns its stock value on a particular date passed as an argument in the form of a
   * double.
   *
   * @param date the date on which the stock's value has to be returned
   * @return the double value of the stock.
   * @throws RuntimeException when the stock API connection fails to get the value of the stock
   */
  Double getStockValue(String date) throws RuntimeException;

}
