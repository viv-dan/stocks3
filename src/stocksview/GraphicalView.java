package stocksview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import stockscontroller.Features;

public interface GraphicalView {
  void setMenu();
  void addFeatures(Features feature);
  void showAllNames(List<String> names);
  String showParticularPortfolio();
  ArrayList<String> showPortfolioDate();
  void showStocks(Map<String,Double> portfolio);
  void resetFocus();
  void showValue(double value,String name);

  void showInputError(String message);

  void chooseWhichPortfolio();

  void chooseBuyOrSell();

  ArrayList<String> showBuySellForm();

  void chooseWhichPortfolioOption();

  void successMessage();

  ArrayList<String> enterGraphDetails();


  void plot(Map<String, Double> trial);

  void chooseWhichStrategy();

  ArrayList<String> investFixedForm();
}
