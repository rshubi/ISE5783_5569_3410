/**
 * 
 */
package renderer;

import primitives.*;
import static primitives.Util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;



/**
 * @author
 * 
 *         This class represents a camera in a 3D space.
 * 
 *         The camera defines the view of the scene and is responsible for
 *         generating rays that pass through the pixels
 * 
 *         in the view plane and into the scene.
 */
public class Camera {
	private Point p0; // The location of the camera in 3D space
	private Vector vUp; // The up vector of the camera
	private Vector vRight; // The right vector of the camera
	private Vector vTo; // The view vector of the camera
	private double width; // The width of the view plane
	private double height; // The height of the view plane
	private double distance; // The distance between the camera and the view plane

	private ImageWriter writer;
	private RayTracerBase rayTracer;
	/**
	 * numOfRays - number of rays for super sampling
	 */
	private int numOfRays = 1;
	private double debugPrint = 1;

	/**
	 * 
	 * AdaptiveSuperSamplingFlag - Flag to choose whether to apply the Adaptive
	 * Super Sampling
	 */
	private boolean AdaptiveSuperSamplingFlag = false;
	private int numOfThreads = 1;
	// private boolean print = false; // printing progress percentage

	/**
	 * 
	 * Constructs a new camera.
	 * 
	 * @param p0  the location of the camera in 3D space.
	 * @param vTo the view vector of the camera. *
	 * @param vUp the up vector of the camera. *
	 * @throws IllegalArgumentException if the up vector is not orthogonal to the
	 *                                  view vector.
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!isZero(vTo.dotProduct(vUp))) {
			throw new IllegalArgumentException("vUp is not orthogonal to vTo");
		}

		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();

		this.p0 = p0;
	}

	/**
	 * 
	 * Sets the sizes of the view plane. *
	 * 
	 * @param width  the width of the view plane.
	 * @param height the height of the view plane.
	 * @return the camera object.
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * Sets the distance between the camera and the view plane.
	 * 
	 * @param distance the distance between the camera and the view plane.
	 * @return the camera object.
	 */
	public Camera setVPDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * 
	 * Sets the image writer for the camera.
	 * 
	 * @param writer The image writer to be set.
	 * @return The Camera object itself for method chaining.
	 */
	public Camera setImageWriter(ImageWriter writer) {
		this.writer = writer;
		return this;
	}

	/**
	 * 
	 * Sets the ray tracer for the camera.
	 * 
	 * @param rayTracerBase The ray tracer to be set.
	 * @return The Camera object itself for method chaining.
	 */
	public Camera setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracer = rayTracerBase;
		return this;
	}

	/**
	 * A setter function for parameter rayTracer this function return the object -
	 * this for builder pattern
	 * 
	 * @param numOfRays number of rays from camera
	 * @return camera- the object - builder
	 */
	public Camera setNumOfRays(int numOfRays) {
		if (numOfRays == 0)
			this.numOfRays = 1;
		else
			this.numOfRays = numOfRays;
		return this;
	}

	/**
	 * 
	 * Casts a ray from the camera's position to the specified pixel coordinates.
	 * 
	 * @param j The horizontal pixel coordinate.
	 * @param i The vertical pixel coordinate.
	 * @return The color of the ray.
	 */
	private Color castRay(int j, int i) {
		Ray ray = constructRay(writer.getNx(), writer.getNy(), j, i);
		return rayTracer.traceRay(ray);
	}

	/**
	 * A function that creates a grid of lines
	 * 
	 * @param interval int value
	 * @param color    Color value
	 * @return Camera-the object - builder
	 */
	public Camera printGrid(int interval, Color color) {
		if (writer == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter",
					"imageWriter");

		for (int i = 0; i < writer.getNx(); i++) {
			for (int j = 0; j < writer.getNy(); j++) {
				if (i % interval == 0 || j % interval == 0)
					writer.writePixel(i, j, color);
			}
		}
		return this;
	}

	/**
	 * A function that finally creates the image. This function delegates the
	 * function of a class imageWriter
	 */
	public void writeToImage() {
		if (writer == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter",
					"imageWriter");
		writer.writeToImage();// delegation
	}

	/**
	 * Constructs a ray that passes through a specific pixel in the view plane.
	 * 
	 * @param nX the number of pixels in the x direction.
	 * @param nY the number of pixels in the y direction.
	 * @param j  the index of the pixel in the x direction.
	 * @param i  the index of the pixel in the y direction.
	 * @return a Ray object that passes through the specified pixel.
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pC;
		if (isZero(distance))
			pC = p0;
		else
			pC = p0.add(vTo.scale(distance));

		Point pIJ = pC;
		double rY = height / nY; // height of every pixel
		double rX = width / nX; // width of every pixel
		double yI = -(i - (nY - 1) / 2d) * rY;
		double xJ = (j - (nX - 1) / 2d) * rX;

		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));

		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));

		if (pIJ.equals(p0))// if distance is zero and piont axactly in the middle...
			return new Ray(p0, new Vector(pIJ.getX(), pIJ.getY(), pIJ.getZ()));
		return new Ray(p0, pIJ.subtract(p0));
	}

	/**
	 * The function transfers beams from camera to pixel, tracks the beam and
	 * receives the pixel color from the point of intersection
	 * 
	 * @return the object - builder
	 */
	public Camera renderImage() {
		if (writer == null)
			throw new MissingResourceException("this function must have values in all the fields", "ImageWriter",
					"imageWriter");
		if (rayTracer == null)
			throw new MissingResourceException("this function must have values in all the fields", "RayTracerBase",
					"rayTracer");
		if (numOfThreads > 1) {
			renderImageThreaded();
			return this;
		}
		for (int i = 0; i < writer.getNx(); i++) {
			for (int j = 0; j < writer.getNy(); j++) {
				if (numOfRays == 1) {
					Ray ray = constructRay(writer.getNx(), writer.getNy(), j, i);
					Color rayColor = rayTracer.traceRay(ray);
					writer.writePixel(j, i, rayColor);
				} else {
					if (AdaptiveSuperSamplingFlag) {
						Color rayColor = AdaptiveSuperSampling(writer.getNx(), writer.getNy(), j, i);
						writer.writePixel(j, i, rayColor);
					} else {
						List<Ray> rays = constructBeamThroughPixel(writer.getNx(), writer.getNy(), j, i);
						Color rayColor = rayTracer.traceRays(rays);
						writer.writePixel(j, i, rayColor);
					}
				}

			}
		}
		return this;
	}

	/**
	 * 
	 * @param nX the number of pixels in the x direction.
	 * @param nY the number of pixels in the y direction.
	 * @param j  the index of the pixel in the x direction.
	 * @param i  the index of the pixel in the y direction.
	 * @return list of rays-beam through a current Pixel
	 */
	public List<Ray> constructBeamThroughPixel(int nX, int nY, int j, int i) {

		// The distance between the screen and the camera cannot be 0
		if (isZero(distance)) {
			throw new IllegalArgumentException("distance cannot be 0");
		}

		int numOfRays = (int) Math.floor(Math.sqrt(this.numOfRays)); // num of rays in each row or column

		if (numOfRays == 1) // if (1-4) so there is 1 ray
			return List.of(constructRay(nX, nY, j, i));

		double Ry = height / nY;
		double Rx = width / nX;
		double Yi = (i - (nY - 1) / 2d) * Ry;
		double Xj = (j - (nX - 1) / 2d) * Rx;

		double PRy = Ry / numOfRays; // height distance between each ray
		double PRx = Rx / numOfRays; // width distance between each ray

		List<Ray> sample_rays = new LinkedList<>();

		for (int row = 0; row < numOfRays; ++row) {// foreach place in the pixel grid
			for (int column = 0; column < numOfRays; ++column) {
				double jitterX = (Math.random() - 0.5) * PRx; // Random jitter in the x direction
				double jitterY = (Math.random() - 0.5) * PRy; // Random jitter in the y direction
				sample_rays.add(constructRaysThroughPixel(PRy, PRx, Yi, Xj, row, column, jitterX, jitterY));// add the
																											// ray
			}
		}
		sample_rays.add(constructRay(nX, nY, j, i));// add the center screen ray
		return sample_rays;
	}

	/**
	 * In this function we treat each pixel like a little screen of its own and
	 * divide it to smaller "pixels". Through each one we construct a ray. This
	 * function is similar to ConstructRayThroughPixel.
	 * 
	 * @param Ry       height of each grid block we divided the pixel into
	 * @param Rx       width of each grid block we divided the pixel into
	 * @param yi       distance of original pixel from (0,0) on Y axis
	 * @param xj       distance of original pixel from (0,0) on X axis
	 * @param j        j coordinate of small "pixel"
	 * @param i        i coordinate of small "pixel"
	 * @param distance distance of screen from camera
	 * @return beam of rays through pixel
	 */
	private Ray constructRaysThroughPixel(double Ry, double Rx, double yi, double xj, int j, int i, double jitterX,
			double jitterY) {
		Point Pc = p0.add(vTo.scale(distance)); // the center of the screen point

		double x_sample_j = (j * Rx + Rx / 2d) + jitterX; // Add jitter to the x coordinate
		double y_sample_i = (i * Ry + Ry / 2d) + jitterY; // Add jitter to the y coordinate

		Point Pij = Pc; // The point at the pixel through which a beam is fired
		// Moving the point through which a beam is fired on the x axis
		if (!isZero(x_sample_j + xj)) {
			Pij = Pij.add(vRight.scale(x_sample_j + xj));
		}
		// Moving the point through which a beam is fired on the y axis
		if (!isZero(y_sample_i + yi)) {
			Pij = Pij.add(vUp.scale(-y_sample_i - yi));
		}
		Vector Vij = Pij.subtract(p0);
		return new Ray(p0, Vij);// create the ray throw the point we calculate here
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

	/**
	 * This function renders image's pixel color map from the scene with
	 * multi-threading
	 */
	private void renderImageThreaded() {
		final int nX = writer.getNx();// numRows
		final int nY = writer.getNy();// numCols
		Pixel.initialize(nY, nX, debugPrint);
		if (numOfRays != 1) {
			if (AdaptiveSuperSamplingFlag)
				while (numOfThreads-- > 0) {
					new Thread(() -> {
						for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
							writer.writePixel(pixel.col, pixel.row,
									AdaptiveSuperSampling(nX, nY, pixel.col, pixel.row));

					}).start();
				}
			else
				while (numOfThreads-- > 0) {
					new Thread(() -> {
						for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
							List<Ray> rays = constructBeamThroughPixel(nX, nY, pixel.col, pixel.row);
							Color rayColor = rayTracer.traceRays(rays);
							writer.writePixel(pixel.col, pixel.row, rayColor);
						}
					}).start();
				}
		} else// If not used the improvement of anti-aliasing
		{
			while (numOfThreads-- > 0) {
				new Thread(() -> {
					for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
						Ray ray = constructRay(nX, nY, pixel.col, pixel.row);
						Color rayColor = rayTracer.traceRay(ray);
						writer.writePixel(pixel.col, pixel.row, rayColor);
					}

				}).start();
			}
		}
		Pixel.waitToFinish();

	}

	/**
	 * Set multi-threading <br>
	 * - if the parameter is 0 - number of cores less 2 is taken
	 *
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Camera setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
		if (threads != 0)
			numOfThreads = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors();
			numOfThreads = cores < 2 ? 1 : cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @param d number of debug print
	 * @return the Render object itself
	 */
	public Camera setDebugPrint(double d) {
		debugPrint = d;
		return this;
	}

	/**
	 * set Adaptive Super Sampling Flag Flag to choose whether to apply the Adaptive
	 * 
	 * @param adaptiveSuperSamplingFlag to set adaptive super simple flag
	 * @return camera-the object - builder
	 */
	public Camera setAdaptiveSuperSamplingFlag(boolean adaptiveSuperSamplingFlag) {
		AdaptiveSuperSamplingFlag = adaptiveSuperSamplingFlag;
		return this;
	}

	/**
	 * Adaptive Super Sampling
	 * 
	 * @param nX        number of pixels in x axis
	 * @param nY        number of pixels in y axis
	 * @param j         number of pixels in x axis
	 * @param i         number of pixels in x axis
	 * @param numOfRays max num of ray for pixel
	 * @return color for pixel
	 */
	private primitives.Color AdaptiveSuperSampling(int nX, int nY, int j, int i) {
		int numOfRaysInRowCol = (int) Math.floor(Math.sqrt(numOfRays));
		if (numOfRaysInRowCol == 1)
			return rayTracer.traceRay(constructRay(nX, nY, j, i));
		Point Pc;
		if (distance != 0)
			Pc = p0.add(vTo.scale(distance));
		else
			Pc = p0;
		Point Pij = Pc;
		double Ry = height / nY;
		double Rx = width / nX;
		double Yi = -(i - ((nY - 1) / 2d)) * Ry;
		double Xj = (j - ((nX - 1) / 2d)) * Rx;
		if (Xj != 0)
			Pij = Pij.add(vRight.scale(Xj));
		if (Yi != 0)
			Pij = Pij.add(vUp.scale(Yi));
		double PRy = Ry / numOfRaysInRowCol;
		double PRx = Rx / numOfRaysInRowCol;
		return AdaptiveSuperSamplingRec(Pij, Rx, Ry, PRx, PRy, null);
	}

	/**
	 * Adaptive Super Sampling recursive
	 * 
	 * @param centerP   the screen location
	 * @param Width     the screen width
	 * @param Height    the screen height
	 * @param minWidth  the min cube width
	 * @param minHeight the min cube height
	 * @param prePoints list of pre points to
	 * @return color for pixel
	 */
	private primitives.Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth,
			double minHeight, List<Point> prePoints) {

		if (Width < minWidth * 2 || Height < minHeight * 2)// there is no more than one pixelon in the pixel
			return rayTracer.traceRay(new Ray(p0, centerP.subtract(p0)));// so trace just one!

		List<Point> nextCenterPList = new LinkedList<>();// center of every recPixelon
		List<Point> cornersList = new LinkedList<>();// all corners of current pixel each time
		List<primitives.Color> colorList = new LinkedList<>();
		Point tempCorner;
		Ray tempRay;
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				tempCorner = centerP.add(vRight.scale(i * Width / 2)).add(vUp.scale(j * Height / 2));
				cornersList.add(tempCorner);
				if (prePoints == null || !isInList(prePoints, tempCorner))// the corner has never been checked
				{
					tempRay = new Ray(p0, tempCorner.subtract(p0));
					nextCenterPList.add(centerP.add(vRight.scale(i * Width / 4)).add(vUp.scale(j * Height / 4)));
					colorList.add(rayTracer.traceRay(tempRay));
				}
			}
		}

		if (nextCenterPList.size() == 0) {// all the corners have been checked
			return primitives.Color.BLACK;
		}

		boolean isAllEquals = true;
		primitives.Color tempColor = colorList.get(0);
		for (primitives.Color color : colorList) {
			if (!tempColor.isAlmostEquals(color))
				isAllEquals = false;
		}
		if (isAllEquals && colorList.size() > 2)
			return tempColor;

		tempColor = primitives.Color.BLACK;
		for (Point center : nextCenterPList) {
			tempColor = tempColor
					.add(AdaptiveSuperSamplingRec(center, Width / 2, Height / 2, minWidth, minHeight, cornersList));
		}
		return tempColor.reduce(nextCenterPList.size());

	}

	/**
	 * is In List - Checks whether a point is in a points list
	 * 
	 * @param point      the point we want to check
	 * @param pointsList where we search the point
	 * @return true if the point is in the list, false otherwise
	 */
	private boolean isInList(List<Point> pointsList, Point point) {
		for (Point tempPoint : pointsList) {
			if (point.isAlmostEquals(tempPoint))
				return true;
		}
		return false;
	}
}
