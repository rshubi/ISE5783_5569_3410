/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.alignZero;;

/**
 * A class for Spot Light. the class extends from PointLight
 * 
 * @author Maayan &amp; Renana *
 */
public class SpotLight extends PointLight {

	private Vector direction;

	/**
	 * constructor for spotlight
	 * 
	 * @param direction1 Vector value
	 * @param intensity1 Color value
	 * @param position1  Point3D value
	 */
	public SpotLight(Color intensity1, Point position1, Vector direction1) {
		super(intensity1, position1);
		this.direction = direction1.normalize();
	}
	public SpotLight(Color intensity1, Point position1, Vector direction1,double r) {
		super(intensity1, position1,r);
		this.direction = direction1.normalize();
	}


	@Override
	public Color getIntensity(Point p) {
		double dirL = alignZero(direction.dotProduct(getL(p)));
		return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(dirL);
	}

}
