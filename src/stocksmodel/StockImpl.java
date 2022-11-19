package stocksmodel;

/**
 * The class implements the Stock interface and its methods to be able to persist and retrive a
 * stock at any time.
 */
class StockImpl implements Stock {

  private final String ticker;
  private static StockAPI stockAPI;

  private StockImpl(String ticker) {
    stockAPI = new StockAPIImpl();
    if (!stockAPI.validTicker(ticker)) {
      throw new IllegalArgumentException("Invalid ticker was given!!");
    }
    this.ticker = ticker;
  }

  /**
   * The method returns a builder to be able to create a Stock Object by passing required ticker
   * symbol.
   *
   * @return the stock object after building it
   */
  public static StockBuilder getBuilder() {
    return new StockBuilder();
  }

  @Override
  public String getSymbol() {
    return this.ticker;
  }

  @Override
  public Double getStockValue(String date) throws RuntimeException {
    return stockAPI.getStockClosingByDate(this.ticker, date);
  }

  /**
   * The class acts a builder for building stock objects with the required ticker symbol. Instead of
   * constructing the stock object directly with the constructor the builder creates the stock
   * object without creating any confusion of passing the arguments
   */
  public static class StockBuilder {

    private String ticker;

    /**
     * The method builds the stock object by calling the stock constructor.
     *
     * @return the stock object
     * @throws IllegalArgumentException when an invalid ticker symbol is passed
     */
    public Stock build() throws IllegalArgumentException {
      return new StockImpl(this.ticker);
    }

    /**
     * The method assigns the ticker symbol that is passsed to the create the stock object.
     *
     * @param t the ticker symbol which has to be assigned to the stock object
     * @return the stock object after assigning the ticker symbol
     * @throws IllegalArgumentException when the ticker symbol passed is invalid
     */
    public StockBuilder ticker(String t) throws IllegalArgumentException {
      if (t == null || t.trim().equals("")) {
        throw new IllegalArgumentException("Invalid ticker was passed!!");
      }
      this.ticker = t;
      return this;
    }
  }

}
