package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * A class for representing final cylinder.
 * 
 * @author Maayan & Renana
 */

public class Cylinder extends Tube {

	private final double height;

	/**
	 * The constructor function gets
	 * 
	 * @param ray    A ray that determines the direction of the cylinder.
	 * @param radius the radius of the cylinder.
	 * @param Height The height of the cylinder.
	 */
	public Cylinder(Ray ray, double radius, double Height) {
		super(ray, radius);
		this.height = Height;
	}

	/**
	 * A get function.
	 * 
	 * @returns the height of the final cylinder
	 */
	public double getHeight() {
		return height;
	}

	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
