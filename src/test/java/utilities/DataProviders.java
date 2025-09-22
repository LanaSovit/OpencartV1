package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider 1

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		String path = "/Users/svetlanasovit/Desktop/autometion/SeleniumWebDriver/OpencartV1.1/testData/Opencart_LoginData.xlsx"; // taking
																																	// xls
																																	// file
																																	// from
																																	// testData
																																	// folder

		ExcelUtility xlutil = new ExcelUtility(path); // creating an object for XLUtility

		int totalrows = xlutil.getRowCount("Аркуш1");
		int totalcols = xlutil.getCellCount("Аркуш1", 1);

		String logindata[][] = new String[totalrows][totalcols]; // create for two dimension array which can store

		for (int i = 1; i <= totalrows; i++) { // 1 read the data from xls storing in two dimension array
			for (int j = 0; j < totalcols; j++) { // i is row, j is col
				logindata[i - 1][j] = xlutil.getCellData("Аркуш1", i, j); // 1, 0
			}
		}
		return logindata; // returning two dimension array
	}

	// DataProvider 2
	// DataProvider 3
	// DataProvider 4
}