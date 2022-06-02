package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String title, final String description) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List Chimpum");
//		super.checkCurrentUrl("http://localhost:8081/acme-toolkits-22.1/inventor/chimpum/list");
		super.sortListing(0, "asc");
		super.checkListingExists();
		super.checkNotListingEmpty();
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, description);

		super.signOut();
	}
	
	
}
