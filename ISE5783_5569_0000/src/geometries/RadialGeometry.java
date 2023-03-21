package geometries;
/**
 * 
 * @author נעמי
 *An abstract class for representing shapes with a radius
 */
public abstract class RadialGeometry implements Geometry {
protected final double radius;
/**
 * A constructor that gets a radius and initializes the field
 * @param r-radius
 */
public RadialGeometry(double r)
{
	radius=r;
}
}
