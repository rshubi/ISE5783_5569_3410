/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Ray;
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
		assertEquals(0, v1.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v2.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v3.dotProduct(normal), "ERROR:normal to plane is not correct");
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
		assertEquals(0, v1.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v2.dotProduct(normal), "ERROR:normal to plane is not correct");
		assertEquals(0, v3.dotProduct(normal), "ERROR:normal to plane is not correct");

	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		// ============ Equivalence Partitions Tests ==============
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(0, 1, 0);
		Point p3 = new Point(0, 0, 1);
		Plane p = new Plane(p1, p2, p3);
		Vector v1 = p1.subtract(p2);
		Vector v2 = p2.subtract(p3);
		Vector v3 = p3.subtract(p1);
		// TC01:A ray that starts outside the plane, is not parallel to the plane, and
		// makes a non-right angle with the plane and cuts it
		Point p4 = new Point(0.833333333333333, 0.16666666666666696, 0.0);
		Ray r = new Ray(new Point(5, 6, 0), new Vector(-2.5, -3.5, 0));
		List<Point> result = p.findIntersections(r);
		assertNotEquals(0, r.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertNotEquals(0, r.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertNotEquals(0, r.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p4), result, "Ray crosses sphere");
		// TC02:A ray that starts outside the plane, is not parallel to the plane, and
		// makes a non-right angle with the plane and does not cut it

		assertNull(p.findIntersections(new Ray(new Point(3, 1, -1), new Vector(2, 2, 2))), "Ray's line out of sphere");
		assertNotEquals(0, r.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertNotEquals(0, r.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertNotEquals(0, r.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		// =============== Boundary Values Tests ==================
		// 2 tests
		// TC11:A ray that starts outside the plane, and is parallel to the plane
		assertNull(p.findIntersections(new Ray(new Point(2, 2, 2), new Vector(-3, 0, 3))), "Ray's line out of sphere");
		// TC12:A ray inside the plane, and parallel to the plane
		assertNull(p.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-3, 0, 3))), "Ray's line out of sphere");
		// 3 tests
		// TC13-A ray that starts in front of the plane, and is perpendicular to the
		// plane
		Ray ray = new Ray(new Point(0.5, -0.5, 1), new Vector(2, 2, 2));
		assertEquals(0, ray.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		// TC14:A ray that starts inside the plane, and is perpendicular to the plane
		ray = new Ray(new Point(0, 1, 0), new Vector(2, 2, 2));
		assertEquals(0, ray.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		// TC15:A ray that starts after the plane, and is perpendicular to the plane
		ray = new Ray(new Point(0, 2, 0), new Vector(2, 2, 2));
		assertEquals(0, ray.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertEquals(0, ray.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		// TC16:The beam starts inside the plane, and is neither perpendicular nor
		// parallel to it.
		ray = new Ray(new Point(0.315829530927468, -0.540796017221324, 1.224966486293857),
				new Vector(0.435266860626076, 2.799817280644068, -1.445387142834855));
		assertNotEquals(0, ray.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertNotEquals(0, ray.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertNotEquals(0, ray.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");
		// TC17:The beginning of the beam is exactly at the reference point of the
		// plane.
		ray = new Ray(new Point(1, 0, 0), new Vector(0.5, 2.8, -1.5));
		assertNotEquals(0, ray.getDir().dotProduct(v1), "The ray is ortogonaly to the plane");
		assertNotEquals(0, ray.getDir().dotProduct(v2), "The ray is ortogonaly to the plane");
		assertNotEquals(0, ray.getDir().dotProduct(v3), "The ray is ortogonaly to the plane");

	}

}
