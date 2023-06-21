
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
	/**the radius of the beam */
	 protected double beamRadius=20d;
	 /** Feature of the soft Shadows */
	 protected boolean softShadowsBool=true;
	 
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
	  * 
	  * @param rays list of the rays
	  * @return color
	  */
	 public abstract Color traceRays(List<Ray> rays) ;

	        
}
