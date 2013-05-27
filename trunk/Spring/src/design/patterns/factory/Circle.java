package design.patterns.factory;

public class Circle implements Shape {

	static {
		System.out.println("Circle Registered");
		ShapeFactory.getInstance().registerProduct(ShapeFactory.CIRCLE, new Circle());
	}
	
	@Override
	public void draw() {
		System.out.println("Circle draw......");
	}
	
	@Override
	public Shape createShape(){
		return new Circle();
	}
}
