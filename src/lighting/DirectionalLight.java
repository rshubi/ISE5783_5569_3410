/**
 * 
 */
package lighting;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A class for Directional Light. the class extends from Light and realizes the
 * LightSource
 * 
 * @author Maayan &amp; Renana
 *
 */
public class DirectionalLight extends Light implements LightSource {

	private final Vector direction;

	/**
	 * Constructor for DirectionalLight this ctor is normalize the direction vector
	 * 
	 * @param intensity1 Color value
	 * @param direction1 Vector value
	 */
	public DirectionalLight(Color intensity1, Vector direction1) {
		super(intensity1);
		direction = direction1.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}

//	@Override
//	 public List<Vector> getL(Point p, int numOfgetL) 
//	 {
//		 return null;
//	 }
	@Override
	public List<Vector> getBeamL(Point p, double radius, int amount) {
		return null;
	}

}
