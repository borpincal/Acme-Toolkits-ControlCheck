package acme.testing.inventor.goti;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorGotiShowTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/goti/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String creationTime, final String theme, final String summary, final String startTime, final String endTime, final String quantity,final String furtherInfo) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List Goti");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationTime", creationTime);
		super.checkInputBoxHasValue("theme", theme);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("quantity", quantity);
		super.checkInputBoxHasValue("furtherInfo", furtherInfo);
		
		super.signOut();
	}	
}
