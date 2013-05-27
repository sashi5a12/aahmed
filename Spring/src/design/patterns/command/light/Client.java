package design.patterns.command.light;

public class Client {
	public static void main(String args[]){
		Button invoker=new Button();
		
		Light receiver=new Light();
		LightOnCommand onCmd=new LightOnCommand(receiver);
		
		invoker.setCommand(onCmd);
		invoker.pressButton();
	}
}
