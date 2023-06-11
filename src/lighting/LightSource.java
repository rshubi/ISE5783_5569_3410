package lighting;

import primitives.*;

/**
 * Public interface of the light source
 * 
 * @author Maayan &amp; Renana
 *
 */
public interface LightSource {
	/**
	 * A function that return the intensity at a point
	 * 
	 * @param p Point3D value
	 * @return intensity color in this point
	 */
	public Color getIntensity(Point p);

	/**
	 * A function that return the vector L of the lighting direction at a point
	 * 
	 * @param p Point3D value
	 * @return the lighting direction on a point
	 */
	public Vector getL(Point p);

	/**
	 * The function calculates the distance between the point and the lighting of
	 * the scene
	 * 
	 * @param point A point from which the distance is calculatedA point from which
	 *              the distance is calculated
	 * @return the distance between the point and the lighting of the scene
	 */
	double getDistance(Point point);

}
