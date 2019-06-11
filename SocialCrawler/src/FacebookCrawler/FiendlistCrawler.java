package FacebookCrawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

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
public class FiendlistCrawler {
 
	 private static String dir = "C:/Users/husseiny.hazimeh/Desktop/chromedriver_win32";
	 private static WebDriver driver;
	 private  String inputFile;
	 
	    public static void main(String[] args) throws IOException, JSONException {
	    	FiendlistCrawler cc = new FiendlistCrawler();
	    	ArrayList<String> newList = cc.get_new_list();
	    	
	    	/* the original friend list fetched from excel file. To write this original list to the excel file
	    	 * change the body of get_new_list() function and write the crawled firend names into a text file
	    	 * then copy/paste these names into an excel sheet, and finally save this sheet as .xls format
	    	 * you will then compare all the new lists (live lists) with this baseline excel list and get 
	    	 * all the friend names who unfriend you.
	    	 */
	    	
	    	cc.setInputFile("d:/friend2.xls");
	    	ArrayList<String> oldList = cc.get_old_list();
	    	System.out.println("i am in excel file");
	    	System.out.println(newList.size() + " current friend list : " + newList);
	    	System.out.println(oldList.size() + " old friend list : " + oldList);
	    	oldList.removeAll(newList);
	    	System.out.println("UnFreind_list : " + oldList);	 
	    }
	    
	    public ArrayList<String> get_new_list() throws IOException, JSONException
	    {
	    	System.setProperty("webdriver.chrome.driver", dir +"\\chromedriver.exe" );
	        driver  = new ChromeDriver();
	         
	        driver.get("https://www.facebook.com/");
	        
	        driver.findElement(By.name("email")).sendKeys("email");
	        driver.findElement(By.name("pass")).sendKeys("pass"); 
	        driver.findElement(By.id("loginbutton")).click();
	      
	        driver.get("https://www.facebook.com/HusseinHazimeh2/friends");
	 
	        JavascriptExecutor js = ((JavascriptExecutor) driver);
        	for(int j=0;j<2500;j++) js.executeScript("window.scrollTo("+j+", document.body.scrollHeight)");
        	
        	List<WebElement> e = driver.findElements(By.xpath(".//*[@class='fsl fwb fcb']"));
        	ArrayList<String> friends = new ArrayList<String>();
        	
        	for (int i=0;i<e.size();i++)
        	{
        		friends.add(e.get(i).getText());
        	}

        	driver.close();
        	return friends;
	    }
	    
	    public  void setInputFile(String inputFile) {
	        this.inputFile = inputFile;
	    }
	    public  ArrayList<String> get_old_list() throws IOException  {
	    	ArrayList<String> friends = new ArrayList<String>();
	        File inputWorkbook = new File(inputFile);
	        Workbook w;
	        try {

	            WorkbookSettings workbookSettings = new WorkbookSettings();
	            workbookSettings.setEncoding( "ISO-8859-1" );
	            
	            w = Workbook.getWorkbook( inputWorkbook, workbookSettings );
	            
	            Sheet sheet = w.getSheet(0);
	            
	            for (int j = 0; j < sheet.getColumns(); j++) {
	                for (int i = 0; i < sheet.getRows(); i++) {
	                    Cell cell = sheet.getCell(j, i);
	                    CellType type = cell.getType();
	                    
	                    if (type == CellType.LABEL) {
	                    	friends.add(cell.getContents());
	                    }
	                    if (type == CellType.NUMBER) {
	                    }
	                   }
	                }
	        } catch (BiffException e) {
	            e.printStackTrace();
	        }
	        return friends;
	    }
}
