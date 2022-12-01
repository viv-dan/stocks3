package stockscontroller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void showLoad() {

    }

    @Override
    public void showOffLoad() {

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
      output.print(message);
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
      output.print("Success");
    }

    @Override
    public void plot(Map<String, Double> cost) {
      for (String n: cost.keySet()) {
        output.println(n+cost.get(n));
      }
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
  public void testInvalidTotalPortfolioValuation(){
    ArrayList<String> res= new ArrayList<>();
    res.add("asd");
    res.add("2022-11-11");
    c.totalPortfolioValue(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDateTotalPortfolioValuation(){
    ArrayList<String> res= new ArrayList<>();
    res.add("PDP");
    res.add("2asd-11-11");
    c.totalPortfolioValue(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid date Unparseable date: \"2asd-11-11\"");
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
  public void testInvalidShowFlexiblePortfolio(){
    ArrayList<String> res= new ArrayList<>();
    res.add("hello");
    res.add("2022-11-11");
    c.showFlexiblePortfolio(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDateShowFlexiblePortfolio(){
    ArrayList<String> res= new ArrayList<>();
    res.add("rush");
    res.add("asd-11-11");
    c.showFlexiblePortfolio(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid Date.");
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
    out.print("2022-11-11"+value);
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidGetCostBasis(){
    ArrayList<String> res= new ArrayList<>();
    res.add("asd");
    res.add("2022-11-11");
    c.getCostBasis(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDateGetCostBasis(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("as2-11-11");
    c.getCostBasis(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid Date.");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testPerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Success");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidNamePerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("asd");
    res.add("2022-11-18");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDatePerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("asd-11-18");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid Date.");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidTickerPerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("rus");
    res.add("20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid ticker was given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidQuantityPerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("20.1");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Enter whole numbers for quantity");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNegativeQuantityPerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("-20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNegativeCommissionPerformBuy(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("20");
    res.add("-20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testPerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-28");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performBuy(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Success");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testInvalidNamePerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("asd");
    res.add("2022-11-18");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDatePerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("asd-11-18");
    res.add("V");
    res.add("20");
    res.add("20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid Date.");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidTickerPerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("rus");
    res.add("20");
    res.add("20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid ticker was given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidQuantityPerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("20.1");
    res.add("20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Enter whole numbers for quantity");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNegativeQuantityPerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("-20");
    res.add("20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNegativeCommissionPerformSell(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-18");
    res.add("V");
    res.add("20");
    res.add("-20.23");
    c.performSell(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testPlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-01");
    res.add("2022-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    plot(out);
    assertEquals(bytes.toString(),b.toString());
  }
  private void plot(PrintStream out){
    out.println("2022-11-011349.5431006472672");
    out.println("2022-11-021312.4473175808623");
    out.println("2022-11-031265.0509060364177");
    out.println("2022-11-041269.9704872321822");
    out.println("2022-11-051269.9704872321822");
    out.println("2022-11-061269.9704872321822");
    out.println("2022-11-071276.9781510524972");
    out.println("2022-11-081286.111626151112");
    out.println("2022-11-091254.3684077240691");
  }

  @Test
  public void testInvalidNamePlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qweasd");
    res.add("2022-11-01");
    res.add("2022-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("portfolio doesn't exist!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidStartDatePlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("as2-11-01");
    res.add("2022-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Unparseable date: \"as2-11-01\"");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidEndDatePlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-01");
    res.add("asd-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Unparseable date: \"asd-11-10\"");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testStartDateEqualEndDatePlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-10");
    res.add("2022-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Start date can't be greater or equal to than end date");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testStartDateAfterEndDatePlotGraph(){
    ArrayList<String> res= new ArrayList<>();
    res.add("qwerty");
    res.add("2022-11-11");
    res.add("2022-11-10");
    c.plotGraph(res);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Start date can't be greater or equal to than end date");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testChangeFile(){
    c.changeFile("StocksCopy.xml");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Success");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","2022-11-11",20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Success");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidWeightsInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","2022-11-11",20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid weights");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testInvalidTickerInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("qwrqasf",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","2022-11-11",20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid ticker was given");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testInvalidAmountInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","2022-11-11",20.1,-2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("invalid amount");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidFeesInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","2022-11-11",-20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidNameInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"asdf","2022-11-11",-20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid inputs given!!");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDateInvestAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.investFixedAmount(weights,"rush","asf-11-11",-20.1,2000.0);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Invalid date Unparseable date: \"asf-11-11\"");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"rush",2000.0,"2022-01-01",
            "2022-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing PortfolioSuccess");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNewPortfolioDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            "2022-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Created a new PortfolioSuccess");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testNoEndDatePortfolioDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            null,20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing PortfolioSuccess");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidWeightsDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            "2022-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing Portfolioinvalid weights");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidCommissionDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            "2022-11-11",-20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing Portfolioinvalid input");
    assertEquals(bytes.toString(),b.toString());
  }

  @Test
  public void testInvalidAmountDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",-2000.0,"2022-01-01",
            "2022-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing Portfolioinvalid input");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidDateDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"asd-01-01",
            "2022-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing PortfolioInvalid date Unparseable date: \"asd-01-01\"");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidEndDateDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            "asd-11-11",20.1,"30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing PortfolioInvalid date Unparseable date: \"asd-11-11\"");
    assertEquals(bytes.toString(),b.toString());
  }
  @Test
  public void testInvalidRecurrenceDollarAverageAmount(){
    Map<String,Double> weights=new HashMap<>();
    weights.put("AAPL",50.0);
    weights.put("VZ",12.5);
    weights.put("V",12.5);
    weights.put("GOOG",12.5);
    weights.put("AMZN",12.5);
    c.dollarAverageInvesting(weights,"welcome",2000.0,"2022-01-01",
            "2022-11-11",20.1,"-30");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    out.print("Investing in Existing Portfolioinvalid input");
    assertEquals(bytes.toString(),b.toString());
  }
}