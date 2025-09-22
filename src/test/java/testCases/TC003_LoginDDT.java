package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "datadriven") // getting data
																										// provider from
																										// different
	// class(DataProviders.class)
	public void verify_loginDDT(String email, String pwd, String expresult) {

		logger.info("*****Start TC003_LoginDDT ******");

		try {

			// Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login Page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// My Account Page
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPAgeExists();

			// CASES (data from xls file which added in DataProviders.java file):

			/*
			 * Data is valid (login+password) - login success - test PASSED - click logout
			 * login failed - test FAILED Data is invalid (login+password) - login success -
			 * test FAILED - click logout login failed - test PASSED
			 */

			if (expresult.equalsIgnoreCase("Valid")) { // take status from xls file (Valid)

				if (targetPage == true) {

					Assert.assertTrue(true); // if test is passed need click to the logout link(next line)
					macc.clickLogout();

				} else {
					Assert.assertTrue(false); // test failed
				}
			}
			if (expresult.equalsIgnoreCase("Invalid")) { // take status from xls file (Invalid)

				if (targetPage == true) {

					Assert.assertTrue(false); // if test is failed need click to the logout link(next line)
					macc.clickLogout();

				} else {
					Assert.assertTrue(true); // test passed
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("*****Finished TC003_LoginDDT ******");
	}
}
