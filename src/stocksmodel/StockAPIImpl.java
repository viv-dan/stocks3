package stocksmodel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

class StockAPIImpl implements StockAPI {

  private static final String apiKey = "MYWEKXDOJ1DOGTIH";
  private static final HashMap<String, JSONObject> stockDataCache = new HashMap<>();
  private static final ArrayList<String> validStocksCache = new ArrayList<>();
  private static final String URL = "https://www.alphavantage.co/query?function=actualfunction&"
          + "outputsize=full&symbol=ticker&apikey=actualapiKey&datatype=json";

  private static String connectToAPI(String ticker, String function) {
    URL url;
    try {
      url = new URL(URL.replace("ticker", ticker).replace("actualfunction", function)
              .replace("actualapiKey", apiKey));
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot get data from source!!");
    }
    return output.toString();
  }

  @Override
  public boolean validTicker(String ticker) {
    if (validStocksCache.contains(ticker)) {
      return true;
    }
    JSONParser parser = new JSONParser();
    try {
      JSONObject js = (JSONObject) parser.parse(connectToAPI(ticker, "GLOBAL_QUOTE"));
      JSONObject quote = (JSONObject) js.get("Global Quote");
      quote.get("01. symbol").toString();
    } catch (ParseException e) {
      throw new RuntimeException("Cannot validate stock!!");
    } catch (NullPointerException e) {
      return false;
    }
    validStocksCache.add(ticker);
    return true;
  }

  @Override
  public Double getStockClosingByDate(String ticker, String date) {
    if (stockDataCache.containsKey(ticker)) {
      JSONObject totalDateData = (JSONObject) stockDataCache.get(ticker).get("Time Series (Daily)");
      JSONObject dateData = (JSONObject) totalDateData.get(date);
      if (dateData == null) {
        throwException("Cannot get stock data for the given date");
        return null;
      } else {
        return Double.parseDouble(dateData.get("4. close").toString());
      }
    }
    JSONParser parser = new JSONParser();
    try {
      JSONObject wholeData = (JSONObject) parser.parse(connectToAPI(
              ticker, "TIME_SERIES_DAILY"));
      stockDataCache.put(ticker, wholeData);
      JSONObject totalDateData = (JSONObject) wholeData.get("Time Series (Daily)");
      JSONObject dateData = (JSONObject) totalDateData.get(date);
      return Double.parseDouble(dateData.get("4. close").toString());
    } catch (ParseException e) {
      throw new RuntimeException("Cannot get stock value!!");
    } catch (NullPointerException e) {
      throwException("Cannot get stock data for the given date");
      return null;
    }
  }

  private void throwException(String s) {
    throw new IllegalArgumentException(s);
  }
}
