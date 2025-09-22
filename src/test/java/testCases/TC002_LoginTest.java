package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups = { "sanity", "master" })
	public void verify_login() {
		logger.info("***** Starting TC002_LoginTest *****");

		try {
			// Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login Page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email")); // Loading config.properties file
			logger.info("User set email");

			lp.setPassword(p.getProperty("password")); // Loading config.properties file
			logger.info("User set password");

			lp.clickLogin();
			logger.info("User clicked Login button");

			// My Account Page
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPAgeExists();

			// Assert.assertEquals(targetPage, true, "Login Failed");
			// OR
			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}

		logger.info("***** Finished TC002_LoginTest *****");
	}
}
