package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * @author נעמי
 *A class for representing a tube 
 *that inherits from the radial geometric shapes class
 */
public class Tube extends RadialGeometry{
protected final Ray axisRay;
/**
 * The constructor function gets
 * @param r-A ray that determines the direction of the tube
 * @param radius-radius of the tube
 */
public Tube(Ray r,double radius)
{
	super(radius);
	axisRay=r; 
}
/**
 *The function accepts a point and returns the normal vector to the tube at that point
 *
 */
public Vector getNormal(Point p)
{
	return null;
}
/**
 * The function
 * @returns the ray of the tube
 */
public Ray getAxisRay() 
{
	return axisRay;
}
}
