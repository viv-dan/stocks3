package stockscontroller;

import java.util.ArrayList;

public interface Features {

  void showPortfolios();
  void showParticularPortfolio();
  void goToMainMenu();
  void totalPortfolioValue(ArrayList<String> portfolio);


  void showFlexiblePortfolio(ArrayList<String> portfolio);

  void getCostBasis(ArrayList<String> portfolio);


  void performBuy(ArrayList<String> values);

  void performSell(ArrayList<String> values);

  void createFlexiblePortfolio();



  void plotGraph(ArrayList<String> values);
}
