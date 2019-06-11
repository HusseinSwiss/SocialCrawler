package FacebookCrawler;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List; 
import java.util.concurrent.TimeoutException; 
 
import org.openqa.selenium.By; 
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import entities.Post;


public class FacebookPostCrawler {
	
	  public static String dir = "C:/Users/husseiny.hazimeh/Desktop/chromedriver_win32";
	  public static WebDriver driver;
	  public static ArrayList<Post> Posts = new ArrayList<Post>();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////		
public static void main (String [] a) throws IOException, InterruptedException, TimeoutException{
	  
	  String accounts [] = { "https://www.facebook.com/cnn/", 
			 "https://www.facebook.com/bbc/"
			  			   };
	  System.setProperty("webdriver.chrome.driver", dir +"\\chromedriver.exe" );
	  
	  //System.setProperty("webdriver.gecko.driver", "C:/Users/Hazimeh/Desktop/geckodriver-v0.19.1-win64/geckodriver.exe" );
	   //driver = new FirefoxDriver();
	   
	  driver  = new ChromeDriver();
	  driver.get("https://www.facebook.com/");
      driver.findElement(By.name("email")).sendKeys("email");
      driver.findElement(By.name("pass")).sendKeys("pass"); 
      driver.findElement(By.id("loginbutton")).click();
    
    
       for(int x=0;x<accounts.length;x++){
      
    	   driver.get(accounts[x]);
  	
    	   JavascriptExecutor js = ((JavascriptExecutor) driver);
		   for(int i=0;i<1000;i++) js.executeScript("window.scrollTo("+i+", document.body.scrollHeight)");
		   
		   try{  
			   List<WebElement> root = driver.findElements(By.xpath(".//*[@class='_ipn clearfix _-5d']")); 
			   List<WebElement> reactions = driver.findElements(By.xpath(".//*[@class='_4arz']")); 
			   List<WebElement> list = driver.findElements(By.xpath(".//*[@class='_5pbx userContent _3576']"));
		       @SuppressWarnings("unused")
			List<WebElement> nof_list = driver.findElements(By.xpath(".//*[@class='_ipm _-56']"));
		     for(int i=0; i<list.size(); i++) { 
		    	  Post post = new Post();
		        try{ 
		         post.set_post_content(list.get(i).getText());
		         //if(nof_list.get(i).getText().equals(""))  post.set_n_of_comments("0 comments"); else post.set_n_of_comments(nof_list.get(i).getText());
		         WebElement noc = root.get(i).findElement(By.tagName("a"));
		         WebElement nor = reactions.get(i).findElement(By.tagName("span"));
		         post.set_n_of_comments(noc.getText());
		         Posts.add(post);
		         System.out.println("post text: " + list.get(i).getText() + "\n comments:  " +  noc.getText() + " nor**** " + nor.getText()); 
		        }catch(ArrayIndexOutOfBoundsException e){System.out.println("eroor " +e.getMessage());};
		     }
		    }catch(NoSuchElementException e){System.out.println("eroor " +e.getMessage());}
	//driver.close();
}
       System.out.println("i am in the loop");
       for(Post p : Posts){
    	   System.out.println(p.get_post_content());
    	   System.out.println(p.get_n_of_comments());
       }
}		
}
