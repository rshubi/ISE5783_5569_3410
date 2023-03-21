package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * 
 * @author נעמי
 *A class for representing a 3D sphere that inherits from RadialGeometry
 */
public class Sphere extends RadialGeometry
{

	private final Point center;
	private final double radius;
	/**
	 * The constructor function gets
	 * @param r-radius of the sphere
	 * @param p-the center point of the sphere
	 */
	public Sphere(double r,Point p)
	{
		super(r);
		center=p;
		radius=r;
		// TODO Auto-generated constructor stub
	}
	/**
	 * The function accepts a point and 
	 * returns the normal vector to the sphere at that point
	 */
	public Vector getNormal(Point p)
	{
		return null;
	}
	/**
	 * The function 
	 * @returns the center point of the sphere
	 */
	public Point getCenter() 
	{
		return center;
	}
	/**
	 * The function
	 * @ returns the radius of the sphere
	 */
	public double getRadius() 
	{
		return radius;
	}
}
