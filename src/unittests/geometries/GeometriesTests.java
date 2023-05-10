/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Plane;
import geometries.Geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import geometries.Sphere;

/**
 * Tests geometries class
 *  @author Maayan & Renana 
 *
 */
class GeometriesTests {

	/**
	 * Test method for
	 * {@link geometries.Geometries#add(geometries.Intersectable[])}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		//TC01:Checking adding a body to the list of bodies
		Triangle triangle = new Triangle(new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, 0, 5));
		Sphere sphere = new Sphere(1, new Point(5, 0, 0));
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

		Geometries collectionGeo = new Geometries(sphere, triangle, plane);

		assertEquals(3, collectionGeo.getGeometriesList().size(), "the length of the list is worng");
		Triangle t = new Triangle(new Point(0, 3, 0), new Point(3, 0, 0), new Point(0, 0, 3));
		collectionGeo.add(t);

		assertEquals(4, collectionGeo.getGeometriesList().size(), "the length of the list is worng");

	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Geometries collection = new Geometries();
		// ============ Equivalence Partitions Tests ==============
		// TC01:Some shapes are cut - triangle and plane cut
				assertEquals(2,
						collection
								.findIntersections(
										new Ray(new Point(0, 0, 2), new Vector(2.002120262832886, 0.414405628871247, -2)))
								.size(),
						"wrong number of intersactions");
		// =============== Boundary Values Tests ==================
		// TC11:Empty body collection
		assertEquals(0, collection.getGeometriesList().size(), "Error:An empty body collection ");

		// TC12:No cut shape
		Sphere sphere = new Sphere(1, new Point(0, 1, 0));
		Triangle triangle = new Triangle(new Point(3, 0, 0), new Point(0, 3, 0), new Point(0, 0, 0));
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 0));

		collection.add(sphere, triangle, plane);

		assertNull(collection.findIntersections(new Ray(new Point(0, 0, 1.5), new Vector(0, 0, 3))),
				"No cut shape must return 0");

		// TC13:Only one shape is cut-the plane cut
		assertEquals(1, collection.findIntersections(new Ray(new Point(0, 0, 1.5), new Vector(-2, 0, -1.5))).size(),
				"wrong number of intersactions");
		
		// TC14:All shapes are cut
		assertEquals(4,
				collection.findIntersections(new Ray(new Point(0, 0, 1),
						new Vector(0.817794869001868, 1.560276981288437, -0.868465759315167))).size(),
				"wrong number of intersactions");

	}

}
