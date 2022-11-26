import stockscontroller.ControllerGUI;
import stocksmodel.InvestorExtension;
import stocksmodel.InvestorExtensionStrategyDollarCostAvg;
import stocksmodel.InvestorImplExtension;
import stocksview.GraphicalView;
import stocksview.GraphicalViewImpl;

public class RunGUI {
  public static void main(String[] args) {
    InvestorExtension i = new InvestorImplExtension();
    ControllerGUI c = new ControllerGUI(new InvestorExtensionStrategyDollarCostAvg());
    c.setView(new GraphicalViewImpl());
  }
}
