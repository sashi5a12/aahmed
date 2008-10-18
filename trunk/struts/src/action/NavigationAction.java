package action;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.PropertyMessageResources;

public class NavigationAction extends Action {
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		ServletContext context=request.getSession().getServletContext();
		
		Enumeration e=context.getAttributeNames();
		while (e.hasMoreElements()){
			Object name=e.nextElement();
			System.out.println(name+"="+context.getAttribute(name.toString()));
		}
		PropertyMessageResources resource=(PropertyMessageResources)context.getAttribute("ERROR_MESSAGES");
		System.out.print(resource.getMessage("temp"));
		return mapping.findForward("showNavigation");
	}

}