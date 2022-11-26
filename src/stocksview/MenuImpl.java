package stocksview;


import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The class implements the Menu interface for text based program.
 */
public class MenuImpl implements Menu {

  private PrintStream out;

  @Override
  public void showMenu() {
    this.out.println("Menu");
    this.out.println("1.Show All Portfolios");
    this.out.println("2.Show a particular Portfolio");
    this.out.println("3.Create Portfolio");
    this.out.println("4.Show Total value of a Portfolio (Value based on closing basis)");
    this.out.println("5.Load Portfolios from a file");
    this.out.println("6.Create Flexible Portfolio");
    this.out.println("7.Buy or Sell a stock in Flexible Portfolio");
    this.out.println("8.Get Cost basis of a flexible portfolio");
    this.out.println("9.Graph");
    this.out.println("10.Exit");
    this.out.println("Choose any option");

  }

  @Override
  public void showInputError() {
    this.out.println("Invalid Input");
  }

  @Override
  public void showSinglePortfolioMenu() {
    this.out.println("Enter Portfolio Name");
  }

  @Override
  public void showAllPortfolioNames(List<String> names) {
    if (names.isEmpty()) {
      this.out.println("No Portfolios Present Currently");
    }
    for (int i = 0; i < names.size(); i++) {
      String name = names.get(i);
      this.out.println(name);
    }

  }

  @Override
  public void showStocks(Map<String, Double> stocks) {
    if (stocks.isEmpty()) {
      this.out.println("Portfolio is Empty");
    } else {
      for (String name : stocks.keySet()) {
        this.out.println("Stock Ticker " + name + " Quantity " + stocks.get(name));
      }
    }
  }


  @Override
  public void showPortfolioNameError() {
    this.out.println("Portfolio not found");
  }

  @Override
  public void showValueOfPortfolio(double value, String date) {
    DecimalFormat df = new DecimalFormat();
    this.out.println("The value of the portfolio based on closing price on date " + date
            + " is USD " + df.format(value));
  }

  @Override
  public void goBackMessage() {
    this.out.println("Press Enter to go back");
  }

  @Override
  public void successMessage() {
    this.out.println("Done Successfully");
  }

  @Override
  public void setStream(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showMessage(String s) {
    this.out.println(s);
  }

  @Override
  public void showCostOfPortfolio(double value, String date) {
    DecimalFormat df = new DecimalFormat();
    this.out.println("The cost basis of the portfolio on date " + date
            + " is USD " + df.format(value));
  }

  @Override
  public void plot(Map<String, Double> cost) {
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
      this.out.print(s + " ");
      temp = ((cost.get(s) - min) / unit);
      printStars(Math.toIntExact(Math.round(temp)));
    }
    this.out.println("Scale: Base Amount : $" + (int) min);
    this.out.println("Scale: * = $ " + unit);
  }

  private void printStars(int i) {
    int j;
    for (j = 0; j < i; j++) {
      this.out.print("* ");
    }
    this.out.println();
  }




}
