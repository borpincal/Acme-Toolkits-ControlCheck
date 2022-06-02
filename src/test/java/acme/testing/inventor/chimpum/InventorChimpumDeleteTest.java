package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTestComponent(final int recordIndex) {
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List Chimpum");
		
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.clickOnSubmit("Delete");
		
		super.checkNotErrorsExist();
		
		super.signOut(); 
	}
	
}
