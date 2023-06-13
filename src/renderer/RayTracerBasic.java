/**
 * 
 */
package renderer;

import primitives.*;

import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import static primitives.Util.*;
import java.util.List;

/**
 * A class inherits from an abstract class (RayTracerBase)
 * 
 * @author Maayan &amp; Renana
 */
public class RayTracerBasic extends RayTracerBase {
	/**
	 * DELTA static field for ray head offset size for shading rays
	 */
	private static final double DELTA = 0.1;
	/**
	 * Constants for stopping conditions in the recursion of
	 * transparencies/reflections
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

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
		if (ray == null)
			return scene.background;
		// var intersections = scene.geometries.findGeoIntersections(ray);
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
		// if (intersections == null)
		// return scene.background;
		// GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		// return calcColor(closestPoint, ray);
	}

	/**
	 * A function that calculates the fill lighting color
	 * 
	 * @param intersection a point on the scene
	 * @param ray          The ray that intersects at the point
	 * @return the color of lighting fill or environment of the scene
	 */

	private Color calcColor(GeoPoint intersection, Ray ray) {
		return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K))
				.add(scene.ambientLight.getIntensity());

	}

	/**
	 * Recursive function for calculating a point color
	 * 
	 * @param intersection geometric body and point
	 * @param ray
	 * @param level        level of Recursive function stops that level=1
	 * @param k
	 * @return
	 */

	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));

	}

	/**
	 * Auxiliary function for calculating the light color
	 * 
	 * @param gp  a point on the scene
	 * @param ray The ray that intersects at the point
	 * @return the color on the point
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
				Double3 ktr = new Double3(transparency(gp, l, n, lightSource));
				if ((ktr.product(k)).greaterThan(MIN_CALC_COLOR_K)) {

					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(material, nl)),
							iL.scale(calcSpecular(material, n, l, nl, v)));
				}

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

	/**
	 * A method of checking non-shading between a point and the light source
	 * 
	 * @param gp    A geometric body that the point is on
	 * @param light The lighting of the scene
	 * @param l     opposite direction vector of light source
	 * @param n     value vector
	 * @return True if the point on the body has no shading and false if there is
	 *         shading
	 */

	/**
	 * private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n)
	 * { Vector lightDirection = l.scale(-1); Vector epsV =
	 * n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA); Point point =
	 * gp.point.add(epsV); Ray lightRay = new Ray(point, lightDirection);
	 * List<GeoPoint> intersections =
	 * scene.geometries.findGeoIntersections(lightRay); if (intersections == null)
	 * return true; double lightDistance = light.getDistance(point); for (GeoPoint
	 * geoPoint : intersections) {
	 * 
	 * if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 &&
	 * gp.geometry.getMaterial().getkT() == 0) return false; } return true; }
	 */

	/**
	 * A function that calculates the refracted rays.
	 * 
	 * @param nor0mal Vector value
	 * @param point   Point3D value
	 * @param ray     Ray value
	 * @return ray for refracted
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector vRay, Vector normal) {
		return new Ray(gp.point, vRay, normal);
	}

	/**
	 * A function that find the most closet point to the ray
	 * 
	 * @param ray Ray value
	 * @return the closet point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null;
		return ray.findClosestGeoPoint(intersections);
	}

	/**
	 * A function that calculates the reflected rays.
	 * 
	 * @param normal Vector value
	 * @param point  Point3D value
	 * @param ray    Ray value
	 * @return ray for reflected
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector vRay, Vector normal) {
		// Vector v = ray.getDir();
		double nv = Util.alignZero(normal.dotProduct(vRay));
		if (Util.isZero(nv))
			return null;
		Vector r = vRay.subtract(normal.scale(nv * 2));
		return new Ray(gp.point, r, normal);
	}

	/**
	 * A function that calculate the globals effects of the color
	 * 
	 * @param gp    geo point
	 * @param ray   of the lighting direction
	 * @param level of Recursive function
	 * @param k     low cumulative attenuation coefficient value
	 * @return the color of global effects
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
				.add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	/**
	 * A function that calculate the globals effects of the color
	 * 
	 * @param ray   of the lighting direction
	 * @param level of Recursive function
	 * @param k     low cumulative attenuation coefficient value
	 * @param kx    cumulative attenuation coefficient value
	 * @return the color of global effects
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
	}

	/**
	 * A function for checking partial shading
	 * 
	 * @param gp    A geometric body that the point is on
	 * @param light The lighting of the scene
	 * @param l     opposite direction vector of light source
	 * @param n     value vector
	 * @return 1 if the point on the body has no shading and 0 if there is shading
	 *         or number to represent partial shading
	 */
	private double transparency(GeoPoint geoPoint, Vector l, Vector n, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

		double lightDistance = light.getDistance(geoPoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return 1.0;
		double ktr = 1.0;
		for (GeoPoint gp : intersections) {
			if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
				ktr *= gp.geometry.getMaterial().getkT();
				if (ktr < MIN_CALC_COLOR_K)
					return 0.0;
			}
		}

		return ktr;

	}

}
