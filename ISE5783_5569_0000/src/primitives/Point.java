package primitives;

public class Point {
	protected final  Double3 xyz;
	public Point(double d1, double d2, double d3)
	{
		xyz=new Double3(d1,d2,d3);

	}

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
	//
	public Point add(Vector v)
	{
		return new Point(xyz.add(v.xyz));
	}
	public Vector subtract(Point cur)
	{
		return new Vector(xyz.subtract(cur.xyz));
	}
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));
	}
	public double distanceSquared(Point p)
	{
		return (xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1) +(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
	}

}
