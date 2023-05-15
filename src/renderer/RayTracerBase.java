/**
 * 
 */
package renderer;

import primitives.Ray;
import scene.Scene;
import primitives.Color;

/**
 * The abstract class
 * 
 * @author Maayan & Renana
 *
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * The constructor function gets
	 * 
	 * @param s scene object
	 */
	public RayTracerBase(Scene s) {
		this.scene = s;
	}

	/**
	 * An abstract function that calculates the color
	 * 
	 * @param ray a ray for calculating the color
	 * @return a color
	 */
	public abstract Color traceRay(Ray ray);
}
