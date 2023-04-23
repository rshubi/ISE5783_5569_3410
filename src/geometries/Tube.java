package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

/**
 * A class for representing a 3D tube
 * 
 * @author Maayan & Renana
 */
public class Tube extends RadialGeometry {
	protected final Ray axisRay;

	/**
	 * The constructor function gets
	 * 
	 * @param ray:    ray that determines the direction of the tube
	 * @param radius: radius of the tube
	 */
	public Tube(Ray ray, double radius) {
		
		super(radius);
		if(isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
		axisRay = ray;
	}

	/**
	 * @return the normal vector of the tube at a specific point
	 */
	public Vector getNormal(Point p) {
		Vector v = axisRay.getDir();
		Point p0 = axisRay.getP0();
		double t = v.dotProduct(p.subtract(p0));
		if(!isZero(t))
        {
		Point o = p0.add(v.scale(t));
		Vector normal = p.subtract(o).normalize();
		return normal;
        }
		 return p.subtract(axisRay.getP0()).normalize();
	}

	/**
	 * A get function
	 * 
	 * @returns the ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}
}
