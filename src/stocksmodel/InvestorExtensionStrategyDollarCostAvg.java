package stocksmodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The class implements the InvestorExtensionInvestStrategy interface and its defined methods for
 * facilitating dollar cost averaging strategy and a lump sum strategy.
 */
public class InvestorExtensionStrategyDollarCostAvg extends AbstractInvestorExtensions
        implements InvestorExtensionInvestStrategy {

  private static final String FILENAME = System.getProperty("user.dir") + "/dollarcostavg.json";
  private final InvestorExtension delegate;

  public InvestorExtensionStrategyDollarCostAvg() {
    delegate = new InvestorImplExtension();
  }

  @Override
  public void changeReadFile(String filename) throws RuntimeException {
    delegate.changeReadFile(filename);
  }

  @Override
  public Map<String, Integer> loadPortfolio(String name) throws RuntimeException {
    return delegate.loadPortfolio(name);
  }

  @Override
  public Double getPortfolioValuation(String name, String date) throws RuntimeException {
    this.loadAndUpdateStrategy(name);
    return delegate.getPortfolioValuation(name, date);
  }

  @Override
  public List<String> loadAllPortfolioNames() throws RuntimeException {
    return delegate.loadAllPortfolioNames();
  }

  @Override
  public void createPortfolio(String name, Map<String, Integer> stocks) throws RuntimeException {
    delegate.createPortfolio(name, stocks);
  }

  @Override
  public void createFlexiblePortfolio(String name) throws RuntimeException {
    delegate.createFlexiblePortfolio(name);
  }

  @Override
  public void createSellTransaction(String portfolio, double commissionFeeValue, String date,
                                    String ticker, Double quantity) throws RuntimeException {
    this.loadAndUpdateStrategy(portfolio);
    delegate.createSellTransaction(portfolio, commissionFeeValue, date, ticker, quantity);
  }

  @Override
  public void createBuyTransaction(String portfolio, double commissionFeeValue, String date,
                                   String ticker, Double quantity) throws RuntimeException {
    this.loadAndUpdateStrategy(portfolio);
    delegate.createBuyTransaction(portfolio, commissionFeeValue, date, ticker, quantity);
  }

  @Override
  public Double getCostBasis(String portfolioName, String date) throws IllegalArgumentException {
    this.loadAndUpdateStrategy(portfolioName);
    return delegate.getCostBasis(portfolioName, date);
  }

  @Override
  public Map<String, Double> loadFlexiblePortfolio(String name, String date)
          throws RuntimeException {
    this.loadAndUpdateStrategy(name);
    return delegate.loadFlexiblePortfolio(name, date);
  }

  @Override
  public void investAmount(String portfolio, Map<String, Double> weights, Double amount,
                           String date, Double commissionFee) throws RuntimeException {
    if (weights == null || amount == null || portfolio == null || date == null
            || commissionFee == null) {
      throw new RuntimeException("invalid input");
    }
    this.weightChecker(weights);
    if (amount == null || amount <= 0) {
      throw new RuntimeException("invalid amount");
    }
    if (getDateFromString(date).after(new Date())) {
      this.highLevelInvestStrategy(portfolio, date, date, 1, commissionFee, amount, weights);
      return;
    }
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

  private void weightChecker(Map<String, Double> weights) {
    double totalWeight = 0.0;
    for (String ticker : weights.keySet()) {
      totalWeight += weights.get(ticker);
    }
    if (totalWeight < 100 || totalWeight > 100) {
      throw new RuntimeException("invalid weights");
    }
  }

  @Override
  public void highLevelInvestStrategy(String portfolio, String startDate, String endDate,
                                      Integer recurrenceDays, Double commissionFee,
                                      Double amount, Map<String, Double> weights)
          throws RuntimeException {
    if (portfolio == null || startDate == null || recurrenceDays == null
            || commissionFee == null || amount == null || weights == null) {
      throw new RuntimeException("invalid inputs");
    }
    this.weightChecker(weights);
    Date start = getDateFromString(startDate);
    if (endDate != null) {
      Date end = getDateFromString(endDate);
      if (start.after(end)) {
        throw new RuntimeException("start date cannot be after end date");
      }
    }
    if (!(recurrenceDays > 0) || commissionFee < 0 || amount <= 0) {
      throw new RuntimeException("invalid input");
    }
    if (!delegate.loadAllPortfolioNames().contains(portfolio + " - flexible")) {
      throw new RuntimeException("Portfolio doesn't exist");
    }
    Calendar c = Calendar.getInstance();
    c.setTime(getDateFromString(startDate));
    c.add(Calendar.DATE, recurrenceDays * (-1));
    startDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    this.persistStrategy(portfolio, startDate, endDate, recurrenceDays, commissionFee, amount,
            weights);
  }

  private String implementStrategy(String portfolio, String startDate, String endDate,
                                   Integer recurrenceDays, Double commissionFee,
                                   Double amount, Map<String, Double> weights) {
    Date endingDate = new Date();
    Date startingDate = getDateFromString(startDate);
    if (endDate != null) {
      endingDate = getDateFromString(endDate);
    }
    while (!startingDate.after(endingDate) && !startingDate.after(new Date())) {
      Calendar c = Calendar.getInstance();
      c.setTime(startingDate);
      c.add(Calendar.DATE, recurrenceDays);
      String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
      if (!getDateFromString(formattedDate).after(endingDate)
              && !getDateFromString(formattedDate).after(new Date())) {
        startingDate = getDateFromString(formattedDate);
        while (true) {
          try {
            if (getDateFromString(formattedDate).after(new Date())
                    || getDateFromString(formattedDate).after(endingDate)) {
              break;
            }
            this.investAmount(portfolio, weights, amount, formattedDate, commissionFee);
            break;
          } catch (RuntimeException e) {
            if (e.getMessage().equals("Cannot get stock data for the given date")) {
              c.add(Calendar.DATE, 1);
              formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            } else {
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
    data = readJSON();
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
    writeToJSON(data);
  }

  private void loadAndUpdateStrategy(String portfolioName) {
    JSONObject data;
    data = readJSON();
    JSONArray portfolio = (JSONArray) data.get(portfolioName);
    if (portfolio == null) {
      return;
    }
    for (int i = 0; i < portfolio.size(); i++) {
      JSONObject strategy = (JSONObject) portfolio.get(i);
      String endDate = null;
      if (strategy.get("endDate") != null) {
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
    writeToJSON(data);
  }

  @Override
  protected String getFilename() {
    return FILENAME;
  }
}
