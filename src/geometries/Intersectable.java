/**
 * 
 */
package geometries;

import primitives.Ray;

import java.util.List;

import primitives.Point;

/**
 * נעמי
 *
 */
public interface Intersectable {
	public List<Point> findIntersections(Ray ray);

}
