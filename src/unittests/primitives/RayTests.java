package unittests.primitives;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for primitives.Ray class
 *
 * @author Maayan &amp; Renana
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	public void testGetPoint() {
		// ============ Equivalence Partitions Tests ==============
		Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
		// TC02: test that check get point function while t>0
		assertEquals(new Point(2, 0, 1), ray.getPoint(2), "The function getPoint dont work correct");
		// TC03: test that check get point function while t<0
		assertEquals(new Point(-2, 0, 1), ray.getPoint(-2), "The function getPoint dont work correct");

		// =============== Boundary Values Tests ==================
		// TC01: test that check get point function while t==0
		assertEquals(new Point(0, 0, 1), ray.getPoint(0), "The function getPoint dont work correct");
	}

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
		Point p1 = new Point(6, 0, 0);
		Point p2 = new Point(7, 0, 0);
		Point p3 = new Point(9, 0, 0);
		List<Point> points = List.of(p1, p2, p3);
		// ============ Equivalence Partitions Tests ==============
		// TC01: The middle point is closest to the beginning of the Ray
		points = List.of(p2, p1, p3);
		assertEquals(p1, ray.findClosestPoint(points));
		// =============== Boundary Values Tests ==================
		// TC11: empty list
		points = null;
		assertEquals(null, ray.findClosestPoint(points));
		// TC12: The first point is closest to the beginning of the Ray
		points = List.of(p1, p2, p3);
		assertEquals(p1, ray.findClosestPoint(points));
		// TC13: The last point is closest to the beginning of the Ray
		points = List.of(p2, p3, p1);
		assertEquals(p1, ray.findClosestPoint(points));
	}
}
