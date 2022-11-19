package stocksmodel;

interface StockAPI {

  /**
   * The method helps validate a stock ticker whether it's a valid stock that is traded in the stock
   * market or not.
   *
   * @param ticker the stock symbol
   * @return true if the stock symbol is valid. False otherwise
   * @throws RuntimeException when the json data cannot be parsed in a right way
   */
  boolean validTicker(String ticker) throws RuntimeException;

  /**
   * The method returns the closing value of the given ticker on a particular date.
   *
   * @param ticker the stock symbol for which the value has to be returned
   * @param date the date on which the value has to be returned
   * @return the double value of the closing price of the stock
   * @throws RuntimeException when the stock data cannot be retrieved on that particular date
   */
  Double getStockClosingByDate(String ticker, String date) throws RuntimeException;
}
