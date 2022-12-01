import stockscontroller.Controller;
import stockscontroller.ControllerGUI;
import stockscontroller.ControllerImpl;
import stocksmodel.InvestorExtension;
import stocksmodel.InvestorExtensionStrategyDollarCostAvg;
import stocksmodel.InvestorImplExtension;
import stocksview.GraphicalViewImpl;
import stocksview.Menu;
import stocksview.MenuImpl;


/**
 * The class is the main method which starts the program by starting controller, model and view
 * of the program.
 */
public class Run {

  /**
   * The method is the starting method of the stocks program which creates controller, model and
   * view classes.
   *
   * @param args the arguments which can be passed to the run method.
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      if (args[0].equals("text")) {
        InvestorExtension i = new InvestorImplExtension();
        Menu m = new MenuImpl();
        Controller c1 = new ControllerImpl(i, m, System.in, System.out);
        c1.startProgram();
        System.exit(0);
      } else if (args[0].equals("gui")) {
        InvestorExtension i = new InvestorImplExtension();
        ControllerGUI c = new ControllerGUI(new InvestorExtensionStrategyDollarCostAvg());
        c.setView(new GraphicalViewImpl());
      }
    } else {
      InvestorExtension i = new InvestorImplExtension();
      ControllerGUI c = new ControllerGUI(new InvestorExtensionStrategyDollarCostAvg());
      c.setView(new GraphicalViewImpl());
    }
  }
}
