package geometries;

/**
 * An abstract class for representing shapes with a radius
 * 
 * @author Maayan & Renana
 */
public abstract class RadialGeometry implements Geometry {
	protected final double radius;

	/**
	 * The constructor function gets
	 * 
	 * @param r Radius of the geometric shape
	 */
	public RadialGeometry(double r) {
		radius = r;
	}
}
