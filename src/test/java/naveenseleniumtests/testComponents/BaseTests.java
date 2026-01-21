package naveenseleniumtests.testComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.poi.ss.usermodel.Cell;

public class BaseTests {
	
	protected static WebDriver driver;
	
	public void initilaizeDriver() {
		driver = new ChromeDriver();
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public void launchApplication() {
		initilaizeDriver();
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		driver.manage().window().maximize();
	}
	
	@AfterMethod(alwaysRun=true)
	public void quitDriver() {
		driver.quit();
	}
	
	protected ArrayList<ArrayList<String>> getTheTestDataForThisTest(String methodName) throws IOException {

	    ArrayList<ArrayList<String>> fullTestData = new ArrayList<>();

	    Path path = Paths.get(
	            System.getProperty("user.dir"),
	            "src", "test", "java", "naveenseleniumtests",
	            "testComponents", "testData.xlsx"
	    );

	    try (Workbook workbook = WorkbookFactory.create(path.toFile())) {

	        XSSFSheet sheet = (XSSFSheet) workbook.getSheet("TestCase");

	        if (sheet == null) {
	            throw new RuntimeException("Sheet 'TestCase' not found in Excel");
	        }
	        
	        for(Row row : sheet) {
	        	Cell firstCell = (Cell) row.getCell(0);
	            if (firstCell == null) continue;
    			ArrayList<String> rowData = new ArrayList<String>();
	        	if (firstCell.getStringCellValue().equalsIgnoreCase(methodName)) {
	        		for(Cell cell : row) {
	        			rowData.add(cell.toString());
	        		}
	        		fullTestData.add(rowData);
	        	}
	        	
	        }
	    }
	    return fullTestData;
	}
	
}
