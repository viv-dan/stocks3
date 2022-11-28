package stocksmodel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is a test class for testing the InvestorImplExtension of the stocks program.
 */
public class InvestorImplExtensionTest {
  private InvestorExtension il;

  private static JSONObject getPortfolioObject(String name) {
    JSONObject data = readJSON();
    JSONObject portfolioObject = (JSONObject) data.get(name);
    if (portfolioObject == null) {
      throw new RuntimeException("portfolio doesn't exist!!");
    }
    return portfolioObject;
  }

  private static JSONObject readJSON() {
    try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/stocks2.json")) {
      JSONParser jsonParser = new JSONParser();
      return (JSONObject) jsonParser.parse(reader);
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  @Before
  public void setupObject() {
    il = new InvestorImplExtension();
  }

  @Test
  public void testChangeReadFile() {
    int numberOld = il.loadAllPortfolioNames().size();
    il.changeReadFile("stocks2Copy.json");
    int numberNew = il.loadAllPortfolioNames().size();
    assertNotEquals(numberOld, numberNew);
  }

  @Test(expected = RuntimeException.class)
  public void testChangeReadFileInvalidFile() {
    il.changeReadFile("fljdskhf");
  }

  @Test(expected = RuntimeException.class)
  public void testChangeReadFileInvalidDoesntExistFile() {
    il.changeReadFile("fljdskhf.json");
  }

  @Test(expected = RuntimeException.class)
  public void testChangeReadFileInvalidNullFile() {
    il.changeReadFile(null);
  }

  @Test(expected = RuntimeException.class)
  public void testChangeReadFileInvalidEmptyFile() {
    il.changeReadFile("   ");
  }

  @Test
  public void testGetPortfolioValuation() {
    Double valueOfRush = il.getPortfolioValuation("rush", "2022-11-01");
    assertEquals(29037.000000000004, valueOfRush, 0);
    Double valueOfRush1 = il.getPortfolioValuation("rush", "2022-11-02");
    assertEquals(27636.0, valueOfRush1, 0);
    Double valueOfRush2 = il.getPortfolioValuation("rush", "2022-11-03");
    assertEquals(26790.0, valueOfRush2, 0);
  }

  @Test
  public void testGetPortfolioValuationDifferentPortfolio() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "2022-11-01");
    assertEquals(15065.0, valueOfRush, 0);
    Double valueOfRush1 = il.getPortfolioValuation("invest fi", "2022-11-02");
    assertEquals(14503.0, valueOfRush1, 0);
    Double valueOfRush2 = il.getPortfolioValuation("invest fi", "2022-11-10");
    assertEquals(29374.0, valueOfRush2, 0);
  }

  @Test
  public void testGetPortfolioValuationDifferentPortfolioStocksDifference() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "2022-11-01");
    assertEquals(15065.0, valueOfRush, 0);
    Double valueOfRush1 = il.getPortfolioValuation("invest fi", "2022-11-04");
    assertEquals(0.0, valueOfRush1, 0);
    Double valueOfRush2 = il.getPortfolioValuation("invest fi", "2022-11-11");
    assertEquals(29939.999999999996, valueOfRush2, 0);
  }

  @Test
  public void testGetPortfolioValuationOnSunday() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "2022-11-05");
    assertEquals(0,valueOfRush,0);
  }

  @Test
  public void testGetPortfolioValuationOnVeryPastDate() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "1900-11-05");
    assertEquals(0,valueOfRush,0);
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnFutureDate() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "2024-11-05");
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnNoPortfolio() {
    Double valueOfRush = il.getPortfolioValuation("invest", "2024-11-05");
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnNullPortfolio() {
    Double valueOfRush = il.getPortfolioValuation(null, "2024-11-05");
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnEmptyPortfolio() {
    Double valueOfRush = il.getPortfolioValuation("  ", "2024-11-05");
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnInvalidDateFormat() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", "2asd-11-05");
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnNullDate() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", null);
  }

  @Test(expected = RuntimeException.class)
  public void testGetPortfolioValuationOnEmptyDate() {
    Double valueOfRush = il.getPortfolioValuation("invest fi", " ");
  }

  @Test
  public void testLoadPortfolioNames() {
    List<String> al = new ArrayList<>();
    al.add("NASDAQ - inflexible");
    al.add("SENSEX - inflexible");
    al.add("Vivdan - inflexible");
    al.add("Rush - inflexible");
    al.add("vivdan - flexible");
    al.add("rockstar - flexible");
    al.add("invest fi - flexible");
    al.add("rush - flexible");
    List<String> al1 = il.loadAllPortfolioNames();
    for (String port : al) {
      if (!al1.contains(port)) {
        fail("Loading portfolios failed");
      }
    }
  }

  @Test
  public void testCreateFlexiblePortfolio() {
    il.createFlexiblePortfolio("junit");
    Map<String, Double> hm = il.loadFlexiblePortfolio("junit", "2022-11-01");
    assertEquals(0, hm.size());
  }

  @Test
  public void testCreateFlexiblePortfolioSaveFormat() {
    il.createFlexiblePortfolio("junit16");
    JSONObject portfolio = getPortfolioObject("junit16");
    assertEquals(0, portfolio.keySet().size());
  }

  @Test(expected = RuntimeException.class)
  public void testCreateFlexiblePortfolioALreadyExists() {
    il.createFlexiblePortfolio("vivdan");
  }

  @Test(expected = RuntimeException.class)
  public void testCreateFlexiblePortfolioNull() {
    il.createFlexiblePortfolio(null);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateFlexiblePortfolioEmptyName() {
    il.createFlexiblePortfolio("  ");
  }

  @Test
  public void testCreateBuyTransaction() {
    il.createFlexiblePortfolio("junit1111");
    il.createBuyTransaction("junit1111", 20, "2022-11-16", "GOOG", 20.0);
    Map<String, Double> hm = il.loadFlexiblePortfolio("junit1111", "2022-11-17");
    assertEquals(1, hm.size());
    assertTrue(hm.containsKey("GOOG"));
    assertEquals(20, hm.get("GOOG"), 0);
  }

  @Test
  public void testCreateMultipleBuyTransaction() {
    il.createFlexiblePortfolio("junit3");
    il.createBuyTransaction("junit3", 20, "2022-11-16", "GOOG", 20.0);
    il.createBuyTransaction("junit3", 20, "2022-11-15", "GOOG", 20.0);
    il.createBuyTransaction("junit3", 20, "2022-11-14", "GOOG", 20.0);
    Map<String, Double> hm = il.loadFlexiblePortfolio("junit3", "2022-11-17");
    assertEquals(1, hm.size());
    assertTrue(hm.containsKey("GOOG"));
    assertEquals(60, hm.get("GOOG"), 0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionPortfolioNotExists() {
    il.createBuyTransaction("junit1", 20, "2022-11-16", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionDateStockIsNotOpen() {
    il.createBuyTransaction("vivdan", 20, "2022-11-13", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionDateFutureDate() {
    il.createBuyTransaction("vivdan", 20, "2022-12-13", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionInvalidStock() {
    il.createBuyTransaction("vivdan", 20, "2022-11-01", "GOOGjksd", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionNegativeComissionFee() {
    il.createBuyTransaction("vivdan", -20, "2022-11-01", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateBuyTransactionNegativeQuanity() {
    il.createBuyTransaction("vivdan", 20, "2022-11-01", "GOOG", -20.0);
  }

  @Test
  public void testCreateSellTransaction() {
    il.createFlexiblePortfolio("junit4");
    il.createBuyTransaction("junit4", 20, "2022-11-16", "GOOG", 20.0);
    il.createBuyTransaction("junit4", 20, "2022-11-15", "GOOG", 20.0);
    il.createBuyTransaction("junit4", 20, "2022-11-14", "GOOG", 20.0);
    il.createSellTransaction("junit4", 20, "2022-11-16", "GOOG", 20.0);
    Map<String, Double> hm1 = il.loadFlexiblePortfolio("junit4", "2022-11-17");
    assertEquals(1, hm1.size());
    assertTrue(hm1.containsKey("GOOG"));
    assertEquals(40, hm1.get("GOOG"), 0);
    il.createSellTransaction("junit4", 20, "2022-11-16", "GOOG", 20.0);
    Map<String, Double> hm2 = il.loadFlexiblePortfolio("junit4", "2022-11-17");
    assertEquals(1, hm2.size());
    assertTrue(hm2.containsKey("GOOG"));
    assertEquals(20, hm2.get("GOOG"), 0);
    il.createSellTransaction("junit4", 20, "2022-11-16", "GOOG", 20.0);
    Map<String, Double> hm3 = il.loadFlexiblePortfolio("junit4", "2022-11-17");
    assertEquals(1, hm3.size());
    assertTrue(hm3.containsKey("GOOG"));
    assertEquals(0, hm3.get("GOOG"), 0);
  }

  @Test
  public void testCreateBuyTransactionSaveFormat() {
    il.createFlexiblePortfolio("junit15");
    il.createBuyTransaction("junit15", 20, "2022-11-16", "GOOG", 20.0);
    JSONObject portfolio = getPortfolioObject("junit15");
    assertEquals(2, portfolio.keySet().size());
    for (Object stockObj : portfolio.keySet()) {
      if (!stockObj.toString().equals("costBasis")) {
        JSONObject stock = (JSONObject) portfolio.get(stockObj.toString());
        for (Object quantity : stock.keySet()) {
          Double stockQuantity = Double.parseDouble(stock.get(quantity.toString()).toString());
          assertEquals(20, stockQuantity,0);
          assertEquals("GOOG", stockObj.toString());
        }
      }
    }
  }

  @Test
  public void testCreateSellTransactionSaveFormat() {
    il.createFlexiblePortfolio("junit14");
    il.createBuyTransaction("junit14", 20, "2022-11-16", "GOOG", 20.0);
    il.createSellTransaction("junit14", 20, "2022-11-16", "GOOG", 20.0);
    JSONObject portfolio = getPortfolioObject("junit14");
    assertEquals(2, portfolio.keySet().size());
    for (Object stockObj : portfolio.keySet()) {
      if (!stockObj.toString().equals("costBasis")) {
        JSONObject stock = (JSONObject) portfolio.get(stockObj.toString());
        for (Object quantity : stock.keySet()) {
          Double stockQuantity = Double.parseDouble(stock.get(quantity.toString()).toString());
          assertEquals(0, stockQuantity,0);
          assertEquals("GOOG", stockObj.toString());
        }
      }
    }
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionPortfolioNotExists() {
    il.createSellTransaction("junit1", 20, "2022-11-16", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionDateStockIsNotOpen() {
    il.createSellTransaction("vivdan", 20, "2022-11-13", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionDateFutureDate() {
    il.createSellTransaction("vivdan", 20, "2022-12-13", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionInvalidStock() {
    il.createSellTransaction("vivdan", 20, "2022-11-01", "GOOGjksd", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionNegativeComissionFee() {
    il.createSellTransaction("vivdan", -20, "2022-11-01", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionNegativeQuanity() {
    il.createSellTransaction("vivdan", 20, "2022-11-01", "GOOG", -20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionInvalidChecks() {
    il.createFlexiblePortfolio("junit5");
    il.createBuyTransaction("junit5", 20, "2022-11-16", "GOOG", 20.0);
    il.createSellTransaction("junit5", 20, "2022-11-15", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionInvalidChecksBeforeBuy() {
    il.createFlexiblePortfolio("junit6");
    il.createSellTransaction("junit6", 20, "2022-11-15", "GOOG", 20.0);
  }

  @Test(expected = RuntimeException.class)
  public void testCreateSellTransactionInvalidChronology() {
    il.createFlexiblePortfolio("junit7");
    il.createBuyTransaction("junit7", 20, "2022-11-14", "GOOG", 20.0);
    il.createSellTransaction("junit7", 20, "2022-11-16", "GOOG", 20.0);
    il.createSellTransaction("junit7", 20, "2022-11-15", "GOOG", 20.0);
  }

  @Test
  public void testCostBasis() {
    il.createFlexiblePortfolio("junit10");
    il.createBuyTransaction("junit10", 20, "2022-11-14", "GOOG", 20.0);
    assertEquals(1920.6 + 20, il.getCostBasis("junit10", "2022-11-14"), 0);
  }

  @Test(expected = RuntimeException.class)
  public void testCostBasisNoStocks() {
    il.createFlexiblePortfolio("junit9");
    il.getCostBasis("junit9", "2022-11-16");
  }

  @Test
  public void testCostBasisAfterSell() {
    il.createFlexiblePortfolio("junit1011");
    il.createBuyTransaction("junit1011", 20, "2022-11-14", "GOOG", 20.0);
    assertEquals(1920.6 + 20, il.getCostBasis("junit1011", "2022-11-14"), 0);
    il.createSellTransaction("junit1011", 30, "2022-11-14", "GOOG", 20.0);
    assertEquals(1920.6 + 20 + 30, il.getCostBasis("junit1011", "2022-11-14"), 0);
  }

  @Test
  public void testCostBasisNoBasis() {
    il.createFlexiblePortfolio("junit11");
    il.createBuyTransaction("junit11", 20, "2022-11-14", "GOOG", 20.0);
    assertEquals(1920.6 + 20, il.getCostBasis("junit11", "2022-11-14"), 0);
    il.createSellTransaction("junit11", 30, "2022-11-14", "GOOG", 20.0);
    assertEquals(1920.6 + 20 + 30, il.getCostBasis("junit11", "2022-11-14"), 0);
    assertEquals(0, il.getCostBasis("junit11", "2022-11-13"), 0);
    assertEquals(1920.6 + 20 + 30, il.getCostBasis("junit11", "2022-11-16"), 0);
  }

  @Test
  public void testLoadFlexiblePortfolioAfterBuys() {
    il.createFlexiblePortfolio("junit12");
    il.createBuyTransaction("junit12", 20, "2022-11-14", "GOOG", 20.0);
    il.createBuyTransaction("junit12", 20, "2022-11-15", "AMZN", 20.0);
    il.createBuyTransaction("junit12", 20, "2022-11-16", "AAPL", 20.0);
    Map<String, Double> hm = il.loadFlexiblePortfolio("junit12", "2022-11-13");
    assertEquals(0, hm.size());
    Map<String, Double> hm1 = il.loadFlexiblePortfolio("junit12", "2022-11-14");
    assertEquals(1, hm1.size());
    assertTrue(hm1.containsKey("GOOG"));
    assertEquals(20, hm1.get("GOOG"), 0);
    Map<String, Double> hm2 = il.loadFlexiblePortfolio("junit12", "2022-11-15");
    assertEquals(2, hm2.size());
    assertTrue(hm2.containsKey("GOOG"));
    assertTrue(hm2.containsKey("AMZN"));
    assertEquals(20, hm2.get("GOOG"), 0);
    assertEquals(20, hm2.get("AMZN"), 0);
    Map<String, Double> hm3 = il.loadFlexiblePortfolio("junit12", "2022-11-16");
    assertEquals(3, hm3.size());
    assertTrue(hm3.containsKey("GOOG"));
    assertTrue(hm3.containsKey("AMZN"));
    assertTrue(hm3.containsKey("AAPL"));
    assertEquals(20, hm3.get("GOOG"), 0);
    assertEquals(20, hm3.get("AMZN"), 0);
    assertEquals(20, hm3.get("AAPL"), 0);
  }

  @Test
  public void testLoadFlexiblePortfolioAfterSells() {
    il.createFlexiblePortfolio("junit13");
    il.createBuyTransaction("junit13", 20, "2022-11-01", "GOOG", 20.0);
    il.createBuyTransaction("junit13", 20, "2022-11-02", "AMZN", 30.0);
    il.createBuyTransaction("junit13", 20, "2022-11-03", "AAPL", 40.0);
    il.createSellTransaction("junit13", 20, "2022-11-01", "GOOG", 20.0);
    Map<String, Double> hm = il.loadFlexiblePortfolio("junit13", "2022-11-02");
    assertEquals(0, hm.get("GOOG"), 0);
    il.createSellTransaction("junit13", 20, "2022-11-04", "AMZN", 30.0);
    Map<String, Double> hm1 = il.loadFlexiblePortfolio("junit13", "2022-11-07");
    assertEquals(0, hm1.get("AMZN"), 0);
    Map<String, Double> hm2 = il.loadFlexiblePortfolio("junit13", "2022-11-03");
    assertEquals(30, hm2.get("AMZN"), 0);
  }

  @Test(expected = RuntimeException.class)
  public void testLoadFlexiblePortfolioDoesNotExist() {
    il.loadFlexiblePortfolio("klcjdskf", "2022-11-01");
  }

  @Test(expected = RuntimeException.class)
  public void testLoadFlexiblePortfolioFurtureDate() {
    il.loadFlexiblePortfolio("vivdan", "2022-12-01");
  }

  @Test(expected = RuntimeException.class)
  public void testLoadFlexiblePortfolioNullPortfolio() {
    il.loadFlexiblePortfolio(null, "2022-11-01");
  }

  @Test(expected = RuntimeException.class)
  public void testLoadFlexiblePortfolioNullDate() {
    il.loadFlexiblePortfolio("vivdan", null);
  }

  @Test
  public void testLoadFlexiblePortfolioPastDate() {
    Map<String, Double> hm = il.loadFlexiblePortfolio("vivdan", "1900-11-01");
    assertEquals(0, hm.size());
  }

  @Test(expected = RuntimeException.class)
  public void testLoadFlexiblePortfolioInvalidDate() {
    System.out.println(il.loadFlexiblePortfolio("vivdan", "2022-1409-10"));
  }

}
