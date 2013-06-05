package annotation.form.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

//1. In annotation-based, use @Controller instead and define requestMapping.
@Controller
@RequestMapping("/customer.do")
public class CustomerController {

	CustomerValidator customerValidator;
	@Autowired
	public CustomerController(CustomerValidator customerValidator){
		this.customerValidator = customerValidator;
	}

	//2. In SimpleFormController, you can initialize the command object for binding in the formBackingObject() method. 
	// In annotation-based, you can do the same by annotated the method name with @RequestMapping(method = RequestMethod.GET).
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		CustomerForm form = new CustomerForm();

		// Make "Spring MVC" as default checked value
		form.setFavFramework(new String[] { "Spring MVC" });

		// Make "Male" as default radio button selected value
		form.setSex("M");

		// make "Hibernate" as the default java skills selection
		form.setJavaSkills("Hibernate");

		// initilize a hidden value
		form.setSecretValue("I'm hidden value");

		model.addAttribute("customerForm", form);
		
		return "CustomerForm";
	}

	//3. In SimpleFormController, usually you put the reference data in model via referenceData() method, 
	// so that the form view can access it. In annotation-based, you can do the same by annotated the method name with @ModelAttribute.

	@ModelAttribute("javaSkillsList")
	public Map<String, String> populateJavaSkillSet() {
		// Data referencing for java skills list box
		Map<String, String> javaSkill = new LinkedHashMap<String, String>();
		javaSkill.put("Hibernate", "Hibernate");
		javaSkill.put("Spring", "Spring");
		javaSkill.put("Apache Wicket", "Apache Wicket");
		javaSkill.put("Struts", "Struts");
		return javaSkill;
	}

	@ModelAttribute("countryList")
	public Map<String, String> populateCountryList() {
		// Data referencing for country dropdown box
		Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("US", "United Stated");
		country.put("CHINA", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		return country;
	}

	@ModelAttribute("numberList")
	public List<String> populatNumberList() {
		// Data referencing for number radiobuttons
		List<String> numberList = new ArrayList<String>();
		numberList.add("Number 1");
		numberList.add("Number 2");
		numberList.add("Number 3");
		numberList.add("Number 4");
		numberList.add("Number 5");
		return numberList;
	}

	@ModelAttribute("webFrameworkList")
	public List<String> populateWebFrameworkList() {
		// Data referencing for web framework checkboxes
		List<String> webFrameworkList = new ArrayList<String>();
		webFrameworkList.add("Spring MVC");
		webFrameworkList.add("Struts 1");
		webFrameworkList.add("Struts 2");
		webFrameworkList.add("JSF");
		webFrameworkList.add("Apache Wicket");
		return webFrameworkList;
	}
	/*
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap();

		List<String> webFrameworkList = populateWebFrameworkList();
		
		
		referenceData.put("webFrameworkList", webFrameworkList);

		List<String> numberList = populatNumberList();
		referenceData.put("numberList", numberList);

		Map<String, String> country = populateCountryList();
		referenceData.put("countryList", country);

		Map<String, String> javaSkill = populateJavaSkillSet();
		referenceData.put("javaSkillsList", javaSkill);
		
		return referenceData;
	}*/

	//4. In SimpleFormController, the form submission is handle by the onSubmit() method. 
	//In annotation-based, you can do the same by annotated the method name with @RequestMapping(method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST)
	protected String processSubmit(
			@ModelAttribute("customerForm") CustomerForm customer,
			BindingResult result, SessionStatus status) throws Exception {
		
		customerValidator.validate(customer, result);
		if (result.hasErrors()) {
			//if validator failed
			return "CustomerForm";
		} else {
			status.setComplete();
			//form success
			System.out.println(customer);
			return "CustomerSuccess";
		}
	}
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
*/
}
