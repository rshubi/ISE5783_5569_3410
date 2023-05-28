package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * A class for representing a 3D tube
 * 
 * @author Maayan &amp; Renana
 */
public class Tube extends RadialGeometry {
	/** axis line of the tube */
	protected final Ray axisRay;

	/**
	 * The constructor for Tube
	 * 
	 * @param ray    a ray that determines the direction of the tube
	 * @param radius radius of the tube
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		if (alignZero(radius) <= 0)
			throw new IllegalArgumentException("Zero or negative radius");
		axisRay = ray;
	}

	@Override
	public Vector getNormal(Point p) {
		double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
		return p.subtract(axisRay.getPoint(t)).normalize();
	}

	/**
	 * A get function to return the ray of the tube
	 * 
	 * @return the ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		return null;
	}
}
