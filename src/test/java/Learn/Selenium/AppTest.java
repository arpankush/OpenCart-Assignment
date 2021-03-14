package Learn.Selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest{

	public static WebDriver driver;
		
	public static void main(String args[]) throws InterruptedException, IOException {
		String url = "https://www.opencart.com/";
		browserSetup(url);
		runTests();
	}

	public static void browserSetup(String url) {
		
		
		  WebDriverManager.chromedriver().setup(); ChromeOptions options = new
		  ChromeOptions(); //options.addArguments("--disable-infobars"); //deprecated
		  options.addArguments("--start-maximized"); driver = new
		  ChromeDriver(options); driver.manage().timeouts().implicitlyWait(60,
		  TimeUnit.SECONDS); 
		  driver.get(url);
		 
		 
		
		/*
		 * WebDriverManager.firefoxdriver().setup();
		 * System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"TRUE")
		 * ;
		 * System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"logs.txt");
		 * driver = new FirefoxDriver(); driver.get(url);
		 */
		
		/*
		 * WebDriverManager.edgedriver().setup(); driver = new EdgeDriver();
		 * driver.get(url);
		 */
	}

	private static void runTests() throws IOException {
		
		By login = By.xpath("//div[@class=\"navbar-right hidden-xs\"]//child::a[@href='https://www.opencart.com/index.php?route=account/login']");
		By username = By.xpath("//input[@id=\"input-email\"]");
		By password = By.xpath("//input[@id=\"input-password\"]");
		By finallyLogin = By.xpath("//button[@type='submit' and @class=\"btn btn-primary btn-lg hidden-xs\"]");
		
		By lnkDemo = By.xpath("//ul[@class=\"nav navbar-nav\"]/descendant::a[text()='Demo']");
		By lnkSelectDemo = By.xpath("//a[@href=\"http://demo.opencart.com/\"]");
		
		By lnkAddCartMacBook = By.xpath("//a[text()='MacBook']/ancestor::div[@class=\"caption\"]/following::div[@class=\"button-group\"]/child::button[@onclick=\"cart.add('43');\"]");
		By txtMackBookAddedToCart = By.xpath("//a[text()='MacBook']/ancestor::div[@class='alert alert-success alert-dismissible']");
		By lnkComponentsDropdown = By.xpath("//a[text()='Components']/parent::li[@class='dropdown']");
		By lnkPhonesnPDAsDropdown = By.xpath("//ul[@class='nav navbar-nav']/li/a[text()='Phones & PDAs']");
		By lnkMonitors = By.xpath("//a[starts-with(text(),'Monitor')]");
		By slctSort = By.xpath("//select[@id='input-sort']");
		By btnAddToCompare = By.xpath("//button[starts-with(@onclick,'compare.add')]");
		By lnkcomparision = By.xpath("//a[text()='product comparison']");
		By lnkFirstComparisionItem = By.xpath("//h1[text()='Product Comparison']/following::table/tbody[1]/tr[1]/td[2]/a[1]");
		By fifthFeature = By.xpath("//div[@id='tab-description']/ul/li[5]");
		By lnkProductPageAddToCart = By.xpath("//button[@id=\"button-cart\"]");
		By lnkCart = By.xpath("//div[@id=\"cart\"]/button");
		By lnkCheckout = By.xpath("//strong[contains(text(),' Checkout')]/parent::a");
		
		String[] loginDetails = readExcel();
		
		//Login
		driver.findElement(login).click();
		driver.findElement(username).sendKeys(loginDetails[0]);
		driver.findElement(password).sendKeys(loginDetails[1]);
		driver.findElement(finallyLogin).click();
		//Open Demo Cart
		driver.findElement(lnkDemo).click();
		driver.findElement(lnkSelectDemo).click();
		
		//Switch Tab and Go to Demo Cart
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		//Add MacBook to Cart
		driver.findElement(lnkAddCartMacBook).click();
		if(driver.findElement(txtMackBookAddedToCart).isDisplayed())
			System.out.println("MackBook added to cart successfully.");
		else
			System.out.println("Can't verfiy whether MackBook is added to cart.");
		
		//Hover on Components Dropdown
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(lnkComponentsDropdown)).perform();
		//Click Monitors
		driver.findElement(lnkMonitors).click();
		//Click Phones & PDAs
		driver.findElement(lnkPhonesnPDAsDropdown).click();
		//Sort High > Low
		Select select = new Select(driver.findElement(slctSort));
		select.selectByVisibleText("Price (High > Low)");
		List<WebElement> objsBtnAddToCompare = driver.findElements(btnAddToCompare);
		for (int i = 0; i <= 2; i++) {
			objsBtnAddToCompare.get(i).click();
		}
		driver.findElement(lnkcomparision).click();
		driver.findElement(lnkFirstComparisionItem).click();
		String strFifthFeature = driver.findElement(fifthFeature).getText();
		writeTextFile(strFifthFeature);
		driver.findElement(lnkProductPageAddToCart).click();
		driver.findElement(lnkCart).click();
		driver.findElement(lnkCheckout).click();
		
		//driver.quit();


	}
	
	public static String[] readExcel() throws IOException {
		String pathname = System.getProperty("user.dir") + "\\resources\\input.xlsx";
		File f = new File(pathname);
		FileInputStream fin = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fin);
		XSSFSheet s = wb.getSheetAt(0);
		XSSFRow r = s.getRow(1);
		String[] login = new String[2];
		login[0] = r.getCell(0).getStringCellValue();
		login[1] = r.getCell(1).getStringCellValue();
		return login;
	}
	
	public static void writeTextFile(String input) throws IOException {
		String fileLocation = System.getProperty("user.dir") + "\\resources\\textFile.txt";
		File f = new File(fileLocation);
		f.createNewFile();
		FileWriter fw = new FileWriter(fileLocation);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(input);
		bw.newLine();
		bw.close();
	}

}
