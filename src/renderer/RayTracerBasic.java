/**
 * 
 */
package renderer;

import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import static primitives.Util.*;

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
	 * @param ray the ray for trace
	 * @return the intersections between the rays and the 3D model, or the
	 *         background color if there are no intersection points
	 */
	public Color traceRay(Ray ray) {
		var intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}

	/**
	 * A function that calculates the fill lighting color
	 * 
	 * @param intersection a point on the scene
	 * @param ray          The ray that intersects at the point
	 * @return the color of lighting fill or environment of the scene
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Auxiliary function for calculating the light color
	 * 
	 * @param gp  a point on the scene
	 * @param ray The ray that intersects at the point
	 * @return the color on the point
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = n.dotProduct(v);
		if (isZero(nv))
			return color;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = n.dotProduct(l);
			if (alignZero(nl * nv) > 0) { 
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
			}
		}
		return color;
	}

	/**
	 * help function that calculate the specolar effect
	 * 
	 * @param material Material value
	 * @param l        Vector value
	 * @param n        Vector value
	 * @param v        Vector value
	 * @param nl       double value
	 * @return the calculate the specolar effect
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector r = l.subtract(n.scale(2 * nl)).normalize();
		double minusRV = -alignZero(r.dotProduct(v));
		return minusRV <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(minusRV, material.nShininess));
	}

	/**
	 * help function that calculate the difusive effect
	 * 
	 * @param material Material value
	 * @param nl       double value
	 * @return double value for calcDiffusive
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl < 0 ? -nl : nl);
	}
}
