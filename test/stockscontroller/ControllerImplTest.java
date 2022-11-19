package stockscontroller;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stocksmodel.InvestorExtension;
import stocksmodel.InvestorImplExtension;
import stocksview.Menu;
import stocksview.MenuImpl;

import static org.junit.Assert.assertEquals;

/**
 * The class is a test class for testing the controller of the stocks program.
 */
public class ControllerImplTest {

  private InvestorExtension i1;
  private Menu m1;
  private PrintStream output;
  private Controller c;
  private ByteArrayOutputStream b;


  @Before
  public void setUp() {
    i1 = new InvestorImplExtension();
    m1 = new MenuImpl();
    b = new ByteArrayOutputStream();
    output = new PrintStream(b);
  }

  private void setOutputToMenu() {
    this.output.println("Menu");
    this.output.println("1.Show All Portfolios");
    this.output.println("2.Show a particular Portfolio");
    this.output.println("3.Create Portfolio");
    this.output.println("4.Show Total value of a Portfolio (Value based on closing basis)");
    this.output.println("5.Load Portfolios from a file");
    this.output.println("6.Create Flexible Portfolio");
    this.output.println("7.Buy or Sell a stock in Flexible Portfolio");
    this.output.println("8.Get Cost basis of a flexible portfolio");
    this.output.println("9.Graph");
    this.output.println("10.Exit");
    this.output.println("Choose any option");
  }

  private void addMessage(String s) {
    this.output.println(s);
  }

  @Test
  public void testFirstMenu() {
    InputStream in = new ByteArrayInputStream("10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidInputInFirstMenu() {
    InputStream in = new ByteArrayInputStream("100 \n 10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Invalid Input");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidAlphabetInFirstMenu() {
    Reader f = new StringReader("6");
    InputStream in = new ByteArrayInputStream("asd \n\n 10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }


  @Test
  public void testShowAllPortfolios() {
    InputStream in = new ByteArrayInputStream("1 \n \n  10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    for (String s : i1.loadAllPortfolioNames()) {
      this.addMessage(s);
    }
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularValidPortfolio() {
    InputStream in = new ByteArrayInputStream(("2 \n2\nPDP\n \n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Show Flexible Portfolio");
    this.addMessage("2.Show Inflexible Portfolio");
    this.addMessage("Enter Portfolio Name");
    Map<String, Integer> stocks = i1.loadPortfolio("PDP");
    for (String name : stocks.keySet()) {
      this.addMessage("Stock Ticker " + name + " Quantity " + stocks.get(name));
    }
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularInvalidPortfolio() {
    InputStream in = new ByteArrayInputStream(("2 \n2\nasd\n \n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Show Flexible Portfolio");
    this.addMessage("2.Show Inflexible Portfolio");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Portfolio not found!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularInvalidPortfolioValue() {
    InputStream in = new ByteArrayInputStream(("4 \nqwerty\n2022-11-11\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    this.addMessage("portfolio doesn't exist!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularValidPortfolioValue() {
    InputStream in = new ByteArrayInputStream(("4 \nPDP\n2022-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getPortfolioValuation("PDP", "2022-02-14");
    this.addMessage("The value of the portfolio based on closing price on date " + "2022-02-14"
            + " is USD " + df.format(value));
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularValidFlexiblePortfolioValue() {
    InputStream in = new ByteArrayInputStream(("4 \nrush\n2022-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getPortfolioValuation("rush", "2022-02-14");
    this.addMessage("The value of the portfolio based on closing price on date " + "2022-02-14"
            + " is USD " + df.format(value));
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularValidPortfolioValueWithoutDateData() {
    InputStream in = new ByteArrayInputStream(("4 \nPDP\n2023-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getPortfolioValuation("PDP", "2022-02-14");
    this.addMessage("Cannot get stock data for the given date");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularValidFlexiblePortfolioValueWithoutDateData() {
    InputStream in = new ByteArrayInputStream(("4 \nrush\n2023-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getPortfolioValuation("rush", "2022-02-14");
    this.addMessage("Cannot get stock data for the given date");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularInvalidDatePortfolioValue() {
    InputStream in = new ByteArrayInputStream(("4 \nPDP\nasd-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    this.addMessage("Invalid date");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testShowParticularInvalidDateFlexiblePortfolioValue() {
    InputStream in = new ByteArrayInputStream(("4 \nrush\nasd-02-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    this.addMessage("Invalid date");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateInvalidNamePortfolio() {
    InputStream in = new ByteArrayInputStream(("3 \nPDP\n\n 10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Portfolio Exists Already with the name");
    this.addMessage("Press Enter to go back");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateInvalidNameFlexiblePortfolio() {
    InputStream in = new ByteArrayInputStream(("6 \nPDP\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Portfolio already exists!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateValidNameFlexiblePortfolio() {
    InputStream in = new ByteArrayInputStream(("6 \ntesters\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateValidPortfolio() {
    InputStream in = new ByteArrayInputStream(
            ("3\nTesting Controller\n2\n AMZN\n 40\n AAPL\n 50\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter Number of Stocks in integer");
    int i;
    int j;
    for (i = 0; i < 2; i++) {
      j = 1;
      this.addMessage("Enter a ticker");
      this.addMessage("Enter Quantity in integer");
    }
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateValidPortfolioWithInvalidNumberOfStocks() {
    InputStream in = new ByteArrayInputStream(("3\nru\n asd\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter Number of Stocks in integer");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreatePortfolioWithInvalidTicker() {
    InputStream in = new ByteArrayInputStream(("3\nru\n1\nasd\n10\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter Number of Stocks in integer");
    int j;
    j = 1;
    this.addMessage("Enter a ticker");
    this.addMessage("Enter Quantity in integer");
    this.addMessage("Invalid ticker was given!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }


  @Test
  public void testValidChangeInUserFile() {
    InputStream in = new ByteArrayInputStream(("5\nstockscopy.xml\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testValidJsonChangeInUserFile() {
    InputStream in = new ByteArrayInputStream(("5\nstocks2Copy.json\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidChangeInUserFileWithNoExtension() {
    InputStream in = new ByteArrayInputStream(("5\nstockscopy\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("invalid file given!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidChangeInUserFile() {
    InputStream in = new ByteArrayInputStream(("5\nrush.xml\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("File doesn't exist in the project directory!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidJsonChangeInUserFile() {
    InputStream in = new ByteArrayInputStream(("5\nrush.json\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("File doesn't exist in the project directory!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testInvalidChangeInUserFileWithWrongExtension() {
    InputStream in = new ByteArrayInputStream(("5\nstocks.pdf\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    this.addMessage("Or");
    this.addMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    this.addMessage("invalid file given!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerValidBuyTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n1\ntesters\nAAPL\n120\n20.2\n2020-11-10\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidPortfolioBuyTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n1\nasd\nAAPL\n120\n20.2\n2020-11-10\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Portfolio doesn't exist!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidTickerBuyTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n1\ntesters\nqwerty\n120\n20.2\n2020-11-10\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Invalid ticker was given!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidQuantityBuyTransaction() {
    InputStream in = new ByteArrayInputStream(("7\n1\ntesters\nAAPL\n-120\n20.2\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidCommissionBuyTransaction() {
    InputStream in = new ByteArrayInputStream(("7\n1\ntesters\nAAPL\n120\n-20.2\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidNumberTransactionOption() {
    InputStream in = new ByteArrayInputStream(("7\n12\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidAlphabetTransactionOption() {
    InputStream in = new ByteArrayInputStream(("7\nasd\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidBuyTransactionDateField() {
    InputStream in = new ByteArrayInputStream(
            ("7\n1\ntesters\nAAPL\n120\n20.2\nasd\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("invalid Date.");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidBuyTransactionClosedDate() {
    InputStream in = new ByteArrayInputStream(("7\n1\ntesters\nAAPL\n120\n20.2\n2022-01-01\n\n10")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Invalid date entered. Please try another valid date where stock market "
            + "is open.");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerCostBasis() {
    InputStream in = new ByteArrayInputStream(("8 \ntesters\n2022-11-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getCostBasis("testers", "2022-11-14");
    this.addMessage("The cost basis of the portfolio on date " + "2022-11-14"
            + " is USD " + df.format(value));
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidDateCostBasis() {
    InputStream in = new ByteArrayInputStream(("8 \ntesters\nasd-11-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getCostBasis("testers", "2022-11-14");
    this.addMessage("invalid Date.");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidPortfolioCostBasis() {
    InputStream in = new ByteArrayInputStream(("8 \nqwerty\n2022-11-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    double value;
    value = i1.getCostBasis("testers", "2022-11-14");
    this.addMessage("portfolio doesn't exist!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerFutureDateCostBasis() {
    InputStream in = new ByteArrayInputStream(("8 \ntesters\n2023-11-14\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter date for value in YYYY-MM-DD format");
    DecimalFormat df = new DecimalFormat();
    this.addMessage("Cannot do a transaction on future dates!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerValidSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\ntesters\nAAPL\n120\n23.2\n2022-11-16\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Done Successfully");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\ntesters\nAAPL\n120\n23.2\n2015-11-16\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Invalid sell. Not enough shares!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidPortfolioTickerSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\ntesters\nGOOG\n120\n23.2\n2015-11-16\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Cannot sell before buying of stock!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidTickerSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\ntesters\nasd\n120\n23.2\n2015-11-16\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Invalid ticker was given!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidQuantitySellTransaction() {
    InputStream in = new ByteArrayInputStream(("7\n2\ntesters\nAAPL\n-120\n23.2\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidCommissionSellTransaction() {
    InputStream in = new ByteArrayInputStream(("7\n2\ntesters\nAAPL\n120\n-23.2\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Invalid Input");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerPortfolioNameSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\nasd\nAAPL\n120\n23.2\n2022-11-16\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Choose Among the options");
    this.addMessage("1.Buy");
    this.addMessage("2.Sell");
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter valid ticker");
    this.addMessage("Enter quantity of transaction");
    this.addMessage("Enter Commission fees for the transaction");
    this.addMessage("Enter date of transaction in YYYY-MM-DD format");
    this.addMessage("Portfolio doesn't exist!!");
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerGraph() {
    InputStream in = new ByteArrayInputStream(
            ("9 \nrush\n2022-11-05\n2022-11-15\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter start date in YYYY-MM-DD format");
    this.addMessage("Enter end  date in YYYY-MM-DD format");
    Map<String, Double> trial = new HashMap<>();
    int i;
    this.plotprinter();
    DecimalFormat df = new DecimalFormat();
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidDateGraph() {
    InputStream in = new ByteArrayInputStream(
            ("9 \nrush\n2022-11-05\n2023-11-15\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter start date in YYYY-MM-DD format");
    this.addMessage("Enter end  date in YYYY-MM-DD format");
    this.addMessage("Invalid Input");
    DecimalFormat df = new DecimalFormat();
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidPortfolioGraph() {
    InputStream in = new ByteArrayInputStream(
            ("9 \nqwre\n2022-11-05\n2022-11-15\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter start date in YYYY-MM-DD format");
    this.addMessage("Enter end  date in YYYY-MM-DD format");
    this.addMessage("Invalid Input");
    DecimalFormat df = new DecimalFormat();
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testControllerInvalidDateFormatGraph() {
    InputStream in = new ByteArrayInputStream(
            ("9 \nrush\n2022-11-05\nasf-15\n\n  10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    c = new ControllerImpl(i1, m1, in, out);
    c.startProgram();
    this.setOutputToMenu();
    this.addMessage("Enter Portfolio Name");
    this.addMessage("Enter start date in YYYY-MM-DD format");
    this.addMessage("Enter end  date in YYYY-MM-DD format");
    this.addMessage("Invalid Input");
    DecimalFormat df = new DecimalFormat();
    this.addMessage("Press Enter to go back");
    this.setOutputToMenu();
    assertEquals(b.toString(), bytes.toString());
  }


  private void plotprinter() {
    this.output.println("2022-11-05 ");
    this.output.println("2022-11-06 ");
    this.output.println("2022-11-07 ");
    this.output.println("2022-11-08 * * * * * * * ");
    this.output.println("2022-11-09 * * * * * * * * * ");
    this.output.println("2022-11-10 * * * * * * * * * * ");
    this.output.println("2022-11-11 * * * * * * * * * * ");
    this.output.println("2022-11-12 * * * * * * * * * * ");
    this.output.println("2022-11-13 * * * * * * * * * * ");
    this.output.println("2022-11-14 * * * * * * * * * * ");
    this.output.println("Scale: Base Amount : $27159");
    this.output.println("Scale: * = $ 5703");
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

  @Test
  public void testControllerWithMockInvestorLoadNames() {
    InputStream in = new ByteArrayInputStream("1\n\n\n 10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("loadAllPortfolioNames called");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testControllerWithMockInvestorLoadOnePortfolio() {
    InputStream in = new ByteArrayInputStream("2\n2\nrushikesh\n\n 10".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("loadPortfolio called with value " + "rushikesh");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testControllerWithGetValue() {
    InputStream in = new ByteArrayInputStream(("4 \ntesters\n2022-02-14\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("getPortfolioValuation called with value testers and 2022-02-14");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testControllerWithMockInvestorCreatePortfolio() {
    InputStream in = new ByteArrayInputStream(("3\nhub\n1\nAAPL\n30\n\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("loadPortfolio called with value hub");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testChangedReadFile() {
    InputStream in = new ByteArrayInputStream(("5\nstock.xml\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("changedReadFile called with value stock.xml");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockCreateFlexiblePortfolio() {
    InputStream in = new ByteArrayInputStream(("6\nhello\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("loadPortfolio called with value hello");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockBuyTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n1\nrushikesh\nAAPL\n10\n10.3\n2022-11-11\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("createBuyTransaction with name rushikesh"
            + "commission value 10.3 Date 2022-11-11"
            + " ticker AAPL quantity 10");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockSellTransaction() {
    InputStream in = new ByteArrayInputStream(
            ("7\n2\nrushikesh\nAAPL\n10\n10.3\n2022-11-11\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("createSellTransaction with name rushikesh"
            + "commission value 10.3 Date 2022-11-11"
            + " ticker AAPL quantity 10");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockCostBasis() {
    InputStream in = new ByteArrayInputStream(("8\nrushikesh\n2022-11-11\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("getCostBasis called with name rushikesh date 2022-11-11");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockLoadFlexiblePortfolio() {
    InputStream in = new ByteArrayInputStream(("2\n1\nrushikesh\n2022-11-11\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("loadFlexiblePortfolio called with name rushikesh date 2022-11-11");
    assertEquals(mockBytes.toString(), b.toString());
  }

  @Test
  public void testMockGraph() {
    InputStream in = new ByteArrayInputStream(
            ("9\nrushikesh\n2022-11-05\n2022-11-11\n\n10").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    ByteArrayOutputStream mockBytes = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(mockBytes);
    InvestorExtension mock = new MockInvestor(mockOut);
    c = new ControllerImpl(mock, m1, in, out);
    c.startProgram();
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-10-11");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-05");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-06");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-07");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-08");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-09");
    this.addMessage("getPortfolioValuation called with value rushikesh and 2022-11-10");
    assertEquals(mockBytes.toString(), b.toString());
  }


  /**
   * This is mock investor class to test the controller class in isolation to test the communication
   * of the controller towards investor model.
   */
  class MockInvestor implements InvestorExtension {

    private final PrintStream op;

    /**
     * The constructor creates a mock investor model. It takes in a printstream to maintain a log
     * for documenting the calls.
     *
     * @param op the print stream to maintain logs
     */
    MockInvestor(PrintStream op) {
      this.op = op;
    }


    @Override
    public void changeReadFile(String filename) throws RuntimeException {
      op.println("changedReadFile called with value " + filename);
    }

    @Override
    public Map<String, Integer> loadPortfolio(String name) throws RuntimeException {
      op.println("loadPortfolio called with value " + name);
      if (name.equals("PDP")) {
        Map<String, Integer> r = new HashMap<>();
        r.put("TWZ", 10);
        return r;
      }
      return new HashMap<>();
    }

    @Override
    public Double getPortfolioValuation(String name, String date) throws RuntimeException {
      op.println("getPortfolioValuation called with value " + name + " and " + date);
      return 0.0;
    }

    @Override
    public List<String> loadAllPortfolioNames() throws RuntimeException {
      op.println("loadAllPortfolioNames called");
      return null;
    }

    @Override
    public void createPortfolio(String name, Map<String, Integer> stocks) throws RuntimeException {
      op.println("createPortfolio called with name " + name);
      for (String s : stocks.keySet()) {
        op.println("Stock name " + s + " Qty " + stocks.get(s));
      }
    }

    @Override
    public void createFlexiblePortfolio(String name) throws RuntimeException {
      op.println("createFlexiblePortfolio called with name " + name);
    }

    @Override
    public void createSellTransaction(String portfolio, double commissionFeeValue,
                                      String date, String ticker,
                                      Integer quantity) throws RuntimeException {
      op.println("createSellTransaction with name " + portfolio
              + "commission value " + commissionFeeValue + " Date "
              + date + " ticker " + ticker + " quantity " + quantity);
    }

    @Override
    public void createBuyTransaction(String portfolio, double commissionFeeValue,
                                     String date, String ticker,
                                     Integer quantity) throws IllegalArgumentException {
      op.println("createBuyTransaction with name " + portfolio
              + "commission value " + commissionFeeValue + " Date "
              + date + " ticker " + ticker + " quantity " + quantity);
    }

    @Override
    public Double getCostBasis(String portfolioName, String date) throws IllegalArgumentException {
      op.println("getCostBasis called with name " + portfolioName + " date " + date);
      return null;
    }

    @Override
    public Map<String, Integer> loadFlexiblePortfolio(String name, String date)
            throws RuntimeException {
      op.println("loadFlexiblePortfolio called with name " + name + " date " + date);
      return null;
    }
  }
}