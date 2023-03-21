package primitives;
/**
 * 
 * @author נעמי
 *A point representation class
 */
public class Point 
{
	protected final  Double3 xyz;
	/**
	 * The constructor function gets 3 doubles:
	 * @param d1
	 * @param d2
	 * @param d3
	 * and creates an object of type double3 from them
	 */
	public Point(double d1, double d2, double d3)
	{
		xyz=new Double3(d1,d2,d3);

	}
	/**
	 * The constructor function gets
	 * @param- value-An object of type double3 that contains three double
	 * and puts it in the field
	 */

	Point(Double3 value)
	{
		xyz=value;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
	}
	@Override
	public String toString()
	{
		return xyz.toString();
	}
	
	/**
	  * Adding a vector to a point(uses add function of double3)
	 * @param cur-the point
	 * @returns a new point
	 */
	public Point add(Vector v)
	{
		return new Point(xyz.add(v.xyz));
	}
	
	/**
	 * Vector subtraction - receives a second point in the parameter, 
	 * @param cur-the point
	 * @returns a vector from the second point to the point on which the subtraction is performed
	 * the action
	 */
	public Vector subtract(Point cur)
	{
		return new Vector(xyz.subtract(cur.xyz));
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));
	}
	/**
	 * Calculating the squared distance between two points
	 * @param p-second point
	 * @returns distance between two points
	 */
	public double distanceSquared(Point p)
	{
		return (xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1) +(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
	}

}
