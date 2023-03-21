package primitives;
/**
 * 
 * @author נעמי
 *
 */
public class Vector extends Point {
	/**
	 * 
	 * The constructor function gets 3 doubles:
	 * @param d1
	 * @param d2
	 * @param d3
	 * and calls the constructor of the parent-point class
	 * If the user enters the zero vector an exception will be thrown
	 */
	public Vector(double d1, double d2, double d3)
	{
		
	  super(d1,d2,d3);
	  if(d1==0&&d2==0&&d3==0)
		  throw new IllegalArgumentException("ERROR:ZERO VECTOR!");
    }
	
/**
 *The constructor function gets
 * @param- value-An object of type double3 that contains three double
 * and calls the constructor of the parent-point class
 * If the user enters the zero vector an exception will be thrown
 */
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
	 /**
	  * Vector connection returns a new vector
	  *the function uses add function of double3
	  */
	 public Vector add(Vector v)
	 {
		 return new Vector(this.xyz.add(v.xyz));
	 }
	 /**
	  * Multiplies a vector by a scalar number 
	  * @param d-scalar
	  * @returns a new vector
	  */
	 public Vector scale(double d)
	 {
		return new Vector(this.xyz.scale(d)) ;
	 }
	 /**
	  * Vector multiplication - 
	  * @param v-second vector
	  * @returns a new vector perpendicular to the two existing vectors
	  */
	 public Vector crossProduct(Vector v)
	 {
		return new Vector((this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2),(this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3),(this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1)); 
	 }
	 /**
	  * Calculation of the squared length of the vector
	  * |u|=sqrt(u scalar product u)
	  * |u|^2=(u scalar product u)
	  * @returns  the squared length of the vector
	  */
	 public double lengthSquared()
	 {
		return this.dotProduct(this);
	 }
	 /**
	  * Calculate the length of the vector
	  * @returns the length of the vector
	  */
	 public double length()
	 {
		 return  Math.sqrt(this.lengthSquared());
	 }
	 /**
	  * a normalization operation 
	  * u^=u*1/||u||
	  * @returns a new normalized vector - a unit vector in the same direction as the original vector
	  */
	 public Vector normalize ()
	 {
		 return this.scale(1/this.length());
	 }
	 /**
	  * scalar product
	  * @param v-second vector
	  * @returns double 
	  */
	 public double dotProduct(Vector v)
	 {
		 return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3;
	 }
}
