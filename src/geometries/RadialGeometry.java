package geometries;

/**
 * An abstract class for representing shapes with a radius
 * 
 * @author Maayan &amp; Renana
 */
public abstract class RadialGeometry implements Geometry {
	/** radius of a rounded geometry */
	protected final double radius;
	/** squared radius of a rounded geometry */
	protected final double radiusSquared;

	/**
	 * The constructor function gets
	 * 
	 * @param r Radius of the geometric shape
	 */
	public RadialGeometry(double r) {
		radius = r;
		radiusSquared = r * r;
	}
}
