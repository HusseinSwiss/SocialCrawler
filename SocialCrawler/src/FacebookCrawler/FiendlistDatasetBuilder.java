package FacebookCrawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author hussein.hazimeh
 * 23.03.2019
 *
 */
public class FiendlistDatasetBuilder {
 
	 private static String dir = "C:/Users/husseiny.hazimeh/Desktop/chromedriver_win32";
	 private static WebDriver driver;

	 
	    public static void main(String[] args) throws IOException, JSONException {
	    	 ArrayList<String> newList = new ArrayList<String>() ;//cc.get_new_list();
	    	
	    	 
	    	newList = new FiendlistDatasetBuilder().get_new_list();
	    	writeinexcel(newList);
	    }
	    
	    public ArrayList<String> get_new_list() throws IOException, JSONException
	    {
	    	System.setProperty("webdriver.chrome.driver", dir +"\\chromedriver.exe" );
	        driver  = new ChromeDriver();
	         
	        driver.get("https://www.facebook.com/");
	        
	        driver.findElement(By.name("email")).sendKeys("email");
	        driver.findElement(By.name("pass")).sendKeys("pass"); 
	        driver.findElement(By.id("loginbutton")).click();
	      
	        driver.get("https://www.facebook.com/groups/2239890049/members/");
	 
	        JavascriptExecutor js = ((JavascriptExecutor) driver);
        	for(int j=0;j<100000;j++) js.executeScript("window.scrollTo("+j+", document.body.scrollHeight)");
        	
        	List<WebElement> e = driver.findElements(By.xpath("//div[@class='_60ri']/a"));
        	ArrayList<String> friends = new ArrayList<String>();
        	
        	for (int i=0;i<e.size();i++)
        	{
        		String url = e.get(i).getAttribute("href");
        		
        		int x = url.indexOf("?");
        		String clean_url = url.substring(0, x);
        		System.out.println(clean_url);
        		friends.add(clean_url);
        	}

        	driver.close();
        	return friends;
	    }
	   
	    private static final String EXCEL_FILE_LOCATION = "d:\\isas\\urls_write.xls";
	    public  static void  writeinexcel(ArrayList<String> list) throws IOException  {

	        //1. Create an Excel file
	        WritableWorkbook myFirstWbook = null;
	        try {

	            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));

	            // create an Excel sheet
	            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
	            
	            int i=0;
	            for(String s: list){
	            	
	            	Label l = new Label(0, i, s);
	            	excelSheet.addCell(l);
	            	i++;
	            }
	          
	            myFirstWbook.write();


	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (WriteException e) {
	            e.printStackTrace();
	        } finally {

	            if (myFirstWbook != null) {
	                try {
	                    myFirstWbook.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } catch (WriteException e) {
	                    e.printStackTrace();
	                }
	            }


	        }

	    }
	    }

