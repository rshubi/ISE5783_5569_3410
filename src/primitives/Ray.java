package primitives;

import java.util.List;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

/**
 * A class for representing a ray
 * 
 * @author Maayan &amp; Renana
 */
public class Ray {
	private static final double DELTA = 0.1;
	private final Point p0;
	private final Vector dir;

	/**
	 * The constructor function gets
	 * 
	 * @param rhsP   handle side point
	 * @param rhsDir right handle side direction vector for create ray
	 */
	public Ray(Point rhsP, Vector rhsDir) {
		p0 = rhsP;
		dir = rhsDir.normalize();
	}

	/**
	 * The constructor function gets
	 * 
	 * @param head           point that ray starts
	 * @param lightDirection direct vector of light
	 * @param n              vector
	 */
	public Ray(Point head, Vector lightDirection, Vector n) {
		if (Util.alignZero(lightDirection.dotProduct(n)) < 0)
			p0 = head.add(n.scale(-DELTA));
		else if (Util.alignZero(lightDirection.dotProduct(n)) > 0)
			p0 = head.add(n.scale(DELTA));
		else // if(Util.isZero(lightDirection.dotProduct(n)))
			p0 = head;

		dir = lightDirection;
		dir.normalize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ray other)
			return p0.equals(other.p0) && dir.equals(other.dir);
		return false;
	}

	@Override
	public String toString() {
		return p0 + "+" + dir;
	}

	/**
	 * A get function to return the starting point of the ray
	 * 
	 * @return the starting point of the ray
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * A get function to return the direction vector of the ray
	 * 
	 * @return the direction vector of the ray
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * A function that calculates a point on the ray at a given distance from the
	 * ray head. The point can be anywhere on the line of the ray.
	 * 
	 * @param t the distance from the ray head
	 * @return the point on the ray
	 */
	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}

	/**
	 * A function that calculates the point closest to the beginning of the ray
	 * 
	 * @param points Collect points
	 * @return the point close to the beginning of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * A function that calculates the geoPoint closest to the beginning of the ray
	 * 
	 * @param intersections Collect geoPoints
	 * @return the point close to the beginning of the ray
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null)
			return null;

		double minDist = Double.POSITIVE_INFINITY;
		GeoPoint closest = null;
		for (GeoPoint geoPoint : intersections) {
			double dist = geoPoint.point.distance(p0);
			if (dist < minDist) {
				minDist = dist;
				closest = geoPoint;
			}
		}

		return closest;
	}
}
