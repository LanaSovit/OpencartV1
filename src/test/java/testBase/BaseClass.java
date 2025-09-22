package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger; // Log4j
	public Properties p;

	@BeforeClass(groups = { "sanity", "regression", "master", "datadriven" })
	@Parameters({ "OS", "browser" })
	public void setup(String OS, String browser) throws IOException {

		// Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass()); // Log4j

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS
			if (OS.equalsIgnoreCase("MAC")) {

				capabilities.setPlatform(Platform.MAC);

			} else if (OS.equalsIgnoreCase("Windows")) {

				capabilities.setPlatform(Platform.WIN11);

			} else if (OS.equalsIgnoreCase("Linux")) {

				capabilities.setPlatform(Platform.LINUX);

			}

			else {
				System.out.println("No matching OS");
				return;
			}

			// browser
			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("No matching browser");
				return;
			}

			driver = new RemoteWebDriver(new URL("http://192.168.4.126:4444/wd/hub"), capabilities);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				System.out.println("Invalid browser name");
				return;
			}

		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appURL")); // reading URL from properties file
		driver.manage().window().maximize();
	}

	public String randomeString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5); // Predefine class (commons-lang3)
		return generatedstring;
	}

	public String randomeNumber() {
		String generatednumber = RandomStringUtils.randomNumeric(10); // Predefine class (commons-lang3)
		return generatednumber;
	}

	public String randomeAlphaNumeric() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		String generatednumber = RandomStringUtils.randomNumeric(5); // Predefine class (commons-lang3)
		return (generatedstring + "*" + generatednumber);
	}

	// Create screenshot
	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "//screenshots//" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;
	}

	@AfterClass
	public void tearDown() {
		// driver.close();
		// OR
		driver.quit();
	}
}