package stocksmodel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class implements the InvestorExtension interface and its defined methods for facilitating
 * flexible portfolios while retaining the inflexible portfolio implementations.
 */
public class InvestorImplExtension implements InvestorExtension {

  private static String FILENAME = System.getProperty("user.dir") + "/stocks2.json";
  private static List<Portfolio> cachePortfolios = new ArrayList<>();
  private final Investor delegate;

  /**
   * The constructor creates an InvestorImplExtension object.
   */
  public InvestorImplExtension() {
    this.delegate = new InvestorImpl();
  }

  private static JSONObject readJSON() {
    try (FileReader reader = new FileReader(FILENAME)) {
      JSONParser jsonParser = new JSONParser();
      return (JSONObject) jsonParser.parse(reader);
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  private static void writeToJSON(JSONObject data) {
    try (FileWriter file = new FileWriter(FILENAME)) {
      file.write(data.toJSONString());
    } catch (IOException e) {
      throw new RuntimeException("Cannot store data!!");
    }
  }

  private static Date futureDateCheck(String date) {
    Date inputDate;
    Date currentDate = new Date();
    try {
      inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
      if (inputDate.after(currentDate)) {
        throw new RuntimeException("Cannot do a transaction on future dates!!");
      }
    } catch (java.text.ParseException e) {
      throw new RuntimeException("invalid Date.");
    }
    return inputDate;
  }

  private static JSONObject getPortfolioObject(String name) {
    JSONObject data = readJSON();
    JSONObject portfolioObject = (JSONObject) data.get(name);
    if (portfolioObject == null) {
      throw new RuntimeException("portfolio doesn't exist!!");
    }
    return portfolioObject;
  }

  private static Date getDateFromString(String date) {
    Date intoDate;
    try {
      intoDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (java.text.ParseException e) {
      throw new RuntimeException("Invalid date");
    }
    return intoDate;
  }

  /**
   * The method changes the storage file to the file path from which the saved portfolios, stocks
   * are read from.
   *
   * @param filename the filename of the storage that is to be read from
   */
  @Override
  public void changeReadFile(String filename) throws RuntimeException {
    if (filename == null || filename.trim().equals("")) {
      throw new RuntimeException("Illegal file name was entered");
    }
    if (filename.endsWith(".json")) {
      File f = new File(System.getProperty("user.dir") + "/" + filename);
      if (f.exists()) {
        FILENAME = FILENAME.replace("stocks2.json", filename);
        cachePortfolios = new ArrayList<>();
      } else {
        throw new RuntimeException("File doesn't exist in the project directory!!");
      }
    } else if (filename.endsWith(".xml")) {
      delegate.changeReadFile(filename);
    } else {
      throw new RuntimeException("invalid file given!!");
    }
  }

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
  @Override
  public Map<String, Integer> loadPortfolio(String name) throws RuntimeException {
    return delegate.loadPortfolio(name);
  }

  /**
   * The method returns the total valuation of the portfolio's name passed as a string argument. It
   * also takes a date as an input as it has to return the valuation of that portfolio on that
   * particular portfolio.
   *
   * @param name the name of the portfolio that valuation has to be returned
   * @param date the date on which the valuation has to be returned
   * @return a double value of the total value of the portfolio
   * @throws RuntimeException when it is not able to read from the physical storage where the
   *                          portfolio is stored and also specify the client of what the reason is
   */
  @Override
  public Double getPortfolioValuation(String name, String date) throws RuntimeException {
    Date valueDate = getDateFromString(date);
    if (delegate.loadAllPortfolioNames().contains(name)) {
      return delegate.getPortfolioValuation(name, date);
    }
    JSONObject portfolioObject = getPortfolioObject(name);
    double totalValue = 0.0;
    for (Object stock : portfolioObject.keySet()) {
      int totalQuantity = 0;
      if (!stock.toString().equals("costBasis")) {
        JSONObject stockObj = (JSONObject) portfolioObject.get(stock.toString());
        for (Object quantity : stockObj.keySet()) {
          Date transactionDate = getDateFromString(quantity.toString());
          if (transactionDate.before(valueDate) || transactionDate.equals(valueDate)) {
            totalQuantity += Double.parseDouble(stockObj.get(quantity.toString()).toString());
          }
        }
        double closingValue = 0;
        if (totalQuantity > 0) {
          closingValue = StockImpl.getBuilder().ticker(stock.toString()).build()
                  .getStockValue(date);
        }
        totalValue += closingValue * totalQuantity;
      }
    }
    return totalValue;
  }

  /**
   * The method returns a list of string of portfolio names that are available to the program.
   *
   * @return the list of string of portfolio names
   * @throws RuntimeException when it is not able to read from the physical storage where the
   *                          portfolio is stored and also specify the client of what the reason is
   */
  @Override
  public List<String> loadAllPortfolioNames() throws RuntimeException {
    List<String> portfolios = delegate.loadAllPortfolioNames();
    portfolios = portfolios.stream().map(s -> s = s + " - inflexible").collect(Collectors.toList());
    JSONObject data = readJSON();
    for (Object portfolio : data.keySet()) {
      portfolios.add(portfolio.toString() + " - flexible");
    }
    return portfolios;
  }

  /**
   * The method creates a portfolio and its corresponding stocks and their respective quantities.
   * After creating it persists the portfolio on to a physical storage. The name of the portfolio
   * and a map of stocks and their respective quantities as keys and values respectively.
   *
   * @param name   the name of the portfolio to be created
   * @param stocks a map of stocks as its keys and their respective quantities as values
   * @throws RuntimeException when it is not able to persist a  portfolio on to physical storage
   */
  @Override
  public void createPortfolio(String name, Map<String, Integer> stocks) throws RuntimeException {
    this.delegate.createPortfolio(name, stocks);
  }

  @Override
  public void createFlexiblePortfolio(String name) throws RuntimeException {
    if (name == null || name.trim().equals("")) {
      throw new RuntimeException("Invalid portfolio name given");
    }
    JSONObject data = readJSON();
    JSONObject newPortfolio = new JSONObject();
    for (Portfolio p : cachePortfolios) {
      if (p.getPortfolioName().equals(name)) {
        throw new RuntimeException("Portfolio already exists!!");
      }
    }
    if (data.get(name) != null) {
      throw new RuntimeException("Portfolio already exists!!");
    }
    Portfolio portfolio = PortfolioImpl.getPortfolioBuilder().addStock(new HashMap<>())
            .name(name).build();
    cachePortfolios.add(portfolio);
    data.put(name, newPortfolio);
    writeToJSON(data);
  }

  private void validateInputs(String portfolio, double commissionFeeValue, String date,
                              String ticker, Double quantity) {
    if (portfolio == null || date == null || ticker == null || quantity == null
            || portfolio.trim().equals("") || date.trim().equals("") || ticker.trim().equals("")
            || commissionFeeValue < 0 || quantity < 0) {
      throw new RuntimeException("Invalid inputs given!!");
    }

  }

  @Override
  public void createBuyTransaction(String portfolio, double commissionFeeValue, String date,
                                   String ticker, Double quantity) throws RuntimeException {
    validateInputs(portfolio, commissionFeeValue, date, ticker, quantity);
    futureDateCheck(date);
    try {
      Double cost = StockImpl.getBuilder().ticker(ticker).build().getStockValue(date);
      JSONObject data = readJSON();
      JSONObject portfolioObject = (JSONObject) data.get(portfolio);
      if (portfolioObject == null) {
        throw new RuntimeException("Portfolio doesn't exist!!");
      }
      JSONObject stock = (JSONObject) portfolioObject.get(ticker);
      if (stock != null) {
        stock.put(date, quantity + Double.parseDouble(stock.getOrDefault(date, 0).toString()));
      } else {
        JSONObject transactions = new JSONObject();
        transactions.put(date, quantity);
        portfolioObject.put(ticker, transactions);
      }
      JSONObject costBasis = (JSONObject) portfolioObject.get("costBasis");
      if (costBasis != null) {
        costBasis.put(date, cost + commissionFeeValue + Double.parseDouble(
                costBasis.getOrDefault(date, 0).toString()));
      } else {
        JSONObject newCostBasis = new JSONObject();
        newCostBasis.put(date, cost + commissionFeeValue);
        portfolioObject.put("costBasis", newCostBasis);
      }
      writeToJSON(data);
    } catch (NullPointerException e) {
      throw new RuntimeException("Invalid date entered. Please try another valid date "
              + "where stock market is open.");
    }
  }

  @Override
  public void createSellTransaction(String portfolio, double commissionFeeValue, String date,
                                    String ticker, Double quantity) throws RuntimeException {
    validateInputs(portfolio, commissionFeeValue, date, ticker, quantity);
    Date sellDate = futureDateCheck(date);
    try {
      StockImpl.getBuilder().ticker(ticker).build().getStockValue(date);
      JSONObject data = readJSON();
      JSONObject portfolioObject = (JSONObject) data.get(portfolio);
      if (portfolioObject == null) {
        throw new RuntimeException("Portfolio doesn't exist!!");
      }
      JSONObject stock = (JSONObject) portfolioObject.get(ticker);
      if (stock != null) {
        int totalBoughtUntilDate = 0;
        int totalBought = 0;
        for (Object keyDate : stock.keySet()) {
          Date transactionDate = getDateFromString(keyDate.toString());
          totalBought += Double.parseDouble(stock.get(keyDate).toString());
          if (transactionDate.before(sellDate) || transactionDate.equals(sellDate)) {
            totalBoughtUntilDate += Double.parseDouble(stock.get(keyDate).toString());
          }
        }
        if (totalBoughtUntilDate >= quantity && totalBought >= quantity) {
          stock.put(date, Double.parseDouble(stock.getOrDefault(date, 0).toString()) - quantity);
          JSONObject costBasis = (JSONObject) portfolioObject.get("costBasis");
          costBasis.put(date, commissionFeeValue + Double.parseDouble(
                  costBasis.getOrDefault(date, 0).toString()));
        } else {
          throw new RuntimeException("Invalid sell. Not enough shares!!");
        }
      } else {
        throw new RuntimeException("Cannot sell before buying of stock!!");
      }
      writeToJSON(data);
    } catch (NullPointerException e) {
      throw new RuntimeException("Invalid date entered. Please try another valid date "
              + "where stock market is open.");
    }
  }

  @Override
  public Double getCostBasis(String portfolioName, String date) throws RuntimeException {
    JSONObject portfolioObject = getPortfolioObject(portfolioName);
    JSONObject costBasis = (JSONObject) portfolioObject.get("costBasis");
    double resultCost = 0.0;
    if (costBasis != null) {
      Date untilDate = futureDateCheck(date);
      for (Object keyDate : costBasis.keySet()) {
        Date transactionDate = getDateFromString(keyDate.toString());
        if (transactionDate.before(untilDate) || transactionDate.equals(untilDate)) {
          resultCost += Double.parseDouble(costBasis.get(keyDate).toString());
        }
      }
      return resultCost;
    } else {
      throw new RuntimeException("No stocks bought till now!!");
    }
  }

  @Override
  public Map<String, Double> loadFlexiblePortfolio(String name, String date)
          throws RuntimeException {
    Date givenDate = futureDateCheck(date);
    Map<String, Double> hm = new HashMap<>();
    JSONObject portfolio = getPortfolioObject(name);
    for (Object stockObj : portfolio.keySet()) {
      if (!stockObj.toString().equals("costBasis")) {
        JSONObject stock = (JSONObject) portfolio.get(stockObj.toString());
        for (Object quantity : stock.keySet()) {
          Date quantityDate = getDateFromString(quantity.toString());
          if (!quantityDate.after(givenDate)) {
            Double stockQuantity = Double.parseDouble(stock.get(quantity.toString()).toString());
            hm.put(stockObj.toString(), hm.getOrDefault(stockObj.toString(), 0.0) + stockQuantity);
          }
        }
      }
    }
    return hm;
  }
}
