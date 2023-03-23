package geometries;
import primitives.Point;
import primitives.Vector;
/**
 * @author נעמי
 *A class for representing a plane inherits from a geometry
 *add a comment
 *anither comment
 */

public class Plane implements Geometry
{
	private final Point q0;
	private final Vector normal;
	/**
	 * constructor-
	 * @param p1
	 * @param p2
	 * @param p3
	 * Three points that form a plane
	 */
	public Plane(Point p1,Point p2,Point p3)
	{
		q0=p1;
		normal=null;
	}
	/**
	 * constructor-
	 * @param p
	 * @param n
	 * A point and a vector forming a plane
	 */
	public Plane(Point p,Vector n)
	{
		q0=p;
		normal=n.normalize();
	}
	//A function that returns the normal to the plane
	public Vector getNormal()
	{
		return normal;
	}
	/**
	 * The function accepts a point and returns the normal to the plane at the point
	 */
	public Vector getNormal(Point p)
	{
		return normal;
	}
	/**
	 * The function
	 * @returns the reference point of the plane
	 */
	public Point getQ0()
	{
		return q0;
	}
}
