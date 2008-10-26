package action;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.PropertyMessageResources;

public class NavigationAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServletContext context = request.getSession().getServletContext();

		Enumeration e = context.getAttributeNames();
		while (e.hasMoreElements()) {
			Object name = e.nextElement();
			System.out.println(name + "="
					+ context.getAttribute(name.toString()));
		}
		PropertyMessageResources resource = (PropertyMessageResources) context
				.getAttribute("ERROR_MESSAGES");
		System.out.print(resource.getMessage("temp"));

		return mapping.findForward("showNavigation");
	}

	public static void main(String[] args) {

		// Load the resource bundle
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		ResourceBundle bundle1 = ResourceBundle.getBundle("org.apache.struts.validator.LocalStrings");

		// Get the message template
		String requiredFieldMessage = bundle.getString("error.requiredfield");

		// Create a String array of size one to hold the arguments
		String[] messageArgs = new String[1];

		// Get the "Name" field from the bundle and load it in as an argument
		messageArgs[0] = bundle.getString("label.name");

		// Format the message using the message and the arguments
		String formattedNameMessage =MessageFormat.format(requiredFieldMessage, messageArgs);

		System.out.println(formattedNameMessage);
		System.out.println(MessageFormat.format(bundle1.getString("variable.missing"), "Empty"));
	}
}