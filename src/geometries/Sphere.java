package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * 
 * A class for representing a 3D sphere
 * 
 * @author Maayan & Renana
 */
public class Sphere extends RadialGeometry {

	private final Point center;
	private final double radius;

	/**
	 * The constructor function gets
	 * 
	 * @param r:     radius of the sphere
	 * @param point: the center point of the sphere
	 */
	public Sphere(double r, Point point) {
		super(r);
		center = point;
		radius = r;
	}

	/**
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
}
