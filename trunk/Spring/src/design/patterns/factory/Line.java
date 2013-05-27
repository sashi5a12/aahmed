package design.patterns.factory;

public class Line implements Shape {

	static {
		System.out.println("Line Registered");
		ShapeFactory.getInstance().registerProduct(ShapeFactory.LINE, new Line());
	}
	
	@Override
	public void draw() {
		System.out.println("Line draw.....");
	}

	@Override
	public Shape createShape(){
		return new Line();
	}
}
