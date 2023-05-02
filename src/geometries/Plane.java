package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class for representing a plane.
 * 
 * @author Maayan & Renana
 */

public class Plane implements Geometry {
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
	 * @param vactor The normal vector to form the plane
	 */
	public Plane(Point point, Vector vector) {
		q0 = point;
		normal = vector.normalize();
	}

	/**
	 * A get function
	 * 
	 * @return the normal vector of the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * A function that calculates the vector perpendicular to the plane
	 * 
	 * @return the normal vector of the plane at a specific point
	 */
	public Vector getNormal(Point p) {

		return normal;
	}

	/**
	 * A get function
	 * 
	 * @returns the reference point of the plane
	 */
	public Point getQ0() {
		return q0;
	}

	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
