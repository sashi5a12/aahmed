package design.patterns.factory;

public class Main {

/*	static
	{
		try
		{
			Class.forName("design.patterns.factory.Square");
			Class.forName("design.patterns.factory.Circle");
			Class.forName("design.patterns.factory.Line");
		}
		catch (ClassNotFoundException any)
		{
			any.printStackTrace();
		}
	}*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShapeFactory factory=ShapeFactory.getInstance();
		Shape shape=factory.createShape(ShapeFactory.CIRCLE);
		shape.draw();
		shape=factory.createShape(ShapeFactory.LINE);
		shape.draw();
		shape=factory.createShape(ShapeFactory.SQUARE);
		shape.draw();
	}

}
