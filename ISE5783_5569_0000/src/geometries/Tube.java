package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * @author נעמי
 *
 */
public class Tube extends RadialGeometry{
protected final Ray axisRay;
/**
 * @param r
 * @param radius
 */
public Tube(Ray r,double radius)
{
	super(radius);
	axisRay=r; 
}
/**
 *
 */
public Vector getNormal(Point p)
{
	return null;
}
/**
 * @return
 */
public Ray getAxisRay() 
{
	return axisRay;
}
}
