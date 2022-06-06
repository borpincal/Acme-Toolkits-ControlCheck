package acme.features.inventor.goti;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.goti.Goti;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorGotiCreateService implements AbstractCreateService<Inventor, Goti> {

	@Autowired
	protected InventorGotiRepository		repository;
	
	@Override
	public boolean authorise(final Request<Goti> request) {
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
	public void bind(final Request<Goti> request, final Goti entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "theme", "summary", "creationTime", "startTime", "endTime", "furtherInfo", "quantity");
		
	}

	@Override
	public void unbind(final Request<Goti> request, final Goti entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
	
		model.setAttribute("masterId",request.getModel().getInteger("masterId"));
		request.unbind(entity, model, "code", "theme", "summary", "creationTime", "startTime", "endTime", "furtherInfo", "quantity");
		
	}

	@Override
	public Goti instantiate(final Request<Goti> request) {
		assert request != null;
		
		Goti result;
		int masterId;
		Invention invention;
		Date moment;
		Date startMoment;
		Date endMoment;
		
		masterId= request.getModel().getInteger("masterId");
		invention = this.repository.findOneInventionById(masterId);
		result = new Goti();
		moment = new Date(System.currentTimeMillis() - 1);
		startMoment = DateUtils.addMonths(moment, 1);
		endMoment = DateUtils.addWeeks(startMoment, 1);
		result.setInvention(invention);
		result.setCreationTime(moment);
		result.setStartTime(startMoment);
		result.setEndTime(endMoment);
		return result;
	}

	@Override
	public void validate(final Request<Goti> request, final Goti entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("code")) {
			String[] partsOfCode;
			String yearSt;
			String monthSt;
			String daySt;
			
			partsOfCode = entity.getCode().split(":");
			yearSt = Integer.valueOf(entity.getCreationTime().getYear()).toString().substring(1);
			monthSt = Integer.valueOf(entity.getCreationTime().getMonth()+1).toString();
			if (monthSt.length()==1) {
				monthSt="0"+monthSt;
			}
			daySt = Integer.valueOf(entity.getCreationTime().getDate()).toString();
			if (daySt.length()==1) {
				daySt = "0"+daySt;
			}

			errors.state(request, partsOfCode[1].equals(yearSt) && partsOfCode[2].equals(monthSt) && partsOfCode[3].equals(daySt), "code", "inventor.chimpum.form.error.code");
		}
		if(!errors.hasErrors("startTime")&&!errors.hasErrors("endTime") ) {
			Calendar calendar;
			
			calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			errors.state(request, entity.getEndTime()!=null && entity.getStartTime()!=null && entity.getStartTime().after(calendar.getTime()), "startTime", "inventor.chimpum.form.error.startTime");
			Calendar calendar2;

			calendar2= new GregorianCalendar();
			calendar2.setTime(entity.getStartTime());
			calendar2.add(Calendar.WEEK_OF_MONTH, 1);
			calendar2.add(Calendar.DAY_OF_MONTH, -1);
			
			errors.state(request, entity.getStartTime()!=null && entity.getEndTime()!=null && entity.getEndTime().after(calendar2.getTime()), "endTime", "inventor.chimpum.form.error.endTime");
		}
		if(!errors.hasErrors("quantity")) {
			final Set<String> acceptedCurrencies;
			final String[] acceptedCurrenciesSt=this.repository.findAcceptedCurrencies().split(";");
			acceptedCurrencies=new HashSet<String>();
			Collections.addAll(acceptedCurrencies, acceptedCurrenciesSt);
			
			errors.state(request, entity.getQuantity().getAmount()>0., "quantity", "inventor.chimpum.form.error.quantity.negative");
			
			errors.state(request, acceptedCurrencies.contains(entity.getQuantity().getCurrency()) , "quantity", "inventor.chimpum.form.error.quantity.invalid");
			
		}
		{
				Boolean isSpam;
				
				isSpam = entity.isSpam(this.repository.getSystemConfiguration());
				
				errors.state(request, !isSpam, "*", "inventor.chimpum.form.error.spam");
			}
	}

	@Override
	public void create(final Request<Goti> request, final Goti entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}