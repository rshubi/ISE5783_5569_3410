/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Maayan & Renana
 */
class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testPlanePointPointPoint() {
		// =============== Boundary Values Tests ==================
		// TC11:Testing the construction of a plane when the three points are on the
		// same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(3, 6, 9), new Point(9, 18, 27)),
				"Plane() should throw an exception, but it failed");
		// TC12:Testing the construction of a plane with two converging points
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(9, 18, 27)),
				"Plane() should throw an exception, but it failed");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Test that checks whether the normal of the plane is correct
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(0, 1, 0);
		Point p3 = new Point(0, 0, 1);
		Vector v1 = p1.subtract(p2);
		Vector v2 = p2.subtract(p3);
		Vector v3 = p3.subtract(p1);
		Plane p = new Plane(p1, p2, p3);
		Vector normal = p.getNormal(p1);
		assertEquals(0,v1.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0,v2.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0,v3.dotProduct(normal), "ERROR:normal to plane is not correct");
		
		
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Test that checks whether the normal of the plane is correct
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(0, 1, 0);
		Point p3 = new Point(0, 0, 1);
		Vector v1 = p1.subtract(p2);
		Vector v2 = p2.subtract(p3);
		Vector v3 = p3.subtract(p1);
		Plane p = new Plane(p1, p2, p3);
		Vector normal = p.getNormal(p1);
		assertEquals(0,v1.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0,v2.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0,v3.dotProduct(normal), "ERROR:normal to plane is not correct");
		
	}

}
