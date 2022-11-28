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

/**
 * The class implements the InvestorExtensionInvestStrategy interface and its defined methods for
 * facilitating dollar cost averaging strategy and a lump sum strategy.
 */
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
