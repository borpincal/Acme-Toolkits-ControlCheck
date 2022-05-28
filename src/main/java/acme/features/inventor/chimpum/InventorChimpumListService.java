package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorChimpumListService implements AbstractListService<Inventor, Chimpum>{

	@Autowired
	protected InventorChimpumRepository		repository;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Invention invention;
		
		masterId = request.getModel().getInteger("masterId");
		invention = this.repository.findOneInventionById(masterId);

		result=(invention != null && invention.getInventionType()==InventionType.COMPONENT && request.getPrincipal().getAccountId() == invention.getInventor().getUserAccount().getId());

		return result;
	}

	@Override
	public Collection<Chimpum> findMany(final Request<Chimpum> request) {
		assert request != null;
		
		Collection<Chimpum> result;
		int masterId;
		
		masterId = request.getModel().getInteger("masterId");
		result = this.repository.findManyChimpumByMasterId(masterId);

		return result;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert model != null;
		assert entity!=null;
		int masterId;
		
		request.unbind(entity, model, "code", "title", "description");
		masterId = request.getModel().getInteger("masterId");
		model.setAttribute("masterId", masterId);
	}
	
}
