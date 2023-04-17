/**
 * 
 */
package unittests;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Triangle class
 * @author Maayan & Renana
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		//TC01:Test that checks whether the normal of the triangle is correct
		 Triangle tri = new Triangle(new Point(0,0,0),new Point(0,2,0),new Point(0,0,2));
	        assertEquals(new Vector(1,0,0),tri.getNormal(null));
	}

}
