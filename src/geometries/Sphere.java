package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * 
 * A class for representing a 3D sphere
 * 
 * @author Maayan &amp; Renana
 */
public class Sphere extends RadialGeometry {

	private final Point center;

	/**
	 * The constructor function gets
	 * 
	 * @param r radius of the sphere
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
	 * @return the center point of the sphere
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * A get function
	 * 
	 * @return the radius of the sphere
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		Point p0 = ray.getP0();
		if (p0.equals(center))
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(p0);
		double tm = ray.getDir().dotProduct(u);
		double dSquared = u.lengthSquared() - (tm * tm);
		double thSquared = alignZero(radiusSquared - dSquared);
		if (thSquared <= 0) // the line is outside or tangent to the sphere
			return null;
		double th = Math.sqrt(thSquared); // always positive
		double t1 = alignZero(tm + th); // always greater than t2
		if (t1 <= 0) // both points are behind the ray head
			return null;
		double t2 = alignZero(tm - th);
		return t2 <= 0 ? List.of(ray.getPoint(t1)) //
				: List.of(ray.getPoint(t2), ray.getPoint(t1)); 
	}
}
