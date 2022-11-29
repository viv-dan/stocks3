package stocksmodel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class abstractInvestorextensions {

  protected abstract String getFilename();

  protected static Date getDateFromString(String date) {
    Date intoDate;
    try {
      intoDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (java.text.ParseException e) {
      throw new RuntimeException("Invalid date " + e.getMessage());
    }
    return intoDate;
  }

  protected JSONObject readJSON() {
    try (FileReader reader = new FileReader(this.getFilename())) {
      JSONParser jsonParser = new JSONParser();
      return (JSONObject) jsonParser.parse(reader);
    } catch (IOException e) {
      throw new RuntimeException("Cannot retrieve stored data " + e.getMessage());
    } catch (ParseException e) {
      throw new RuntimeException("parsing");
    }
  }

  protected void writeToJSON(JSONObject data) {
    try (FileWriter file = new FileWriter(this.getFilename())) {
      file.write(data.toJSONString());
    } catch (IOException e) {
      throw new RuntimeException("Cannot store data!!");
    }
  }
}
