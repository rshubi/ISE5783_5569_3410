/**
 * 
 */
package unittests;

import primitives.Point;
import primitives.Ray;
import geometries.Sphere;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Maayan & Renana
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// TC01:Test that checks whether the normal of the sphere is correct
		Point o = new Point(0, 0, 0);
		double r = 1;
		Point p = new Point(1, 0, 0);
		Sphere s = new Sphere(r, o);
		//assertEquals(0, o.subtract(p).dotProduct(s.getNormal(p)), "normal to tube is incorrect");
		assertEquals(p, s.getNormal(p), "GetNormal() the normal is incorrect");

	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
				"Ray's line out of sphere");
		// TC02: Ray starts before and crosses the sphere (2 points)
		Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
       if(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0))).size()>0)
       {
		List<Point>result = new ArrayList(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0))));
		//result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
		assertNotNull(result, "Ray's line out of sphere");
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
		// TC03: Ray starts inside the sphere (1 point)
		Point p3 = new Point(1.9865781936884233,0.11546312774753693,0.11546312774753693);
	       
		result = sphere.findIntersections(new Ray(new Point(1.6, 0.1, 0.1), new Vector(5, 0.2, 0.2)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p3), result, "Ray crosses sphere");
		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 3, 0), new Vector(0, 1, 0))),
				"Ray's line out of sphere");
		// =============== Boundary Values Tests ==================
		// 6 tests
		// TC11:Ray starts at the center (1 points)
		Point p4 = new Point(1.4486372738374493,-0.8937139343904745,0.0);
		result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(2.52,-5.02, 0)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p4), result, "Ray crosses sphere");
		// TC12:Ray starts inside (1 points)
		Point p5 = new Point(0, 0, 0);
		Point p6 = new Point(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point(0.7, 0, 0), new Vector(6, 0, 0)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p6), result, "Ray crosses sphere");
		// TC13:Ray starts before the sphere (2 points)
		result = sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-3, 0, 0)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p5, p6), result, "Ray crosses sphere");
		// TC14:Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
		"Tc14 failed:Wrong number of points");
		// TC15: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
				"Ray's line out of sphere");
		// TC16:A ray on the straight line that passes through the center of the sphere
		result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(2.5, 0, 0)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p6), result, "Ray crosses sphere");
		// 2 tests
		// TC17:A ray on the straight line that passes through the center of the sphere
		Point p7 = new Point(1.973156858195953, 0.164792775617886, 0.160652016639489);
		Point p8 = new Point(0.293236790704458, -0.003836783994931, 0.707439781942493);
		Ray r = new Ray(new Point(1.973156858195953, 0.164792775617886, 0.160652016639489),
				new Vector(-1.679920067491496, -0.168629559612817, 0.546787765303003));
		result = sphere.findIntersections(r);
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(List.of(p8), result, "Ray crosses sphere");
		// TC18:A ray on the straight line that passes through the center of the sphere
		Point p9 = new Point(1.95275829295553, 0, 0.303729542857564);
		Ray r1 = new Ray(new Point(1.95275829295553, 0, 0.303729542857564),
				new Vector(2.357802219973595, 1.308218640555971, -0.303729542857564));
		assertNull(sphere.findIntersections(r1),"TC18 FAILED:Wrong number of points");
		
		// 3 test
		// TC19:A ray that hits the sphere and leaves a point on the sphere
		Ray r2 = new Ray(new Point(1, 0, 1), new Vector(-1, 0, 0));
		assertNull(sphere.findIntersections(r2),"TC19 FAILED:Wrong number of points");
		
		// TC20:p0 left from the sphere
		r2 = new Ray(new Point(3, 0, 1), new Vector(1, 0, 0));
		assertNull(sphere.findIntersections(r2),"TC20 FAILED:Wrong number of points");
		// TC20:p0 right from the sphere
		r2 = new Ray(new Point(1, 0, 1), new Vector(-0.5, 0, 0));
		assertNull(sphere.findIntersections(r2),"TC21 FAILED:Wrong number of points");
		// TC21:A ray on the straight line outside the sphere when the segment is from
		// the center to the beginning of the ray
		// orthogonally to the ray
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, -2, 2))),
				"Ray's line out of sphere");
       }

	}
}
