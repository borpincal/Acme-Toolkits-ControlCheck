package acme.features.inventor.chimpum;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumUpdateService implements AbstractUpdateService<Inventor, Chimpum>{
	
	
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
		
		if(!errors.hasErrors("code")) {
			Chimpum existing;
			
			existing = this.repository.findChimpumByCode(entity.getCode());
			
			errors.state(request, existing==null || existing.getId() == entity.getId(), "code", "inventor.chimpum.form.error.code.duplicated");
		}
		if(!errors.hasErrors("startTime")) {
			Calendar calendar;
			
			calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			errors.state(request, entity.getStartTime().after(calendar.getTime()), "startTime", "inventor.chimpum.form.error.startTime");
		
		}
		if(!errors.hasErrors("endTime")) {
			Calendar calendar;

			calendar= new GregorianCalendar();
			calendar.setTime(entity.getStartTime());
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			errors.state(request, entity.getEndTime().after(calendar.getTime()), "endTime", "inventor.chimpum.form.error.endTime");
			
			
		}
		if(!errors.hasErrors("budget")) {
			final Set<String> acceptedCurrencies;
			final String[] acceptedCurrenciesSt=this.repository.findAcceptedCurrencies().split(";");
			acceptedCurrencies=new HashSet<String>();
			Collections.addAll(acceptedCurrencies, acceptedCurrenciesSt);
			
			errors.state(request, entity.getBudget().getAmount()>0., "budget", "inventor.chimpum.form.error.budget.negative");
			
			errors.state(request, acceptedCurrencies.contains(entity.getBudget().getCurrency()) , "budget", "inventor.chimpum.form.error.budget.invalid");
		
		}
		
	}

	@Override
	public void update(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

}
