package stocksview;

import java.util.ArrayList;
import stockscontroller.Features;

public interface GraphicalView extends View {

  void addFeatures(Features feature);

  void showLoad();

  void showOffLoad();

  String showParticularPortfolio();

  ArrayList<String> showPortfolioDate();

  void resetFocus();

  void showInputError(String message);

  void chooseWhichPortfolio();

  void chooseBuyOrSell();

  ArrayList<String> showBuySellForm();

  void chooseWhichPortfolioOption();

  ArrayList<String> enterGraphDetails();

  void chooseWhichStrategy();

  void investFixedForm();
}
