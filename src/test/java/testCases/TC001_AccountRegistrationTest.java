package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "regression", "master" })
	public void verify_account_registration() {

		logger.info("***** Starting TC001_AccountRegistrationTest *****");

		try {

			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Click on My Account Link");

			hp.clickRegister();
			logger.info("Click on Register Link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Providing Customer Details");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com"); // random generated email

			String password = randomeAlphaNumeric();
			regpage.setPassword(password);

			regpage.setPrivacyPolicy();
			logger.info("Select Privacy Policy checkbox");

			regpage.clickContinue();
			logger.info("Click on Continue Button");

			// Validation
			logger.info("Validating expected message");
			String confmsg = regpage.getConfirmationMSG();

			if (confmsg.equals("Your Account Has Been Created!")) {

				AssertJUnit.assertTrue(true);
			} else {
				logger.error("Test failed");
				logger.debug("Debug logs");
				AssertJUnit.assertTrue(false);
			}
			// Assert.assertEquals(confmsg, "Your Account Has Been Created!");

		} catch (Exception e) {

			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
}