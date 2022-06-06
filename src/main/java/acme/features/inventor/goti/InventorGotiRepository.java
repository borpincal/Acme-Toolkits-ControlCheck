package acme.features.inventor.goti;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.goti.Goti;
import acme.entities.inventions.Invention;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorGotiRepository extends AbstractRepository {

	@Query("SELECT i FROM Invention i where i.id = ?1")
	Invention findOneInventionById(int masterId);
	
	@Query("SELECT c.invention FROM Goti c where c.id = ?1")
	Invention findInventionFromGoti(int chimpumId);
	
	@Query("SELECT c from Goti c where c.invention.id = ?1")
	List<Goti> findManyGotiByMasterId(int masterId);
	
	@Query("SELECT c from Goti c where c.invention.inventor.id = ?1")
	List<Goti> findManyGotiByInventor(int masterId);
	
	@Query("SELECT c FROM Goti c where c.id = ?1")
	Goti findGotiById(int id);
	
	@Query("SELECT c FROM Goti c where c.code = ?1")
	Goti findGotiByCode(String code);
	
	@Query("SELECT ac.acceptedCurrencies from SystemConfiguration ac")
	String findAcceptedCurrencies();
	
	@Query("SELECT c FROM SystemConfiguration c")
	SystemConfiguration getSystemConfiguration();
	
}
