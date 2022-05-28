package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor, Chimpum>{

	@Autowired
	protected InventorChimpumRepository		repository;
	
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;

		boolean result;
		int masterId;
		Invention invention;
		
		masterId = request.getModel().getInteger("id");
		invention = this.repository.findChimpumById(masterId).getInvention();

		result=(invention != null && invention.getInventionType()==InventionType.COMPONENT && request.getPrincipal().getAccountId() == invention.getInventor().getUserAccount().getId());

		return result;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "title", "description", "startTime", "endTime", "link", "budget");
		
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
	
//		model.setAttribute("masterId",request.getModel().getInteger("masterId"));
		request.unbind(entity, model, "code", "title", "description", "startTime", "endTime", "link", "budget");
		
		
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		Chimpum result;
		int masterId;
		
		masterId=request.getModel().getInteger("id");
		result = this.repository.findChimpumById(masterId);
		
		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
		
	}

}
