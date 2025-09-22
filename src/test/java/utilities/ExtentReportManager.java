package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter; // UI of the report
	public ExtentReports extent; // populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test
							// methods
	String repName;

	public void onStart(ITestContext testContext) {
		/*
		 * SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.HH.mm.ss"); //format date
		 * and time generation Date dt= new Date(); String currentdatetimestamp=
		 * df.format(dt);
		 */
		// OR
		String timeStamp = new SimpleDateFormat("yyyy.MM.HH.mm.ss").format(new Date()); // date and time of creation
																						// report

		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".//reports//" + repName); // specify location of the report

		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("Opencart Functional Testing"); // Name of report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); // return current user of the
																			// system(tester's name)
		extent.setSystemInfo("Environemnt", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("OS"); // capture from xml file
		extent.setSystemInfo("Opeation System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser"); // capture from xml file
		extent.setSystemInfo("Browser", browser);

		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups(); // capture from xml file(from
																							// include section)

		if (!includeGroups.isEmpty()) { // if groups are absent in xml file

			extent.setSystemInfo("Groups", includeGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName()); // get test class and name which was executed and
																	// creating new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report

		test.log(Status.PASS, result.getName() + "got successfully executed");
	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName()); // get test class and name which was executed and
																	// creating new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report

		test.log(Status.FAIL, result.getName() + "got failed");
		test.log(Status.INFO, result.getThrowable().getMessage()); // print error method inside report

		// before attaching screenshot we must to capture it (in BaseClass add method at
		// first)
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.SKIP, result.getName() + "got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext context) {

		extent.flush();

		// open report automatically after all tests

		String pathOfExtentReport = System.getProperty("user.dir") + "//reports//" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());// open report in the browser automatically
		} catch (IOException e) {

			e.printStackTrace();
		}

		// send report to the email automatcally
//		try {
//			URL url = new URL("file:///" + System.getProperty("user.dir") + "//reports//" + repName);
//
//			// Create the email message
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.googlemail.com"); // only for gmail
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("sovitsv@gmail.com", "password"));
//			email.setSSLOnConnect(true);
//			email.setFrom("sovitsv+sender@gmail.com"); // Sender
//			email.setSubject("Test Results");
//			email.setMsg("Please find Report in attached files");
//			email.addTo("sovitsv+receiver@gmail.com"); // receiver
//			email.attach(url, "Extent Report", "please check report");
//			email.send();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}