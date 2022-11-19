package stocksmodel;

import java.util.HashMap;
import java.util.Map;

/**
 * The class implements Portfolio interface and its methods to be able to have a composition of
 * stocks and their respective quantities.
 */
class PortfolioImpl implements Portfolio {

  private final String name;
  private final Map<Stock, Integer> stockPair;

  private PortfolioImpl(String name, Map<Stock, Integer> stockPair) {
    if (name == null || name.trim().equals("")) {
      throw new IllegalArgumentException("Invalid portfolio name was given!!");
    }
    this.name = name;
    this.stockPair = stockPair;
  }

  /**
   * The method returns a builder to be able to create a portfolio Object by passing required name.
   *
   * @return the portfolio object after building it
   */
  public static PortfolioBuilder getPortfolioBuilder() {
    return new PortfolioBuilder();
  }

  @Override
  public String getPortfolioName() {
    return this.name;
  }

  @Override
  public Map<Stock, Integer> getStockComposition() {
    Map<Stock, Integer> copyComposition = new HashMap<>();
    copyComposition.putAll(stockPair);
    return copyComposition;
  }

  /**
   * The class acts a builder for building portfolio objects with the required name. Instead of
   * constructing the portfolio object directly with the constructor the builder creates the
   * portfolio object without creating any confusion of passing the arguments
   */
  public static class PortfolioBuilder {
    private String name;
    private Map<Stock, Integer> stockPair;

    /**
     * The method builds the portfolio object by calling the portfolio constructor.
     *
     * @return the portfolio object
     * @throws IllegalArgumentException when an invalid name is passed
     */
    public Portfolio build() throws IllegalArgumentException {
      return new PortfolioImpl(name, stockPair);
    }

    /**
     * The method assigns the name that is passed to the create the portfolio object.
     *
     * @param name the name which has to be assigned to the portfolio object
     * @return the portfolio object after assigning the name
     * @throws IllegalArgumentException when the name passed is invalid
     */
    public PortfolioBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * The method assigns the portfolio with certain number of stocks that it should contain and
     * their respective stock quantities.
     *
     * @param stocks the map which contains the stock object and its respective quantity as keys and
     *               values
     * @return the portfolio object after assigning the stocks and quantities
     */
    public PortfolioBuilder addStock(Map<Stock, Integer> stocks) {
      this.stockPair = new HashMap<>();
      if (stocks != null) {
        stockPair.putAll(stocks);
      }
      return this;
    }
  }

}
