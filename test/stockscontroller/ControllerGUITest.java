package stockscontroller;

import org.junit.Before;
import org.junit.Test;

import stocksmodel.InvestorExtension;
import stocksmodel.InvestorExtensionInvestStrategy;
import stocksmodel.InvestorExtensionStrategyDollarCostAvg;
import stocksmodel.InvestorImplExtension;
import stocksview.GraphicalViewImpl;

import static org.junit.Assert.*;

public class ControllerGUITest {
  InvestorExtensionInvestStrategy i;
  Features c;

  @Before
  public void setUp(){
    i=new InvestorExtensionStrategyDollarCostAvg();
    c = new ControllerGUI(i);
    c.setView(new GraphicalViewImpl());
  }

  @Test
  public void testShowPortfolios(){
    c.showParticularPortfolio();
  }

}