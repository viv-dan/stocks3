package stocksview;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stockscontroller.Features;

public class GraphicalViewImpl extends JFrame implements GraphicalView{


  private JButton showPortfolio;
  private JButton showParticularPortfolio;
  private JButton createPortfolio;
  private JButton getCostBasis;
  private JButton performTransaction;
  private JButton createFlexible;
  private JButton getFlexiblePortfolio;
  private JButton getInflexiblePortfolio;
  private JButton investAmount;
  private JButton highLevelStrategy;
  private JButton exitButton;
  private JButton buy;
  private JButton sell;
  private JButton goBack;
  private JButton showValue;
  private JButton investStrategy;
  private JButton plotGraph;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  public GraphicalViewImpl(){
    super("Welcome");
    goBack = new JButton("Go Back to Main Menu");
    goBack.setActionCommand("GoBack");
    getInflexiblePortfolio=new JButton("Inflexible Portfolio");
    getFlexiblePortfolio= new JButton("Flexible Portfolio");
    buy=new JButton("Buy a stock in a Portfolio");
    buy.setActionCommand("buy");
    sell=new JButton("Sell a stock in a Portfolio");
    sell.setActionCommand("sell");
    createFlexible=new JButton("Create Flexible Portfolio");
    createFlexible.setActionCommand("createFlexible");
    investAmount=new JButton("Invest Fixed Amount in Existing Portfolio");
    investAmount.setActionCommand("fixedAmount");
    highLevelStrategy=new JButton("Dollar Cost Averaging Strategy");
    highLevelStrategy.setActionCommand("highLevelInvest");
    setSize(400,400);
    setLocation(300,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    setMenu();
  }
  public void setMenu(){
    mainPanel.removeAll();
    mainPanel.repaint();
    JPanel menu = new JPanel();
    menu.setBorder(BorderFactory.createTitledBorder("Menu"));
    menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
    //Buttons
    showPortfolio=new JButton("Show All Portfolios");
    showPortfolio.setActionCommand("showPortfolio");
    showParticularPortfolio=new JButton("Show Particular Portfolios");
    showParticularPortfolio.setActionCommand("showParticularPortfolio");
    createPortfolio=new JButton("Create Flexible Portfolio");
    createPortfolio.setActionCommand("createPortfolio");
    showValue = new JButton("Portfolio Value");
    showValue.setActionCommand("valueOfP");
    getCostBasis=new JButton("Get cost Basis of a particular Portfolio");
    getCostBasis.setActionCommand("costBasis");
    performTransaction=new JButton("Perform a Buy or Sell Transaction");
    performTransaction.setActionCommand("performBuySell");
    investStrategy=new JButton("Investment Strategy");
    investStrategy.setActionCommand("strategy");
    plotGraph=new JButton("Graph");
    plotGraph.setActionCommand("graph");
    exitButton=new JButton("Exit");
    exitButton.setActionCommand("exit");
    //Add Components
    mainPanel.add(menu);
    menu.add(showPortfolio);
    menu.add(showParticularPortfolio);
    menu.add(createPortfolio);
    menu.add(showValue);
    menu.add(getCostBasis);
    menu.add(performTransaction);
    menu.add(investStrategy);
    menu.add(plotGraph);
    menu.add(exitButton);
    setVisible(true);
    resetFocus();
  }


  @Override
  public void addFeatures(Features feature) {
    showPortfolio.addActionListener(e -> feature.showPortfolios());
    showParticularPortfolio.addActionListener(e -> this.chooseWhichPortfolio());
    goBack.addActionListener(e -> feature.goToMainMenu());
    createPortfolio.addActionListener(e -> this.chooseWhichPortfolioOption());
    showValue.addActionListener(e -> feature.totalPortfolioValue(this.showPortfolioDate()));
    getInflexiblePortfolio.addActionListener(e -> feature.showParticularPortfolio());
    getFlexiblePortfolio.addActionListener(e -> feature.showFlexiblePortfolio(this.showPortfolioDate()));
    getCostBasis.addActionListener(e -> feature.getCostBasis(this.showPortfolioDate()));
    performTransaction.addActionListener(e -> this.chooseBuyOrSell());
    buy.addActionListener(e -> feature.performBuy(this.showBuySellForm()));
    sell.addActionListener(e -> feature.performSell(this.showBuySellForm()));
    createFlexible.addActionListener(e -> feature.createFlexiblePortfolio());
    investStrategy.addActionListener(e -> this.chooseWhichStrategy());
    plotGraph.addActionListener(e -> feature.plotGraph(this.enterGraphDetails()));
    investAmount.addActionListener(e -> this.investFixedForm());
//    investAmount.addActionListener(feature.investFixedAmount(this.investFixedForm()));
    exitButton.addActionListener(e -> {System.exit(0);});
  }

  @Override
  public void showAllNames(List<String> names) {
    mainPanel.removeAll();
    mainPanel.repaint();
    JPanel other = new JPanel();
    other.setBorder(BorderFactory.createTitledBorder("List Of All Portfolios"));
    other.setLayout(new BoxLayout(other, BoxLayout.PAGE_AXIS));
    setSize(500,300);
    setLocation(300,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JList l = new JList<>(names.toArray());
    l.setLayoutOrientation(ListSelectionModel.SINGLE_SELECTION);
    l.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    l.setVisibleRowCount(-1);
    JScrollPane listScroll = new JScrollPane(l);
    listScroll.setPreferredSize(new Dimension(250,80));
    //other.add(new JLabel("List Of All Portfolios"));
    other.add(l);
    mainPanel.add(other);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public String showParticularPortfolio() {
    setSize(500,300);
    setLocation(300,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel.setLayout(new FlowLayout());
    String name = JOptionPane.showInputDialog("Enter name of portfolio");
    return name;
  }
  @Override
  public ArrayList<String> showPortfolioDate() {
    JPanel controls = new JPanel(new FlowLayout());
    JTextField portfolio = new JTextField(5);
    JLabel portfolioLabel = new JLabel("Enter portfolio");
    controls.add(portfolioLabel, BorderLayout.WEST);
    controls.add(portfolio, BorderLayout.CENTER);
    JTextField date = new JTextField(5);
    JLabel dateLabel = new JLabel("Enter date in YYYY-MM-DD format");
    controls.add(dateLabel, BorderLayout.WEST);
    controls.add(date, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(this, controls, "form", JOptionPane.QUESTION_MESSAGE);
    ArrayList<String> ret = new ArrayList<>();
    ret.add(portfolio.getText());
    ret.add(date.getText());
    return ret;
  }

  @Override
  public void showStocks(Map<String, Double> portfolio) {
    mainPanel.removeAll();
    mainPanel.repaint();
    String[] columns = {"Stock","Quantity"};
    String [][] data=new String[portfolio.size()][2];
    int i;
    i=0;
    for (String s:portfolio.keySet()) {
      data[i][0]=s;
      data[i][1]= String.valueOf(portfolio.get(s));
    }
    JTable j=new JTable(data,columns);
    mainPanel.add(j);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showValue(double value,String name) {
    DecimalFormat df = new DecimalFormat();
    JOptionPane.showMessageDialog(this,
            "The value of the portfolio "+name+" is USD "+df.format(value));
  }

  @Override
  public void showInputError(String message) {
    JOptionPane.showMessageDialog(this,message);
  }

  @Override
  public void chooseWhichPortfolio() {
    mainPanel.removeAll();
    mainPanel.repaint();
    mainPanel.add(getFlexiblePortfolio);
    mainPanel.add(getInflexiblePortfolio);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public void chooseBuyOrSell() {
    mainPanel.removeAll();
    mainPanel.repaint();
    mainPanel.add(buy);
    mainPanel.add(sell);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public ArrayList<String> showBuySellForm() {
    JPanel controls = new JPanel(new FlowLayout());
    JTextField portfolio = new JTextField(5);
    JLabel portfolioLabel = new JLabel("Enter portfolio");
    JTextField date = new JTextField(5);
    JLabel dateLabel = new JLabel("Enter date of Transaction in YYYY-MM-DD format");
    JTextField ticker = new JTextField(5);
    JLabel tickerLabel = new JLabel("Enter Ticker");
    JTextField quantity = new JTextField(5);
    JLabel quantityLabel = new JLabel("Enter Quantity");
    JTextField commissionFee = new JTextField(5);
    JLabel commissionLabel = new JLabel("Enter Commission Fee");
    controls.add(portfolioLabel, BorderLayout.WEST);
    controls.add(portfolio, BorderLayout.CENTER);
    controls.add(dateLabel, BorderLayout.WEST);
    controls.add(date, BorderLayout.CENTER);
    controls.add(tickerLabel, BorderLayout.WEST);
    controls.add(ticker, BorderLayout.CENTER);
    controls.add(quantityLabel, BorderLayout.WEST);
    controls.add(quantity, BorderLayout.CENTER);
    controls.add(commissionLabel, BorderLayout.WEST);
    controls.add(commissionFee, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(this, controls, "form",
            JOptionPane.QUESTION_MESSAGE);
    ArrayList<String> values = new ArrayList<>();
    values.add(portfolio.getText());
    values.add(date.getText());
    values.add(ticker.getText());
    values.add(quantity.getText());
    values.add(commissionFee.getText());
    return values;
  }

  @Override
  public void chooseWhichPortfolioOption() {
    mainPanel.removeAll();
    mainPanel.repaint();
    mainPanel.add(createFlexible);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public void successMessage() {
    JOptionPane.showMessageDialog(this,"Done Successfully");
  }

  @Override
  public ArrayList<String> enterGraphDetails() {
    JPanel controls = new JPanel(new FlowLayout());
    JTextField portfolio = new JTextField(5);
    JLabel portfolioLabel = new JLabel("Enter portfolio");
    JTextField startDate = new JTextField(5);
    JLabel startDateLabel = new JLabel("Enter Start date in YYYY-MM-DD format");
    JTextField endDate = new JTextField(5);
    JLabel endDateLabel = new JLabel("Enter End date in YYYY-MM-DD format");
    controls.add(portfolioLabel, BorderLayout.WEST);
    controls.add(portfolio, BorderLayout.CENTER);
    controls.add(startDateLabel, BorderLayout.WEST);
    controls.add(startDate, BorderLayout.CENTER);
    controls.add(endDateLabel, BorderLayout.WEST);
    controls.add(endDate, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(this, controls, "Graph",
            JOptionPane.QUESTION_MESSAGE);
    ArrayList<String>values=new ArrayList<>();
    values.add(portfolio.getText());
    values.add(startDate.getText());
    values.add(endDate.getText());
    return values;
  }

  @Override
  public void plot(Map<String, Double> trial) {

  }

  @Override
  public void chooseWhichStrategy() {
    mainPanel.removeAll();
    mainPanel.repaint();
    mainPanel.add(investAmount);
    mainPanel.add(highLevelStrategy);
    mainPanel.add(goBack);
    validate();
  }

  @Override
  public ArrayList<String> investFixedForm() {
    return null;
  }


}
