package acme.testing.inventor.goti;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorGotiUpdateTest  extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/goti/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String code, final String theme, final String summary, final String startTime, final String endTime, final String quantity,final String furtherInfo) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List Goti");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("theme", theme);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		
		super.clickOnSubmit("Update");
		
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
	@CsvFileSource(resources = "/inventor/goti/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final String code, final String theme, final String summary, final String startTime, final String endTime, final String quantity,final String furtherInfo) {

		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List Goti");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("theme", theme);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("furtherInfo", furtherInfo);
		
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();
		
		super.signOut();
	}

}
