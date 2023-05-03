package geometries;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.isZero;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Polygon;

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
		List<Point> list = new ArrayList();
		Point p0 = ray.getP0();
		Point p1 = vertices.get(0);
		Point p2 = vertices.get(1);
		Point p3 = vertices.get(2);
		Vector v1 = p1.subtract(p0);
		Vector v2 = p2.subtract(p0);
		Vector v3 = p3.subtract(p0);
		Vector n1 = (v1.crossProduct(v3)).normalize();
		Vector n2 = (v2.crossProduct(v3)).normalize();
		Vector n3 = (v3.crossProduct(v1)).normalize();
		Point p = plane.findIntersections(ray).get(0);
		if ((ray.getDir().dotProduct(n1) > 0 && ray.getDir().dotProduct(n2) > 0 && ray.getDir().dotProduct(n3) > 0)
				|| (ray.getDir().dotProduct(n1) < 0 && ray.getDir().dotProduct(n2) < 0
						&& ray.getDir().dotProduct(n3) < 0))
			list.add(p);
		return list;

	}

}
