package stockscontroller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stocksmodel.InvestorExtension;
import stocksmodel.InvestorExtensionInvestStrategy;
import stocksmodel.InvestorExtensionStrategyDollarCostAvg;
import stocksmodel.InvestorImplExtension;
import stocksview.GraphicalView;
import stocksview.GraphicalViewImpl;

import static org.junit.Assert.*;

public class ControllerGUITest {
  InvestorExtensionInvestStrategy i;
  Features c;
  private PrintStream output;

  private ByteArrayOutputStream b;
  private class MockView implements GraphicalView{

    @Override
    public void addFeatures(Features feature) {

    }

    @Override
    public String showParticularPortfolio() {
      return "PDP";
    }

    @Override
    public ArrayList<String> showPortfolioDate() {
      ArrayList<String> test = new ArrayList<>();
      test.add("rush");
      test.add("2022-11-11");
      return test;
    }

    @Override
    public void resetFocus() {

    }

    @Override
    public void showInputError(String message) {

    }

    @Override
    public void chooseWhichPortfolio() {

    }

    @Override
    public void chooseBuyOrSell() {

    }

    @Override
    public ArrayList<String> showBuySellForm() {
      return null;
    }

    @Override
    public void chooseWhichPortfolioOption() {

    }

    @Override
    public ArrayList<String> enterGraphDetails() {
      return null;
    }

    @Override
    public void chooseWhichStrategy() {

    }

    @Override
    public void investFixedForm() {

    }

    @Override
    public void showMenu() {

    }

    @Override
    public void showStocks(Map<String, Double> stocks) {
      for (String ticker:stocks.keySet()) {
        output.print(ticker+stocks.get(ticker));
      }
    }

    @Override
    public void showAllPortfolioNames(List<String> names) {
      List<String> n=i.loadAllPortfolioNames();
      for (String name:n) {
        output.println(name);
      }
    }

    @Override
    public void showValueOfPortfolio(double value, String date) {
      output.println(date+value);
    }

    @Override
    public void showCostOfPortfolio(double value, String date) {
      output.print(date+value);
    }

    @Override
    public void successMessage() {

    }

    @Override
    public void plot(Map<String, Double> cost) {

    }
  }

  @Before
  public void setUp(){
    i=new InvestorExtensionStrategyDollarCostAvg();
    c = new ControllerGUI(i);
    c.setView(new MockView());
    b = new ByteArrayOutputStream();
    output = new PrintStream(b);
  }

  @Test
  public void testShowParticularPortfolio(){
    c.showParticularPortfolio();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Map<String,Integer> stocks=i.loadPortfolio("PDP");
    for (String n:stocks.keySet() ) {
      out.print(n+Double.parseDouble(String.valueOf(stocks.get(n))));
    }
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testShowPortfolios(){
    List<String> res= i.loadAllPortfolioNames();
    c.showPortfolios();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    for (String n:res) {
      out.println(n);
    }
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testTotalPortfolioValuation(){
    ArrayList<String> res= new ArrayList<>();
    res.add("PDP");
    res.add("2022-11-11");
    c.totalPortfolioValue(res);
    double value=i.getPortfolioValuation("PDP","2022-11-11");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.println("PDP"+value);
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testShowFlexiblePortfolio(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-11");
    c.showFlexiblePortfolio(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Map<String,Double> stocks=i.loadFlexiblePortfolio("qwerty","2022-11-11");
    for (String n:stocks.keySet() ) {
      out.print(n+Double.parseDouble(String.valueOf(stocks.get(n))));
    }
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testGetCostBasis(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-11");
    c.getCostBasis(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    double value=i.getCostBasis("qwerty","2022-11-11");
    out.println();
    assertEquals(bytes.toString(),b.toString());
  }

}