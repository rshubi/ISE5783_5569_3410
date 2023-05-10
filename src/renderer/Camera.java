
package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Camera Class
 *  @author Maayan & Renana 
 *
 */
public class Camera {
	private Point p0;
	private Vector vUp;
	private Vector vRight;
	private Vector vTo;
	private double width;
	private double height;
	private double distance;
/**
 * The constructor function 
 * @param p0 Camera location point
 * @param vTo A vector that sets the direction of the camera to the forward
 * @param vUp A vector that sets the direction of the camera to the up
 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!Util.isZero(vTo.dotProduct(vUp)))
			throw new IllegalArgumentException("vUp is not ortogonal to vTo");

		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();
		this.p0 = p0;
	}
	/**
	 * A Set Function
	 * @param width Width of the view plane
	 * @param height height of the view plane
	 * @return
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}
/**
 * A Set Function
 * @param distance distance of the camera from the geometric body
 * @return
 */
	public Camera setVPDistance(double distance) {
		this.distance = distance;
		return this;
	}
/**
 * The function returns a ray that passes through the center of a certain pixel
 * @param nX row number of view plane
 * @param nY column number of view plane
 * @param j column of a certain pixel
 * @param i row of a certain pixel
 * @return a ray that passes through the center of a certain pixel
 */
	public Ray constructRay(int nX, int nY, int j, int i)/* throws Exception */ {// constructRayThroughPixel
		Point Pc;
		if (Util.isZero(distance))
			Pc = p0;
		else
			Pc = p0.add(vTo.scale(distance));

		double Ry = height / nY;
		double Rx = width / nX;
		double Yi = (i - (nY - 1) / 2d) * Ry;
		double Xj = (j - (nX - 1) / 2d) * Rx;

		if (Util.isZero(Xj) && Util.isZero(Yi))
			return new Ray(p0, Pc.subtract(p0));
		Point Pij = Pc;
		if (!Util.isZero(Xj))
			Pij = Pij.add(vRight.scale(Xj));

		if (!Util.isZero(Yi))
			Pij = Pij.add(vUp.scale(-Yi));

		Vector Vij = Pij.subtract(p0);

		if (Pij.equals(p0))
			return new Ray(p0, new Vector(Pij.getX(), Pij.getY(), Pij.getZ()));
		return new Ray(p0, Vij);
	}

	/**
	 * A get function
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * A get function
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * A get function
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * A get function
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * A get function
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * A get function
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * A get function
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

}
