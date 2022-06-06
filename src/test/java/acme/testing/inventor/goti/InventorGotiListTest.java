package acme.testing.inventor.goti;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorGotiListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/goti/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String theme, final String summary) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List Goti");

		super.sortListing(0, "asc");
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, theme);
		super.checkColumnHasValue(recordIndex, 2, summary);

		super.signOut();
	}
	
	
}
