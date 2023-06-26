package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.*;
import static primitives.Util.*;
//import renderer.PixelManager;

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
	private int maxRaysForSuperSampling = 500;
	private boolean AdaptiveSuperSamplingFlag = false;
	private double debugPrint = 0;
	private int _threads =3;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage
	private int numOfRays = 50;
	private boolean addaptive =true;

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
	 *//*
		 * public Camera setUseAdaptive(boolean useAdaptive) { this.useAdaptive =
		 * useAdaptive; return this; }
		 */

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
	 * The function returns a ray that passes through the center of a certain pixel
	 * 
	 * @param nX row number of view plane
	 * @param nY column number of view plane
	 * @param j  column of a certain pixel
	 * @param i  row of a certain pixel
	 * @return a ray that passes through the center of a certain pixel
	 */
	/*
	 * public Ray constructRay(int nX, int nY, int j, int i) throws Exception {//
	 * constructRayThroughPixel Point pC = p0.add(vTo.scale(distance));
	 * 
	 * double rY = height / nY; double rX = width / nX; double yI = (i - (nY - 1) /
	 * 2d) * rY; double xJ = (j - (nX - 1) / 2d) * rX;
	 * 
	 * Point pIJ = pC; if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ)); if
	 * (!isZero(yI)) pIJ = pIJ.add(vUp.scale(-yI));
	 * 
	 * return new Ray(p0, pIJ.subtract(p0)); }
	 */

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
		 {
			Color rayColor;
			int nX = imageWriter.getNx();
			int nY = imageWriter.getNy();
			if (imageWriter == null)
				throw new MissingResourceException("ERROR: renderImage, imageWriter is null", "ImageWriter",
						"imageWriter");
			if (rayTracer == null)
				throw new MissingResourceException("ERROR: renderImage, rayTracer is null", "RayTracerBase",
						"rayTracer");
			for (int i = 0; i < nX; i++) {
				for (int j = 0; j < nY; j++) {
					if(addaptive==false) {
					     rayColor = castRay(nX, nY, j, i);
					imageWriter.writePixel(j, i, rayColor);
					}
					else
					{
						rayColor = castSSrays(nX, nY, j, i);
						imageWriter.writePixel(j, i, rayColor);
					}
					
				}
			}
			return this;
		} 
			
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
	 * 
	 * @param nX the x resolution
	 * @param nY the y resolution
	 * @param i  rows
	 * @param j  columns
	 * @return the color
	 */
	private Color castRay(int nX, int nY, int i, int j) {
		
		if (numOfRays == 1)
			return rayTracer.traceRay(constructRay(nX, nY, i, j), numOfRays);
		else {
			//if(!addaptive) 
			return rayTracer.traceRays(constructRays(nX, nY, i, j), numOfRays);
		    
			}
		
	}
	public Ray constructRay(int nX, int nY, int j, int i) {
		return new Ray(p0, findPixelLocation(nX, nY, j, i).subtract(p0));
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
		double rY = height / nY / 1;
		double rX = width / nX / 1;
		double x, y;

		for (int rowNumber = 0; rowNumber < 1; rowNumber++) {
			for (int colNumber = 0; colNumber < 1; colNumber++) {
				y = -(rowNumber - (1 - 1d) / 2) * rY;
				x = (colNumber - (1 - 1d) / 2) * rX;
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

	private Color castSSrays(int nX, int nY, int i, int j)
    {
        //Ray r = constructRay(nX,nY,i,j);
        List<Point> corners = constructCorners(nX,nY,i,j);
        Color color = rayTracer.traceRaySS(corners.get(0), corners.get(1)
                ,corners.get(2), corners.get(3), p0,1,numOfRays);
        //imageWriter.writePixel(i,j,color);
        return color;
    }

	/**
     * for supersampling
     * This function receive the pixel position and size and return a list of all the
     * corner Points of the Pixel
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public List<Point> constructCorners(int nX, int nY, int j, int i){

        Point Pc = p0.add(vTo.scale(distance));

        double Rx = width/nX;
        double Ry = height/nY;

        double Yi = ( i - (nY -1)/2d)*Ry;
        double Xj = ( j - (nX-1)/2d)*Rx;

        Point Pij = Pc;
        if (!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi))
            Pij = Pij.add(vUp.scale(-Yi));

  


        List<Point> points = new ArrayList<>();
        points.add(new Point(Pij.getX() + -Rx/2, Pij.getY() + Ry/2 , Pij.getZ()));
        points.add(new Point(Pij.getX() + Rx/2, Pij.getY() + Ry/2 , Pij.getZ()));
        points.add(new Point(Pij.getX() + Rx/2, Pij.getY() + -Ry/2 , Pij.getZ()));
        points.add(new Point(Pij.getX() + -Rx/2, Pij.getY() + -Ry/2 , Pij.getZ()));

        return points;

    }

	

	
	
	

	
	

}
