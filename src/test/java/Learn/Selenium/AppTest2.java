package Learn.Selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AppTest2 extends AppTest {

	public static void main(String[] args) throws InterruptedException {
		browserSetup("https://www.testandquiz.com/selenium/testing.html");
		runTests();
	}

	private static void runTests() throws InterruptedException {
		// Double Click
		// Actions action = new Actions(driver);
		/*
		 * WebElement dblClkBtn = driver.findElement(By.id("dblClkBtn"));
		 * action.doubleClick(dblClkBtn).perform(); //Handle Alert String alertText =
		 * driver.switchTo().alert().getText(); if
		 * (alertText.equals("hi, JavaTpoint Testing")) {
		 * driver.switchTo().alert().accept(); System.out.println("Accepted"); } else {
		 * driver.switchTo().alert().dismiss(); System.out.println("Dismissed: " +
		 * alertText); }
		 */

		// Drag & Drop
		Actions action = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		
		  WebElement s = driver.findElement(By.id("sourceImage")); WebElement t =
		  driver.findElement(By.id("targetDiv"));
		  
		  driver.switchTo(); js.executeScript("arguments[0].scrollIntoView();", s);
		  action.clickAndHold(s)
		  .pause(Duration.ofSeconds(2))
		  .moveToElement(t)
		  .pause(Duration.ofSeconds(2))
		  .release().build().perform();
		
		Thread.sleep(3000);
		
		driver.quit();
	}

}
