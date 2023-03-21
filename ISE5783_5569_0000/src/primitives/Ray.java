package primitives;
/**
 * 
 *  *A ray representation class
 * @author נעמי
 *
 */
public class Ray 
{

	final Point p0;
	final Vector dir;
	/**
	 * The constructor function gets
	 * @param p0In-point
	 * @param dirIn-vector
	 * and creates a ray
	 */
	Ray(Point p0In,Vector dirIn)
	{
		p0=p0In;
		dir=dirIn.normalize();
	}
	@Override
	 public boolean equals(Object obj) {
	 if (this == obj) return true;
	 if (obj instanceof Vector other)
	 return p0.equals(obj)&&dir.equals(obj);
	 return false;
	 }
	 @Override
	 public String toString()
	 {
		return p0.toString()+"+"+dir.toString();
	 }
	 /**
	  * getP0()
	  * @returns the starting point of the ray
	  */
	public Point getP0() {
		return p0;
	}
	/**
	 * getDir() 
	 * @returns the vector of the ray
	 */
	public Vector getDir() {
		return dir;
	}
}
