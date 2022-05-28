package acme.features.inventor.chimpum;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.entities.inventions.Invention;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository {

	@Query("SELECT i FROM Invention i where i.id = ?1")
	Invention findOneInventionById(int masterId);
	
	@Query("SELECT c.invention FROM Chimpum c where c.id = ?1")
	Invention findInventionFromChimpum(int chimpumId);
	
	@Query("SELECT c from Chimpum c where c.invention.id = ?1")
	List<Chimpum> findManyChimpumByMasterId(int masterId);
	
	@Query("SELECT c FROM Chimpum c where c.id = ?1")
	Chimpum findChimpumById(int id);
	
	@Query("SELECT c FROM Chimpum c where c.code = ?1")
	Chimpum findChimpumByCode(String code);
	
	@Query("SELECT ac.acceptedCurrencies from SystemConfiguration ac")
	String findAcceptedCurrencies();
	
	@Query("SELECT c FROM SystemConfiguration c")
	SystemConfiguration getSystemConfiguration();
	
}
