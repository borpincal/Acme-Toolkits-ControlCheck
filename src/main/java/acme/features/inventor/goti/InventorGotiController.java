package acme.features.inventor.goti;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.goti.Goti;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorGotiController extends AbstractController<Inventor,Goti>{

	@Autowired
	protected InventorGotiListService	listService;
	
	@Autowired
	protected InventorGotiShowService	showService;
	
	@Autowired
	protected InventorGotiCreateService	createService;
	
	@Autowired
	protected InventorGotiUpdateService	updateService;
//	
	@Autowired
	protected InventorGotiDeleteService	deleteService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}
}
