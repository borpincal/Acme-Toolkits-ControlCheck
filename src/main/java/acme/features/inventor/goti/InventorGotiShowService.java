package acme.features.inventor.goti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.goti.Goti;
import acme.entities.inventions.Invention;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorGotiShowService implements AbstractShowService<Inventor, Goti>{

	@Autowired
	protected InventorGotiRepository		repository;
	
	@Override
	public boolean authorise(final Request<Goti> request) {
		assert request != null;
		
		final boolean result;
		final int gotiId;
		final Invention invention;
		
		gotiId = request.getModel().getInteger("id");
		invention = this.repository.findInventionFromGoti(gotiId);
		result=(invention != null && request.getPrincipal().getAccountId() == invention.getInventor().getUserAccount().getId());
		
		return result;
	}

	@Override
	public Goti findOne(final Request<Goti> request) {
		assert request != null;
		
		Goti result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findGotiById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Goti> request, final Goti entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String defaultCurrency =  this.repository.getSystemConfiguration().getSystemCurrency();
		
		final Money quantity = MoneyExchange.of(entity.getQuantity(), defaultCurrency).execute().getTarget();
		
		model.setAttribute("quantity", quantity);
		request.unbind(entity, model, "code", "creationTime", "theme", "summary", "startTime", "endTime","furtherInfo");
		
	}

}
