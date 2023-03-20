package geometries;

import primitives.Ray;

public class Cylinder extends Tube{

	private final double height;
	public Cylinder(Ray r,double radius,double h)
	{
		super(r,radius);
		this.height=h;
	}
	public double getHeight() {
		return height;
	}
}
