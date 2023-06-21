
package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import static primitives.Util.*;
import java.util.List;
import java.util.ArrayList;

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

	@Override
	public Color traceRay(Ray ray) {
		if (ray == null)
			return scene.background;
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	@Override
	public Color traceRays(List<Ray> rays) {

		Color color = Color.BLACK;
		for (Ray ray : rays) {
			color = color.add(traceRay(ray));
		}
		return color.reduce(rays.size());

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
	 * A function for checking partial shading
	 * 
	 * @param gp    a geometric body that the point is on
	 * @param light The lighting of the scene
	 * @param l     opposite direction vector of light source
	 * @param n     value vector
	 * @return 1 if the point on the body has no shading and 0 if there is shading
	 *         or number to represent partial shading
	 */

	private Double3 transparency(GeoPoint geoPoint, Vector l, Vector n, LightSource light, double nv) {

		{
			Vector lightDirection = l.scale(-1);
			Ray lightRay;

			if (nv < 0) {
				lightRay = new Ray(geoPoint.point, lightDirection, n);
			} else
				lightRay = new Ray(geoPoint.point, lightDirection, n.scale(-1));
			double lightDistance = light.getDistance(geoPoint.point);
			List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
			if (intersections == null)
				return Double3.ONE;
			Double3 ktr = Double3.ONE;
			for (GeoPoint gp : intersections) {
				if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
					ktr = ktr.product(gp.geometry.getMaterial().kT);
					if (ktr.lowerThan(MIN_CALC_COLOR_K))
						return Double3.ZERO;
				}
			}
			return ktr;
		}
	}

	/**
	 * help function that calculate the specolar effect
	 * 
	 * @param material      Material value
	 * @param l             vector value
	 * @param n             vector value
	 * @param v             vector value
	 * @param ightIntensity color value
	 * @return the calculate the specolar effect
	 */
	private Color calcSpecular(Material material, Vector l, Vector n, Vector v, Color lightIntensity) {
		double ln = alignZero(l.dotProduct(n));
		Vector r = l.subtract(n.scale(2 * ln));
		double vr = alignZero(v.dotProduct(r));
		double vrnsh = Math.pow(Math.max(0, -vr), material.nShininess);
		return lightIntensity.scale(material.kS.scale(vrnsh));
	}

	/**
	 * help function that calculate the difusive effect
	 * 
	 * @param material       Material value
	 * @param l              Vector value
	 * @param n              Vector value
	 * @param lightIntensity color value
	 * @return double value for calcDiffusive
	 */
	private Color calcDiffusive(Material material, Vector l, Vector n, Color lightIntensity) {
		double ln = alignZero(Math.abs(l.dotProduct(n)));
		return lightIntensity.scale(material.kD.scale(Math.abs(ln)));
	}

	/**
	 * A method of checking non-shading between a point and the light source
	 * 
	 * @param gp    A geometric body that the point is on
	 * @param light The lighting of the scene
	 * @param l     opposite direction vector of light source
	 * @param n     value vector
	 * @param nv    double value
	 * @return True if the point on the body has no shading and false if there is
	 *         shading
	 */
	@SuppressWarnings("unused")
	private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {
		Vector lightDirection = l.scale(-1);
		Vector epsV = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point point = gp.point.add(epsV);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;
		double lightDistance = light.getDistance(point);
		for (GeoPoint geoPoint : intersections) {
			if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0
					&& gp.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K))
				return false;
		}
		return true;
	}

	/**
	 * A function that calculates the refracted rays.
	 * 
	 * @param gp     GeoPoint value
	 * @param vRay   Ray value
	 * @param normal Vector value
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
	 * @param gp     GeoPoint value
	 * @param vRay   Ray value
	 * @param normal Vector value
	 * 
	 * @return ray for reflected
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector vRay, Vector normal) {
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
	 * Auxiliary function for calculating the light color
	 * 
	 * @param gp  a point on the scene
	 * @param ray The ray that intersects at the point
	 * @param k   cumulative attenuation coefficient value
	 * @return the color on the point
	 */

	private Color calcLocalEffect(GeoPoint intersection, Ray ray, Double3 k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) {
			return Color.BLACK;
		}
		Material material = intersection.geometry.getMaterial();
		Color color = intersection.geometry.getEmission();
		int beam = 1000;
		Double3 ktr;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				if (softShadowsBool)
					ktr = transparencyBeam(lightSource, n, intersection, beam);
				else
					ktr = transparency(intersection, l, n, lightSource, nv);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(material, l, n, lightIntensity),
							calcSpecular(material, l, n, v, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * Recursive function for calculating a point color
	 * 
	 * @param intersection geometric body and point
	 * @param ray
	 * @param level        level of Recursive function stops that level=1
	 * @param k            cumulative attenuation coefficient value
	 * @return color
	 */

	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffect(intersection, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));

	}

	/**
	 * 
	 * @param lightSource
	 * @param n           vector
	 * @param geoPoint    the point on the geometry body
	 * @param beam        The number of rays in the beam
	 * @return the shadow coefficient for a beam of rays
	 */
	private Double3 transparencyBeam(LightSource lightSource, Vector n, GeoPoint geoPoint, int beam) {
		Double3 tempKtr = new Double3(0d);
		List<Vector> beamL = lightSource.getBeamL(geoPoint.point, beamRadius, beam);

		for (Vector vl : beamL) {
			tempKtr = tempKtr.add(transparency(geoPoint, vl, n, lightSource, 1));
		}
		tempKtr = tempKtr.reduce(beamL.size());

		return tempKtr;
	}
	
	
    


}
