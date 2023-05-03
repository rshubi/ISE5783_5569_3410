package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

	/**
	 * A function that calculates the normal vector of the ball at a certain point
	 * 
	 * @return the normal vector of the sphere at a specific point
	 */
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

	public List<Point> findIntersections(Ray ray) {
		List<Point> list = new ArrayList();
		Point p0 = ray.getP0();
		Vector u = center.subtract(p0);
		Vector v = ray.getDir();
		double tm = v.dotProduct(u);
		double d = Math.sqrt(u.lengthSquared() - (tm * tm));
		double th = Math.sqrt(radius * radius - d * d);
		double t1 = tm + th;
		double t2 = tm - th;
		Point p1 = ray.getPoint(t1);
		Point p2 = ray.getPoint(t2);
		if (p1 != null)
			list.add(p1);
		if (p2 != null)
			list.add(p2);
		return list;

	}
}
