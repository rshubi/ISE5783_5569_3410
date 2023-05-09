package primitives;

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
	 * @param rhsP-        right handle side point
	 * @param rhsDir-right handle side direction vector for create ray
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

	public Point getPoint(double t) {
		if (t > 0)
			return p0.add(dir.scale(t));
		return null;
	}
}
