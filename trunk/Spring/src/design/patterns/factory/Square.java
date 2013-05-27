package design.patterns.factory;

public class Square implements Shape {

	static {
		System.out.println("Square Registered");
		ShapeFactory.getInstance().registerProduct(ShapeFactory.SQUARE, new Square());
	}
	
	@Override
	public void draw() {
		System.out.println("Square draw....");
	}
	
	@Override
	public Shape createShape(){
		return new Square();
	}

}
