package geometries;

import primitives.*;

/**
 * Interface Geometry for geometric shapes
 * 
 * @author Maayan &amp; Renana
 */
public abstract class Geometry extends Intersectable {
	/**
	 * The color of the geometric body. Black color (default)
	 */
	protected Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * abstract function that calculates the vector perpendicular to the body at a
	 * point.
	 * 
	 * @param point a point on the surface of the geometry shape
	 * @return the normal to the current shape at the point
	 */
	public abstract Vector getNormal(Point point);

	/**
	 * get function to return the color of the geometric body
	 * 
	 * @return The color of the geometric body
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * A set function for the color of the geometric body.
	 * 
	 * @param emission1 the color to set
	 * @return the object - builder
	 */
	public Geometry setEmission(Color emission1) {
		this.emission = emission1;
		return this;
	}

	/**
	 * get function to return the material of the geometric body
	 * 
	 * @return the material of the geometric body
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * A set function for the material of the geometric body.
	 * 
	 * @param mat the material to set
	 * @return the object - builder
	 */
	public Geometry setMaterial(Material mat) {
		this.material = mat;
		return this;
	}

}
