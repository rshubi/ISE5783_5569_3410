/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A class for Point Light. the class extends from Light and realizes the
 * LightSource
 * 
 * @author Maayan &amp; Renana
 *
 */
public class PointLight extends Light implements LightSource {

	private final Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;

	/**
	 * The constructor for PointLight
	 * 
	 * @param intensity1 Color value
	 * @param position1  Point3D value
	 */
	public PointLight(Color intensity1, Point position1) {
		super(intensity1);
		position = position1;
	}

	/**
	 * A set function for filed kC
	 * 
	 * @param kC1 the kC to set-double
	 * @return the object - builder
	 */
	public PointLight setKc(double kC1) {
		kC = kC1;
		return this;
	}

	/**
	 * A set function for filed kL
	 * 
	 * @param kL1 the kL to set-double
	 * @return the object - builder
	 */
	public PointLight setKl(double kL1) {
		kL = kL1;
		return this;
	}

	/**
	 * A set function for filed kQ
	 * 
	 * @param kQ1 the kQ to set-double
	 * @return the object - builder
	 */
	public PointLight setKq(double kQ1) {
		kQ = kQ1;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double dSquared = p.distanceSquared(position);
		return intensity.reduce((kC + kL * Math.sqrt(dSquared) + kQ * dSquared));
	}

	@Override
	public Vector getL(Point p) {
		// In order not to reach a state of exception due to the zero vector
		if (p.equals(position))
			return null;
		return p.subtract(position).normalize();
	}
	@Override
	public double getDistance(Point point) {
		return position.distance(point);
	}
}
