package stocksview;

import java.util.ArrayList;
import stockscontroller.Features;

/**
 * The interface defines a graphical user interface.
 */
public interface GraphicalView extends View {

  /**
   * The add feature methods adds the features of the application to the view.
   *
   * @param feature a feature controller type object
   */
  void addFeatures(Features feature);

  /**
   * The method displays a loading screen on the screen.
   */
  void showLoad();

  /**
   * The method removes the loading screen from the screen.
   */
  void showOffLoad();

  /**
   * The method shows the form to load a particular portfolio.
   *
   * @return the name of the portfolio retrieved from the form.
   */
  String showParticularPortfolio();

  /**
   * The method shows a form to enter details for a flexible portfolio with date.
   *
   * @return the array list of details entered by the user.
   */
  ArrayList<String> showPortfolioDate();

  /**
   * The method resets the focus of the view to the main JFrame.
   */
  void resetFocus();

  /**
   * The methods show an input error message dialog box with the error message.
   *
   * @param message the string error message to be printed.
   */
  void showInputError(String message);

  /**
   * The method show the options of choosing a flexible or inflexible portfolio.
   */
  void chooseWhichPortfolio();

  /**
   * The method shows the options to perform a buy or sell transaction.
   */
  void chooseBuyOrSell();

  /**
   * The method shows the sell form to enter the details of the sell transaction.
   *
   * @return the array list of details entered by the user.
   */
  ArrayList<String> showBuySellForm();

  /**
   * The method shows an option to create a type portfolio.
   */
  void chooseWhichPortfolioOption();

  /**
   * The method shows a form for the user to enter details for plotting a graph.
   *
   * @return the array list of details entered by the user.
   */
  ArrayList<String> enterGraphDetails();

  /**
   * The method shows the user to choose among the investment options.
   */
  void chooseWhichStrategy();

  /**
   * The method shows the user the form to enter details for the fixed investment.
   */
  void investFixedForm();
}
