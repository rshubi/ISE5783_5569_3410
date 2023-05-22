/**
 * 
 */
package renderer;

import primitives.Color;
import primitives.Point;

import primitives.Ray;
import scene.Scene;

/**
 * A class inherits from an abstract class (RayTracerBase)
 * 
 * @author Maayan &amp; Renana
 */
public class RayTracerBasic extends RayTracerBase {
	/**
	 * A constructor that invokes the constructor of the parent class
	 * (RayTracerBase)
	 * 
	 * @param scene scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * A function that looks for intersections between the ray and the 3D model of
	 * the scene
	 * 
	 * @return the intersections between the rays and the 3D model, or the
	 *         background color if there are no intersection points
	 */
	public Color traceRay(Ray ray) {
		Point closestPoint = ray.findClosestPoint(scene.geometries.findIntersections(ray));
		return closestPoint == null ? scene.background : calcColor(closestPoint);

	}

	/**
	 * A function that calculates the fill lighting color
	 * 
	 * @param point a point on the scene
	 * @return the color of lighting fill or environment of the scene
	 */
	public Color calcColor(Point point) {
		return scene.ambientLight.getIntensity();
	}

}
