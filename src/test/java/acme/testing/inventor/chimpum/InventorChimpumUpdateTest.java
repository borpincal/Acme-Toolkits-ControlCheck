package acme.testing.inventor.chimpum;

import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumUpdateTest  extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String code, final String title, final String description, final String startTime, final String endTime, final String budget,final String link) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List Chimpum");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Update");
		
		super.clickOnMenu("Inventor", "List Chimpum");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(String code, final String title, final String description, final String startTime, final String endTime, final String budget,final String link) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List Chimpum");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		
		code=this.codeToCodeWithDate(code);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Update");
		
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
		
		code=code+yearSt+"-"+monthSt+"-"+daySt;
		
		return code;
	}

}
