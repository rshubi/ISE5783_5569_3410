package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.alignZero;

/**
 * 
 * A class for representing a 3D sphere
 * 
 * @author Maayan & Renana
 */
public class Sphere extends RadialGeometry {

	private final Point center;
	// private final double radius;

	/**
	 * The constructor function gets
	 * 
	 * @param r     radius of the sphere
	 * @param point the center point of the sphere
	 */
	public Sphere(double r, Point point) {
		super(r);
		center = point;
	}

	@Override
	public Vector getNormal(Point p) {

		return p.subtract(center).normalize();
	}

	/**
	 * A get function
	 * 
	 * @returns the center point of the sphere
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * A get function
	 * 
	 * @returns the radius of the sphere
	 */
	public double getRadius() {
		return radius;
	}
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> list = new ArrayList();
		if (ray.getP0().equals(center)) 
			return List.of(new Point((ray.getPoint(radius)).getX(),(ray.getPoint(radius)).getY(),(ray.getPoint(radius)).getZ()));
		Point p0 = ray.getP0();
		Vector u = center.subtract(p0);
		Vector v = ray.getDir();
		double tm = alignZero(v.dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm)));
		double th = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tm + th);
		double t2 = alignZero(tm - th);
		Point p1 = ray.getPoint(t1);
		Point p2 = ray.getPoint(t2);
		if (p1 != null)
			list.add(p1);
		if (p2 != null)
			list.add(p2);
		if(list.size()>0)
		   return list;
		return null;

	}
}
