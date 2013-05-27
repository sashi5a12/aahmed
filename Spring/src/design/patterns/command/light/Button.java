package design.patterns.command.light;

public class Button {
	Command command;

	public void setCommand(Command command) {
		this.command = command;
	}

	public void pressButton(){
		command.execute();
	}
}
