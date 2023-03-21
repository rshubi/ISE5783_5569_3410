package geometries;

import primitives.Point;
import primitives.Vector;
/**
 * 
 * @author נעמי
 *Interface Geometry for 3D shapes
 */
public interface Geometry
{
/**
 * Every class that implements the interface should have a function that receives a point and 
 * @param p
 * @return the normal to the current form
 */
public Vector getNormal(Point p);

}
