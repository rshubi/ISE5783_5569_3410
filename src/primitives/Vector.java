package primitives;

/**
 * A class for representing a vector
 * 
 * @author Maayan & Renana
 *
 */
public class Vector extends Point {
	/**
	 * 
	 * The constructor function gets
	 * 
	 * @param x coordinate value
	 * @param y coordinate value
	 * @param z coordinate value
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		// Compares the zero vector to the vector I got
		if (Double3.ZERO.equals(xyz))
			throw new IllegalArgumentException("ERROR:ZERO VECTOR!");
	}

	/**
	 * The constructor function gets
	 * 
	 * @param rhs: right handle side operand for constructor
	 */
	Vector(Double3 rhs) {
		super(rhs);
		if (Double3.ZERO.equals(xyz))
			throw new IllegalArgumentException("ERROR:ZERO VECTOR!");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Vector other)
			return super.equals(obj);
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * @param vToAdd: A vector that is added to a current vector
	 * @returns a new vector
	 */
	public Vector add(Vector vToAdd) {
		return new Vector(this.xyz.add(vToAdd.xyz));
	}

	/**
	 * Multiply a vector by a scalar
	 * 
	 * @param scalar: A number with which the vector is multiplied
	 * @returns a new vector
	 */
	public Vector scale(double scalar) {
		return new Vector(this.xyz.scale(scalar));
	}

	/**
	 * Vector multiplication
	 * 
	 * @param v-second vector
	 * @returns a new vector perpendicular to the two existing vectors
	 */
	public Vector crossProduct(Vector v) {
		return new Vector((this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2), //
				(this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3), //
				(this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1));
	}

	/**
	 * @returns the squared length of the vector
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * @returns the length of the vector
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * @returns the normalized vector
	 */
	public Vector normalize() {
		return this.scale(1 / this.length());
	}

	/**
	 * scalar multiplication
	 * 
	 * @param v-second vector for scalar multiplication
	 * @returns A number that represents the scalar product
	 */
	public double dotProduct(Vector v) {
		return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
	}
}
