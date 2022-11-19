package stocksmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * The class is a test class for testing the portfolio model in the stocks program.
 */
public class PortfolioImplTest {

  private Portfolio p;

  @Before
  public void createPortfolio() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    HashMap<Stock, Integer> hm = new HashMap<>();
    int qty = 80;
    for (int i = 0; i < 10; i++) {
      Stock s = StockImpl.getBuilder().ticker(stocks[i]).build();
      hm.put(s, qty + 80);
    }
    p = PortfolioImpl.getPortfolioBuilder().name("tst").addStock(hm).build();
  }

  @Test
  public void testStocksNumber() {
    assertEquals(10, p.getStockComposition().keySet().size());
  }

  @Test
  public void testPortfolioName() {
    assertEquals("tst", p.getPortfolioName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    HashMap<Stock, Integer> hm = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      Stock s = StockImpl.getBuilder().ticker(stocks[i]).build();
      hm.put(s, 80);
    }
    p = PortfolioImpl.getPortfolioBuilder().addStock(hm).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyNameSpace() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    HashMap<Stock, Integer> hm = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      Stock s = StockImpl.getBuilder().ticker(stocks[i]).build();
      hm.put(s, 80);
    }
    p = PortfolioImpl.getPortfolioBuilder().name("").addStock(hm).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyNameNull() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    HashMap<Stock, Integer> hm = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      Stock s = StockImpl.getBuilder().ticker(stocks[i]).build();
      hm.put(s, 80);
    }
    p = PortfolioImpl.getPortfolioBuilder().name(null).addStock(hm).build();
  }

  @Test
  public void testStocksAdded() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    Map<Stock, Integer> hm = p.getStockComposition();
    ArrayList<String> al = new ArrayList<>();
    for (Stock s : hm.keySet()) {
      al.add(s.getSymbol());
    }
    for (String s : stocks) {
      if (!al.contains(s)) {
        Assert.fail("Stock was not properly added");
      }
    }
    assertEquals(10, hm.keySet().size());
  }

  @Test
  public void testStocksAddedQuantity() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    Map<Stock, Integer> hm = p.getStockComposition();
    HashMap<String, Integer> hmqty = new HashMap<>();
    int qty = 80;
    for (int i = 0; i < 10; i++) {
      hmqty.put(stocks[i], qty + 80);
    }
    for (Stock s : hm.keySet()) {
      if (!Objects.equals(hm.get(s), hmqty.get(s.getSymbol()))) {
        Assert.fail("Quantity not equal");
      }
    }
    assertEquals(hmqty.keySet().size(), hm.keySet().size());
  }

  @Test
  public void mutationTest() {
    Map<Stock, Integer> hm = p.getStockComposition();
    Stock s = StockImpl.getBuilder().ticker("you").build();
    hm.put(s, 100);
    assertNotEquals(p.getStockComposition().keySet().size(), hm.keySet().size());
  }


}