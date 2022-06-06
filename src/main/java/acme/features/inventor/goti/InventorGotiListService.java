package acme.features.inventor.goti;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.goti.Goti;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorGotiListService implements AbstractListService<Inventor, Goti>{

	@Autowired
	protected InventorGotiRepository		repository;

	@Override
	public boolean authorise(final Request<Goti> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Goti> findMany(final Request<Goti> request) {
		assert request != null;
		
		Collection<Goti> result;
		int masterId;
		
		masterId = request.getPrincipal().getActiveRoleId();
		result = this.repository.findManyGotiByInventor(masterId);

		return result;
	}

	@Override
	public void unbind(final Request<Goti> request, final Goti entity, final Model model) {
		assert request != null;
		assert model != null;
		assert entity!=null;
		
		request.unbind(entity, model, "code", "theme", "summary");
	}
	
}
