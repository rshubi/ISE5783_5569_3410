package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * A class for representing a triangle
 * 
 * @author Maayan & Renana
 */
public class Triangle extends Polygon {
	/**
	 * The constructor function gets
	 * 
	 * @param point1 First point to create the triangle
	 * @param point2 Second point to create the triangle
	 * @param point3 Third point to create the triangle
	 */
	public Triangle(Point point1, Point point2, Point point3) {
		super(point1, point2, point3);
	}

	public List<Point> findIntersections(Ray ray) {

		return null;
	}

}
