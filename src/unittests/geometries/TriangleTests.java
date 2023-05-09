/**
 * 
 */
package unittests;

import geometries.Plane;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Triangle class
 * 
 * @author Maayan & Renana
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Test that checks whether the normal of the triangle is correct
		Point p1 = new Point(0, 0, 0);
		Point p2 = new Point(0, 2, 0);
		Point p3 = new Point(0, 0, 2);
		Vector v1 = p1.subtract(p2);
		Vector v2 = p2.subtract(p3);
		Vector v3 = p3.subtract(p1);
		Triangle tri = new Triangle(p1, p2, p3);
		Vector normal = tri.getNormal(null);
		assertEquals(0, v1.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v2.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v3.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(new Vector(1, 0, 0), normal, "GetNormal() should throw an exception, but it failed");
	}

	/**
	 * Test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {

		Triangle tr = new Triangle(new Point(0, 4, -4), new Point(4, 0, -4), new Point(-4, 0, -4));
		Triangle mytr = new Triangle(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0));
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray goes through the triangle
		Point p1 = new Point(2, 2, 0);
		List<Point> result1 = mytr
				.findIntersections(new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3)));
		assertEquals(List.of(p1), result1, "Ray crosses sphere");

		// TC02: the ray is outside the triangle against edge
		assertNull(tr.findIntersections(new Ray(new Point(5, 5, -2), new Vector(2, 2, -3))),
				"the ray is outside the triangle against edge");

		// TC03: the ray is outside the triangle against vertex
		assertNull(tr.findIntersections(new Ray(new Point(-4.4, 0, 0), new Vector(1, 1, 1))),
				"the ray is outside the triangle against edge");

		// =============== Boundary Values Tests ==================
		// TC11: The ray intersects on the edge of the triangle
		assertNull(tr.findIntersections(new Ray(new Point(3, 5, 1), new Vector(-2, -5, -5))), "TC11:ERROR");

		// TC12: The ray intersects on vertice of the triangle
		assertNull(tr.findIntersections(new Ray(new Point(3, 5, 1), new Vector(-3, -1, -5))), "TC12:ERROR");

		// TC13: The ray On the straight line continuing the edge of the triangle
		assertNull(tr.findIntersections(new Ray(new Point(3, 5, 1), new Vector(2, -5, -5))),
				"TC13:ERROR:the ray is outside the triangle against edge");

	}

}
