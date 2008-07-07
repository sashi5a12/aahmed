package interceptor;

import java.util.Map;

import login.User;
import login.UserAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor{

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		Map session=invocation.getInvocationContext().getSession();
		User user=(User)session.get("USER");
		if(user == null){
			return Action.LOGIN;
		}
		else {
			Action action=(Action)invocation.getAction();
			if(action instanceof UserAware){
				((UserAware)action).setUser(user);
			}
			return invocation.invoke();
		}
	}
	
}
