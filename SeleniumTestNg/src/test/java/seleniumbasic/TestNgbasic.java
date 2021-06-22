package seleniumbasic;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNgbasic 
{

	@BeforeSuite
	public void beforeSuite()
	{
		
		System.out.println("functional suite start");
	}
	@BeforeTest
	public void beforeTest()
	{
		
		System.out.println("DB connection established");
	}
	@BeforeMethod
	public void beforeMethod()
	{
		System.out.println("browser launched");

	}
	@AfterMethod
	public  void afterMethod()
	{
		System.out.println("browser closed");

	}
	
	@AfterTest
	public  void afterTest()
	{
		System.out.println("DB connection closed");

	}
	@org.testng.annotations.AfterSuite
	public void AfterSuite()
	{
		
		System.out.println("functional suite end");
	}
	
	@Test(priority=2)
	public void verifyLogin()
	{
		System.out.println("Test case for login verification");
		Assert.assertEquals("a","a","mismatch");
	}
	
	@Test(priority=1)
	public void verifyTitle()
	{
		System.out.println("Test case for title verification");
		Assert.assertEquals("a","a","mismatch");
	}
	
	@Test(priority=3)
	public void verifyDropdown()
	{
		System.out.println("dropdown verified");
		
	}
	
}
