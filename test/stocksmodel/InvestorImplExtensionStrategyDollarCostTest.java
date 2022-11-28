package stocksmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvestorImplExtensionStrategyDollarCostTest {

  private InvestorExtensionInvestStrategy ist;

  @Before
  public void setup(){
    ist = new InvestorExtensionStrategyDollarCostAvg();
  }

  @Test
  public void testInvestAmount(){
    ist.createFlexiblePortfolio("strategy test");
    Map<String, Double> hm = new HashMap<>();
    hm.put("AAPL",20.0);
    hm.put("AMZN",60.0);
    hm.put("GOOG",10.0);
    hm.put("VZ",5.0);
    hm.put("MSFT",5.0);
    ist.investAmount("strategy test", hm, 2000.0, "2022-11-01", 30.0);
    Map<String, Double> hm1 = ist.loadFlexiblePortfolio("strategy test", "2022-11-01");
    assertEquals(hm.size(), hm1.size());
    for(String ticker : hm1.keySet()){
      assertTrue(hm.containsKey(ticker));
      StockAPI sa = new StockAPIImpl();
      Double tickerValue = sa.getStockClosingByDate(ticker, "2022-11-01");
      double quantity  = ((hm.get(ticker)/100)*1970)/tickerValue;
      assertEquals(quantity, hm1.get(ticker),0);
    }
    assertEquals(2000, ist.getCostBasis("strategy test", "2022-11-01"),0);
    assertEquals(0, ist.loadFlexiblePortfolio("strategy test", "2022-10-30").size());
    assertEquals(1970, ist.getPortfolioValuation("strategy test","2022-11-01"),0);
  }

}
