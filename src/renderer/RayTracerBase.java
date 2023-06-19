/**
 * 
 */
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
	 protected double beamRadius=20d;
	 protected boolean softShadows=true;
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
	 
	 public abstract Color traceRays(List<Ray> rays) ;

	        
}
