package stocksview;

import java.util.List;
import java.util.Map;

import stockscontroller.Features;

public interface GraphicalView {
  void setMenu();
  void addFeatures(Features feature);
  void showAllNames(List<String> names);
  String showParticularPortfolio();
  String showDate();
  void showStocks(Map<String,Double> portfolio);
  void resetFocus();
  void showValue(double value,String name);

  void showInputError(String message);

  void chooseWhichPortfolio();

  void chooseBuyOrSell();

  String showBuySellForm();

  void chooseWhichPortfolioOption();

  void successMessage();

  String enterGraphDetails();
}
