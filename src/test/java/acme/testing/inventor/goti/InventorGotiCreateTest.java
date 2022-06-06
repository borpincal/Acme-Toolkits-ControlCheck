package acme.testing.inventor.goti;

import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorGotiCreateTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/goti/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, String code, final String theme, final String summary, final String startTime, final String endTime, final String quantity,final String furtherInfo) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my components");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.clickOnButton("Create Goti");
		
		code=this.codeToCodeWithDate(code);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("theme", theme);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		
		super.clickOnSubmit("Create");
		
		super.clickOnMenu("Inventor", "List Goti");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("theme", theme);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("quantity", quantity);
		super.checkInputBoxHasValue("furtherInfo", furtherInfo);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/goti/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, String code, final String theme, final String summary, final String startTime, final String endTime, final String quantity,final String furtherInfo) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my components");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.clickOnButton("Create Goti");
		
		code=this.codeToCodeWithDate(code);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("theme", theme);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();

		super.signOut();
	}
	
	private String codeToCodeWithDate(String code) {
		Date moment;
		String yearSt;
		String monthSt;
		String daySt;
		
		moment = new Date(System.currentTimeMillis() - 1);
		
		yearSt = Integer.valueOf(moment.getYear()).toString().substring(1);
		monthSt = Integer.valueOf(moment.getMonth()+1).toString();
		if (monthSt.length()==1) {
			monthSt="0"+monthSt;
		}
		daySt = Integer.valueOf(moment.getDate()).toString();
		if (daySt.length()==1) {
			daySt = "0"+daySt;
		}
		
		code=code+yearSt+":"+monthSt+":"+daySt;
		
		return code;
	}
}
