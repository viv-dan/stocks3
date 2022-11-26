package stockscontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import stocksmodel.InvestorExtensionInvestStrategy;
import stocksview.GraphicalView;

public class ControllerGUI implements Features{
  private InvestorExtensionInvestStrategy i1;
  private GraphicalView g1;

  public ControllerGUI(InvestorExtensionInvestStrategy i){
    this.i1=i;
  }

  public void setView(GraphicalView g){
    this.g1=g;
    g1.addFeatures(this);
  }
  @Override
  public void showPortfolios() {
    List<String> names = i1.loadAllPortfolioNames();
    g1.showAllNames(names);
    g1.resetFocus();
  }


  @Override
  public void showFlexiblePortfolio() {
    try {
      String name;
      String date;
      String res = g1.showDate();
      String com[] =res.split("!");
      name=com[0];
      date=com[1];
      Map<String,Double> port = i1.loadFlexiblePortfolio(name,date);
      g1.showStocks(port);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void getCostBasis() {
    try {
      String name;
      String date;
      String res = g1.showDate();
      if(res.isEmpty()){
        throw new RuntimeException("Fields Can't be empty");
      }
      String com[] =res.split("!");
      name=com[0];
      date=com[1];
      double value = i1.getCostBasis(name,date);
      g1.showValue(value,name);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void performBuy() {
    String s=g1.showBuySellForm();
    String com[] =s.split("!");
    String name,date,ticker;
    double quantity,commission;
    name=com[0];
    date=com[1];
    ticker=com[2];
    quantity= Double.parseDouble(com[3]);
    commission= Double.parseDouble(com[4]);
    try {
      i1.createBuyTransaction(name,commission,date,ticker,quantity);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void performSell() {
    String s=g1.showBuySellForm();
    String com[] =s.split("!");
    String name,date,ticker;
    double quantity,commission;
    name=com[0];
    date=com[1];
    ticker=com[2];
    quantity= Double.parseDouble(com[3]);
    commission= Double.parseDouble(com[4]);
    try {
      i1.createSellTransaction(name,commission,date,ticker,quantity);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void createFlexiblePortfolio() {
    try{
      String name;
      name=g1.showParticularPortfolio();
      i1.createFlexiblePortfolio(name);
      g1.successMessage();
      g1.setMenu();
    }catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void createInflexiblePortfolio() {
    try{
      String name;
      name=g1.showParticularPortfolio();
      //i1.createPortfolio(name,stocks);
      g1.successMessage();
      g1.setMenu();
    }catch (Exception e){
      g1.showInputError(e.getMessage());
    }

  }

  @Override
  public void plotGraph() {
    try{

    }
    catch (Exception e){

    }
  }

  @Override
  public void showParticularPortfolio() {
    try {
      Map<String,Integer> port = i1.loadPortfolio(g1.showParticularPortfolio());
      Map<String, Double> hm = new HashMap<>();
      for(String ss : port.keySet()){
        hm.put(ss,Double.parseDouble(port.get(ss).toString()));
      }
      g1.showStocks(hm);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
    g1.resetFocus();
  }

  @Override
  public void goToMainMenu() {
    g1.setMenu();
    g1.addFeatures(this);
  }

  @Override
  public void totalPortfolioValue() {
    try{
      String name;
      String date;
      String res = g1.showDate();
      String com[] =res.split("!");
      name=com[0];
      date=com[1];
      g1.showValue(i1.getPortfolioValuation(name,date),name);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }


}
