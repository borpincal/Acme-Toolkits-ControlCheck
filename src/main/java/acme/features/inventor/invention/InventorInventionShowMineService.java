package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionType;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorInventionShowMineService implements AbstractShowService<Inventor, Invention> {

	// Internal state ------------------------------------------------------------

	@Autowired
	protected InventorInventionRepository 		repository;

	// AbstractShowService<Inventor, Invention> interface ------------------------
	
	@Override
	public boolean authorise(final Request<Invention> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Inventor inventor;
		Principal principal;

		masterId = request.getModel().getInteger("id");
		inventor = this.repository.findOneInventionById(masterId).getInventor();
		principal = request.getPrincipal();
		result = (
			inventor.getUserAccount().getId() == principal.getAccountId()
		);
		
		return result;
	}

	@Override
	public Invention findOne(final Request<Invention> request) {
		assert request != null;
		
		Invention result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneInventionById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Invention> request, final Invention entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency = this.repository.getSystemConfiguration().getSystemCurrency();
		
		final Money retailPrice = MoneyExchange.of(entity.getRetailPrice(), defaultCurrency).execute().getTarget();
		
		model.setAttribute("retailPrice", retailPrice);
		
		final Collection<Chimpum> chimpumCollection = this.repository.findChimpumByInventionId(entity.getId());
		final boolean chimpumExists = !chimpumCollection.isEmpty();
		
		model.setAttribute("chimpumExists", chimpumExists);
		model.setAttribute("masterId", entity.getId());
		model.setAttribute("isComponent", entity.getInventionType()==InventionType.COMPONENT);
		request.unbind(entity, model, "code", "name", "technology", "description", "link", "inventionType", "published");
	}
	
}
