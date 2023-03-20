package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry
{

	private final Point center;
	private final double radius;
	public Sphere(double r,Point p)
	{
		super(r);
		center=p;
		radius=r;
		// TODO Auto-generated constructor stub
	}
	public Vector getNormal(Point p)
	{
		return null;
	}
	public Point getCenter() 
	{
		return center;
	}
	public double getRadius() 
	{
		return radius;
	}
}
