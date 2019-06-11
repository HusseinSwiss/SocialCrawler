package LinkedInCrawler;
  
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Common.Common;
import entities.LinkedInCompany; 

/**
 * @author hussein.hazimeh
 * @HES-SO, Switzerland
 * 22.03.2019
 *
 */
public class LinkedInCompanyCrawler{
	
	
	 private static WebDriver driver;  
	 private static JSONArray list;
	 private static JSONObject o2; 
	 private  String username;
	 private  String password;
	 private String excel_path;
	 private String driver_path;
	 private String json_path;
	  
	public void set_json_path(String path)
	{
			this.json_path = path;
	}
	public void login(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	public void set_excel_path(String path)
	{
		this.excel_path = path;
	}
	public void set_driver_path(String path)
	{
		this.driver_path = path;
	}
   public  void getdata(int start, int end) throws IOException, JSONException, InterruptedException
   {
   	 
   	Common com = new Common();
       com.setInputFile(this.excel_path);
      
       List<String> urls =  com.read(start, end);
         
        System.setProperty("webdriver.chrome.driver", this.driver_path +"\\chromedriver.exe" );
        driver  = new ChromeDriver();
         
        driver.get("https://www.linkedin.com/home");
        driver.findElement(By.name("session_key")).sendKeys(this.username);
        driver.findElement(By.name("session_password")).sendKeys(this.password); 
        driver.findElement(By.id("login-submit")).click();
        
         String company_name = null;
    	 String business_type = null;
    	 String business_loc = null;
    	 String num_followers = null;
    	 String overview = null;
    	 String website = null;
    	 String com_size = null;
    	 String com_type = null;
    	 String founded = null;
    	 String spetialities = null;
    	 String funding_total_rounds = null; 
         String last_round_date = null;
         String last_round_amount = null;
        
        ArrayList<LinkedInCompany> companies = new ArrayList<LinkedInCompany>();
        
        for(int i=0;i<urls.size();i++)
        {  
        	driver.get(urls.get(i));
        	System.out.println(urls.get(i));

        	company_name = driver.findElement(By.xpath(".//*[@class='org-top-card-summary__title t-24 t-black truncate']")).getText();
        	business_type = driver.findElement(By.xpath(".//*[@class='org-top-card-summary__info-item org-top-card-summary__industry']")).getText();
        	business_loc = driver.findElement(By.xpath(".//*[@class='org-top-card-summary__info-item org-top-card-summary__headquarter']")).getText();
        	num_followers = driver.findElement(By.xpath(".//*[@class='org-top-card-summary__info-item org-top-card-summary__follower-count']")).getText();
        
        	driver.get(urls.get(i)+"/about");
        	
        	overview = driver.findElement(By.xpath(".//*[@class='break-words white-space-pre-wrap mb5 t-14 t-black--light t-normal']")).getText();
        	website = driver.findElement(By.xpath(".//*[@class='link-without-visited-state']")).getText();
        	com_size = driver.findElement(By.xpath(".//*[@class='org-page-details__definition-text t-14 t-black--light t-normal']")).getText();
        	
        	List<WebElement> about = driver.findElements(By.xpath(".//*[@class='org-page-details__definition-term t-14 t-black t-bold']")); //e.g.: Founded
        	List<WebElement> _about =  driver.findElements(By.xpath(".//*[@class='org-page-details__definition-text t-14 t-black--light t-normal']")); //e.g.: 2003
        	
        	for(int e = 0; e < about.size(); e++)
        	{
        		switch(about.get(e).getText())
        		{
        		case "Company size":
        			com_size = _about.get(e).getText();
        			break;
        		case "Type":
        			com_type = _about.get(e).getText();
        			break;
        		case "Founded":
        			founded = _about.get(e).getText();
        			break;
        		case "Specialties":
        			spetialities = _about.get(e).getText();
        			break;
        		}
        	}
        	
        	try
        	{
        		
        	funding_total_rounds = driver.findElement(By.xpath(".//*[@class='org-funding__links t-14 t-black--light t-normal']")).getText(); 
        	last_round_date = driver.findElement(By.xpath(".//*[@class='org-funding__links t-14 t-normal t-black--light']")).getText();
        	last_round_amount =  driver.findElement(By.xpath(".//*[@class='t-24 t-light t-black--light']")).getText();
        	
        	}
        	catch(NoSuchElementException e){}
        	
        	JavascriptExecutor js = ((JavascriptExecutor) driver);
        	for(int j=0;j<30;j++) js.executeScript("window.scrollTo("+j+", document.body.scrollHeight)");
        	
         
        	 System.out.println("business_type : " + business_type + " business_loc: " + business_loc + " followers_count: " + num_followers
   			 + " website : " + website + " size: " + com_size + " type : " + com_type + " founded : " + founded + " spetial : " + spetialities
   			 + " funding_total_rounds : " +funding_total_rounds + " last_round_date : " + last_round_date + " amout : " + last_round_amount);
 		       
 		      LinkedInCompany company = new LinkedInCompany();
 		      
 		      company.setFullname(company_name);
 		      company.setLocation(business_loc);
 		      company.setBusiness_type(business_type);
 		      company.setCom_size(com_size);
 		      company.setCom_type(com_type);
 		      company.setFounded(founded);
 		      company.setFollowers_count(num_followers);
 		      company.setOverview(overview);
 		      company.setSpetialities(spetialities);
 		      company.setWebsite(website);
 		      company.setFunding_total_rounds(funding_total_rounds);
 		      company.setlast_round_date(last_round_date);
 		      company.setlast_round_amount(last_round_amount);
 		      
 		      companies.add(company);
        } 
        driver.close(); 
        
        	System.out.println(companies.size() + " is the size of the persons");
        	  
	    	list = new JSONArray();
	    	
        	for(LinkedInCompany c : companies)
        	{
        		System.out.println(c.getFullname() + " ++ " );
        		
        		o2 = new JSONObject();
        		o2.put("Company_name: ", c.getFullname());
        		o2.put("Business_location: ", c.getLocation());
        		o2.put("Business_type: ", c.getBusiness_type());
        		o2.put("Company_size: ", c.getCom_size());
        		o2.put("Company_type: ", c.getCom_type());
        		o2.put("Founded_date: ", c.getFounded());
        		o2.put("Num_of_followers: ", c.getFollowers_count());
        		o2.put("Company_overview: ", c.getOverview());
        		o2.put("Spetialities: ", c.getSpetialities());
        		o2.put("Company_website: ", c.getWebsite());
        		o2.put("Funding_total_rounds: ", c.getFunding_total_rounds());
        		o2.put("Last_round_date: ", c.getlast_round_date());
        		o2.put("Last_round_amount: ", c.getlast_round_amount());
        		
        		list.add(o2);
        	}
        	com.buildDataset("LinkedIn_companies", list, this.json_path); 
        	
        	} 
}
