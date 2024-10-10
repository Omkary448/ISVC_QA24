package PageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.WaitHelper;
import utils.CommonUtils;
import utils.WaitUtils;
import utils.waitUtilsZ;

public class Exceptions {
	public WebDriver ldriver;
	public WebDriver driver;
	public WaitHelper waitHelper;
	public WaitUtils waitUtils;
	public waitUtilsZ waitUtilsZ;
	public CommonUtils commonclick;

	public Exceptions(WebDriver rdriver) {

		// Assign the passed WebDriver instance to the class variable
		ldriver = rdriver;
		// Initialize all PageFactory elements on this page using the WebDriver instance
		PageFactory.initElements(rdriver, this);
		waitUtils = new WaitUtils(ldriver); // Initialize wait utility
		waitHelper = new WaitHelper(ldriver); // Initialize WaitHelper
		waitUtilsZ = new waitUtilsZ(ldriver);
		commonclick = new CommonUtils(ldriver);

	}

	@FindBy(xpath = "//span[@class='slds-form-element__label'][normalize-space()='Exception']/..//span[@class='slds-radio_faux']")
	WebElement SelectServiceTypeException;
	@FindBy(xpath = "//div[@class='slds-listbox slds-listbox_vertical slds-dropdown slds-dropdown_fluid slds-dropdown_left slds-dropdown_length-with-icon-7']//span[@title='GRL Exception Return'][normalize-space()='GRL Exception Return']/../../..//span[@title='Exception Return'][normalize-space()='Exception Return']")
	WebElement ExceptionReturn;
	@FindBy(xpath = "//section[@aria-expanded='true']//section[@role='tabpanel']//lightning-input[@data-name='Sales_Issue_Return_Reason_Justification__c']//input")
	WebElement Justification;
	@FindBy(xpath = "//span[text()=\"select box condition\"]")
	WebElement SelectBoxCondition;
	@FindBy(xpath = "//span[text()=\"Outer Box Open - Product Sealed\"]")
	WebElement ProductSealed;
	@FindBy(xpath = "//span[text()=\"GRL Exception Return\"]")
	WebElement SelectGRLException;
	@FindBy(xpath = "//span[@title='Quarterly Exception']")
	WebElement QuaterlyException;
	@FindBy(xpath = "//input[@placeholder='Enter MRB Reference Number']")
	List<WebElement> EnterMRB;

	public void SelectServiceTypeException() {
		commonclick.scrollAndClick(SelectServiceTypeException);
	}

	public void ExceptionReturn() {
		commonclick.scrollAndClick(ExceptionReturn);
	}

	public void Justification() {
		commonclick.scrollAndClick(Justification);
		Justification.sendKeys("QA Validation R4C Test");

	}

	public void SelectBoxCondition() {
		commonclick.scrollAndClick(SelectBoxCondition);
		commonclick.scrollAndClick(ProductSealed);
	}

	public void SelectGRLException() {
		commonclick.scrollAndClick(SelectGRLException);
	}

	public void QuaterlyException() {
		commonclick.scrollAndClick(QuaterlyException);
	}

	public void EnterMRB() {

		// Path to the Excel file
		String excelFilePath = "C:\\Users\\oyadavx\\Downloads\\MMCPN_BulkUploadTemplate (1) - Copy - Copy.xlsx";

		try {
			FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0); // Assuming values are in the first sheet

			// Skip the header row
			Iterator<Row> iterator = sheet.iterator();
			if (iterator.hasNext()) {
				iterator.next(); // Skip the header row
			}

			// Ensure the list of web elements and the data match in size
			int textboxCount = EnterMRB.size();
			int rowIndex = 0;

			// Iterate through the rows and web elements
			for (WebElement textbox : EnterMRB) {
				if (iterator.hasNext() && rowIndex < textboxCount) {
					Row currentRow = iterator.next(); // Get the next data row

					// Get value from the third column (index 2)
					Cell thirdColumnCell = currentRow.getCell(9); // Adjust based on actual column index
					String thirdColumnData = "";

					if (thirdColumnCell != null) {
						switch (thirdColumnCell.getCellType()) {
						case STRING:
							thirdColumnData = thirdColumnCell.getStringCellValue().trim();
							break;
						case NUMERIC:
							thirdColumnData = String.valueOf((int) thirdColumnCell.getNumericCellValue());
							break;
						case BOOLEAN:
							thirdColumnData = String.valueOf(thirdColumnCell.getBooleanCellValue());
							break;
						case FORMULA:
							thirdColumnData = thirdColumnCell.getCellFormula();
							break;
						case BLANK:
							thirdColumnData = "";
							break;
						default:
							thirdColumnData = thirdColumnCell.toString().trim();
						}

						System.out.println("Row " + rowIndex + " - Third column data: " + thirdColumnData);

						// Enter the data into the corresponding text field
						if (!thirdColumnData.isEmpty()) {
							textbox.clear(); // Clear any existing value
							textbox.sendKeys(thirdColumnData); // Enter the third column data
						}
					}
				}
				rowIndex++; // Move to the next textbox
			}

			workbook.close();
			excelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FindBy(xpath = "//span[text()=\"Customer Routed Freight\"]")
	WebElement VerifyCMF;

	public void VerifyCMF() {
		String CMFValue = VerifyCMF.getText(); // Fetch the text from the element
		String expectedCMFValue = "Customer Routed Freight"; // Expected text
System.out.println("CMF Location: "+CMFValue);
		// Assertion to verify the CMF value
		Assert.assertEquals(CMFValue, expectedCMFValue, "CMF value did not match!");

	}
	@FindBy(xpath = "//span[text()=\"Intel Managed Freight\"]")
	WebElement VerifyIMF;

	public void VerifyIMF() {
		// Fetch the text from the located element
		String IMFValue = VerifyIMF.getText();
		String expectedCMFValue = "Intel Managed Freight";
		System.out.println("IMF Location: "+IMFValue);

		// Assertion to verify the CMF value
		Assert.assertEquals(IMFValue, expectedCMFValue, "IMF value did not match!");
	}

}
