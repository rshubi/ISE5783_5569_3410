package primitives;

import java.util.List;

/**
 * 
 * A class for representing a ray
 * 
 * @author Maayan & Renana
 *
 */
public class Ray {

	final Point p0;
	final Vector dir;

	/**
	 * The constructor function gets
	 * 
	 * @param rhsP   right handle side point
	 * @param rhsDir right handle side direction vector for create ray
	 */
	public Ray(Point rhsP, Vector rhsDir) {
		p0 = rhsP;
		dir = rhsDir.normalize();
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
	 * A get function
	 * 
	 * @returns the starting point of the ray
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * A get function
	 * 
	 * @returns the direction vector of the ray
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * A function that calculates the point on the ray
	 * 
	 * @param t scalar of dir vector
	 * @return the point on the ray
	 */
	public Point getPoint(double t) {
		if (t > 0)
			return p0.add(dir.scale(t));
		return null;
	}

	/**
	 * A function that calculates the point closest to the beginning of the ray
	 * 
	 * @param points Collect points
	 * @return the point close to the beginning of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		if (points == null)
			return null;
		Point closet = points.get(0);
		for (Point point : points) {
			if (point.distance(p0) < closet.distance(p0))
				closet = point;
		}
		return closet;

	}

}
