package design.patterns.factory;

import java.util.HashMap;

public class ShapeFactory {
	private static ShapeFactory shapeFactory;
	
	public static final String CIRCLE="design.patterns.factory.Circle";
	public static final String LINE="design.patterns.factory.Line";
	public static final String SQUARE="design.patterns.factory.Square";
	
	private HashMap<String, Shape> productsMap=new HashMap<String, Shape>();
	
	private ShapeFactory(){
		
	}
	
	public static ShapeFactory getInstance(){
		if(shapeFactory == null){
			synchronized (ShapeFactory.class) {
				if (shapeFactory==null){
					shapeFactory = new ShapeFactory();
				}
			}
		}
		return shapeFactory;
	}
	
	public void registerProduct(String productId, Shape shape){
		this.productsMap.put(productId, shape);
	}
	
	public Shape createShape(String productId){
		if (productsMap.containsKey(productId)==false){
			try {
				Class.forName(productId);
			} catch (ClassNotFoundException e) {
				return null;
			}
		}
		System.out.println("Registered Products: "+ productsMap);
		return productsMap.get(productId).createShape();
	}
}
