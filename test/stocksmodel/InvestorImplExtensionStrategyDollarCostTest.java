package stocksmodel;

import org.junit.Before;
import org.junit.Test;

public class InvestorImplExtensionStrategyDollarCostTest {

  private InvestorExtensionInvestStrategy ist;

  @Before
  public void setup(){
    ist = new InvestorExtensionStrategyDollarCostAvg();
  }

  @Test
  public void testInvestAmount(){

  }

}
