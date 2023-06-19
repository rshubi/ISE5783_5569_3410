package unittests.primitives;

import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Maayan &amp; Renana
 */
public class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Two point connection test
		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(4, 5, 6);
		Point pr = p1.add(v1);
		assertEquals(new Point(5, 7, 9), pr, "Add() wrong result length");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	public void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Two point subtraction test
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(5, 7, 9);
		Vector vr = p2.subtract(p1);
		assertEquals(new Vector(4, 5, 6), vr, "Subtract() wrong result length");
		// =============== Boundary Values Tests ==================
		// TC11:Test that scaling two vectors does not produce a zero vector
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
				"Subtract() should throw an exception, but it failed");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	public void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Testing the distance between two points
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(5, 7, 9);
		double dr = p1.distance(p2);
		assertEquals(Math.sqrt(77), dr, 0.0000001, "Distance() wrong result");

		// =============== Boundary Values Tests ==================
		// TC02:Distance between a point from itself
		assertEquals(0, p1.distance(p1), 0.0000001, "Distance() wrong result");

	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	public void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01:Testing the distance squared between two points
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(5, 7, 9);
		double dr = p1.distanceSquared(p2);
		assertEquals(0, p1.distance(p1), 0.0000001, "Distance() wrong result");
		// =============== Boundary Values Tests ==================
		// TC02:Distance between a point from itself
		assertEquals(77, dr, 0.0000001, "DistanceSquared() wrong result");
	}
}