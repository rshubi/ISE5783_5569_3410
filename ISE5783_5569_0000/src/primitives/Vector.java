package primitives;

public class Vector extends Point {
	
	public Vector(double d1, double d2, double d3)
	{
		
	  super(d1,d2,d3);
	  if(d1==0&&d2==0&&d3==0)
		  throw new IllegalArgumentException("ERROR:ZERO VECTOR!");
    }

	Vector(Double3 value)
	{
		super(value);
		if(value.equals(value.ZERO))
			throw new IllegalArgumentException("ERROR:ZERO VECTOR!");
		
		
	}
	
	@Override
	 public boolean equals(Object obj) {
	 if (this == obj) return true;
	 if (obj instanceof Vector other)
	 return super.equals(obj);
	 return false;
	 }
	 @Override
	 public String toString()
	 {
		 return super.toString();
	 }
	 public Vector add(Vector v)
	 {
		 return new Vector(this.xyz.add(v.xyz));
	 }
	 public Vector scale(double d)
	 {
		return new Vector(this.xyz.scale(d)) ;
	 }
	 public Vector crossProduct(Vector v)
	 {
		return new Vector((this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2),(this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3),(this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1)); 
	 }
	 public double lengthSquared()
	 {
		return this.length()*this.length();
	 }
	 public double length()
	 {
		 return this.lengthSquared() ;
	 }
	 public Vector normalize ()
	 {
		 return this.scale(1/this.length());
	 }
	 public double dotProduct(Vector v)
	 {
		 return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3;
	 }
}
