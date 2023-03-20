package geometries;
import primitives.Point;
import primitives.Vector;
public class Plane implements Geometry
{
	private final Point q0;
	private final Vector normal;
	public Plane(Point p1,Point p2,Point p3)
	{
		q0=p1;
		normal=null;
	}
	public Plane(Point p,Vector n)
	{
		q0=p;
		normal=n.normalize();
	}
	public Vector getNormal()
	{
		return normal;
	}
	
	public Vector getNormal(Point p)
	{
		return normal;
	}
	public Point getQ0()
	{
		return q0;
	}
}
