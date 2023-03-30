package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class for representing a 3D tube
 * 
 * @author Maayan & Renana
 */
public class Tube extends RadialGeometry {
	protected final Ray axisRay;

	/**
	 * The constructor function gets
	 * 
	 * @param ray:    ray that determines the direction of the tube
	 * @param radius: radius of the tube
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axisRay = ray;
	}

	/**
	 * @return the normal vector of the tube at a specific point
	 */
	public Vector getNormal(Point p) {
		return null;
	}

	/**
	 * A get function
	 * 
	 * @returns the ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}
}
