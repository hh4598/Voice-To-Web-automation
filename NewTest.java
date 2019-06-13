package example;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterTest;

public class NewTest {
private WebDriver driver;  

//@Test
//  public void f() throws InterruptedException {
//	testme();
//  }

@BeforeTest
  public void beforeTest() {
	driver = new ChromeDriver();
	driver.manage().window().maximize();
  }

  @AfterTest
  public void afterTest() throws InterruptedException {
	  Thread.sleep(2000);
	  driver.quit();
  }

  public void testme(String s) throws InterruptedException {
	  	System.out.println("in selenium : "+s);

		driver.get("http://www.amazon.in/"); 
				
	    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(s);
	    
	    driver.findElement(By.className("nav-input")).click();
	    
	    List<WebElement> allLinks = driver.findElements(By.cssSelector("a[class='a-link-normal a-text-normal']"));
//	    System.out.println("executed");
	    
//	    for(WebElement list :allLinks) {
//	    	System.out.println(list.getText() + " - " + list.getAttribute("href"));
//	    }
	    String firstLinkData = allLinks.get(0).getAttribute("href");
	    String urlPattern = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
	    Pattern p = Pattern.compile(urlPattern);
	    Matcher m = p.matcher(firstLinkData);
	    if(m.find()) {
	    	driver.navigate().to(m.group(0));  	  
	    }
	    driver.findElement(By.id("add-to-cart-button")).click();
	    Thread.sleep(5000);
		}
}
