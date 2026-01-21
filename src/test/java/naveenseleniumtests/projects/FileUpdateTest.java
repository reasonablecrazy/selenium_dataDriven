package naveenseleniumtests.projects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FileUpdateTest {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement priceApple = wait.until(ExpectedConditions.
				presenceOfElementLocated(By.
						xpath("//div[text()='Apple']/parent::div/following-sibling::div[2]/div")));
		WebElement downloadButton = driver.findElement(By.id("downloadButton"));
		downloadButton.click();
		
		
		WebElement uploadButton = driver.findElement(By.id("fileinput"));
		///Users/naveennarayanan99/Downloads
		File file = new File(File.separator+"Users"+File.separator+"naveennarayanan99"+File.separator+"Downloads"
				+File.separator+"download.xlsx");
		updateExcelSheet("Apple",file);

		uploadButton.sendKeys(file.toString());
		
		priceApple = wait.until(ExpectedConditions.
				presenceOfElementLocated(By.
						xpath("//div[text()='Apple']/parent::div/following-sibling::div[2]/div")));
		Assert.assertTrue(priceApple.getText().equalsIgnoreCase("277"),"Expected 300, but found: "+priceApple.getText());
		
		driver.quit();

	}

	private static void updateExcelSheet(String string, File file) throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		
		int rowNum =0;
		int columnNum = 0;
	    Workbook workbook;
	    
	    try (FileInputStream fis = new FileInputStream(file)) {
	        workbook = WorkbookFactory.create(fis);
	    }

		
			XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");
			for(Row row:sheet) {
				for(Cell cell:row) {
					CellType cellType = cell.getCellType();
					String value;
					if(cellType==CellType.NUMERIC) {
						value = String.valueOf(cell.getNumericCellValue());
						if(value.equalsIgnoreCase(string)) {
							columnNum = cell.getColumnIndex();
							rowNum=cell.getRowIndex();
							break;
						}
					}else {
						value = cell.getStringCellValue();
						if(value.equalsIgnoreCase(string)) {
							columnNum = cell.getColumnIndex();
							rowNum=cell.getRowIndex();
							break;
						}			
					}
				}
			}
			System.out.println("Row:"+rowNum+" column:"+columnNum);
			
			Row targetRow = sheet.getRow(rowNum);
			int targetColumnNumber = columnNum+2;
			Cell targetColumn = targetRow.getCell(targetColumnNumber);
			targetColumn.setCellValue("277");
			
			try (FileOutputStream fos = new FileOutputStream(file)) {
		        workbook.write(fos);
		    }
			workbook.close();
		
	}
}