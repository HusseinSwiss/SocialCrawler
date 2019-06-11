package Common;

import java.io.IOException;

import org.json.JSONException;
import org.openqa.selenium.NoSuchElementException;
  
import LinkedInCrawler.LinkedInPersonCrawler;

/**
 * @author husseiny.hazimeh
 * @HES-SO, Switzerland
 * @
 * 28.03.2019
 */
public class Main {
	//example of feature extraction from LinkedIn person profiles
	public static void main(String [] args) throws NoSuchElementException, IOException, JSONException, InterruptedException{	
		 LinkedInPersonCrawler f = new LinkedInPersonCrawler();
		 f.login("username", "password");
		 f.set_driver_path("C:/Users/husseiny.hazimeh/Desktop/chromedriver_win32");
		 f.set_excel_path("d:/isas/urls.xls");
		 f.set_json_path("d:\\LinkedIn.json");
		 f.getdata(0, 2);
	}
}
