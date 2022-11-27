package stocksmodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InvestorExtensionStrategyDollarCostAvg implements InvestorExtensionInvestStrategy {

  private static final String FILENAME = System.getProperty("user.dir") + "/dollarcostavg.json";
  private final InvestorExtension delegate;

  public InvestorExtensionStrategyDollarCostAvg() {
    delegate = new InvestorImplExtension();
  }

  private static Date getDateFromString(String date) {
    Date intoDate;
    try {
      intoDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (java.text.ParseException e) {
      throw new RuntimeException("Invalid date " + e.getMessage());
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
    delegate.changeReadFile(filename);
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
  @Override
  public Double getPortfolioValuation(String name, String date) throws RuntimeException {
    this.loadAndUpdateStrategy(name);
    return delegate.getPortfolioValuation(name, date);
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
    return delegate.loadAllPortfolioNames();
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
    delegate.createPortfolio(name, stocks);
  }

  /**
   * The method creates a flexible portfolio in which buying and selling stocks at a later is done.
   *
   * @param name the name of the portfolio that is to be created
   * @throws RuntimeException is thrown when there is already a portfolio of the name that is passed
   *                          already exists or if it is not able to persist the portfolio onto physical storage
   */
  @Override
  public void createFlexiblePortfolio(String name) throws RuntimeException {
    delegate.createFlexiblePortfolio(name);
  }

  /**
   * The method does a transaction of sell in the given portfolio based on the parameters passed.
   * UPDATE: the quantity data type now has to be double as the user now can sell fractional shares
   *
   * @param portfolio          the name of the portfolio in which the sell transaction has to be done
   * @param commissionFeeValue the commission fee which is included for every transaction of sell
   * @param date               the date on which the sell transaction has to be performed
   * @param ticker             the ticker symbol of the stock on which the sell transaction has to be performed
   * @param quantity           the quantity of the stock to be sold
   * @throws RuntimeException is thrown when the portfolio doesn't exist or the stock market is
   *                          closed on the particular date that is entered. Exception is also thrown when the
   *                          stock quantity is less or not even bought till the date in the portfolio
   */
  @Override
  public void createSellTransaction(String portfolio, double commissionFeeValue, String date, String ticker, Double quantity) throws RuntimeException {
    this.loadAndUpdateStrategy(portfolio);
    delegate.createSellTransaction(portfolio, commissionFeeValue, date, ticker, quantity);
  }

  /**
   * The method does a transaction of buy in the given portfolio based on the parameters passed.
   * UPDATE: the quantity data type now has to be double to facilitate the dollar-cost averaging
   *
   * @param portfolio          the name of the portfolio in which the buy transaction has to be done
   * @param commissionFeeValue the commission fee which is included for every transaction of buy
   * @param date               the date on which the buy transaction has to be performed
   * @param ticker             the ticker symbol of the stock that which has to be bought
   * @param quantity           the quantity of the stock to buy
   * @throws RuntimeException is thrown when the portfolio doesn't exist or the stock market is
   *                          closed on the particular date that is entered.
   */
  @Override
  public void createBuyTransaction(String portfolio, double commissionFeeValue, String date, String ticker, Double quantity) throws RuntimeException {
    this.loadAndUpdateStrategy(portfolio);
    delegate.createBuyTransaction(portfolio, commissionFeeValue, date, ticker, quantity);
  }

  /**
   * The method returns the cost basis(total investment) of the portfolio until the desired date.
   *
   * @param portfolioName the name of the portfolio that the cost basis has to be returned
   * @param date          the date until which the cost basis has to be returned
   * @return returns the cost basis of the portfolio in the form of a double
   * @throws IllegalArgumentException is thrown when the portfolio doesn't exist or if it is empty
   */
  @Override
  public Double getCostBasis(String portfolioName, String date) throws IllegalArgumentException {
    this.loadAndUpdateStrategy(portfolioName);
    return delegate.getCostBasis(portfolioName, date);
  }

  /**
   * The method returns the stock composition of the flexible portfolio until the desired date.
   *
   * @param name the name of the portfolio that the composition has to be returned
   * @param date the date until which the composition has to be returned
   * @return returns a map of stocks and its respective quantities in the form of a map
   * @throws RuntimeException is thrown when the portfolio doesn't exist
   */
  @Override
  public Map<String, Double> loadFlexiblePortfolio(String name, String date) throws RuntimeException {
    this.loadAndUpdateStrategy(name);
    return delegate.loadFlexiblePortfolio(name, date);
  }

  @Override
  public void investAmount(String portfolio, Map<String, Double> weights, Double amount,
                           String date, Double commissionFee) {
    int noOfStocks = weights.size();
    amount = amount - commissionFee;
    commissionFee = commissionFee / noOfStocks;
    for (String ticker : weights.keySet()) {
      Stock stock = StockImpl.getBuilder().ticker(ticker).build();
      Double amountPerStock = (weights.get(ticker) * amount) / 100.0;
      Double amountOfShares = amountPerStock / stock.getStockValue(date);
      delegate.createBuyTransaction(portfolio, commissionFee, date, ticker, amountOfShares);
    }
  }

  @Override
  public void highLevelInvestStrategy(String portfolio, String startDate, String endDate,
                                      Integer recurrenceDays, Double commissionFee,
                                      Double amount, Map<String, Double> weights) {
    persistStrategy(portfolio, startDate, endDate, recurrenceDays, commissionFee, amount, weights);
  }

  private String implementStrategy(String portfolio, String startDate, String endDate,
                                   Integer recurrenceDays, Double commissionFee,
                                   Double amount, Map<String, Double> weights) {
    Date endingDate = new Date();
    Date startingDate = getDateFromString(startDate);
    if (endDate != null) {
      endingDate = getDateFromString(endDate);
    }
    while (!startingDate.after(endingDate)) {
      Calendar c = Calendar.getInstance();
      c.setTime(startingDate);
      c.add(Calendar.DATE, recurrenceDays);
      String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
      if (getDateFromString(formattedDate).before(endingDate)) {
        startingDate = getDateFromString(formattedDate);
        while (true) {
          try {
            this.investAmount(portfolio, weights, amount, formattedDate, commissionFee);
            break;
          } catch (RuntimeException e) {
            if (e.getMessage().equals("Cannot get stock data for the given date")) {
              c.add(Calendar.DATE, 1);
              formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            }else{
              throw new RuntimeException(e.getMessage());
            }
          }
        }
      } else {
        break;
      }
    }
    return new SimpleDateFormat("yyyy-MM-dd").format(startingDate);
  }

  private void persistStrategy(String portfolio, String startDate, String endDate,
                               Integer recurrenceDays, Double commissionFee,
                               Double amount, Map<String, Double> weights) {
    JSONObject data;
    try (FileReader reader = new FileReader(FILENAME)) {
      JSONParser jsonParser = new JSONParser();
      data = (JSONObject) jsonParser.parse(reader);
      JSONArray portfolioObj = (JSONArray) data.get(portfolio);
      if (portfolioObj == null) {
        portfolioObj = new JSONArray();
      }
      JSONObject strategy = new JSONObject();
      JSONObject weightsObj = new JSONObject(weights);
      strategy.put("startDate", startDate);
      strategy.put("endDate", endDate);
      strategy.put("recurrenceDays", recurrenceDays);
      strategy.put("commissionFee", commissionFee);
      strategy.put("amount", amount);
      strategy.put("weights", weightsObj);
      portfolioObj.add(strategy);
      data.put(portfolio, portfolioObj);
    } catch (IOException e) {
      throw new RuntimeException("Cannot persist strategy " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
    try (FileWriter file = new FileWriter(FILENAME)) {
      file.write(data.toJSONString());
    } catch (IOException e) {
      throw new RuntimeException("Cannot persist strategy data!!");
    }
  }

  private void loadAndUpdateStrategy(String portfolioName) {
    JSONObject data;
    try (FileReader reader = new FileReader(FILENAME)) {
      JSONParser jsonParser = new JSONParser();
      data = (JSONObject) jsonParser.parse(reader);
      JSONArray portfolio = (JSONArray) data.get(portfolioName);
      if(portfolio==null){
        return;
      }
      for (int i = 0; i < portfolio.size(); i++) {
        JSONObject strategy = (JSONObject) portfolio.get(i);
        String endDate = null;
        if(strategy.get("endDate")!=null){
          endDate = strategy.get("endDate").toString();
        }
        String startDate = this.implementStrategy(portfolioName,
                strategy.get("startDate").toString(),
                endDate,
                Integer.parseInt(strategy.get("recurrenceDays").toString()),
                Double.parseDouble(strategy.get("commissionFee").toString()),
                Double.parseDouble(strategy.get("amount").toString()),
                (Map<String, Double>) strategy.get("weights"));
        strategy.put("startDate", startDate);
      }
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
    try (FileWriter file = new FileWriter(FILENAME)) {
      file.write(data.toJSONString());
    } catch (IOException e) {
      throw new RuntimeException("Cannot persist strategy data!!");
    }
  }
}
