package stocksmodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InvestorImplExtensionStrategyDollarCostTest {

  private InvestorExtensionInvestStrategy ist;

  @Before
  public void setup(){
    ist = new InvestorExtensionStrategyDollarCostAvg();
  }

  @Test
  public void testInvestAmount(){
    ist.createFlexiblePortfolio("strategy test");
    Map<String, Double> hm = new HashMap<>();
    hm.put("AAPL",20.0);
    hm.put("AMZN",60.0);
    hm.put("GOOG",10.0);
    hm.put("VZ",5.0);
    hm.put("MSFT",5.0);
    ist.investAmount("strategy test", hm, 2000.0, "2022-11-01", 30.0);
    Map<String, Double> hm1 = ist.loadFlexiblePortfolio("strategy test", "2022-11-01");
    assertEquals(hm.size(), hm1.size());
    for(String ticker : hm1.keySet()){
      assertTrue(hm.containsKey(ticker));
      StockAPI sa = new StockAPIImpl();
      Double tickerValue = sa.getStockClosingByDate(ticker, "2022-11-01");
      double quantity  = ((hm.get(ticker)/100)*1970)/tickerValue;
      assertEquals(quantity, hm1.get(ticker),0);
    }
    assertEquals(2000, ist.getCostBasis("strategy test", "2022-11-01"),0);
    assertEquals(0, ist.loadFlexiblePortfolio("strategy test", "2022-10-30").size());
    assertEquals(1970, ist.getPortfolioValuation("strategy test","2022-11-01"),0);
  }

  @Test
  public void testInvestAmountAnother(){
    ist.createFlexiblePortfolio("strategy test1");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test1", hm, 2000.0, "2022-11-03", 30.0);
    Map<String, Double> hm1 = ist.loadFlexiblePortfolio("strategy test1", "2022-11-03");
    assertEquals(hm.size(), hm1.size());
    for(String ticker : hm1.keySet()){
      assertTrue(hm.containsKey(ticker));
      StockAPI sa = new StockAPIImpl();
      Double tickerValue = sa.getStockClosingByDate(ticker, "2022-11-03");
      double quantity  = ((hm.get(ticker)/100)*1970)/tickerValue;
      assertEquals(quantity, hm1.get(ticker),0);
    }
    assertEquals(2000, ist.getCostBasis("strategy test1", "2022-11-03"),0);
    assertEquals(0, ist.loadFlexiblePortfolio("strategy test1", "2022-10-30").size());
    assertEquals(1970, ist.getPortfolioValuation("strategy test1","2022-11-03"),0);
  }

  @Test
  public void testInvestAmountAnother1(){
    ist.createFlexiblePortfolio("strategy test2");
    Map<String, Double> hm = new HashMap<>();
    hm.put("MSFT",20.0);
    hm.put("VZ",60.0);
    hm.put("A",10.0);
    hm.put("AMZN",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test2", hm, 2000.0, "2022-11-04", 30.0);
    Map<String, Double> hm1 = ist.loadFlexiblePortfolio("strategy test2", "2022-11-04");
    assertEquals(hm.size(), hm1.size());
    for(String ticker : hm1.keySet()){
      assertTrue(hm.containsKey(ticker));
      StockAPI sa = new StockAPIImpl();
      Double tickerValue = sa.getStockClosingByDate(ticker, "2022-11-04");
      double quantity  = ((hm.get(ticker)/100)*1970)/tickerValue;
      assertEquals(quantity, hm1.get(ticker),0);
    }
    assertEquals(2000, ist.getCostBasis("strategy test2", "2022-11-04"),0);
    assertEquals(0, ist.loadFlexiblePortfolio("strategy test2", "2022-10-30").size());
    assertEquals(1970, ist.getPortfolioValuation("strategy test2","2022-11-04"),0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNoPortfolio(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test3", hm, 2000.0, "2022-11-04", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountInvalidDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test3", hm, 2000.0, "20220938", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountInvalidFutureDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test3", hm, 2000.0, "2022-12-21", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountInvalidAnotherDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("strategy test3", hm, 2000.0, "2022-11-06", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountWeightsGreater100(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",65.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountWeightsLess100(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",59.9999);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountWeightsLessThan100(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",59.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNull(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, null, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNull1(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount(null, hm, 2000.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNull2(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", null, 2000.0, "2022-11-01", 30.0);
  }

  @Test
  public void testInvestAmountStartDateFuture(){
    ist.createFlexiblePortfolio("dollar test1010");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("dollar test1010", hm, 2000.0, "2022-12-21", 30.0);
    JSONObject data;
    try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/dollarcostavg.json")) {
      JSONParser jsonParser = new JSONParser();
      data = (JSONObject) jsonParser.parse(reader);
      JSONArray portfolio = (JSONArray) data.get("dollar test1010");
      assertEquals(1,portfolio.size());
      for (int i = 0; i < portfolio.size(); i++) {
        JSONObject strategy = (JSONObject) portfolio.get(i);
        assertEquals("2022-12-21",strategy.get("endDate"));
        assertEquals("2022-12-20", strategy.get("startDate"));
        assertEquals(2000.0, strategy.get("amount"));
        assertEquals(30.0, strategy.get("commissionFee"));
        assertEquals(1, Integer.parseInt(strategy.get("recurrenceDays").toString()),0);
        Map<String, Double> we = (Map<String, Double>) strategy.get("weights");
        assertEquals(hm.size(), we.size());
        for(String s : hm.keySet()){
          assertEquals(hm.get(s), we.get(s));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNull3(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, null, 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNull4(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, "2022-11-01", null);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountZero(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 0.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountNegative(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, -2.0, "2022-11-01", 30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testInvestAmountFeeNegative(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.investAmount("vivdan", hm, 2000.0, "2022-11-01", -30.0);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNoPortfolio(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("jfhseoijncslh", "2022-10-25", "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNullPortfolio(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy(null, "2022-10-25", "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNull1(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", null, "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNull3(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",null, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNull4(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, null, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNull5(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, 20.0, null, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestNull6(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, 20.0, 2000.0, null);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestWeightsLess100(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",59.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28", 5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestWeightsgreater100(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",61.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestInvalidStartDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022asj-10-25", "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestInvalidEndDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022ask-11-28",5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestRecurNegatrive(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",-5, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestRecurZero(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",0, 20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestFeeNegative(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, -20.0, 2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestAmountZero(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, 20.0, 0.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestAmountNegative(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-10-25", "2022-11-28",5, 20.0, -2000.0, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testHighLevelInvestAmountStartDateAfterEndDate(){
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("vivdan", "2022-12-25", "2022-11-28",5, 20.0, 2000.0, hm);
  }

  @Test
  public void testHighLevelInvestDataFormatCheckSave(){
    ist.createFlexiblePortfolio("dollar test");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test", "2022-10-25", "2022-11-28",5, 20.0, 2000.0, hm);
    JSONObject data;
    try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/dollarcostavg.json")) {
      JSONParser jsonParser = new JSONParser();
      data = (JSONObject) jsonParser.parse(reader);
      JSONArray portfolio = (JSONArray) data.get("dollar test");
      assertEquals(1,portfolio.size());
      for (int i = 0; i < portfolio.size(); i++) {
        JSONObject strategy = (JSONObject) portfolio.get(i);
        assertEquals("2022-11-28",strategy.get("endDate"));
        assertEquals("2022-10-20", strategy.get("startDate"));
        assertEquals(2000.0, strategy.get("amount"));
        assertEquals(20.0, strategy.get("commissionFee"));
        assertEquals(5, Integer.parseInt(strategy.get("recurrenceDays").toString()),0);
        Map<String, Double> we = (Map<String, Double>) strategy.get("weights");
        assertEquals(hm.size(), we.size());
        for(String s : hm.keySet()){
          assertEquals(hm.get(s), we.get(s));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  @Test
  public void testHighLevelInvestDataFormatCheckSaveWithEndNull(){
    ist.createFlexiblePortfolio("dollar test1");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test1", "2022-10-25", null,5, 20.0, 2000.0, hm);
    JSONObject data;
    try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/dollarcostavg.json")) {
      JSONParser jsonParser = new JSONParser();
      data = (JSONObject) jsonParser.parse(reader);
      JSONArray portfolio = (JSONArray) data.get("dollar test1");
      assertEquals(1,portfolio.size());
      for (int i = 0; i < portfolio.size(); i++) {
        JSONObject strategy = (JSONObject) portfolio.get(i);
        assertNull(strategy.get("endDate"));
        assertEquals("2022-10-20", strategy.get("startDate"));
        assertEquals(2000.0, strategy.get("amount"));
        assertEquals(20.0, strategy.get("commissionFee"));
        assertEquals(5, Integer.parseInt(strategy.get("recurrenceDays").toString()));
        Map<String, Double> we = (Map<String, Double>) strategy.get("weights");
        assertEquals(hm.size(), we.size());
        for(String s : hm.keySet()){
          assertEquals(hm.get(s), we.get(s));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  @Test
  public void testHighLevelInvestTestOnNextRecurHolidayPlusOne(){
    ist.createFlexiblePortfolio("dollar test2");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test2", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    assertEquals(0, ist.getPortfolioValuation("dollar test2", "2012-01-01"),0);
    assertEquals(0, ist.getCostBasis("dollar test2", "2012-01-01"),0);
  }

  @Test
  public void testHighLevelInvestTestAfterValuation(){
    ist.createFlexiblePortfolio("dollar test313");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test313", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test413");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    ist.investAmount("dollar test413", hm, 2000.0, "2012-01-03", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test413", "2012-01-03"), ist.getPortfolioValuation("dollar test313", "2012-01-03"));
    //assertEquals(ist.getCostBasis("dollar test4", "2012-01-09"), ist.getCostBasis("dollar test3", "2012-01-09"));
    Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test313", "2012-01-03");
    Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test413", "2012-01-03");
    assertEquals(hm2.size(), hm3.size());
    for(String s: hm2.keySet()){
      if(!hm3.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
  }

  @Test
  public void testHighLevelInvestTestAfterCostBasis(){
    ist.createFlexiblePortfolio("dollar test312");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test312", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test412");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    ist.investAmount("dollar test412", hm, 2000.0, "2012-01-03", 20.0);
    //assertEquals(ist.getPortfolioValuation("dollar test4", "2012-01-09"), ist.getPortfolioValuation("dollar test3", "2012-01-09"));
    assertEquals(ist.getCostBasis("dollar test412", "2012-01-03"), ist.getCostBasis("dollar test312", "2012-01-03"));
    Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test312", "2012-01-03");
    Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test412", "2012-01-03");
    assertEquals(hm2.size(), hm3.size());
    for(String s: hm2.keySet()){
      if(!hm3.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
  }

  @Test
  public void testHighLevelInvestTestAfterLoad(){
    ist.createFlexiblePortfolio("dollar test3222");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test3222", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test4222");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    ist.investAmount("dollar test4222", hm, 2000.0, "2012-01-09", 20.0);
    //assertEquals(ist.getPortfolioValuation("dollar test4", "2012-01-09"), ist.getPortfolioValuation("dollar test3", "2012-01-09"));
    //assertEquals(ist.getCostBasis("dollar test4", "2012-01-09"), ist.getCostBasis("dollar test3", "2012-01-09"));
    Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test3222", "2012-01-09");
    Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test4222", "2012-01-09");
    assertEquals(hm2.size(), hm3.size());
    for(String s: hm2.keySet()){
      if(!hm3.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
  }

  @Test
  public void testHighLevelInvestTestAfterThreeRecurrences(){
    ist.createFlexiblePortfolio("dollar test322");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test322", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test422");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    ist.investAmount("dollar test422", hm, 2000.0, "2012-01-03", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test422", "2012-01-03"), ist.getPortfolioValuation("dollar test322", "2012-01-03"));
    //assertEquals(ist.getCostBasis("dollar test4", "2012-01-09"), ist.getCostBasis("dollar test3", "2012-01-09"));
    Map<String, Double> hm0 =  ist.loadFlexiblePortfolio("dollar test322", "2012-01-03");
    Map<String, Double> hm00 =  ist.loadFlexiblePortfolio("dollar test422", "2012-01-03");
    assertEquals(hm0.size(), hm00.size());
    for(String s: hm0.keySet()){
      if(!hm00.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
    ist.investAmount("dollar test422", hm, 2000.0, "2012-01-09", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test422", "2012-01-09"), ist.getPortfolioValuation("dollar test322", "2012-01-09"));
    assertEquals(ist.getCostBasis("dollar test422", "2012-01-09"), ist.getCostBasis("dollar test322", "2012-01-09"));
    Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test322", "2012-01-09");
    Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test422", "2012-01-09");
    assertEquals(hm2.size(), hm3.size());
    for(String s: hm2.keySet()){
      if(!hm3.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
    ist.investAmount("dollar test422", hm, 2000.0, "2012-01-17", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test422", "2012-01-17"), ist.getPortfolioValuation("dollar test322", "2012-01-17"));
    assertEquals(ist.getCostBasis("dollar test422", "2012-01-17"), ist.getCostBasis("dollar test322", "2012-01-17"));
    Map<String, Double> hm4 =  ist.loadFlexiblePortfolio("dollar test322", "2012-01-17");
    Map<String, Double> hm5 =  ist.loadFlexiblePortfolio("dollar test422", "2012-01-17");
    assertEquals(hm4.size(), hm5.size());
    for(String s: hm4.keySet()){
      if(!hm4.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
    ist.investAmount("dollar test422", hm, 2000.0, "2012-01-23", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test422", "2012-01-23"), ist.getPortfolioValuation("dollar test322", "2012-01-23"));
    assertEquals(ist.getCostBasis("dollar test422", "2012-01-23"), ist.getCostBasis("dollar test322", "2012-01-23"));
    Map<String, Double> hm6 =  ist.loadFlexiblePortfolio("dollar test322", "2012-01-23");
    Map<String, Double> hm7 =  ist.loadFlexiblePortfolio("dollar test422", "2012-01-23");
    assertEquals(hm6.size(), hm7.size());
    for(String s: hm6.keySet()){
      if(!hm6.containsKey(s)){
        fail("Not equal. Test failed");
      }
    }
  }

  @Test
  public void testHighLevelInvestTestNoEndDate(){
    ist.createFlexiblePortfolio("dollar test31");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test31", "2022-11-28", null,7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test41");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    Date d = getDateFromString("2022-11-28");
    while(!d.after(new Date())){
      try {
        ist.investAmount("dollar test41", hm, 2000.0, d.toString(), 20.0);
        assertEquals(ist.getPortfolioValuation("dollar test41", d.toString()), ist.getPortfolioValuation("dollar test31", d.toString()));
        assertEquals(ist.getCostBasis("dollar test41", d.toString()), ist.getCostBasis("dollar test31", d.toString()));
        Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test31", d.toString());
        Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test41", d.toString());
        assertEquals(hm2.size(), hm3.size());
        for(String s: hm2.keySet()){
          if(!hm3.containsKey(s)){
            fail("Not equal. Test failed");
          }
        }
      }catch(RuntimeException e){
        //
      }
      Calendar c = Calendar.getInstance();
      c.setTime(d);
      c.add(Calendar.DATE, 1);
      String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
      d = getDateFromString(formattedDate);
    }
  }

  @Test
  public void testHighLevelInvestTestFutureEndDate(){
    ist.createFlexiblePortfolio("dollar test32");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test32", "2022-11-28", "2023-01-01",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test42");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    Date d = getDateFromString("2022-11-28");
    Date d1 = getDateFromString("2023-01-01");
    while(!d.after(new Date()) && !d.after(d1)){
      try {
        ist.investAmount("dollar test42", hm, 2000.0, d.toString(), 20.0);
        assertEquals(ist.getPortfolioValuation("dollar test42", d.toString()), ist.getPortfolioValuation("dollar test32", d.toString()));
        assertEquals(ist.getCostBasis("dollar test42", d.toString()), ist.getCostBasis("dollar test32", d.toString()));
        Map<String, Double> hm2 =  ist.loadFlexiblePortfolio("dollar test32", d.toString());
        Map<String, Double> hm3 =  ist.loadFlexiblePortfolio("dollar test42", d.toString());
        assertEquals(hm2.size(), hm3.size());
        for(String s: hm2.keySet()){
          if(!hm3.containsKey(s)){
            fail("Not equal. Test failed");
          }
        }
      }catch(RuntimeException e){
        //
      }
      Calendar c = Calendar.getInstance();
      c.setTime(d);
      c.add(Calendar.DATE, 1);
      String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
      d = getDateFromString(formattedDate);
    }
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

  @Test
  public void testHighLevelInvestTestFutureStartDate() {
    ist.createFlexiblePortfolio("dollar test33");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM", 20.0);
    hm.put("V", 60.0);
    hm.put("A", 10.0);
    hm.put("B", 5.0);
    hm.put("C", 5.0);
    ist.highLevelInvestStrategy("dollar test33", "2022-12-21", "2023-01-01", 7, 20.0, 2000.0, hm);
    try {
      ist.getCostBasis("dollar test33", "2022-11-28");
    }catch (RuntimeException e){
      assertEquals("No stocks bought till now!!", e.getMessage());
    }
  }
}
