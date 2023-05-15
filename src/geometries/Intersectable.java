/**
 * 
 */
package geometries;

import primitives.Ray;

import java.util.List;

import primitives.Point;

/**
 * An interface for calculating the intersection points between the beam and the geometric body 
 * @author Maayan & Renana
 * 
 */

/**
 * A function that calculates the intersection points of the ray in a certain
 * body
 * 
 * @param ray A ray that cuts through the body
 * @return a list of the points that the ray intersects the body in case there
 *         is no intersection point the function will return null
 */
public interface Intersectable {
	public List<Point> findIntersections(Ray ray);

}
