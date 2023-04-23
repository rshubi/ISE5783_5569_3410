/**
 * 
 */
package unittests;

import primitives.Point;
import geometries.Sphere;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

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
		Vector normal = p.subtract(o).normalize();
		assertEquals(normal,s.getNormal(p), "GetNormal() the normal is incorrect");

	}

}
