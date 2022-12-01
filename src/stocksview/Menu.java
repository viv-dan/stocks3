package stocksview;

import java.io.PrintStream;


/**
 * The interface specifies the features of Menu view for text based program.
 */
public interface Menu extends View {

  /**
   * The method displays an input error message when an invalid input is entered by the user.
   */
  void showInputError();

  /**
   * The method displays the message prompting the user to enter the portfolio name.
   */
  void showSinglePortfolioMenu();

  /**
   * The method displays the error message when a particular portfolio is not found.
   */
  void showPortfolioNameError();

  /**
   * The method displays the message of prompting the user to enter to go back to the main menu.
   */
  void goBackMessage();

  /**
   * The method sets the output stream of the view so as the view displays output to that stream.
   *
   * @param out PrintStream that the output should be returned
   */
  void setStream(PrintStream out);

  /**
   * The method displays the message that is passed as a string onto the output stream.
   *
   * @param s the message in the form of a string that is to be displayed
   */
  void showMessage(String s);


}
