package stocksmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * The class is a test class for testing the Investor model in the stocks program.
 */
public class InvestorImplTest {

  private Investor il;

  @Before
  public void createInvestor() {
    il = new InvestorImplExtension();
  }

  @Test
  public void testChangeReadFile() {
    int stocksSize = il.loadAllPortfolioNames().size();
    il.changeReadFile("StocksCopy.xml");
    int stocksCopySize = il.loadAllPortfolioNames().size();
    assertNotEquals(stocksSize, stocksCopySize);
    il.changeReadFile("Stocks.xml");
  }

  @Test(expected = RuntimeException.class)
  public void testExceptionChangeReadFile() {
    il.changeReadFile("kjsdhijhoria");
  }

  @Test(expected = RuntimeException.class)
  public void testExceptionChangeReadFileNotThere() {
    il.changeReadFile("kjsdhijhoria.xml");
  }

  @Test(expected = RuntimeException.class)
  public void testExceptionChangeReadFileNull() {
    il.changeReadFile(null);
  }

  @Test(expected = RuntimeException.class)
  public void testExceptionChangeReadFileEmpty() {
    il.changeReadFile("");
  }


  @Test
  public void testLoadPortfolio() {
    Map<String, Integer> hm = il.loadPortfolio("PDP");
    ArrayList<String> al = new ArrayList<>();
    al.add("MSFT");
    al.add("AMZN");
    al.add("INTC");
    for (String key : hm.keySet()) {
      if (!al.contains(key)) {
        Assert.fail("key not found");
      }
    }
    assertEquals(23, hm.get("MSFT"), 0);
    assertEquals(100, hm.get("INTC"), 0);
    assertEquals(6438, hm.get("AMZN"), 0);
  }

  @Test
  public void testLoadPortfolioSENSEX() {
    Map<String, Integer> hm = il.loadPortfolio("SENSEX");
    ArrayList<String> al = new ArrayList<>();
    al.add("MSFT");
    al.add("AMZN");
    al.add("AAPL");
    al.add("GOOG");
    for (String key : hm.keySet()) {
      if (!al.contains(key)) {
        Assert.fail("key not found");
      }
    }
    assertEquals(100, hm.get("MSFT"), 0);
    assertEquals(120, hm.get("GOOG"), 0);
    assertEquals(150, hm.get("AMZN"), 0);
    assertEquals(90, hm.get("AAPL"), 0);
  }

  @Test
  public void testLoadPortfolioCache() {
    Map<String, Integer> hm = il.loadPortfolio("SENSEX");
    ArrayList<String> al = new ArrayList<>();
    al.add("MSFT");
    al.add("AMZN");
    al.add("AAPL");
    al.add("GOOG");
    for (String key : hm.keySet()) {
      if (!al.contains(key)) {
        Assert.fail("key not found");
      }
    }
    assertEquals(100, hm.get("MSFT"), 0);
    assertEquals(120, hm.get("GOOG"), 0);
    assertEquals(150, hm.get("AMZN"), 0);
    assertEquals(90, hm.get("AAPL"), 0);
    hm = il.loadPortfolio("SENSEX");
    al = new ArrayList<>();
    al.add("MSFT");
    al.add("AMZN");
    al.add("AAPL");
    al.add("GOOG");
    for (String key : hm.keySet()) {
      if (!al.contains(key)) {
        Assert.fail("key not found");
      }
    }
    assertEquals(100, hm.get("MSFT"), 0);
    assertEquals(120, hm.get("GOOG"), 0);
    assertEquals(150, hm.get("AMZN"), 0);
    assertEquals(90, hm.get("AAPL"), 0);
  }

  @Test(expected = RuntimeException.class)
  public void testLoadPortfolioInvalidInput() {
    Map<String, Integer> hm = il.loadPortfolio("lkaheofijseoc");
    assertEquals(0, hm.size());
  }

  @Test(expected = RuntimeException.class)
  public void testLoadPortfolioInvalidPortfolio() {
    Map<String, Integer> hm = il.loadPortfolio("");
  }

  @Test(expected = RuntimeException.class)
  public void testLoadPortfolioInvalidPortfolioNull() {
    Map<String, Integer> hm = il.loadPortfolio(null);
  }

  @Test(expected = RuntimeException.class)
  public void testLoadPortfolioInvalidInputAnother() {
    Map<String, Integer> hm = il.loadPortfolio("SENSE");
    assertEquals(0, hm.size());
  }

  @Test
  public void testPortfolioValuation() {
    Double d = il.getPortfolioValuation("PDP", "2022-02-14");
    assertEquals(19990845.92, d, 0);
  }

  @Test
  public void testPortolioValuationAnother() {
    Double d = il.getPortfolioValuation("SENSEX", "2022-10-28");
    assertEquals(64704.7, d, 0);
  }

  @Test
  public void testPortfolioValuationCache() {
    Double d = il.getPortfolioValuation("SENSEX", "2022-10-28");
    assertEquals(64704.7, d, 0);
    il.loadPortfolio("SENSEX");
    d = il.getPortfolioValuation("SENSEX", "2022-10-28");
    assertEquals(64704.7, d, 0);
  }

  @Test(expected = RuntimeException.class)
  public void testPortfolioValuationOnSunday() {
    Double d = il.getPortfolioValuation("SENSEX", "2022-10-23");
  }

  @Test(expected = RuntimeException.class)
  public void testPortfolioValuationNullValue() {
    Double d = il.getPortfolioValuation(null, "2022-10-28");
  }

  @Test(expected = RuntimeException.class)
  public void testPortfolioValuationNoValue() {
    Double d = il.getPortfolioValuation("", "2022-10-28");
  }

  @Test(expected = RuntimeException.class)
  public void testPortfolioValuationOnFutureData() {
    Double d = il.getPortfolioValuation("SENSEX", "2023-10-23");
  }

  @Test(expected = RuntimeException.class)
  public void testPortfolioValuationOnVeryPastDate() {
    Double d = il.getPortfolioValuation("SENSEX", "1900-10-23");
  }

  @Test
  public void testAllPortfolioNames() {
    List<String> l = il.loadAllPortfolioNames();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(System.getProperty("user.dir") + "/Stocks.xml"));
      doc.getDocumentElement().normalize();
      NodeList portfolioList = doc.getElementsByTagName("Portfolio");
      for (int i = 0; i < portfolioList.getLength(); i++) {
        Node portfolio = portfolioList.item(i);
        Element portfolioElement = (Element) portfolio;
        String portfolioName = portfolioElement.getElementsByTagName("Name").item(0)
                .getTextContent() + " - inflexible";
        if (!l.contains(portfolioName)) {
          Assert.fail("failed because of portfolios not matched");
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      Assert.fail("something happened");
    }
  }

  @Test
  public void testCreatePortfolio() {
    Map<String, Integer> hm = new HashMap<>();
    hm.put("GOOG", 46);
    hm.put("MSFT", 78);
    hm.put("AMZN", 100);
    hm.put("VZ", 90);
    hm.put("GME", 40);
    hm.put("INTC", 80);
    il.createPortfolio("testing from junit", hm);
    Map<String, Integer> m = il.loadPortfolio("testing from junit");
    assertEquals(m.size(), hm.size());
    for (String key : m.keySet()) {
      if (!hm.containsKey(key)) {
        Assert.fail("failed create portfolio");
      }
    }
  }

  @Test
  public void testCreatePortfolioSavingFormat() {
    Map<String, Integer> hm = new HashMap<>();
    hm.put("GOOG", 46);
    hm.put("MSFT", 78);
    hm.put("AMZN", 100);
    hm.put("VZ", 90);
    hm.put("GME", 40);
    hm.put("INTC", 80);
    il.createPortfolio("testing from junit", hm);
    NodeList portfolioList = getXMLDoc().getElementsByTagName("Portfolio");
    for (int i = 0; i < portfolioList.getLength(); i++) {
      Node portfolio = portfolioList.item(i);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        Element portfolioElement = (Element) portfolio;
        String portfolioName = portfolioElement.getElementsByTagName("Name").item(0)
                .getTextContent();
        if (portfolioName.equals("testing from junit")) {
          NodeList stockList = portfolioElement.getElementsByTagName("Stock");
          for (int j = 0; j < stockList.getLength(); j++) {
            Node stockNode = stockList.item(j);
            String ticker = null;
            int stockQuantity = 0;
            if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
              Element stockElement = (Element) stockNode;
              ticker = stockElement.getElementsByTagName("Name").item(0).getTextContent();
              stockQuantity = Integer.parseInt(stockElement.getElementsByTagName("Quantity")
                      .item(0).getTextContent());
            }
            assertTrue(hm.containsKey(ticker));
            assertEquals(hm.get(ticker), stockQuantity, 0);
            Stock s = StockImpl.getBuilder().ticker(ticker).build();
          }
        }
      }
    }
  }

  private static Document getXMLDoc() throws RuntimeException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(System.getProperty("user.dir") + "/Stocks.xml"));
      doc.getDocumentElement().normalize();
      return doc;
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException("Cannot access data.");
    }
  }

  @Test(expected = RuntimeException.class)
  public void testCreatePortfoliowithEmptyName() {
    Map<String, Integer> hm = new HashMap<>();
    hm.put("GOOG", 46);
    hm.put("MSFT", 78);
    hm.put("AMZN", 100);
    hm.put("VZ", 90);
    hm.put("GME", 40);
    hm.put("INTC", 80);
    il.createPortfolio("", hm);
  }

  @Test(expected = RuntimeException.class)
  public void testCreatePortfoliowithNullName() {
    Map<String, Integer> hm = new HashMap<>();
    hm.put("GOOG", 46);
    hm.put("MSFT", 78);
    hm.put("AMZN", 100);
    hm.put("VZ", 90);
    hm.put("GME", 40);
    hm.put("INTC", 80);
    il.createPortfolio(null, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testCreatePortfoliowithEmptyStocks() {
    Map<String, Integer> hm = new HashMap<>();
    il.createPortfolio(null, hm);
  }

  @Test(expected = RuntimeException.class)
  public void testCreatePortfoliowithNullStocks() {
    il.createPortfolio("PDP", null);
  }


}
