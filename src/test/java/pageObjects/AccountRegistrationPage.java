package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	// Constructor
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	// Locators
	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastname;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkdPolicy;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	// Action Methods for element from Locators
	public void setFirstName(String fname) {
		txtFirstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
		txtLastname.sendKeys(lname);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void setPrivacyPolicy() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", chkdPolicy);
		// chkdPolicy.click();
	}

	public void clickContinue() {
		// Solution 1
		// btnContinue.click();
		btnContinue.submit();
	}
	// If Solution 1 doesn't work try these solutions:

	// Solution 2
	// btnContinue.submit();

	// Solution 3
	// Actions act= Actions(driver);
	// act.moveToElement(btnContinue).click().perform();

	// Solution 4
	// javascriptExecutor js= (javascriptExecutor)driver;
	// js.executeScript("arguments[0].click();", btnContinue);

	// Solution 5
	// btnContinue.sendKeys(Keys.RETURN);

	// Solution 6
	// WebDriverWait myWait= new WebDriverWait(driver, Duration.ofSeconds(10));
	// myWait.until(ExpectedConditions.elementToBeClickable(btnContinue).click());

	public String getConfirmationMSG() {

		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}