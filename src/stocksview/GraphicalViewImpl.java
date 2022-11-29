package stocksview;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


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
  private JButton submit;
  private JButton submitDollar;
  private JButton showValue;
  private JButton changeFile;
  private JButton changeFlexibleFile;
  private JButton changeInflexibleFile;
  private JButton investStrategy;
  private JButton plotGraph;
  private JTextField portfolio;
  private JLabel portfolioLabel;
  private JTextField amount;
  private JLabel amountLabel;
  private Map<JTextField,JTextField> weights;
  private JTextField startDate ;
  private JLabel startDateLabel;
  private JTextField endDate ;
  private JLabel endDateLabel ;
  private JTextField recurrenceDays;

  private JLabel recurrenceDaysLabel ;
  private JTextField commissionAmount;
  private JLabel commissionAmountLabel;

  private JPanel mainPanel;
  private JPanel menu;
  private JPanel second;
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
    highLevelStrategy=new JButton("Invest By Dollar Cost Averaging Strategy by creating New Portfolio");
    highLevelStrategy.setActionCommand("highLevelInvest");
    submit=new JButton("Submit");
    submit.setActionCommand("submitInvestForm");
    submitDollar=new JButton("Submit");
    submitDollar.setActionCommand("submitDollarInvestForm");
    changeFlexibleFile=new JButton("Change Flexible portfolios File(Select a .json file)");
    changeFlexibleFile.setActionCommand("flexibleChangeFile");
    changeInflexibleFile=new JButton("Change Inflexible portfolios File(Select a .xml file)");
    changeInflexibleFile.setActionCommand("inflexibleChangeFile");


    portfolio = new JTextField(5);
    portfolioLabel = new JLabel("Enter portfolio");
    amount = new JTextField(5);
    amountLabel = new JLabel("Enter Total Investment Amount");
    weights=new HashMap<>();
    startDate = new JTextField(5);
    startDateLabel = new JLabel("Enter Start date in YYYY-MM-DD format");
    endDate = new JTextField(5);
    endDateLabel = new JLabel("Enter End date in YYYY-MM-DD format");
    commissionAmount = new JTextField(5);
    commissionAmountLabel = new JLabel("Enter commission fees");
    recurrenceDays = new JTextField(5);
    recurrenceDaysLabel = new JLabel("Enter the number of days for recurrence of the strategy");


    setSize(1000,800);
    setLocation(300,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel = new JPanel();
    second=new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    setVisible(true);
    showMenu();
    mainPanel.add(second);
  }
  public void showMenu(){
    mainPanel.repaint();
    second.removeAll();
    menu = new JPanel();
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
    changeFile=new JButton("Change File");
    changeFile.setActionCommand("changeFile");
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
    menu.add(changeFile);
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
    changeFile.addActionListener(e -> this.chooseFileChange());
    changeInflexibleFile.addActionListener(e -> feature.changeFile(this.openFileChange()));
    changeInflexibleFile.addActionListener(e -> feature.changeFile(this.openFileChange()));
    plotGraph.addActionListener(e -> feature.plotGraph(this.enterGraphDetails()));
    investAmount.addActionListener(e -> this.investFixedForm());
    submit.addActionListener(e -> feature.investFixedAmount(this.getFormData(),
            this.portfolioName(),this.startDate(),this.getCommissionAmount(),this.getAmount()));
    highLevelStrategy.addActionListener(e -> this.dollarInvestmentForm());
    submitDollar.addActionListener(e -> feature.dollarAverageInvesting(this.getFormData(),this.portfolioName(),
            this.getAmount(),this.startDate(),this.endDate.getText(),
            this.getCommissionAmount(),this.recurrenceDays.getText()));
//    investAmount.addActionListener(feature.investFixedAmount(this.investFixedForm()));
    exitButton.addActionListener(e -> {System.exit(0);});
  }
  private String openFileChange(){
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "XML and JSON Files", "xml", "json");
    fchooser.setFileFilter(filter);
    int value = fchooser.showOpenDialog(second);
    if (value == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return (f.getName());
    }
    return null;
  }

  private void chooseFileChange(){
    second.removeAll();
    second.add(changeFlexibleFile);
    second.add(changeInflexibleFile);
    mainPanel.repaint();
    validate();
  }
  private Double getCommissionAmount(){
    try {
      double fee;
      fee=Double.parseDouble(commissionAmount.getText());
      if(fee<=0){
        throw new RuntimeException();
      }
      return fee;
    }catch (Exception e){
      this.showInputError("Invalid Input");
      second.removeAll();
      mainPanel.repaint();
      validate();
      return null;
    }
  }
  private Double getAmount(){
    try {
      double fee;
      fee=Double.parseDouble(amount.getText());
      if(fee<=0){
        throw new RuntimeException();
      }
      return fee;
    }catch (Exception e){
      this.showInputError("Invalid Input");
      second.removeAll();
      mainPanel.repaint();
      validate();
      return null;
    }
  }


  @Override
  public void showAllPortfolioNames(List<String> names) {
    second.removeAll();
    mainPanel.repaint();
    JPanel other = new JPanel();
    other.setBorder(BorderFactory.createTitledBorder("List Of All Portfolios"));
    other.setLayout(new BoxLayout(other, BoxLayout.PAGE_AXIS));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JList l = new JList<>(names.toArray());
    l.setLayoutOrientation(ListSelectionModel.SINGLE_SELECTION);
    l.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    l.setVisibleRowCount(-1);
    JScrollPane listScroll = new JScrollPane(l);
    listScroll.setPreferredSize(new Dimension(250,80));
    //other.add(new JLabel("List Of All Portfolios"));
    other.add(l);
    second.add(other);
    validate();
  }

  @Override
  public String showParticularPortfolio() {
    String name = JOptionPane.showInputDialog("Enter name of portfolio");
    return name;
  }
  @Override
  public ArrayList<String> showPortfolioDate() {
    JPanel controls = new JPanel(new FlowLayout());
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
//    JButton submit = new JButton("Submit");

//    submit.addActionListener(e -> {
//      ret.add(portfolio.getText());
//      ret.add(date.getText());
//    });
//    controls.add(submit);
//    second.removeAll();
//    second.add(controls);
//    mainPanel.repaint();
    //validate();
    return ret;
  }

  @Override
  public void showStocks(Map<String, Double> portfolio) {
    second.removeAll();
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
    second.add(j);
    validate();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showValueOfPortfolio(double value,String name) {
    DecimalFormat df = new DecimalFormat();
    JLabel display=new JLabel("The value of the portfolio "+name+" is USD "+df.format(value));
    second.removeAll();
    second.add(display);
    //JOptionPane.showMessageDialog(this,
           // "The value of the portfolio "+name+" is USD "+df.format(value));
    mainPanel.repaint();
    validate();
  }

  @Override
  public void showCostOfPortfolio(double value, String date) {
    DecimalFormat df = new DecimalFormat();
    JLabel display=new JLabel("The cost of the portfolio on "+date+" is USD "+df.format(value));
    second.removeAll();
    second.add(display);
    //JOptionPane.showMessageDialog(this,
    // "The value of the portfolio "+name+" is USD "+df.format(value));
    mainPanel.repaint();
    validate();
  }

  @Override
  public void showInputError(String message) {
    JOptionPane.showMessageDialog(this,message);
  }

  @Override
  public void chooseWhichPortfolio() {
    second.removeAll();
    second.add(getFlexiblePortfolio);
    second.add(getInflexiblePortfolio);
    mainPanel.repaint();
    validate();
  }

  @Override
  public void chooseBuyOrSell() {
    second.removeAll();
    second.add(buy);
    second.add(sell);
    mainPanel.repaint();
    validate();
  }

  @Override
  public ArrayList<String> showBuySellForm() {
    JPanel controls = new JPanel(new FlowLayout());
    JTextField date = new JTextField(5);
    JLabel dateLabel = new JLabel("Enter date of Transaction in YYYY-MM-DD format");
    JTextField ticker = new JTextField(5);
    JLabel tickerLabel = new JLabel("Enter Ticker");
    JTextField quantity = new JTextField(5);
    JLabel quantityLabel = new JLabel("Enter Quantity");
    controls.add(portfolioLabel, BorderLayout.WEST);
    controls.add(portfolio, BorderLayout.CENTER);
    controls.add(dateLabel, BorderLayout.WEST);
    controls.add(date, BorderLayout.CENTER);
    controls.add(tickerLabel, BorderLayout.WEST);
    controls.add(ticker, BorderLayout.CENTER);
    controls.add(quantityLabel, BorderLayout.WEST);
    controls.add(quantity, BorderLayout.CENTER);
    controls.add(commissionAmountLabel, BorderLayout.WEST);
    controls.add(commissionAmount, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(this, controls, "form",
            JOptionPane.QUESTION_MESSAGE);
    ArrayList<String> values = new ArrayList<>();
    values.add(portfolio.getText());
    values.add(date.getText());
    values.add(ticker.getText());
    values.add(quantity.getText());
    values.add(commissionAmount.getText());
    return values;
  }

  @Override
  public void chooseWhichPortfolioOption() {
    second.removeAll();
    mainPanel.repaint();
    second.add(createFlexible);
    validate();
  }

  @Override
  public void successMessage() {
    JOptionPane.showMessageDialog(this,"Done Successfully");
  }

  @Override
  public ArrayList<String> enterGraphDetails() {
    JPanel controls = new JPanel(new FlowLayout());
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
    DefaultCategoryDataset data = new DefaultCategoryDataset();
    for (String s: trial.keySet()) {
      data.addValue(trial.get(s),"Values",s);
    }
    JFreeChart line = ChartFactory.createBarChart("Portfolio","Dates",
            "Values", data,PlotOrientation.HORIZONTAL, true,true,false);
    line.setBorderPaint(Color.BLUE);
    ChartPanel chartPanel = new ChartPanel( line );
    second.removeAll();
    second.add(chartPanel);
    mainPanel.repaint();
    validate();

  }

  @Override
  public void chooseWhichStrategy() {
    second.removeAll();
    mainPanel.repaint();
    second.add(investAmount);
    second.add(highLevelStrategy);
    validate();
  }
  private void setUpForm(JPanel controls){
    String name = JOptionPane.showInputDialog("Enter number of stocks you want to invest in.");
    int value;
    try{
      value=Integer.parseInt(name);
      if(value<=0){
        throw new RuntimeException();
      }
    }
    catch (Exception e){
      this.showInputError("Invalid Input");
      second.removeAll();
      mainPanel.repaint();
      validate();
      return;
    }
    controls.add(portfolioLabel);
    controls.add(portfolio);
    controls.add(amountLabel);
    controls.add(amount);
    controls.add(startDateLabel);
    controls.add(startDate);
    controls.add(commissionAmountLabel);
    controls.add(commissionAmount);
    weights=new HashMap<>();
    for(int i=0;i<value;i++){
      weights.put(new JTextField(5),new JTextField(5));
    }
    for (JTextField text:weights.keySet() ) {
      controls.add(new JLabel("Enter Stock Ticker"));
      controls.add(weights.get(text));
      controls.add(new JLabel("Enter Weightage as Percentage"));
      controls.add(text);
    }
  }

  @Override
  public void investFixedForm() {
    JPanel controls = new JPanel();
    setUpForm(controls);
    controls.add(submit);
    controls.setLayout(new BoxLayout(controls,BoxLayout.Y_AXIS));
    second.removeAll();
    second.add(controls);
    mainPanel.repaint();
    validate();
  }

  private void dollarInvestmentForm(){
    JPanel controls = new JPanel();
    setUpForm(controls);
    controls.add(endDateLabel);
    controls.add(endDate);
    controls.add(recurrenceDaysLabel);
    controls.add(recurrenceDays);
    controls.add(submitDollar);
    controls.setLayout(new BoxLayout(controls,BoxLayout.Y_AXIS));
    second.removeAll();
    second.add(controls);
    mainPanel.repaint();
    validate();
  }



  private Map<String,Double> getFormData(){
    Map<String,Double> value=new HashMap<>();
    try{
      double temp;
      for (JTextField text: weights.keySet()) {
        temp = Double.parseDouble(text.getText());
        if(temp<=0 || temp>=100){
          throw new RuntimeException();
        }else {
          value.put(weights.get(text).getText(),temp );
        }

      }
    }catch (Exception e){
      this.showInputError("Invalid Input");
      second.removeAll();
      mainPanel.repaint();
      validate();
      return null;
    }
    return value;
  }
  private String portfolioName(){
    return portfolio.getText();
  }
  private String startDate(){
    return startDate.getText();
  }



}
