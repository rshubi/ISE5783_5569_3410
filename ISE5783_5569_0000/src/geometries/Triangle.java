package geometries;
import primitives.Point;
/**
 * 
 * @author נעמי
 *A class for representing a triangle that inherits from a polygon
 */
public class Triangle extends Polygon{
	/**
	 *  The constructor function gets
	 * @param p1
	 * @param p2
	 * @param p3
	 * Three points forming a triangle
	 */
	public Triangle(Point p1,Point p2,Point p3)
	{
		super(p1,p2,p3);
	}

}
