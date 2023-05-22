package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * A class for representing a triangle
 * 
 * @author Maayan &amp; Renana
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

	@Override
	public List<Point> findIntersections(Ray ray) {
		var list = plane.findIntersections(ray);
		if (list == null)
			return null;
		Point p0 = ray.getP0();
		Vector dir = ray.getDir();
		Vector v1 = vertices.get(0).subtract(p0);
		Vector v2 = vertices.get(1).subtract(p0);
		Vector n1 = (v1.crossProduct(v2)).normalize();
		double sign1 = alignZero(dir.dotProduct(n1));
		if (sign1 == 0)
			return null;
		Vector v3 = vertices.get(2).subtract(p0);
		Vector n2 = (v2.crossProduct(v3)).normalize();
		double sign2 = alignZero(dir.dotProduct(n2));
		if (sign1 * sign2 <= 0)
			return null;
		Vector n3 = (v3.crossProduct(v1)).normalize();
		double sign3 = alignZero(dir.dotProduct(n3));
		if (sign1 * sign3 <= 0)
			return null;
		return list;
	}

}
