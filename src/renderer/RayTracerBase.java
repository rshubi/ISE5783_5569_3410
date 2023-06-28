
package renderer;

import primitives.Ray;
import scene.Scene;

import java.util.List;

import primitives.Color;

/**
 * abstract class for RayTracerBase
 * 
 * @author Maayan &amp; Renana
 *
 */
public abstract class RayTracerBase {
	/** The scene object to trace with a ray */
	protected final Scene scene;
	/** the radius of the beam */
	protected double beamRadius = 20d;
	/** Feature of the soft Shadows */
	protected boolean softShadowsBool = false;
	protected int numOfRays = 50;

	/**
	 * The constructor for RayTracerBase
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

	/**
	 * Traces list of rays through scene and calculates the average of the color
	 * from the rays
	 * 
	 * @param rays list of rays through the scene
	 * @return color-the average of the color from the rays
	 */
	public abstract Color traceRays(List<Ray> rays);

}
