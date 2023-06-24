package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.*;
import static primitives.Util.*;
import renderer.Pixel;

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
		
		

		private int _threads = 1;
		private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
		private boolean print = false; // printing progress percentage
	private int numOfRays=500;
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
		if(!AdaptiveSuperSamplingFlag)
		{
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
		else
			return renderImageAdaptiveSuperSampling();
	}
	
	
public Camera renderImageAdaptiveSuperSampling() {
	if (AdaptiveSuperSamplingFlag) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        //Multi threads for calculating pixel color
        final Pixel thePixel = new Pixel(nY,nX);
        Thread[] threads = new Thread[_threads];
        for(int i = 0 ; i<_threads; i++)
        {
            threads[i] = new Thread(()->{
                Pixel pixel = new Pixel();
                try {
                    //while there is a pixel that is not processed by a thread
                    while (thePixel.nextPixel(pixel)) {
                                primitives.Color color = AdaptiveSuperSampling(nX, nY, pixel.col(), pixel.row(),maxRaysForSuperSampling);
                                imageWriter.writePixel(pixel.col(), pixel.row(), new Color(color.getColor()));
                    }
                }
                catch (Exception e){}
            });
        }


        //starts all threads
        for (Thread thread : threads)
            thread.start();

        //Wait for all threads to finish
        for (Thread thread : threads)
            thread.join();

        //finish to create the image
        if(print)
            System.out.print("\r100%\n");
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
	 * @param nX the x resolution
     * @param nY the y resolution
	 * @param i rows
	 * @param j columns
	 * @return the color
	 */
	 private Color castRay(int nX, int nY, int i, int j) {
	       /* if (useAdaptive)
	            return adaptiveHelper(findPixelLocation(nX, nY, i, j), nX, nY);
	        else if (antiAliasingFactor == 1)*/
		 if (numOfRays== 1)
	            return rayTracer.traceRay(constructRay(nX, nY, i, j),numOfRays);
	        else
	            return rayTracer.traceRays(constructRays(nX, nY, i, j),numOfRays);
	    }
	/*
	 * ///////////////////////////////////////////////////////////////// public
	 * List<Ray> constructBeamThroughPixel (int nX, int nY, int j, int i, int
	 * raysAmount){
	 * 
	 * //The distance between the screen and the camera cannot be 0 if
	 * (isZero(distance)) { throw new
	 * IllegalArgumentException("distance cannot be 0"); }
	 * 
	 * int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //num of rays in each
	 * row or column
	 * 
	 * if (numOfRays==1) return List.of(constructRay(nX, nY, j, i));
	 * 
	 * double Ry= height/nY; double Rx=width/nX; double Yi=(i-(nY-1)/2d)*Ry; double
	 * Xj=(j-(nX-1)/2d)*Rx;
	 * 
	 * double PRy = Ry / numOfRays; //height distance between each ray double PRx =
	 * Rx / numOfRays; //width distance between each ray
	 * 
	 * List<Ray> sample_rays = new ArrayList<>();
	 * 
	 * //double Ry = height/nY; //The number of pixels on the y axis //double Rx =
	 * width/nX; //The number of pixels on the x axis //double yi = ((i -
	 * nY/2d)*Ry); //distance of original pixel from (0,0) on Y axis //double xj=
	 * ((j - nX/2d)*Rx); //distance of original pixel from (0,0) on x axis //double
	 * pixel_Ry = Ry/num_of_sample_rays; //The height of each grid block we divided
	 * the parcel into //double pixel_Rx = Rx/num_of_sample_rays; //The width of
	 * each grid block we divided the parcel into
	 * 
	 * for (int row = 0; row < numOfRays; ++row) {//foreach place in the pixel grid
	 * for (int column = 0; column < numOfRays; ++column) {
	 * sample_rays.add(constructRaysThroughPixel(PRy,PRx,Yi, Xj, row, column));//add
	 * the ray } } sample_rays.add(constructRay(nX, nY, j, i));//add the center
	 * screen ray return sample_rays; }
	 *//**
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
		 *//*
			 * private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double
			 * xj, int j, int i){ Point Pc = p0.add(vTo.scale(distance)); //the center of
			 * the screen point
			 * 
			 * double y_sample_i = (i *Ry + Ry/2d); //The pixel starting point on the y axis
			 * double x_sample_j= (j *Rx + Rx/2d); //The pixel starting point on the x axis
			 * 
			 * Point Pij = Pc; //The point at the pixel through which a beam is fired
			 * //Moving the point through which a beam is fired on the x axis if
			 * (!isZero(x_sample_j + xj)) { Pij = Pij.add(vRight.scale(x_sample_j + xj)); }
			 * //Moving the point through which a beam is fired on the y axis if
			 * (!isZero(y_sample_i + yi)) { Pij = Pij.add(vUp.scale(-y_sample_i -yi )); }
			 * Vector Vij = Pij.subtract(p0); return new Ray(p0,Vij);//create the ray throw
			 * the point we calculate here }
			 * /////////////////////////////////////////////////////////////////////////////
			 * ///////
			 */

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
     * @param p- point on the view plane
     * @return color of this point
     */
    private Color calcPointColor(Point p) {
        return rayTracer.traceRay(new Ray(p0, p.subtract(p0)),numOfRays);
    }

	/**
     * calculate average color of the pixel by using adaptive Super-sampling
     *
     * @param center- the center of the pixel
     * @param nY-     number of pixels to width
     * @param nX-     number of pixels to length
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

	public Ray constructRay(int nX, int nY, int j, int i) {
		return new Ray(p0, findPixelLocation(nX, nY, j, i).subtract(p0));
	}
	/**
     * check if this point exist in the HashTable return his color otherwise calculate the color and save it
     *
     * @param point-           certain point in the pixel
     * @param pointColorTable- dictionary that save points and their color
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
     * @param max-         the depth of the recursion
     * @param center-      the center of the pixel
     * @param rX-          the width of the pixel
     * @param rY-          the height of the pixel
     * @param upLeftCol-   the color of the vUp left corner
     * @param upRightCol-  the color of the vUp vRight corner
     * @param downLeftCol- the color of the down left corner
     * @param downRightCol - the color of the down vRight corner
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
    /**
     * Adaptive Super Sampling
     *@param nX number of pixels in x axis
     *@param nY number of pixels in y axis
     *@param j number of pixels in x axis
     *@param i number of pixels in x axis
     *@param numOfRays max num of ray for pixel
     *@return color for pixel
     */
    private primitives.Color AdaptiveSuperSampling(int nX, int nY, int j, int i, int numOfRays) throws Exception {
        int numOfRaysInRowCol = (int)Math.floor(Math.sqrt(numOfRays));
        if(numOfRaysInRowCol == 1)
            return rayTracer.traceRay(constructRay(nX, nY, j, i),numOfRays);
        Point Pc;
        if (distance != 0)
            Pc = p0.add(vTo.scale(distance));
        else
            Pc = p0;
        Point Pij = Pc;
        double Ry = height/nY;
        double Rx = width/nX;
        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;
        if(Xj != 0) Pij = Pij.add(vRight.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(vUp.scale(-Yi)) ;
        double PRy = Ry/numOfRaysInRowCol;
        double PRx = Rx/numOfRaysInRowCol;
        return AdaptiveSuperSamplingRec(Pij, Rx, Ry, PRx, PRy,null);
    }
    /**
     * Adaptive Super Sampling recursive
     *@param centerP the screen location
     *@param Width the screen width
     *@param Height the screen height
     *@param minWidth the min cube width
     *@param minHeight the min cube height
     *@param prePoints list of pre points to
     *@return color for pixel
     */
    private primitives.Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, List<Point> prePoints) throws Exception {

        if (Width < minWidth * 2 || Height < minHeight * 2) {
            return rayTracer.traceRay(new Ray(p0, centerP.subtract(p0)),numOfRays);
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2) {
            	tempCorner = centerP.add(vRight.scale(i * Width / 2)).add(vUp.scale(j * Height / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(p0, tempCorner.subtract(p0));
                    nextCenterPList.add(centerP.add(vRight.scale(i * Width / 4)).add(vUp.scale(j * Height / 4)));
                    colorList.add(rayTracer.traceRay(tempRay,numOfRays));
                    }
                }
            }

        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            return primitives.Color.BLACK;
        }


        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            return tempColor;


        tempColor = primitives.Color.BLACK;
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width/2,  Height/2,  minWidth,  minHeight ,  cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());


    }
    /**
     * is In List - Checks whether a point is in a points list
     * @param point the point we want to check
     * @param pointsList where we search the point
     * @return true if the point is in the list, false otherwise
     */
    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if(point.isAlmostEquals(tempPoint))
                return true;
        }
        return false;
    }
    


}
