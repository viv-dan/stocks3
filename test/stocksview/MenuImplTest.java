package stocksview;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for testing the menu view of the stocks program.
 */
public class MenuImplTest {

  private Menu m1;
  private PrintStream output;
  private ByteArrayOutputStream b;

  @Before
  public void setUp() {
    m1 = new MenuImpl();
    b = new ByteArrayOutputStream();
    output = new PrintStream(b);
  }

  private void addMessage(String s) {
    output.println(s);
  }

  @Test
  public void testShowMenu() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.showMenu();
    this.addMessage("Menu");
    this.addMessage("1.Show All Portfolios");
    this.addMessage("2.Show a particular Portfolio");
    this.addMessage("3.Create Portfolio");
    this.addMessage("4.Show Total value of a Portfolio (Value based on closing basis)");
    this.addMessage("5.Load Portfolios from a file");
    this.addMessage("6.Create Flexible Portfolio");
    this.addMessage("7.Buy or Sell a stock in Flexible Portfolio");
    this.addMessage("8.Get Cost basis of a flexible portfolio");
    this.addMessage("9.Graph");
    this.addMessage("10.Exit");
    this.addMessage("Choose any option");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowInputError() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.showInputError();
    this.addMessage("Invalid Input");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowSinglePortfolioMenu() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.showSinglePortfolioMenu();
    this.addMessage("Enter Portfolio Name");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowAllPortfolioNames() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    List<String> names = new ArrayList<>();
    names.add("PDP");
    names.add("First Portfolio");
    m1.showAllPortfolioNames(names);
    for (int i = 0; i < names.size(); i++) {
      String name = names.get(i);
      this.addMessage(name);
    }
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowEmptyPortfolioNames() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    List<String> names = new ArrayList<>();
    m1.showAllPortfolioNames(names);
    this.addMessage("No Portfolios Present Currently");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowStocks() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    Map<String, Double> s = new HashMap<>();
    s.put("AAPL", 10.0);
    s.put("AMZN", 20.0);
    m1.showStocks(s);
    for (String name : s.keySet()) {
      this.addMessage("Stock Ticker " + name + " Quantity " + s.get(name));
    }
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testEmptyShowStocks() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    Map<String, Double> s = new HashMap<>();
    m1.showStocks(s);
    this.addMessage("Portfolio is Empty");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowPortfolioNameError() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.showPortfolioNameError();
    this.addMessage("Portfolio not found");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowValueOfPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    double value;
    value = 1123123.123123;
    m1.showValueOfPortfolio(value, "2022-10-10");
    DecimalFormat df = new DecimalFormat();
    this.addMessage("The value of the portfolio based on closing price on date "
            + "2022-10-10" + " is USD "
            + df.format(value));
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testGoBackMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.goBackMessage();
    this.addMessage("Press Enter to go back");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSuccessMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.successMessage();
    this.addMessage("Done Successfully");
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSetStream() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowMessage() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    m1.showMessage("HELLO WORLD");
    this.addMessage("HELLO WORLD");
    assertEquals(b.toString(), bytes.toString());
  }



  @Test
  public void testPlotCloseValues() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    Map<String, Double> trial = new TreeMap<>();
    trial.put("1", 1207807.11);
    trial.put("2", 1209807.11);
    trial.put("3", 1200007.4);
    trial.put("4", 1207007.0);
    trial.put("5", 1204807.00);
    trial.put("6", 1209907.00);
    m1.plot(trial);
    plotHelper(trial);
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testPlotSkewedValues() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    m1.setStream(out);
    Map<String, Double> trial = new TreeMap<>();
    trial.put("1", 1207807.11);
    trial.put("2", 1207874.11);
    trial.put("3", 2405634.4);
    trial.put("4", 5875699.0);
    trial.put("5", 4215623.00);
    trial.put("6", 1985673.00);
    m1.plot(trial);
    plotHelper(trial);
    assertEquals(b.toString(), bytes.toString());
  }

  private void plotHelper(Map<String, Double> cost) {
    Collection<Double> rows;
    rows = cost.values();
    double max;
    double min;
    max = Collections.max(rows);
    min = Collections.min(rows);
    int scale;
    scale = (int) (max - min);
    int unit = scale / 10;
    double temp;
    for (String s : cost.keySet()) {
      this.output.print(s + " ");
      temp = ((cost.get(s) - min) / unit);
      printStars(Math.toIntExact(Math.round(temp)));
    }
    this.addMessage("Scale: Base Amount : $" + (int) min);
    this.addMessage("Scale: * = $ " + unit);
  }

  private void printStars(int i) {
    int j;
    for (j = 0; j < i; j++) {
      this.output.print("* ");
    }
    this.addMessage("");
  }
}