package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry for geometric shapes
 * 
 * @author Maayan & Renana
 */

public interface Geometry {

	/**
	 * @param point a point on the surface of the geometry shape
	 * @return the normal to the current shape at the point
	 */
	public Vector getNormal(Point point);

}
