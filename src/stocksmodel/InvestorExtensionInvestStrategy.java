package stocksmodel;

import java.util.Map;

/**
 * The interface extends InvestorExtension interface and defines additional methods that are
 * needed to implement high level investing strategies.
 */
public interface InvestorExtensionInvestStrategy extends InvestorExtension {


  /**
   * The method helps invest a fixed amount into an existing portfolio containing multiple stocks,
   * using a specified weight for each stock in the portfolio on a specific date.
   *
   * @param portfolio the portfolio into which the fixed amount is invested
   * @param weights the map containing stock tickers and their corresponding weights as their values
   * @param amount the amount to be invested into the portfolio
   * @param date the specific date on which the investment has to be done
   * @param commissionFee the commission fee for the investment
   */
  void investAmount(String portfolio, Map<String, Double> weights, Double amount, String date,
                    Double commissionFee);

  /**
   * The method helps in investing specific amount into a portfolio that recurs based on the number
   * of days that are passed. Start date and end dates are also specified so that the investment
   * can be ongoing or implemented in a certain time period. If no end date is passed, the
   * investment strategy will be ongoing.
   *
   * @param portfolio the name of the portfolio in which strategy is implemented
   * @param startDate the starting date of the investing strategy
   * @param endDate the ending date of the investing strategy
   * @param recurrenceDays the days after which the investment has to be made again
   * @param commissionFee the commission fee for each investment
   * @param amount the amount to be invested into the portfolio each time
   * @param weights the map containing stock tickers and their corresponding weights as their values
   */
  void highLevelInvestStrategy(String portfolio, String startDate, String endDate,
                               Integer recurrenceDays, Double commissionFee, Double amount,
                               Map<String, Double> weights);

}
