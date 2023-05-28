package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * A class for representing a plane
 * 
 * @author Maayan &amp; Renana
 */

public class Plane extends Geometry {
	private final Point q0;
	private final Vector normal;

	/**
	 * The constructor function gets
	 * 
	 * @param point1 First point to create the plane
	 * @param point2 Second point to create the plane
	 * @param point3 Third point to create the plane
	 */
	public Plane(Point point1, Point point2, Point point3) {
		q0 = point1;
		Vector v1 = point2.subtract(point1);
		Vector v2 = point3.subtract(point1);
		normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * The constructor function gets
	 * 
	 * @param point  The reference point for creating the plane
	 * @param vector The normal vector to form the plane
	 */
	public Plane(Point point, Vector vector) {
		q0 = point;
		normal = vector.normalize();
	}

	/**
	 * A get function that returns the normal
	 * 
	 * @return the normal vector of the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point p) {

		return normal;
	}

	/**
	 * A get function that returns the point of the plane
	 * 
	 * @return the reference point of the plane
	 */
	public Point getQ0() {
		return q0;
	}

	@Override
	public String toString() {
		return "Plane: q0=" + q0 + ", normal=" + normal + " ";
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		double nv = normal.dotProduct(ray.getDir());
		if (isZero(nv) || q0.equals(ray.getP0()))
			return null;
		Vector nQMinusP0 = q0.subtract(ray.getP0());
		double t = alignZero((normal.dotProduct(nQMinusP0) / nv));
		return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
	}
}
