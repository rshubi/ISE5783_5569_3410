/**
 * 
 */
package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * @author נעמי
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

	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!Util.isZero(vTo.dotProduct(vUp)))
			throw new IllegalArgumentException("vUp is not ortogonal to vTo");

		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();
		this.p0 = p0;
	}

	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public Camera setVPDistance(double distance) {
		this.distance = distance;
		return this;
	}

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
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

}
