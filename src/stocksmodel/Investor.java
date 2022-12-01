package stocksmodel;

import java.util.List;
import java.util.Map;

/**
 * The interface specifies the methods that are available to be able to create, persist and also
 * valuate portfolios.
 */
interface Investor {

  /**
   * The method changes the storage file to the file path from which the saved portfolios, stocks
   * are read from.
   *
   * @param filename the filename of the storage that is to be read from
   */
  void changeReadFile(String filename) throws RuntimeException;

  /**
   * The method returns the composition of a particular portfolio based on the name that is passed
   * as an argument. It returns a map which contains the portfolio's stocks as keys and their
   * respective quantities as values.
   *
   * @param name the name of the portfolio which the method has to return the composition
   * @return a map which particular stocks as keys and their respective quantities as values
   * @throws RuntimeException when it is not able to read from the physical storage where the
   *                          portfolio is stored and also specify the client of what the reason is
   */
  Map<String, Integer> loadPortfolio(String name) throws RuntimeException;

  /**
   * The method returns the totol valuation of the portfolio's name passed as a string argument. It
   * also takes a date as an input as it has to return the valuation of that portfolio on that
   * particular portfolio.
   *
   * @param name the name of the portfolio that valuation has to be returned
   * @param date the date on which the valuation has to be returned
   * @return a double value of the total value of the portfolio
   * @throws RuntimeException when it is not able to read from the physical storage where the
   *                          portfolio is stored and also specify the client of what the reason is
   */
  Double getPortfolioValuation(String name, String date) throws RuntimeException;

  /**
   * The method returns a list of string of portfolio names that are available to the program.
   *
   * @return the list of string of portfolio names
   * @throws RuntimeException when it is not able to read from the physical storage where the
   *                          portfolio is stored and also specify the client of what the reason is
   */
  List<String> loadAllPortfolioNames() throws RuntimeException;

  /**
   * The method creates a portfolio and its corresponding stocks and their respective quantities.
   * After creating it persists the portfolio on to a physical storage. The name of the portfolio
   * and a map of stocks and their respective quantities as keys and values respectively.
   *
   * @param name   the name of the portfolio to be created
   * @param stocks a map of stocks as its keys and their respective quantities as values
   * @throws RuntimeException when it is not able to persist a  portfolio on to physical storage
   */
  void createPortfolio(String name, Map<String, Integer> stocks) throws RuntimeException;

}
