package geometries;

import primitives.Ray;
/**
 * 
 * @author נעמי
 *A cylinder representation class inherits from an Tube( infinite cylinder)
 */

public class Cylinder extends Tube{
//field height keep the high to Cylinder
	private final double height;
	/**
	 * A builder who accepts
	 * @param r-A Ray that creates the cylinder
	 * @param radius-Radius that creates the cylinder
	 * @param h-height because it is a finite roll
	 */
	public Cylinder(Ray r,double radius,double h)
	{
		super(r,radius);
		this.height=h;
	}
	/**
	 * A function that 
	 * @returns the height of the final cylinder
	 */
	public double getHeight() {
		return height;
	}
}
