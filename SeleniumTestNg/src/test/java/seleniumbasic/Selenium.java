package seleniumbasic;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Selenium {
	public WebDriver driver;

	public void testInitialize(String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"E:\\seleniumfiles\\driverfiles\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"E:\\seleniumfiles\\driverfiles\\geckodriver-v0.29.1-win32\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver",
					"E:\\seleniumfiles\\driverfiles\\edgedriver_win32\\edgedriver.exe");
			driver = new EdgeDriver();
		} else {
			throw new Exception("Invalid browser");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		testInitialize("firefox");
	}

	@AfterMethod
	public void tearDown() {
		//driver.close();
		// driver.quit();
	}

	@Test(enabled = false)

	public void verifyLogin() {
		driver.get("http://demo.guru99.com/test/newtours/");
		WebElement username = driver.findElement(By.name("userName"));
		WebDriverWait wait=new WebDriverWait(driver,90);
		//wait.until(ExpectedConditions.visibilityOf(username));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("userName")));
		WebElement password = driver.findElement(By.name("password"));
		WebElement submit = driver.findElement(By.name("submit"));

		username.sendKeys("test123");
		password.sendKeys("12345");
		submit.click();
	}
	
	@Test(enabled=true)
	public void loginTest() throws InterruptedException
	{
		driver.get("https://buffalocart.com/demo/billing/public/login");
		WebElement username = driver.findElement(By.id("username"));
		
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		username.sendKeys("admin");
		password.sendKeys("123456");
		loginButton.click();
		WebElement endtour=driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']"));
		endtour.click();
		/*List<WebElement> sidebar = driver.findElements(By.xpath("//ul[@class='sidebar-menu']//a//span[@class='title']"));
		for(int i=0;i<sidebar.size();i++)
		{
			System.out.println(sidebar.get(i).getText());
		}*/
		
		WebElement sidebar=driver.findElement(By.xpath("//ul[@class='sidebar-menu']//a//span[@class='title']"));
		sidebar.click();
		Thread.sleep(3000);
		List<WebElement> menu = driver.findElements(By.xpath("//li[@class='treeview  active']//ul[@class='treeview-menu menu-open']//span"));
		Thread.sleep(5000);
		for(int i=0;i<menu.size();i++)
		{
			System.out.println(menu.get(i).getText());
			//Thread.sleep(5000);
			//if(menu.get(i).getText().equals(Users))
		}		
		
		
	}

	@Test(enabled = false)
	public void verifyPageTitle() {
		driver.get("http://demo.guru99.com/test/newtours/");
		String expectedPageTitle = "Welcome: Mercury Tours";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedPageTitle, "page title mismatch found");
	}

	@Test(enabled = false)
	public void verifyrRegisterButtonEnableOrNot() {
		driver.get("http://demowebshop.tricentis.com/register");
		WebElement register = driver.findElement(By.xpath("//a[@class='ico-register']"));
		register.click();
		WebElement registerButton = driver.findElement(By.id("register-button"));
		boolean actualValue = registerButton.isEnabled();
		// Assert.assertTrue(actualValue, "register button disabled");
		Assert.assertFalse(actualValue, "register button enabled");

	}

	@Test(enabled = false)
	public void softAssert() {
		SoftAssert sAssert = new SoftAssert();
		System.out.println("Assert method started");
		sAssert.assertTrue(false, "failed");
		System.out.println("Assert method completed");
		sAssert.assertAll();
	}

	@Test(enabled = false)
	public void navigationCommands() {
		driver.get("http://demo.guru99.com/test/newtours/");
		// driver.navigate().to("http://demo.guru99.com/test/newtours/");
		WebElement register = driver.findElement(By.xpath("//a[text()='REGISTER']"));
		register.click();
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
	}

	@Test(enabled = false)
	public void fileUpload() {
		driver.get("http://demo.guru99.com/test/upload/");
		WebElement file = driver.findElement(By.id("uploadfile_0"));
		file.sendKeys("E:\\seleniumfiles\\data.txt");
		WebElement checkbox = driver.findElement(By.id("terms"));
		checkbox.click();
		WebElement submit = driver.findElement(By.id("submitbutton"));
		submit.click();
	}

	@Test(enabled = false)
	public void windowHandles() {
		driver.get("http://demo.guru99.com/popup.php");
		String parentWindow = driver.getWindowHandle();
		// System.out.println(parentWindow);
		WebElement clickhere = driver.findElement(By.xpath("//a[text()='Click Here']"));
		clickhere.click();
		Set<String> handleIds = driver.getWindowHandles();
		Iterator<String> itr = handleIds.iterator();
		while (itr.hasNext()) {
			// System.out.println(itr.next());
			String childWindow = itr.next();
			if (!parentWindow.equals(childWindow)) {
				driver.switchTo().window(childWindow);
				WebElement email = driver.findElement(By.xpath("//input[@name='emailid']"));
				email.sendKeys("jovi6@gmail.com");
				WebElement submitbutton = driver.findElement(By.xpath("//input[@name='btnLogin']"));
				submitbutton.click();
				driver.close();

			}
		}

		driver.switchTo().window(parentWindow);

	}

	@Test(enabled = false)
	public void newTab() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		// System.out.println(parentWindow);
		WebElement newTab = driver.findElement(By.id("tabButton"));
		newTab.click();
		Thread.sleep(3000);
		Set<String> handlesId = driver.getWindowHandles();
		Iterator<String> it = handlesId.iterator();
		while (it.hasNext()) {
			// System.out.println(it.next());
			String childWindow = it.next();
			if (!parentWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				String expectedString = "This is a sample page";
				WebElement newTabText = driver.findElement(By.id("sampleHeading"));
				String actualString = newTabText.getText();
				// System.out.println(actualString);
				Assert.assertEquals(actualString, expectedString, "message mismatch");

			}
		}
	}

	@Test(enabled = false)
	public void newWindow() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		WebElement nextWindow = driver.findElement(By.id("windowButton"));
		nextWindow.click();
		Thread.sleep(3000);
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			String childWindow = it.next();
			if (!parentWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				String expectedMessage = "This is a sample page";
				WebElement nextWindowMessage = driver.findElement(By.id("sampleHeading"));
				String actualMessage = nextWindowMessage.getText();
				Assert.assertEquals(actualMessage, expectedMessage, "Message mismatch");

			}
		}
	}

	@Test(enabled = false)
	public void newWMessage() throws InterruptedException {
		driver.get("https://demoqa.com/browser-windows");
		String parentWindow = driver.getWindowHandle();
		WebElement nextMsgWindow = driver.findElement(By.id("messageWindowButton"));
		nextMsgWindow.click();
		Thread.sleep(3000);
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			String childWindow = it.next();
			if (!parentWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				String expectedMessage = "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.";
				WebElement nextWindowMessage = driver.findElement(By.xpath(
						"//body[text()='Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.']"));
				String actualMessage = nextWindowMessage.getText();
				Assert.assertEquals(actualMessage, expectedMessage, "Message mismatch");
			}

		}

	}
	
	@Test(enabled=false)
	public void handlingAlert()
	{
		driver.get("http://demo.guru99.com/test/delete_customer.php");
		WebElement custid=driver.findElement(By.xpath("//input[@name='cusid']"));
		custid.sendKeys("7896");
		WebElement submit=driver.findElement(By.xpath("//input[@name='submit']"));
		submit.click();
		Alert alert=driver.switchTo().alert();
		String actualText=alert.getText();
		String expectedText="Do you really want to delete this Customer?";
		Assert.assertEquals(actualText, expectedText, "Text mismatch");
		//alert.accept();
		alert.dismiss();
		
	}
	
	@Test(enabled=false)
	public void alertHw1()
	{
		driver.get("https://demoqa.com/alerts");
		WebElement click1=driver.findElement(By.xpath("//button[@id='alertButton']"));
		click1.click();
		Alert alert=driver.switchTo().alert();
		String actualText=alert.getText();
		String expectedText="You clicked a button";
		Assert.assertEquals(actualText, expectedText, "Text mismatch");
		alert.accept();
	
	}

	@Test(enabled=false)
	public void alertHw2() throws InterruptedException
	{
		driver.get("https://demoqa.com/alerts");
		WebElement click3=driver.findElement(By.xpath("//button[@id='confirmButton']"));
		click3.click();
		Alert alert=driver.switchTo().alert();
		String actualText=alert.getText();
		String expectedText="Do you confirm action?";
		Assert.assertEquals(actualText, expectedText, "Text mismatch");
		//alert.accept();
		Thread.sleep(3000);
		alert.dismiss();
		
	}

	
	@Test(enabled=false)
	public void alertHw3() throws InterruptedException
	{
		driver.get("https://demoqa.com/alerts");
		WebElement click4=driver.findElement(By.xpath("//button[@id='promtButton']"));
		click4.click();
		Alert alert=driver.switchTo().alert();
		String actualText=alert.getText();
		String expectedText="Please enter your name";
		Assert.assertEquals(actualText, expectedText, "Text mismatch");
		alert.sendKeys("sheeba");
		Thread.sleep(3000);
		alert.accept();
		//alert.dismiss();
		
	}
	
	@Test(enabled=false)
	public void handlingDropdown()
		{
			driver.get("http://demo.guru99.com/test/newtours/");
			WebElement register=driver.findElement(By.xpath("//a[text()='REGISTER']"));
			register.click();
			WebElement dropdown=driver.findElement(By.xpath("//select[@name='country']"));
			Select select=new Select(dropdown);
			//select.selectByVisibleText("ANGOLA");
			//select.selectByIndex(4);
			select.selectByValue("ANGOLA");
			List <WebElement> actualCountryList=select.getOptions();
			for(int i=0;i<actualCountryList.size();i++)
			{
				System.out.println(actualCountryList.get(i).getText());
			}
		}
	
	@Test(enabled=false)
	public void dropdownhw()
	{
		driver.get("https://demoqa.com/select-menu");
		WebElement dropdown=driver.findElement(By.xpath("//select[@id='oldSelectMenu']"));
		Select select=new Select(dropdown);
		select.selectByVisibleText("Purple");
		
		ArrayList<String> expectedList=new ArrayList<String>();  
		expectedList.add("Red");
		expectedList.add("Blue");
		expectedList.add("Green");
		expectedList.add("Yellow");
		expectedList.add("Purple");
		expectedList.add("Black");
		expectedList.add("White");
		expectedList.add("Violet");
		expectedList.add("Indigo");
		expectedList.add("Magenta");
		expectedList.add("Aqua");
		//System.out.println(expectedList);
		List <WebElement> actualList=select.getOptions();
		for(int i=0;i<actualList.size();i++)
		{
			System.out.println(actualList.get(i).getText());
		}
		if(actualList.size()==expectedList.size())
		{
			for(int i=0;i<expectedList.size();i++)
			{
				WebElement actualTxt=actualList.get(i);
				String expectedTxt=expectedList.get(i);
				Assert.assertEquals(actualTxt, expectedTxt,"failed");
				
				
			}
		}
		else
		{
			System.out.println("size different");
		}
	}
	
	@Test(enabled=false)
	public void howManyDropdown()
	{
		driver.get("https://pynishant.github.io/dropdown-visibility-demo.html");
		List <WebElement> dropdown=driver.findElements(By.tagName("select"));
		System.out.println("No.of dropdown="+dropdown.size());
		
		//WebElement langdrop=driver.findElement(By.xpath("//div[@class='select-selected']"));
		//langdrop.click();
		//WebElement dropdown1=driver.findElement(By.xpath("//select[@id='lang1']"));
		//Select select1=new Select(dropdown1);
		//select1.selectByIndex(2);
		//WebElement dropdown2=driver.findElement(By.xpath("//select[@id='cars']"));
		//Select select2=new Select(dropdown2);
		//select2.selectByVisibleText("Toyota");
		WebElement dropdown4=driver.findElement(By.xpath("//select[@id='lang2']"));
		Select select4=new Select(dropdown4);
		select4.selectByValue("c#");
		select4.selectByValue("python");
		select4.deselectByValue("c#");
		
		
   }
	@Test(enabled=false)
	public void handlingFrames()
	{
		driver.get("https://demoqa.com/frames");
		//driver.switchTo().frame("frame1");
		//driver.switchTo().frame(0);
		WebElement frame=driver.findElement(By.id("frame1"));
		driver.switchTo().frame(frame);
		WebElement txt=driver.findElement(By.id("sampleHeading"));
		String actualTxt=txt.getText();
		String expectedTxt="This is a sample page";
		Assert.assertEquals(actualTxt, expectedTxt,"no match");
		driver.switchTo().parentFrame();
		//driver.switchTo().defaultContent();
	}
	
	@Test(enabled=false)
	public void doubleclick()
	{
		driver.get("http://demo.guru99.com/test/simple_context_menu.html");
		WebElement doubleClick=driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
		Actions action=new Actions(driver);
		action.doubleClick(doubleClick).build().perform();
		Alert alert=driver.switchTo().alert();
		alert.accept();
	}
	
	@Test(enabled=false)
	public void rightClick()
	{
		driver.get("http://demo.guru99.com/test/simple_context_menu.html");
		WebElement rightClick=driver.findElement(By.xpath("//span[text()='right click me']"));
		Actions action=new Actions(driver);
		action.contextClick(rightClick).build().perform();
	}
	
	@Test(enabled=false)
	public void formHw()
	{
		driver.get("https://demoqa.com/automation-practice-form");
		WebElement firstName=driver.findElement(By.id("firstName"));
		firstName.sendKeys("jovita");
		WebElement lastName=driver.findElement(By.id("lastName"));
		lastName.sendKeys("lopez");
		WebElement email=driver.findElement(By.id("userEmail"));
		email.sendKeys("jovi@gmail.com");
		WebElement  gender=driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[1]/form/div[3]/div[2]/div[2]/label"));
		gender.click();
		WebElement  dob=driver.findElement(By.id("dateOfBirthInput"));
		dob.sendKeys("16 May 2020");
		
		WebElement number=driver.findElement(By.id("userNumber"));
		number.sendKeys("9876543478");
		WebElement subjects=driver.findElement(By.xpath("//div[@class='subjects-auto-complete__value-container subjects-auto-complete__value-container--is-multi css-1hwfws3']"));
		subjects.sendKeys("physics");
		WebElement hobby=driver.findElement(By.xpath("//label[@class='custom-control-label' and @for='hobbies-checkbox-2']"));
		hobby.click();
		WebElement address=driver.findElement(By.id("currentAddress"));
		address.sendKeys("pathanamthitta");
		
		WebElement file = driver.findElement(By.id("uploadPicture"));
	
		file.sendKeys("E:\\seleniumfiles\\data.txt");
		
		WebElement submit = driver.findElement(By.id("submit"));
		submit.click();
		
		
	}
	@Test(enabled=false)
	public void mouseOver()
	{
		driver.get("https://demoqa.com/menu/");
		
		List<WebElement> mainItems=driver.findElements(By.xpath("//ul[@id='nav']/li/a"));
		for(int i=0;i<mainItems.size();i++)
		{
			String main=mainItems.get(i).getText();
			if(main.equalsIgnoreCase("Main Item 1"))
			{
				Actions action=new Actions(driver);
				//action.moveToElement(mainItems.get(i)).build().perform();
				action.moveToElement(mainItems.get(i), 100, 100).build().perform();
			}
		}
	}
	
	@Test(enabled=false)
	public void dragAndDrop()
	{
		driver.get("https://demoqa.com/droppable");
		WebElement drag=driver.findElement(By.id("draggable"));
		WebElement drop=driver.findElement(By.id("droppable"));
		Actions action=new Actions(driver);
		action.dragAndDrop(drag, drop).build().perform();
		
	}
	
	@Test(enabled=false)
	public void dragAndDropByOffset()
	{
		driver.get("https://demoqa.com/dragabble");
		WebElement drag=driver.findElement(By.id("dragBox"));
		Actions action=new Actions(driver);
		//action.dragAndDropBy(drag, 100, 100).build().perform();
		action.clickAndHold(drag).dragAndDropBy(drag, 100, 100).build().perform();
		
	}
	
	@Test(enabled=false)
	public void mouseOverHw()
	{
		driver.get("https://demoqa.com/menu/");
		//driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		List<WebElement> mainItems=driver.findElements(By.xpath("//ul[@id='nav']/li/a"));
		for(int i=0;i<mainItems.size();i++)
		{
			String main=mainItems.get(i).getText();
			if(main.equalsIgnoreCase("Main Item 2"))
			{
				Actions action=new Actions(driver);
				action.moveToElement(mainItems.get(i)).build().perform();
				List<WebElement> sublist=driver.findElements(By.tagName("a"));
				for(int j=0;j<sublist.size();j++)
				{
					String subText=sublist.get(j).getText();
					//System.out.println(subText);
					if(subText.equalsIgnoreCase("SUB SUB LIST »"))
					{

						action.moveToElement(sublist.get(j)).build().perform();
						List<WebElement> subsub2=driver.findElements(By.tagName("a"));
						for(int k=0;k<subsub2.size();k++)
						{
							String susbsub2txt=subsub2.get(k).getText();
							if(susbsub2txt.equalsIgnoreCase("Sub Sub Item 2"))
							{
								action.moveToElement(subsub2.get(k)).build().perform();
							}

						}
					}
				}
			}
		}
	}


@Test(enabled=false)
public void verifyTableHeader()
{
	driver.get("http://demo.guru99.com/test/web-table-element.php#");
	List<WebElement> tableHead=driver.findElements(By.xpath("//table[@class='dataTable']//th"));
	List<String> expected=new ArrayList<String>();
	expected.add("Company");
	expected.add("Group");
	expected.add("Prev Close (Rs)");
	expected.add("Current Price (Rs)");
	expected.add("% Change");
	System.out.println(expected.size());
	if(tableHead.size()==tableHead.size())
	{
		for(int i=0;i<tableHead.size();i++)
		{
	//System.out.println(tableHead.get(i).getText());
			String expectedTxt=expected.get(i);
			String actualTxt=tableHead.get(i).getText();

			Assert.assertEquals(actualTxt,expectedTxt,"Mismatch!!");
		}
	}
	else
	{
		System.out.println("Size Mismatch");
	}
}

@Test(enabled=false)
public void tableHW()
{

	driver.get("https://www.w3schools.com/html/html_tables.asp");
	List<WebElement> tableHead=driver.findElements(By.xpath("//table[@id='customers']//th"));
	List<String> expectedHead=new ArrayList<String>();
	expectedHead.add("Company");
	expectedHead.add("Contact");
	expectedHead.add("Country");
	if(tableHead.size()==expectedHead.size())
	{
		for(int i=0;i<tableHead.size();i++)
		{
			String expectedHeadText=expectedHead.get(i);
			String actualText=tableHead.get(i).getText();
			Assert.assertEquals(actualText,expectedHeadText,"Mismatch!!");

		}
	}
	else
	{
		System.out.println("Size Mismatch");
	}


	}
@Test(enabled=false)
public void handleTableRowHw()
{

	driver.get("https://www.w3schools.com/html/html_tables.asp");
	List<WebElement> tablerow3=driver.findElements(By.xpath("//table[@id='customers']//tr[3]/td"));
	List<String> expectedRow=new ArrayList<String>();
	expectedRow.add("Centro comercial Moctezuma");
	expectedRow.add("Francisco Chang");
	expectedRow.add("Mexico");
	if(tablerow3.size()==expectedRow.size())
	{
		for(int i=0;i<tablerow3.size();i++)
		{

			String expectedText=expectedRow.get(i);
			String actualText=tablerow3.get(i).getText();
			System.out.println(actualText);
			Assert.assertEquals(actualText,expectedText,"Mismatch!!");

		}
	}
	else
	{
		System.out.println("Size Mismatch");
	}

}

@Test(enabled=false)
public void handleTablerow5Hw()
{

	driver.get("https://www.w3schools.com/html/html_tables.asp");
	List<WebElement> tablerow5=driver.findElements(By.xpath("//table[@id='customers']//tr[5]/td"));
	List<String> expectedRow5=new ArrayList<String>();
	expectedRow5.add("Island Trading");
	expectedRow5.add("Helen Bennett");
	expectedRow5.add("UK");
	//System.out.println(tablerow3.size());
	if(tablerow5.size()==expectedRow5.size())
	{
		for(int i=0;i<tablerow5.size();i++)
		{

			String expectedText=expectedRow5.get(i);
			String actualText=tablerow5.get(i).getText();
			System.out.println(actualText);
			Assert.assertEquals(actualText,expectedText,"Mismatch!!");

		}
	}
	else
	{
		System.out.println("Size Mismatch");
	}

}


@Test(enabled=false)
public void Robotclass() throws AWTException, InterruptedException
	{
		driver.get("http://demo.guru99.com/test/newtours/");
		WebElement register=driver.findElement(By.xpath("//a[text()='REGISTER']"));
		register.click();
		Robot robot=new Robot();
		WebElement firstname=driver.findElement(By.xpath("//input[@name='firstName']"));
		firstname.sendKeys("ram");
		Thread.sleep(4000);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(4000);
	}

@Test(enabled=false)
public void javascriptExecutor()
{
	driver.get("http://demowebshop.tricentis.com/login");
	JavascriptExecutor js=(JavascriptExecutor)driver;
	//js.executeScript("document.getElementById('Email').value='123'");
	//js.executeScript("document.getElementById('newsletter-subscribe-button').click()");
	js.executeScript("window.scrollBy(0,1000)");
}

@Test(enabled=false)
public void scrollingToView()
{
	driver.get("http://demo.guru99.com/test/guru99home/");
	WebElement linux=driver.findElement(By.xpath("//a[text()='Linux']"));
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("argument[0].scrollIntoView();", linux);
	
}
}



			