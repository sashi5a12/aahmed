package SimpleFormController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class CustomerController extends SimpleFormController {
	public CustomerController() {
		setCommandClass(CustomerForm.class);
		setCommandName("customerForm");
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		CustomerForm form = new CustomerForm();

		// Make "Spring MVC" as default checked value
		form.setFavFramework(new String[] { "Spring MVC" });

		// Make "Male" as default radio button selected value
		form.setSex("M");

		// make "Hibernate" as the default java skills selection
		form.setJavaSkills("Hibernate");

		// initilize a hidden value
		form.setSecretValue("I'm hidden value");

		return form;
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap();

		// Data referencing for web framework checkboxes
		List<String> webFrameworkList = new ArrayList<String>();
		webFrameworkList.add("Spring MVC");
		webFrameworkList.add("Struts 1");
		webFrameworkList.add("Struts 2");
		webFrameworkList.add("JSF");
		webFrameworkList.add("Apache Wicket");
		referenceData.put("webFrameworkList", webFrameworkList);

		// Data referencing for number radiobuttons
		List<String> numberList = new ArrayList<String>();
		numberList.add("Number 1");
		numberList.add("Number 2");
		numberList.add("Number 3");
		numberList.add("Number 4");
		numberList.add("Number 5");
		referenceData.put("numberList", numberList);

		// Data referencing for country dropdown box
		Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("US", "United Stated");
		country.put("CHINA", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		referenceData.put("countryList", country);

		// Data referencing for java skills list box
		Map<String, String> javaSkill = new LinkedHashMap<String, String>();
		javaSkill.put("Hibernate", "Hibernate");
		javaSkill.put("Spring", "Spring");
		javaSkill.put("Apache Wicket", "Apache Wicket");
		javaSkill.put("Struts", "Struts");
		referenceData.put("javaSkillsList", javaSkill);
		
		return referenceData;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		CustomerForm customer = (CustomerForm)command;
		System.out.println(customer);
		return new ModelAndView("CustomerSuccess","customer",customer);
	}
	
	

}
