package CrunchbaseCrawler;
 

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
 

import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.chrome.ChromeDriver;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CrunchbaseCrawler {
	
	private static String dir = "C:/Users/husseiny.hazimeh/Desktop/chromedriver_win32";
	private static WebDriver driver;
	private String inputFile;

	
    public void setInputFile(String inputFile) {
    	
        this.inputFile = inputFile;
    }

    public List<String> read() throws IOException  {
    	List<String> urls = new ArrayList<>();
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            Sheet sheet = w.getSheet(0);
            
            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < 4; i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        urls.add(cell.getContents());
                    }

                    if (type == CellType.NUMBER) {
                    }
                   }
                }
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	 new CrunchbaseCrawler().getdata();
        
    }
    
    public void getdata() throws IOException, InterruptedException
    {
    	CrunchbaseCrawler test = new CrunchbaseCrawler();
        test.setInputFile("d:/isas/telegram.xls");
        List<String> urls =  test.read();
        
        
        
        System.setProperty("webdriver.chrome.driver", dir +"\\chromedriver.exe" ); driver  = new ChromeDriver();
//        for(int i=0;i<urls.size();i++)
//        { 
        for(int i=0; i<20;i++){
        	
        	
        	System.out.println("https://www.reddit.com/");
        	driver.get("https://github.com/husseinswiss");
        	//Thread.sleep(10000);
        	
        }
        driver.close();
        }
        
    }
    
   

