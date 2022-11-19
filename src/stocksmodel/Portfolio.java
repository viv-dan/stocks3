package stocksmodel;

import java.util.Map;

/**
 * An interface that specifies the methods of a portfolio. A portfolio is a composition of one or
 * more stocks with their respective quantities.
 */
interface Portfolio {

  /**
   * The method returns the portfolio's name.
   *
   * @return the portfolio's name
   */
  String getPortfolioName();

  /**
   * The method returns the portfolio's composition as a map with the stocks and their respective
   * quantities as keys and values.
   *
   * @return a map with the stocks and their respective quantities as keys and values.
   */
  Map<Stock, Integer> getStockComposition();
}
