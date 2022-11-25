package stocksmodel;

import java.util.Map;

public interface InvestorExtensionInvestStrategy extends InvestorExtension{


  void investAmount(String portfolio, Map<String, Double> weights, Double amount, String date, Double commissionFee);

  void highLevelInvestStrategy(String portfolio, String startDate, String endDate, Integer recurrenceDays, Double commissionFee, Double amount, Map<String, Double> weights);

}
