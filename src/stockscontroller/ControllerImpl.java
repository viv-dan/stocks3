package stockscontroller;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import stocksmodel.InvestorExtension;
import stocksview.Menu;

/**
 * This class implements the Controller interface which communicates with model and view classes.
 * The constructor takes in a Menu and an Investor object.
 *
 * <p>UPDATES:
 * Added new Switch cases in the startProgram method to incorporate new features.
 * The parameter for the constructor has been changed to an extension Investor
 * interface model type.</p>
 */
public class ControllerImpl implements Controller {
  private final InvestorExtension i1;
  private final Menu m1;
  private final InputStream in;

  /**
   * The constructor method creates an ControllerImpl object. It takes in a Menu view, Investor
   * model object, an input stream and an output stream object for taking in inputs and writing
   * to the stream respectively.
   * UPDATE: Change in the type of parameter in the constructor,previously it accepted
   * Investor Model object, now it takes in the Investor Extension Interface type object.
   *
   * @param i   investorextension model object for data retrieval
   * @param m   menu view object for writing to the stream
   * @param in  input stream from which the data is read from
   * @param out output stream to which the data is written into
   */
  public ControllerImpl(InvestorExtension i, Menu m, InputStream in, PrintStream out) {
    this.i1 = i;
    this.m1 = m;
    m1.setStream(out);
    this.in = in;
  }

  @Override
  public void startProgram() {
    Scanner sc = new Scanner(this.in);
    int i = 0;
    while (i != 10) {
      m1.showMenu();
      try {
        i = Integer.parseInt(sc.nextLine().trim());
      } catch (Exception e) {
        m1.showInputError();
        m1.goBackMessage();
        sc.nextLine();
        continue;
      }
      switchControl(i, sc);
      if (i == 10) {
        break;
      }
    }
    sc.close();
  }

  private void switchControl(int i, Scanner sc) {
    try {
      switch (i) {
        case 1:
          this.showAllPortfolioNames(sc);
          break;
        case 2:
          this.showParticularPortfolio(sc);
          break;
        case 3:
          this.createPortfolioControl(sc);
          m1.goBackMessage();
          sc.nextLine();
          break;
        case 4:
          this.totalPortfolioValueControl(sc);
          break;
        case 5:
          this.loadFromFileController(sc);
          break;
        case 6:
          this.createFlexiblePortfolioControl(sc);
          break;
        case 7:
          this.performTransactionControl(sc);
          m1.goBackMessage();
          sc.nextLine();
          break;
        case 8:
          this.getCostBasisController(sc);
          break;
        case 9:
          this.plotGraphControl(sc);
          break;
        case 10:
          break;
        default:
          m1.showInputError();
      }
    } catch (Exception e) {
      m1.showMessage(e.getMessage());
    }
  }

  private void getCostBasisController(Scanner sc) {
    double value;
    m1.showSinglePortfolioMenu();
    String s;
    s = sc.nextLine();
    String date;
    m1.showMessage("Enter date for value in YYYY-MM-DD format");
    try {
      date = sc.nextLine();
      value = i1.getCostBasis(s, date);
      m1.showCostOfPortfolio(value, date);
    } catch (RuntimeException e) {
      m1.showMessage(e.getMessage());
    }
    m1.goBackMessage();
    sc.nextLine();
  }

  private void createFlexiblePortfolioControl(Scanner sc) {
    try {
      m1.showSinglePortfolioMenu();
      String s = sc.nextLine();
      if (s.equals(" ") || s.isEmpty()) {
        m1.showInputError();
        m1.goBackMessage();
        sc.nextLine();
        return;
      }
      try {
        i1.loadPortfolio(s);
        m1.showMessage("Portfolio already exists!!");
      } catch (RuntimeException e) {
        i1.createFlexiblePortfolio(s);
        m1.successMessage();
      }
    } catch (Exception e) {
      m1.showMessage(e.getMessage());
    }
    m1.goBackMessage();
    sc.nextLine();
  }

  private void plotGraphControl(Scanner sc) {
    m1.showSinglePortfolioMenu();
    String name;
    name = sc.nextLine();
    m1.showMessage("Enter start date in YYYY-MM-DD format");
    String sd = sc.nextLine();
    m1.showMessage("Enter end  date in YYYY-MM-DD format");
    String ed = sc.nextLine();
    try {
      i1.getPortfolioValuation(name, "2022-10-11");
    } catch (RuntimeException e) {
      m1.showInputError();
      m1.goBackMessage();
      sc.nextLine();
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date sd1;
    Date ed1;
    Date diff;
    try {
      sd1 = sdf.parse(sd);
      ed1 = sdf.parse(ed);
      if (sd1.after(ed1) || ed1.after(new Date()) || sd1.equals(ed1)) {
        m1.showInputError();
        m1.goBackMessage();
        sc.nextLine();
        return;
      }
      diff = new Date(ed1.getTime() - sd1.getTime());
    } catch (ParseException e) {
      m1.showInputError();
      m1.goBackMessage();
      sc.nextLine();
      return;
    }
    int days = (int) (diff.getTime() / 86400000);
    plotHelper(sc, timeCalculator(days, sd1, ed1), name);
  }

  private ArrayList<String> timeCalculator(int days, Date sd1, Date ed1) {
    int time = days;
    boolean years = false;
    boolean weeks = false;
    boolean months = false;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (days > 30) {
      time = days / 7;
      weeks = true;
      if (time > 30) {
        time = time / 4;
        months = true;
        if (time > 30) {
          time = time / 12;
          years = true;
        }
      }
    }
    return dateCalculator(years, months, weeks, sd1, ed1, time);
  }

  private ArrayList<String> dateCalculator(boolean years, boolean months, boolean weeks, Date sd1,
                                           Date ed1, int time) {
    ArrayList<String> dates = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    cal.setTime(sd1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int i;
    if (years) {
      for (i = 0; i <= time; i++) {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (cal.getTime().before(ed1)) {
          dates.add(sdf.format(cal.getTime()));
        } else {
          dates.add(sdf.format(ed1));
        }
        cal.add(Calendar.YEAR, 1);
      }
    } else if (months) {
      for (i = 0; i < time; i++) {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (cal.getTime().before(ed1)) {
          dates.add(sdf.format(cal.getTime()));
        } else {
          dates.add(sdf.format(ed1));
        }
        cal.add(Calendar.MONTH, 1);
      }
    } else if (weeks) {
      for (i = 0; i < time; i++) {
        if (cal.getTime().before(ed1)) {
          dates.add(sdf.format(cal.getTime()));
        } else {
          dates.add(sdf.format(ed1));
        }
        cal.add(Calendar.WEEK_OF_YEAR, 1);
      }
    } else {
      for (i = 0; i < time; i++) {
        if (cal.getTime().before(ed1)) {
          dates.add(sdf.format(cal.getTime()));
        } else {
          dates.add(sdf.format(ed1));
        }
        cal.add(Calendar.DATE, 1);
      }
    }
    return dates;
  }

  private void plotHelper(Scanner sc, ArrayList<String> dates, String name) {
    String call;
    Map<String, Double> trial = new TreeMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date ed1;
    Calendar cal = Calendar.getInstance();
    boolean temp;
    double value;
    int i;
    int count;
    for (i = 0; i < dates.size(); i++) {
      temp = true;
      call = (dates.get(i));
      count = 0;
      do {
        try {
          if (count > 25) {
            trial.put(dates.get(i), 0.0);
            break;
          }
          value = i1.getPortfolioValuation(name, call);
          trial.put(dates.get(i), value);
          temp = false;
        } catch (Exception e) {
          try {
            count++;
            ed1 = sdf.parse(call);
            cal.setTime(ed1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            call = sdf.format(cal.getTime());
          } catch (ParseException ex) {
            m1.showInputError();
            m1.goBackMessage();
            sc.nextLine();
            return;
          }
        }
      }
      while (temp);
    }
    m1.plot(trial);
    m1.goBackMessage();
    sc.nextLine();
  }


  private void performTransactionControl(Scanner sc) {
    m1.showMessage("Choose Among the options");
    m1.showMessage("1.Buy");
    m1.showMessage("2.Sell");
    int j;
    try {
      j = Integer.parseInt(sc.nextLine().trim());
    } catch (Exception e) {
      m1.showInputError();
      return;
    }
    try {
      if (j == 2 || j == 1) {
        m1.showSinglePortfolioMenu();
        String name = sc.nextLine();
        m1.showMessage("Enter valid ticker");
        String ticker;
        double commission;
        int qty;
        ticker = sc.nextLine();
        try {
          m1.showMessage("Enter quantity of transaction");
          qty = Integer.parseInt(sc.nextLine().trim());
          m1.showMessage("Enter Commission fees for the transaction");
          commission = Double.parseDouble(sc.nextLine().trim());
          if (qty <= 0 || commission < 0) {
            throw new RuntimeException();
          }
        } catch (Exception e) {
          m1.showInputError();
          return;
        }
        m1.showMessage("Enter date of transaction in YYYY-MM-DD format");
        String date;
        date = sc.nextLine();
        if (j == 1) {
          i1.createBuyTransaction(name, commission, date, ticker, qty);
          m1.successMessage();
          return;
        }
        i1.createSellTransaction(name, commission, date, ticker, qty);
        m1.successMessage();
      } else {
        m1.showInputError();
      }
    } catch (RuntimeException e) {
      m1.showMessage(e.getMessage());
    } catch (Exception e) {
      m1.showInputError();
    }
  }


  private void loadFromFileController(Scanner sc) {
    m1.showMessage("Enter file name with .xml extension to change Inflexible Portfolios File ");
    m1.showMessage("Or");
    m1.showMessage("Enter file name with .json extension to change Flexible Portfolios File ");
    String s = sc.nextLine().trim();
    try {
      i1.changeReadFile(s);
      m1.successMessage();
    } catch (Exception e) {
      m1.showMessage(e.getMessage());
    }
    m1.goBackMessage();
    sc.nextLine();
  }

  private void showParticularPortfolio(Scanner sc) {
    m1.showMessage("Choose Among the options");
    m1.showMessage("1.Show Flexible Portfolio");
    m1.showMessage("2.Show Inflexible Portfolio");
    int j;
    try {
      j = Integer.parseInt(sc.nextLine());
    } catch (Exception e) {
      m1.showInputError();
      m1.goBackMessage();
      sc.nextLine();
      return;
    }
    String s;
    try {
      if (j == 1) {
        m1.showSinglePortfolioMenu();
        s = sc.nextLine();
        m1.showMessage("Enter date for the portfolio in YYYY-MM-DD format");
        String date;
        date = sc.nextLine();
        m1.showStocks(i1.loadFlexiblePortfolio(s, date));
      } else if (j == 2) {
        m1.showSinglePortfolioMenu();
        s = sc.nextLine();
        m1.showStocks(i1.loadPortfolio(s));
      } else {
        m1.showInputError();
        m1.goBackMessage();
        sc.nextLine();
        return;
      }
    } catch (Exception e) {
      m1.showMessage(e.getMessage());
    }
    m1.goBackMessage();
    sc.nextLine();
  }

  private void showAllPortfolioNames(Scanner sc) {
    m1.showAllPortfolioNames(i1.loadAllPortfolioNames());
    m1.goBackMessage();
    sc.nextLine();
  }

  private void totalPortfolioValueControl(Scanner sc) {
    double value;
    m1.showSinglePortfolioMenu();
    String s;
    s = sc.nextLine();
    String date;
    m1.showMessage("Enter date for value in YYYY-MM-DD format");
    try {
      date = sc.nextLine();
      value = i1.getPortfolioValuation(s, date);
      m1.showValueOfPortfolio(value, date);
    } catch (RuntimeException e) {
      m1.showMessage(e.getMessage());
    }
    m1.goBackMessage();
    sc.nextLine();
  }

  private void createPortfolioControl(Scanner sc) {
    try {
      m1.showSinglePortfolioMenu();
      String name;
      name = sc.nextLine().trim();
      if (name.equals("") || name == null) {
        throw new RuntimeException("Invalid portfolio Name");
      }
      try {
        i1.loadPortfolio(name);
        m1.showMessage("Portfolio Exists Already with the name");
        return;
      } catch (RuntimeException e) {
        try {
          i1.loadFlexiblePortfolio(name, "2022-11-01");
          m1.showMessage("Portfolio Exists Already with the name");
          return;
        } catch (RuntimeException ex) {
          m1.showMessage("Enter Number of Stocks in integer");
          createPortfolioControlHelper(name, sc);
        }
      }
    } catch (NumberFormatException e) {
      m1.showInputError();
    } catch (RuntimeException e) {
      m1.showMessage(e.getMessage());
    }
  }

  private void createPortfolioControlHelper(String name, Scanner sc) {
    int n;
    n = Integer.parseInt(sc.nextLine().trim());
    if (n <= 0) {
      m1.showInputError();
      return;
    }
    int j;
    int qty;
    HashMap<String, Integer> portfolio = new HashMap<>();
    for (j = 0; j < n; j++) {
      m1.showMessage("Enter a ticker");
      String k;
      k = sc.nextLine().trim();
      m1.showMessage("Enter Quantity in integer");
      qty = Integer.parseInt(sc.nextLine().trim());
      if (qty <= 0) {
        m1.showMessage("Number shouldn't be less than 1");
        return;
      }
      portfolio.put(k, portfolio.getOrDefault(k, 0) + qty);
    }
    i1.createPortfolio(name, portfolio);
    m1.successMessage();
  }
}