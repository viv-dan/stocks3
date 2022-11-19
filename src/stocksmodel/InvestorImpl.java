package stocksmodel;

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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The class implements Investor interface and its methods to be able to create, persist and
 * valuate portfolios.
 */
class InvestorImpl implements Investor {

  private static String FILENAME = System.getProperty("user.dir") + "/Stocks.xml";
  private static ArrayList<Portfolio> portfolios = new ArrayList<>();

  private static Document getXMLDoc() throws RuntimeException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(FILENAME));
      doc.getDocumentElement().normalize();
      return doc;
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException("Cannot access data.");
    }
  }

  @Override
  public void changeReadFile(String filename) throws RuntimeException {
    if (filename == null || filename.trim().equals("") || !filename.endsWith(".xml")) {
      throw new RuntimeException("Invalid File input given!!");
    }
    File f = new File(System.getProperty("user.dir") + "/" + filename);
    if (f.exists()) {
      FILENAME = System.getProperty("user.dir") + "/" + filename;
      portfolios = new ArrayList<>();
    } else {
      throw new RuntimeException("File doesn't exist in the project directory!!");
    }
  }

  @Override
  public Map<String, Integer> loadPortfolio(String name) throws RuntimeException {
    if (name == null || name.trim().equals("")) {
      throw new RuntimeException("Invalid portfolio name");
    }
    HashMap<String, Integer> portfolioStocks = new HashMap<>();
    for (Portfolio p : portfolios) {
      if (p.getPortfolioName().equals(name)) {
        Map<Stock, Integer> stocksPortfolio = p.getStockComposition();
        for (Stock s : stocksPortfolio.keySet()) {
          portfolioStocks.put(s.getSymbol(), stocksPortfolio.get(s));
        }
        return portfolioStocks;
      }
    }
    Map<Stock, Integer> hm = portfolioRetreiveHelper(name);
    for (Stock s : hm.keySet()) {
      portfolioStocks.put(s.getSymbol(), hm.get(s));
    }
    if (!hm.isEmpty()) {
      Portfolio portfolio = PortfolioImpl.getPortfolioBuilder().addStock(hm).name(name).build();
      portfolios.add(portfolio);
    }
    return portfolioStocks;
  }

  @Override
  public Double getPortfolioValuation(String name, String date) throws RuntimeException {
    if (name == null || name.trim().equals("")) {
      throw new RuntimeException("Invalid portfolio name");
    }
    double valuation = 0;
    for (Portfolio p : portfolios) {
      if (p.getPortfolioName().equals(name)) {
        Map<Stock, Integer> stocksPortfolio = p.getStockComposition();
        for (Stock s : stocksPortfolio.keySet()) {
          valuation += (s.getStockValue(date) * stocksPortfolio.get(s));
        }
        return valuation;
      }
    }
    Map<Stock, Integer> hm = portfolioRetreiveHelper(name);
    for (Stock s : hm.keySet()) {
      valuation += (s.getStockValue(date) * hm.get(s));
    }
    return valuation;
  }

  private Map<Stock, Integer> portfolioRetreiveHelper(String name) {
    HashMap<Stock, Integer> hm = new HashMap<>();
    NodeList portfolioList = getXMLDoc().getElementsByTagName("Portfolio");
    for (int i = 0; i < portfolioList.getLength(); i++) {
      Node portfolio = portfolioList.item(i);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        Element portfolioElement = (Element) portfolio;
        String portfolioName = portfolioElement.getElementsByTagName("Name").item(0)
                .getTextContent();
        if (portfolioName.equals(name)) {
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
            Stock s = StockImpl.getBuilder().ticker(ticker).build();
            hm.put(s, stockQuantity);
          }
        }
      }
    }
    if (hm.isEmpty()) {
      throw new RuntimeException("Portfolio not found!!");
    }
    return hm;
  }

  @Override
  public List<String> loadAllPortfolioNames() throws RuntimeException {
    List<String> portfolioNames = new ArrayList<>();
    NodeList portfolioList = getXMLDoc().getElementsByTagName("Portfolio");
    for (int i = 0; i < portfolioList.getLength(); i++) {
      Node portfolio = portfolioList.item(i);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        Element portfolioElement = (Element) portfolio;
        String portfolioName = portfolioElement.getElementsByTagName("Name").item(0)
                .getTextContent();
        portfolioNames.add(portfolioName);
      }
    }
    return portfolioNames;
  }

  @Override
  public void createPortfolio(String name, Map<String, Integer> stocks) throws RuntimeException {
    if (name == null || name.trim().equals("")) {
      throw new RuntimeException("Invalid portfolio name");
    }
    if (stocks.size() == 0 || stocks == null) {
      throw new RuntimeException("Invalid stocks given");
    }
    HashMap<Stock, Integer> hm = new HashMap<>();
    Document doc = getXMLDoc();
    Element rootPortfolio = doc.getDocumentElement();
    Element newPortfolio = doc.createElement("Portfolio");
    Element portfolioName = doc.createElement("Name");
    portfolioName.appendChild(doc.createTextNode(name));
    newPortfolio.appendChild(portfolioName);
    for (String stockTicker : stocks.keySet()) {
      Element newStock = doc.createElement("Stock");
      Element stockName = doc.createElement("Name");
      stockName.appendChild(doc.createTextNode(stockTicker));
      newStock.appendChild(stockName);
      Element stockQuantity = doc.createElement("Quantity");
      stockQuantity.appendChild(doc.createTextNode(String.valueOf(stocks.get(stockTicker))));
      newStock.appendChild(stockQuantity);
      newPortfolio.appendChild(newStock);
      hm.put(StockImpl.getBuilder().ticker(stockTicker).build(), stocks.get(stockTicker));
    }
    portfolios.add(PortfolioImpl.getPortfolioBuilder().addStock(hm).name(name).build());
    rootPortfolio.appendChild(newPortfolio);
    DOMSource source = new DOMSource(doc);
    this.saveFile(source);
  }

  private void saveFile(DOMSource source) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      StreamResult result = new StreamResult(FILENAME);
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException("Cannot save data to XML File!!");
    }
  }

}
