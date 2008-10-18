package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;
import org.apache.struts.tiles.TilesRequestProcessor;

public class MyRequestProcessor extends TilesRequestProcessor {

	@Override
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("-----------------MyRequestProcessor.processPreprocess-----------------------------");
		return true;
	}

	
}
