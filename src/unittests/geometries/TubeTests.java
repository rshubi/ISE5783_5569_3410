/**
 * 
 */
package unittests.geometries;

import primitives.Point;
import geometries.Tube;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Tube class
 * 
 * @author Maayan & Renana
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Ray r = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
		Tube t = new Tube(r, 1);
		// ============ Equivalence Partitions Tests ==============
		// TC01:Test that checks whether the normal of the tube is correct
		Point p = new Point(-0.8, 0.99, 0.6);
		Vector n = t.getNormal(p);
		assertEquals(new Vector(-0.8, 0, 0.6).normalize(), n, "normal to tube is incorrect");
		// =============== Boundary Values Tests ==================
		// the point is in front of the head of the ray
		Point p1 = new Point(0, 0, 1);
		Vector n1 = t.getNormal(p1);
		assertEquals(new Vector(0, 0, 1).normalize(), n1, "normal to tube is incorrect");
	}
}
