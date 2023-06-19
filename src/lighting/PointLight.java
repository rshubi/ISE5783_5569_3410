/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;;
/**
 * A class for Point Light. the class extends from Light and realizes the
 * LightSource
 * 
 * @author Maayan &amp; Renana
 *
 */
public class PointLight extends Light implements LightSource {

	private final Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;
	private  static final Random RND= new Random();
	private double radius = 0;
    
	/**
	 * The constructor for PointLight
	 * 
	 * @param intensity1 Color value
	 * @param position1  Point3D value
	 */
	public PointLight(Color intensity1, Point position1) {
		super(intensity1);
		position = position1;
	}
	public PointLight(Color intensity1, Point position1,double radius) {
		super(intensity1);
		position = position1;
		this.radius=radius;
	}

	/**
	 * A set function for filed kC
	 * 
	 * @param kC1 the kC to set-double
	 * @return the object - builder
	 */
	public PointLight setKc(double kC1) {
		kC = kC1;
		return this;
	}

	/**
	 * A set function for filed kL
	 * 
	 * @param kL1 the kL to set-double
	 * @return the object - builder
	 */
	public PointLight setKl(double kL1) {
		kL = kL1;
		return this;
	}

	/**
	 * A set function for filed kQ
	 * 
	 * @param kQ1 the kQ to set-double
	 * @return the object - builder
	 */
	public PointLight setKq(double kQ1) {
		kQ = kQ1;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double dSquared = p.distanceSquared(position);
		return intensity.reduce((kC + kL * Math.sqrt(dSquared) + kQ * dSquared));
	}
	 
	 @Override
	    public List<Vector> getBeamL(Point p, double radius, int amount) {
	        if (p.equals(position)) {
	            return null;
	        }
	        LinkedList<Vector> beam = new LinkedList<>();

	        //from pointlight position to p point
	        Vector v = this.getL(p);
	        beam.add(v);
	        if (amount <= 1) {
	            return beam;
	        }

	        double distance = this.position.distance(p);

	        if (isZero(distance)) {
	            throw new IllegalArgumentException("distance cannot be 0");
	        }

	        Point lightHead = new Point(v.getX(),v.getY(),v.getZ());
	        Vector normX;

	        // if v._head == (0,0,w) then normX.head ==(-w,0,0)
	        // otherwise normX.head == (-y,x,0)
	        if (isZero(lightHead.getX()) && isZero(lightHead.getY())) {
	            normX = new Vector(lightHead.getZ() * -1, 0, 0).normalize();
	        } else {
	            normX = new Vector(lightHead.getY() * -1, lightHead.getX(), 0).normalize();
	        }

	        Vector normY = v.crossProduct(normX).normalize();
	        double cosTheta;
	        double sinTheta;

	        double d;
	        double x;
	        double y;

	        for (int counter = 0; counter < amount; counter++) {
	            Point newPoint = new Point(this.position.getX(),this.position.getY(),this.position.getZ());
	            // randomly coose cosTheta and sinTheta in the range (-1,1)
	            cosTheta = 2 * RND.nextDouble() - 1;
	            sinTheta = Math.sqrt(1d - cosTheta * cosTheta);

	            //d ranged between -radius and  +radius
	            d = radius * (2 * RND.nextDouble() - 1);
	            //d ranged between -radius and  +radius
	            if (isZero(d)) { 
	                counter--;
	                continue;
	            }
	            x = d * cosTheta;
	            y = d * sinTheta;

	            if (!isZero(x)) {
	                newPoint = newPoint.add(normX.scale(x));
	            }
	            if (!isZero(y)) {
	                newPoint = newPoint.add(normY.scale(y));
	            }
	            beam.add(p.subtract(newPoint).normalize());
	        }
	        return beam;

	 }
	public PointLight setRadius(double r) {
        this.radius = r;//
        return this;
    }
	
	
	@Override
	public Vector getL(Point p) {
		// In order not to reach a state of exception due to the zero vector
		if (p.equals(position))
			return null;
		return p.subtract(position).normalize();
	}

	@Override
	public double getDistance(Point point) {
		return position.distance(point);
	}
	public double getradius() {
		return this.radius;
	}
}
