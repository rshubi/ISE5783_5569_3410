package primitives;

/**
 * A class for representing point
 * 
 * @author Maayan &amp; Renana
 */
public class Point {
	/** the triad of coordinates X, Y, Z */
	protected final Double3 xyz;

	/**
	 * The constructor function gets 3 coordinate values and builds the Point object
	 * 
	 * @param x coordinate value
	 * @param y coordinate value
	 * @param z coordinate value
	 */
	public Point(double x, double y, double z) {
		xyz = new Double3(x, y, z);
	}

	/**
	 * The constructor function gets
	 * 
	 * @param rhs right handle side operand for constructor
	 */

	Point(Double3 rhs) {
		xyz = rhs;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
	}

	@Override
	public String toString() {
		return "" + xyz;
	}

	/**
	 * Adding a vector to a point
	 * 
	 * @param vToAdd A vector that is added to a point
	 * @return a new point
	 */
	public Point add(Vector vToAdd) {
		return new Point(xyz.add(vToAdd.xyz));
	}

	/**
	 * Vector subtraction to a point
	 * 
	 * @param pToSub point for subtraction
	 * @return a new vector
	 */
	public Vector subtract(Point pToSub) {
		return new Vector(xyz.subtract(pToSub.xyz));
	}

	/**
	 * Calculating the distance between two points
	 * 
	 * @param point A second point to calculate the distance between the current
	 *              point
	 * @return the distance between 2 points
	 */
	public double distance(Point point) {
		return Math.sqrt(distanceSquared(point));
	}

	/**
	 * Calculating the squared distance between two points
	 * 
	 * @param point A second point to calculate the distance between the current
	 *              point
	 * @return the distance between two points in a square
	 */
	public double distanceSquared(Point point) {
		double dx = xyz.d1 - point.xyz.d1;
		double dy = xyz.d2 - point.xyz.d2;
		double dz = xyz.d3 - point.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * A get function to return the first coordinate
	 * 
	 * @return the first coordinate
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * A get function to return the second coordinate
	 * 
	 * @return the second coordinate
	 */
	public double getY() {
		return xyz.d2;
	}

	/**
	 * A get function to return the third coordinate
	 * 
	 * @return the third coordinate
	 */
	public double getZ() {
		return xyz.d3;
	}
	/**
     * Checks whether the different between the points is [almost] zero
     * @param point second point for the check
     * @return true if the different between the points is zero or almost zero, false otherwise
     */
    public boolean isAlmostEquals(Point point) {

        return  (Util.isZero(xyz.d1-point.getX())) &&
                (Util.isZero(xyz.d2-point.getY())) &&
                (Util.isZero(xyz.d3-point.getZ()));
    }
}
