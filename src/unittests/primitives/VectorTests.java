/**
 * 
 */
package unittests.primitives;

import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Maayan & Renana
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.add(v2);
		// TC01:Test that the connection of two vectors is correct
		assertEquals(new Vector(1, 5, 1), vr, "Add() wrong result");
		// =============== Boundary Values Tests ==================

		Vector v3 = new Vector(-1, -2, -3);
		// TC11:Checking that adding two vectors does not produce a zero vector
		assertThrows(IllegalArgumentException.class, () -> v1.add(v3),
				"Add() should throw an exception, but it failed");

	}

	/**
	 * Test method for {@link primitives.Vector#Sub()(primitives.Vector.Point)}.
	 */
	@Test
	void testSubtract() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.subtract(v2);
		// TC01:Test that the connection of two vectors is correct
		assertEquals(new Vector(1, -1, 5), vr, "Sub() wrong result");
		// =============== Boundary Values Tests ==================

		// TC11:Checking that adding two vectors does not produce a zero vector
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
				"Add() should throw an exception, but it failed");

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector vr = v1.scale(2);
		// TC01:Test that scaling between two vectors is correct
		assertEquals(new Vector(2, 4, 6), vr, "Scale() wrong result");
		// =============== Boundary Values Tests ==================
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"Scale() should throw an exception, but it failed");

	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);
		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
		// TC02: Test cross-product result orthogonality to its operands
		assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
		assertEquals(0, vr.dotProduct(v2), "crossProduct() result is not orthogonal to 1st operand");
		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-product of co-lined vectors
		Vector v3 = new Vector(-2, -4, -6);
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
				"crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		// TC01:Test that checks whether the calculation of the length squared of the
		// vector is correct
		assertEquals(14, v1.lengthSquared(), "ERROR: LengthSquared() wrong value");
		// =============== Boundary Values Tests ==================
		// TC11:Test that checks whether it is possible to calculate the length squared
		// of the zero vector
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).lengthSquared(),
				"LengthSquared() should throw an exception, but it failed");

	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(0, 3, 4);
		// TC01:Test that checks whether the calculation of the length of the vector is
		// correct
		assertEquals(5, v1.length(), "ERROR: Length() wrong value");
		// =============== Boundary Values Tests ==================
		// TC11:Test that checks whether it is possible to calculate the length of the
		// zero vector
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).length(),
				"Length() wrong result length");

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		// TC01:test that checks whether the normalization operation of a vector is
		// correct
		assertEquals(1, v1.normalize().length(), "ERROR: Normalize() wrong value");
		// =============== Boundary Values Tests ==================
		// TC11:A test that checks whether the zero vector can be normalized
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).normalize().length(),
				"Normalize() wrong result length");

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 2, 2);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(4, 5, 6);
		Vector v3 = new Vector(0, -2, 2);
		// TC01:Test that tests the calculation of a scalar product between orthogonal
		// vectors
		assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");
		// TC02:Test that checks that the calculation of a scalar product is correct
		assertEquals(26, v1.dotProduct(v2), "ERROR: DotProduct() wrong value");
		// =============== Boundary Values Tests ==================
		// TC11:test that checks whether it is possible to calculate a scalar product
		// with the zero vector
		assertThrows(IllegalArgumentException.class, () -> v1.dotProduct(new Vector(0, 0, 0)),
				"ERROR: DotProduct() wrong value");

	}

}
