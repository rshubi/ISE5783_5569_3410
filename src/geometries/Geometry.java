package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry for geometric shapes
 * 
 * @author Maayan &amp; Renana
 */

public interface Geometry extends Intersectable {

	/**
	 * A function that calculates the vector perpendicular to the body at a point.
	 * 
	 * @param point a point on the surface of the geometry shape
	 * @return the normal to the current shape at the point
	 */
	public Vector getNormal(Point point);

}
