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
 * @author Maayan &amp; Renana
 *
 */
public abstract class RayTracerBase {
	/** The scene object to trace with a ray */
	protected final Scene scene;

	/**
	 * The constructor function gets
	 * 
	 * @param s scene object
	 */
	public RayTracerBase(Scene s) {
		this.scene = s;
	}

	/**
	 * Traces a ray through scene and calculates the color of the ray
	 * 
	 * @param ray a ray to trace through the scene
	 * @return the color of the ray
	 */
	public abstract Color traceRay(Ray ray);
}
