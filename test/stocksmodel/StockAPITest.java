package stocksmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The class is a test class for testing the StockAPI class of the stocks program.
 */
public class StockAPITest {

  StockAPI sapi;

  @Before
  public void setupAPI() {
    sapi = new StockAPIImpl();
  }

  @Test
  public void testValidTicker() {
    assertTrue(sapi.validTicker("GOOG"));
  }

  @Test
  public void testInvalidTicker() {
    assertFalse(sapi.validTicker("jhsdlf"));
  }

  @Test
  public void testMultipleValidTickers() {
    assertTrue(sapi.validTicker("GOOG"));
    assertTrue(sapi.validTicker("MSFT"));
    assertTrue(sapi.validTicker("AMZN"));
    assertTrue(sapi.validTicker("AAPL"));
  }

  @Test
  public void testMultipleValidTickersCache() {
    assertTrue(sapi.validTicker("GOOG"));
    assertTrue(sapi.validTicker("MSFT"));
    assertTrue(sapi.validTicker("AMZN"));
    assertTrue(sapi.validTicker("AAPL"));
    assertTrue(sapi.validTicker("GOOG"));
    assertTrue(sapi.validTicker("MSFT"));
    assertTrue(sapi.validTicker("AMZN"));
    assertTrue(sapi.validTicker("AAPL"));
  }


  @Test
  public void testGetValueOfStockCache() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOG", "2022-11-01"), 0);
    assertEquals(87.07, sapi.getStockClosingByDate("GOOG", "2022-11-02"), 0);
    assertEquals(83.49, sapi.getStockClosingByDate("GOOG", "2022-11-03"), 0);
  }

  @Test
  public void testGetValueOfStockAAPL() {
    assertEquals(150.65, sapi.getStockClosingByDate("AAPL", "2022-11-01"), 0);
    assertEquals(145.03, sapi.getStockClosingByDate("AAPL", "2022-11-02"), 0);
    assertEquals(138.88, sapi.getStockClosingByDate("AAPL", "2022-11-03"), 0);
  }

  @Test
  public void testGetValueOfStockAMZN() {
    assertEquals(96.79, sapi.getStockClosingByDate("AMZN", "2022-11-01"), 0);
    assertEquals(92.12, sapi.getStockClosingByDate("AMZN", "2022-11-02"), 0);
    assertEquals(89.3, sapi.getStockClosingByDate("AMZN", "2022-11-03"), 0);
  }

  @Test
  public void testGetValueOfStock() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOG", "2022-11-01"), 0);
  }

  @Test
  public void testGetValueOfStockDate1() {
    assertEquals(87.07, sapi.getStockClosingByDate("GOOG", "2022-11-02"), 0);
  }

  @Test
  public void testGetValueOfStockDate2() {
    assertEquals(83.49, sapi.getStockClosingByDate("GOOG", "2022-11-03"), 0);
  }

  @Test(expected = NullPointerException.class)
  public void testGetValueOfStockInvalid() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOGskdljc", "2022-11-01"), 0);
  }

  @Test(expected = NullPointerException.class)
  public void testGetValueOfStockInvalidDate() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOGskdljc", "2022-11-13"), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOfStockInvalidFutureDate() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOGskdljc", "2022-12-13"), 0);
  }

  @Test(expected = NullPointerException.class)
  public void testGetValueOfStockInvalidPastDate() {
    assertEquals(90.5, sapi.getStockClosingByDate("GOOGskdljc", "1900-12-13"), 0);
  }
}
