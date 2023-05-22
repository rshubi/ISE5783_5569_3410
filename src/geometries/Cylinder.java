package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class for representing final cylinder
 * 
 * @author Maayan &amp; Renana
 */

public class Cylinder extends Tube {

	private final double height;

	/**
	 * The constructor function gets
	 * 
	 * @param ray    a ray that determines the direction of the cylinder
	 * @param radius the radius of the cylinder
	 * @param Height The height of the cylinder
	 */
	public Cylinder(Ray ray, double radius, double Height) {
		super(ray, radius);
		this.height = Height;
	}

	/**
	 * A get function
	 * 
	 * @return the height of the final cylinder
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}

	@Override
	public String toString() {
		return super.toString() + "Cylinder: height=" + height + " ";
	}

	/**
	 * A function to calculate intersection points with the ray
	 * 
	 * @param ray The cutting ray
	 * @return null
	 */
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
