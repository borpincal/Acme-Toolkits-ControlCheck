package acme.features.inventor.goti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.goti.Goti;
import acme.entities.inventions.Invention;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorGotiDeleteService implements AbstractDeleteService<Inventor, Goti>{

	@Autowired
	protected InventorGotiRepository		repository;
	
	
	@Override
	public boolean authorise(final Request<Goti> request) {
		assert request != null;

		boolean result;
		int masterId;
		Invention invention;
		
		masterId = request.getModel().getInteger("id");
		invention = this.repository.findGotiById(masterId).getInvention();

		result=(invention != null && request.getPrincipal().getAccountId() == invention.getInventor().getUserAccount().getId());

		return result;
	}

	@Override
	public void bind(final Request<Goti> request, final Goti entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "theme", "summary", "startTime", "endTime", "furtherInfo", "quantity");
		
		
	}

	@Override
	public void unbind(final Request<Goti> request, final Goti entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
	
		request.unbind(entity, model, "code", "theme", "summary", "startTime", "endTime", "furtherInfo", "quantity");
		
		
	}

	@Override
	public Goti findOne(final Request<Goti> request) {
		assert request != null;
		
		Goti result;
		int masterId;
		
		masterId=request.getModel().getInteger("id");
		result = this.repository.findGotiById(masterId);
		
		return result;
	}

	@Override
	public void validate(final Request<Goti> request, final Goti entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Goti> request, final Goti entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
		
	}

}
