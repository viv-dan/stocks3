package stockscontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import stocksmodel.InvestorExtensionInvestStrategy;
import stocksview.GraphicalView;

public class ControllerGUI implements Features{
  private InvestorExtensionInvestStrategy i1;
  private GraphicalView g1;

  public ControllerGUI(InvestorExtensionInvestStrategy i){
    this.i1=i;
  }

  public void setView(GraphicalView g){
    this.g1=g;
    g1.addFeatures(this);
  }
  @Override
  public void showPortfolios() {
    List<String> names = i1.loadAllPortfolioNames();
    g1.showAllPortfolioNames(names);
    g1.resetFocus();
  }


  @Override
  public void showFlexiblePortfolio(ArrayList<String> portfolio) {
    try {
      String name;
      String date;
      checkNull(portfolio);
      name= portfolio.get(0);
      date=portfolio.get(1);
      g1.showLoad();
      Map<String,Double> port = i1.loadFlexiblePortfolio(name,date);
      g1.showOffLoad();
      g1.showStocks(port);
    }
    catch (Exception e){
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void getCostBasis(ArrayList<String> portfolio) {
    try {
      String name;
      String date;
      checkNull(portfolio);
      name=portfolio.get(0);
      date=portfolio.get(1);
      g1.showLoad();
      double value = i1.getCostBasis(name,date);
      g1.showOffLoad();
      g1.showCostOfPortfolio(value,date);
    }
    catch (Exception e){
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
    }
  }

  private void checkNull(ArrayList<String> check){
    for (String n:check) {
      if(n.isEmpty() || n==null){
        throw new RuntimeException("Fields can't be empty");
      }
    }
  }

  @Override
  public void performBuy(ArrayList<String> values) {
    try {
      String name,date,ticker;
      double quantity,commission;
      checkNull(values);
      name= values.get(0);
      date=values.get(1);
      ticker=values.get(2);
      quantity= Integer.parseInt(values.get(3));
      commission= Double.parseDouble(values.get(4));
      g1.showLoad();
      i1.createBuyTransaction(name,commission,date,ticker,quantity);
      g1.showOffLoad();
      g1.successMessage();
    }
    catch (NumberFormatException e1){
      g1.showOffLoad();
      g1.showInputError("Enter whole numbers for quantity");
    }
    catch (Exception e){
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void performSell(ArrayList<String> values) {
    try {
      String name,date,ticker;
      double quantity,commission;
      checkNull(values);
      name= values.get(0);
      date=values.get(1);
      ticker=values.get(2);
      quantity= Integer.parseInt(values.get(3));
      commission= Double.parseDouble(values.get(4));
      g1.showLoad();
      i1.createSellTransaction(name,commission,date,ticker,quantity);
      g1.showOffLoad();
      g1.successMessage();
    }
    catch (NumberFormatException e1){
      g1.showOffLoad();
      g1.showInputError("Enter whole numbers for quantity");
    }
    catch (Exception e){
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void createFlexiblePortfolio() {
    try{
      String name;
      name=g1.showParticularPortfolio();
      try{
        i1.loadPortfolio(name);
      }catch (Exception e){
        i1.createFlexiblePortfolio(name);
        g1.successMessage();
      }
    }catch (Exception e){
      g1.showInputError(e.getMessage());
    }
  }

  @Override
  public void plotGraph(ArrayList<String> values) {
    try{
      checkNull(values);

    }catch (Exception e){
      g1.showInputError(e.getMessage());
      return;
    }
      String sd,ed,name;
      name= values.get(0);
      sd= values.get(1);
      ed= values.get(2);
    try {
      g1.showLoad();
      i1.getPortfolioValuation(name, "2022-10-11");
      g1.showOffLoad();
    } catch (RuntimeException e) {
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
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
        g1.showInputError("Start date can't be greater or equal to than end date");
        return;
      }
      diff = new Date(ed1.getTime() - sd1.getTime());
    } catch (ParseException e) {
      g1.showInputError(e.getMessage());
      return;
    }
    int days = (int) (diff.getTime() / 86400000);
    plotHelper(timeCalculator(days, sd1, ed1), name);
  }

  @Override
  public void investFixedAmount(Map<String, Double> formData, String name, String date,Double commissionFee,Double amount) {
    if(name==null || date==null || formData==null || commissionFee==null || checkMapNull(formData)){
      g1.showInputError("Invalid input");
    }
    else{
      try{
        i1.investAmount(name,formData,amount,date,commissionFee);
        g1.successMessage();
      }catch (Exception e){
        g1.showInputError(e.getMessage());
      }
    }

  }
  private boolean checkMapNull(Map<String,Double> value){
    if (value==null){
      return true;
    }
    else if(value.isEmpty()){
      return true;
    }
    else {
      for (String n:value.keySet()) {
        if(n.isEmpty() || n== null || value.get(n)==null){
          return true;
        }
      }
    }
    return false;
  }
  @Override
  public void dollarAverageInvesting(Map<String, Double> formData,
                                     String portfolioName, Double amount,
                                     String startDate, String endDate,
                                     Double commissionAmount, String recurrence) {
    if(portfolioName==null || amount ==null ||
            startDate==null ||
          commissionAmount==null || recurrence==null || formData==null ||checkMapNull(formData)){
      g1.showInputError("Invalid Input");
      return;
    }
    else {
      if(endDate.equals("")){
        endDate=null;
      }
      try {
        try{
          i1.loadPortfolio(portfolioName);
          g1.showInputError("Can't Invest in a Inflexible Portfolio");
          return;
        }catch (Exception e){
          try{
            i1.createFlexiblePortfolio(portfolioName);
            g1.showInputError("Created a new Portfolio");
          }catch (Exception e1){
            g1.showInputError("Investing in Existing Portfolio");
          }
        }

        i1.highLevelInvestStrategy(portfolioName,startDate,endDate,
                Integer.parseInt(recurrence),commissionAmount,amount,formData);
        g1.successMessage();
      }catch (NumberFormatException e){
        g1.showInputError("Enter whole number value for recurrence days");
      }
      catch (Exception e){
        g1.showInputError(e.getMessage());
      }
    }
  }

  @Override
  public void changeFile(String name) {
    if(name==null){
      g1.showInputError("Error in file change");
      return;
    }
    else {
      try{
        i1.changeReadFile(name);
        g1.successMessage();
      }catch (Exception e){
        g1.showInputError(e.getMessage());
      }
    }
  }


  private ArrayList<String> timeCalculator(int days, Date sd1, Date ed1) {
    int time = days;
    boolean years = false;
    boolean weeks = false;
    boolean months = false;
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

  private void plotHelper(ArrayList<String> dates, String name) {
    String call;
    Map<String, Double> trial = new TreeMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date ed1;
    Calendar cal = Calendar.getInstance();
    boolean temp;
    double value;
    int i;
    int count;
    g1.showLoad();
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
            g1.showOffLoad();
            g1.showInputError("Parse exception");
            return;
          }
        }
      }
      while (temp);
    }
    g1.showOffLoad();
    g1.plot(trial);
  }


  @Override
  public void showParticularPortfolio() {
    try {
      Map<String,Integer> port = i1.loadPortfolio(g1.showParticularPortfolio());
      Map<String, Double> hm = new HashMap<>();
      for(String ss : port.keySet()){
        hm.put(ss,Double.parseDouble(port.get(ss).toString()));
      }
      g1.showStocks(hm);
    }
    catch (Exception e){
      g1.showInputError(e.getMessage());
    }
    g1.resetFocus();
  }


  @Override
  public void totalPortfolioValue(ArrayList<String> portfolio) {
    try{
      String name;
      String date;
      checkNull(portfolio);
      name= portfolio.get(0);
      date= portfolio.get(1);
      g1.showLoad();
      g1.showValueOfPortfolio(i1.getPortfolioValuation(name,date),name);
      g1.showOffLoad();
    }
    catch (Exception e){
      g1.showOffLoad();
      g1.showInputError(e.getMessage());
    }
  }


}
