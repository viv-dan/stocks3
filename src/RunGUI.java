import stockscontroller.Controller;
import stockscontroller.ControllerGUI;
import stockscontroller.ControllerImpl;
import stocksmodel.InvestorExtension;
import stocksmodel.InvestorExtensionStrategyDollarCostAvg;
import stocksmodel.InvestorImplExtension;
import stocksview.GraphicalView;
import stocksview.GraphicalViewImpl;
import stocksview.Menu;
import stocksview.MenuImpl;

public class RunGUI {
  public static void main(String[] args) {
    InvestorExtension i = new InvestorImplExtension();
    ControllerGUI c = new ControllerGUI(new InvestorExtensionStrategyDollarCostAvg());
    c.setView(new GraphicalViewImpl());
  }
}
