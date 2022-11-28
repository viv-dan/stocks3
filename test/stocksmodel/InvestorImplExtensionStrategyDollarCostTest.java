package stocksmodel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        assertEquals("2022-10-25", strategy.get("startDate"));
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
        assertEquals("2022-10-25", strategy.get("startDate"));
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
    assertEquals(0, ist.getPortfolioValuation("dollar test2", "2012-01-08"),0);
    assertEquals(0, ist.getCostBasis("dollar test2", "2012-01-08"),0);
  }

  @Test
  public void testHighLevelInvestTest(){
    ist.createFlexiblePortfolio("dollar test3");
    Map<String, Double> hm = new HashMap<>();
    hm.put("IBM",20.0);
    hm.put("V",60.0);
    hm.put("A",10.0);
    hm.put("B",5.0);
    hm.put("C",5.0);
    ist.highLevelInvestStrategy("dollar test3", "2012-01-01", "2012-01-31",7, 20.0, 2000.0, hm);
    ist.createFlexiblePortfolio("dollar test4");
    Map<String, Double> hm1 = new HashMap<>();
    hm1.put("IBM",20.0);
    hm1.put("V",60.0);
    hm1.put("A",10.0);
    hm1.put("B",5.0);
    hm1.put("C",5.0);
    ist.investAmount("dollar test4", hm, 2000.0, "2012-01-09", 20.0);
    assertEquals(ist.getPortfolioValuation("dollar test4", "2012-01-09"), ist.getPortfolioValuation("dollar test3", "2012-01-09"));
    assertEquals(ist.getCostBasis("dollar test4", "2012-01-09"), ist.getCostBasis("dollar test3", "2012-01-09"));

  }




}
