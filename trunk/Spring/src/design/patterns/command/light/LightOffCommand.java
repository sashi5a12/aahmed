package design.patterns.command.light;

public class LightOffCommand implements Command {

	Light ligh;
	
	public LightOffCommand(Light ligh) {
		super();
		this.ligh = ligh;
	}

	@Override
	public void execute() {
		ligh.turnOff();
	}

}
