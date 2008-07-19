package execAndWait;

import com.opensymphony.xwork2.ActionSupport;

public class ExecAndWaitAction extends ActionSupport{

	public String execute(){
		for(int i=0; i<1000; i++){
			System.out.println(i);
			for(int j=0; j<1000; j++){
				System.out.println(j);
			}
		}
		return SUCCESS;
	}
}
