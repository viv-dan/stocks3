package stockscontroller;

import java.util.ArrayList;
import java.util.Map;
import stocksview.GraphicalView;

public interface Features {

  void showPortfolios();

  void setView(GraphicalView g);

  void showParticularPortfolio();

  void totalPortfolioValue(ArrayList<String> portfolio);

  void showFlexiblePortfolio(ArrayList<String> portfolio);

  void getCostBasis(ArrayList<String> portfolio);

  void performBuy(ArrayList<String> values);

  void performSell(ArrayList<String> values);

  void createFlexiblePortfolio();

  void plotGraph(ArrayList<String> values);

  void investFixedAmount(Map<String, Double> formData, String name, String date,
                         Double commissionFee, Double amount);

  void dollarAverageInvesting(Map<String, Double> formData,
                              String portfolioName, Double amount,
                              String startDate, String endDate,
                              Double commissionAmount, String recurrence);

  void changeFile(String name);
}
