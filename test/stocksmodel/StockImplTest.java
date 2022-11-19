package stocksmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class is a test class for testing the stock model in the stocks program.
 */
public class StockImplTest {

  private Stock s;

  @Before
  public void createStock() {
    s = StockImpl.getBuilder().ticker("GOOG").build();
  }

  @Test
  public void testSymbol() {
    assertEquals("GOOG", s.getSymbol());
  }

  @Test
  public void testSymbolFuzzy() {
    String[] stocks = new String[]{"GOOG", "MSFT", "AAPL", "AMZN", "TWTR", "INTC",
      "GME", "RUM", "VZ", "TMUS"};
    for (int i = 0; i < 100; i++) {
      s = StockImpl.getBuilder().ticker(stocks[i % 10]).build();
      assertEquals(stocks[i % 10], s.getSymbol());
    }
  }

  @Test
  public void testGetValue() {
    assertEquals(new Double(96.58), s.getStockValue("2022-10-28"));
    assertEquals(new Double(96.58), s.getStockValue("2022-10-28"));
  }

  @Test(expected = RuntimeException.class)
  public void testFutureValue() {
    s.getStockValue("2023-10-28");
  }

  @Test(expected = RuntimeException.class)
  public void testSundayValue() {
    s.getStockValue("2022-10-30");
  }

  @Test(expected = RuntimeException.class)
  public void testSaturdayValue() {
    s.getStockValue("2022-10-29");
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidDate() {
    s.getStockValue("2022-10-");
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidDateanother() {
    s.getStockValue("2022-29-10");
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidDatemore() {
    s.getStockValue("20-29-10");
  }

  @Test(expected = RuntimeException.class)
  public void testNoDate() {
    s.getStockValue("");
  }

  @Test(expected = RuntimeException.class)
  public void testNullDate() {
    s.getStockValue(null);
  }

  @Test(expected = RuntimeException.class)
  public void testNoStockValueFile() {
    s = StockImpl.getBuilder().ticker("ewuor").build();
    s.getStockValue("2022-10-28");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalTicker() {
    s = StockImpl.getBuilder().ticker("   ").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalTickeremppty() {
    s = StockImpl.getBuilder().ticker("").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalTickerNull() {
    s = StockImpl.getBuilder().ticker(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptySymbol() {
    s = StockImpl.getBuilder().build();

  }

}