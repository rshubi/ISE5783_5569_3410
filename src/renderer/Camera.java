package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.*;

import lighting.PointLight;

import static primitives.Util.*;


/**
 * A class for Camera construction
 * 
 * @author Maayan &amp; Renana
 *
 */
public class Camera {
	private final Point p0;
	private Vector vUp;
	private Vector vRight;
	private Vector vTo;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	private int antiAliasingFactor = 1;
	private int maxAdaptiveLevel = 2;
	private boolean useAdaptive = false;
	 private int threadsCount;
	 private double printInterval;
	/**
	 * The constructor for camera
	 * 
	 * @param p0  Camera location point
	 * @param vTo A vector that sets the direction of the camera to the forward
	 * @param vUp A vector that sets the direction of the camera to the up
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!isZero(vTo.dotProduct(vUp)))
			throw new IllegalArgumentException("vUp is not ortogonal to vTo");

		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		vRight = (vTo.crossProduct(vUp)).normalize();
		this.p0 = p0;
	}

	/**
	 * A set function for the size.
	 * 
	 * @param width  Width of the view plane
	 * @param height height of the view plane
	 * @return the size
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * setter for UseAdaptive
	 * 
	 * @param useAdaptive- the number of pixels in row/col of every pixel
	 * @return camera itself
	 */
	public Camera setUseAdaptive(boolean useAdaptive) {
		this.useAdaptive = useAdaptive;
		return this;
	}

	/**
	 * setter for maxAdaptiveLevel
	 * 
	 * @param maxAdaptiveLevel- The depth of the recursion
	 * @return camera itself
	 */
	public Camera setMaxAdaptiveLevel(int maxAdaptiveLevel) {
		this.maxAdaptiveLevel = maxAdaptiveLevel;
		return this;
	}

	/**
	 * A Set Function for the size distance
	 * 
	 * @param distance distance of the camera from the geometric body
	 * @return the distance
	 */
	public Camera setVPDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * A get function to return P0
	 * 
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * A get function to return vUp
	 * 
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * A get function to return vRight
	 * 
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * A get function to return vTo
	 * 
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * A get function to return the width
	 * 
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * A get function to return the height
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * A get function to return the distance
	 * 
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * A set function for the ImageWriter
	 * 
	 * @param imageWriter pixel color matrix
	 * @return the object itself
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * A set function for the RayTracer
	 * 
	 * @param rayTracer a ray to trace through the scene
	 * @return the object itself
	 */

	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}

	/**
	 * A function that builds for each pixel a ray and sets the color in the
	 * corresponding pixel
	 * 
	 * @return the camera
	 */
	public Camera renderImage() {
		Color rayColor;
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: renderImage, imageWriter is null", "ImageWriter", "imageWriter");
		if (rayTracer == null)
			throw new MissingResourceException("ERROR: renderImage, rayTracer is null", "RayTracerBase", "rayTracer");
		for (int i = 0; i < nX; i++) {
			for (int j = 0; j < nY; j++) {
				rayColor = castRay(nX, nY,j, i);
				imageWriter.writePixel(j, i, rayColor);
			}
		}
		return this;
	}

	/**
	 * A function to create a grid of lines
	 * 
	 * @param interval the number of profits
	 * @param color    the color of the grid
	 */
	public void printGrid(int interval, Color color) {
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: renderImage, imageWriter is null", "ImageWriter", "imageWriter");

		for (int i = 0; i < nX; i++) {
			for (int j = 0; j < nY; j++) {
				// check if this grid line
				if (i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(i, j, color);
			}
		}

	}

	/**
	 * A function that finally creates the image. this function delegates the
	 * function of a class imageWriter
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("ERROR: renderImage, imageWriter is null", "ImageWriter", "imageWriter");
		imageWriter.writeToImage();
	}

	/**
	 * The function creates a beam and calculates the color in the ray
	 * @param nX the x resolution
     * @param nY the y resolution
	 * @param i rows
	 * @param j columns
	 * @return the color
	 */
	 private Color castRay(int nX, int nY, int i, int j) {
	        if (useAdaptive)
	            return adaptiveHelper(findPixelLocation(nX, nY, i, j), nX, nY);
	        else if (antiAliasingFactor == 1)
	            return rayTracer.traceRay(constructRay(nX, nY, i, j));
	        else
	            return rayTracer.traceRays(constructRays(nX, nY, i, j));
	    }

	/**
	 * function that calculates the pixels location
	 *
	 * @param nX the x resolution
	 * @param nY the y resolution
	 * @param i  the x coordinate
	 * @param j  the y coordinate
	 * @return the ray
	 */
	private Point findPixelLocation(int nX, int nY, int j, int i) {

		double rY = height / nY;
		double rX = width / nX;

		double yI = -(i - (nY - 1d) / 2) * rY;
		double jX = (j - (nX - 1d) / 2) * rX;
		Point pIJ = p0.add(vTo.scale(distance));

		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));
		if (jX != 0)
			pIJ = pIJ.add(vRight.scale(jX));
		return pIJ;
	}

	/**
	 * function that returns the rays from the camera to the point
	 *
	 * @param nX the x resolution
	 * @param nY the y resolution
	 * @param i  the x coordinate
	 * @param j  the y coordinate
	 * @return the ray
	 */
	public List<Ray> constructRays(int nX, int nY, int j, int i) {
		List<Ray> rays = new LinkedList<>();
		Point centralPixel = findPixelLocation(nX, nY, j, i);
		double rY = height / nY / antiAliasingFactor;
		double rX = width / nX / antiAliasingFactor;
		double x, y;

		for (int rowNumber = 0; rowNumber < antiAliasingFactor; rowNumber++) {
			for (int colNumber = 0; colNumber < antiAliasingFactor; colNumber++) {
				y = -(rowNumber - (antiAliasingFactor - 1d) / 2) * rY;
				x = (colNumber - (antiAliasingFactor - 1d) / 2) * rX;
				Point pIJ = centralPixel;
				if (y != 0)
					pIJ = pIJ.add(vUp.scale(y));
				if (x != 0)
					pIJ = pIJ.add(vRight.scale(x));
				rays.add(new Ray(p0, pIJ.subtract(p0)));
			}
		}
		return rays;
	}
	 /**
     * get the point and return the color of the ray to this point
     *
     * @param p point on the view plane
     * @return color of this point
     */
    private Color calcPointColor(Point p) {
        return rayTracer.traceRay(new Ray(p0, p.subtract(p0)));
    }

	/**
     * calculate average color of the pixel by using adaptive Super-sampling
     *
     * @param center the center of the pixel
     * @param nY    number of pixels to width
     * @param nX   number of pixels to length
     * @return- the average color of the pixel
     */
    private Color adaptiveHelper(Point center, double nY, double nX) {
        Hashtable<Point, Color> pointColorTable = new Hashtable<Point, Color>();
        double rY = height / nY / 2;
        double rX = width / nX / 2;
        Color upRight = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(rX)));
        Color upLeft = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(-rX)));
        Color downRight = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(rX)));
        Color downLeft = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(-rX)));

        return adaptive(1, center, rX, rY, pointColorTable, upLeft, upRight, downLeft, downRight);
    }
/**
 * 
 * @param nX number of pixels to length
 * @param nY number of pixels to width
 * @param j the y coordinate
 * @param i the x coordinate
 * @return A ray that passes through a specific pixel center
 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		return new Ray(p0, findPixelLocation(nX, nY, j, i).subtract(p0));
	}
	/**
     * check if this point exist in the HashTable return his color otherwise calculate the color and save it
     *
     * @param point certain point in the pixel
     * @param pointColorTable dictionary that save points and their color
     * @return the color of the point
     */
    private Color getPointColorFromTable(Point point, Hashtable<Point, Color> pointColorTable) {
        if (!(pointColorTable.containsKey(point))) {
            Color color = calcPointColor(point);
            pointColorTable.put(point, color);
            return color;
        }
        return pointColorTable.get(point);
    }
	/**
     * recursive method that return the average color of the pixel- by checking the color of the four corners
     *
     * @param max the depth of the recursion
     * @param center     the center of the pixel
     * @param rX         the width of the pixel
     * @param rx          the height of the pixel
     * @param upLeftCol  the color of the vUp left corner
     * @param upRightCol the color of the vUp vRight corner
     * @param downLeftCol the color of the down left corner
     * @param downRightCol the color of the down vRight corner
     * @return the average color of the pixel
     */
    private Color adaptive(int max, Point center, double rX, double rY, Hashtable<Point, Color> pointColorTable,
                           Color upLeftCol, Color upRightCol, Color downLeftCol, Color downRightCol) {
        if (max == maxAdaptiveLevel) {
            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);
        }
        if (upRightCol.equals(upLeftCol) && downRightCol.equals(downLeftCol) && downLeftCol.equals(upLeftCol))
            return upRightCol;
        else {
            Color rightPCol = getPointColorFromTable(center.add(vRight.scale(rX)), pointColorTable);
            Color leftPCol = getPointColorFromTable(center.add(vRight.scale(-rX)), pointColorTable);
            Color upPCol = getPointColorFromTable(center.add(vUp.scale(rY)), pointColorTable);
            Color downPCol = getPointColorFromTable(center.add(vUp.scale(-rY)), pointColorTable);
            Color centerCol = calcPointColor(center);

            rX = rX / 2;
            rY = rY / 2;
            upLeftCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
                    upLeftCol, upPCol, leftPCol, centerCol);
            upRightCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
                    upPCol, upRightCol, centerCol, leftPCol);
            downLeftCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
                    leftPCol, centerCol, downLeftCol, downPCol);
            downRightCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
                    centerCol, rightPCol, downPCol, downRightCol);
            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);
        }
        
    }
    public Camera setMultithreading(int n) {
        this.threadsCount = n;
        return this;
    }
    public Camera setDebugPrint(double k) {
        this.printInterval = k;
        return this;
    }


}