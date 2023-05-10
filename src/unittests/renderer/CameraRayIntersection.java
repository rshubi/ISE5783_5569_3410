/**
 * 
 */
package unittests.renderer;

import primitives.Point;
import geometries.Plane;
import geometries.Triangle;
import primitives.Ray;
import geometries.Sphere;
import primitives.Vector;
import renderer.Camera;
import unittests.renderer.CameraTest;

/**
 * @author Maayan
 *
 */
public class CameraRayIntersection {
	static final Point ZERO_POINT = new Point(0, 0, 0);

	public void constructRaySphere() {
		// TC01: 2 intersection points
		Sphere sphere = new Sphere(1, new Point(0, 0, -3));
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);
		assertEquals(2, Camera.constructRay(camera, sphere).size(), "The count of intersections are not correct");

		// TC02:18 intersection points
		sphere = new Sphere(2.5, new Point(0, 0, -2.5));
		camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);
		assertEquals(18, constructRay(camera, sphere).size(), "The count of intersections are not correct");

		// TC03:10 intersection points
		sphere = new Sphere(2, new Point(0, 0, -2));
		// same camera like tc02
		assertEquals(10, constructRay(camera, sphere).size(), "The count of intersections are not correct");

		// TC04:9 intersection points
		sphere = new Sphere(4, ZERO_POINT);
		camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
		assertEquals(9, constructRay(camera, sphere).size(), "The count of intersections are not correct");

		// TC05:0 intersection points
		sphere = new Sphere(0.5, new Point(0, 0, 1));
		camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);
		assertNull(constructRay(camera, sphere), "The count of intersections are not correct");
	}

	public void constructRayPlane() {
		// TC01:9 intersection points
		Plane plane = new Plane(new Point(0, 2, 0), new Vector(0, 1, 0));
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1)).setVPDistance(1).setVPSize(3,
				3);
		assertEquals(9, constructRay(camera, plane).size(), "The count of intersections are not correct");

		// TC02:9 intersection points
		plane = new Plane(new Point(2, 0, 0), new Vector(1, 2, -0.5));
		// same camera
		assertEquals(9, constructRay(camera, plane).size(), "The count of intersections are not correct");

		// TC03:6 intersection points
		plane = new Plane(new Point(2, 0, 0), new Vector(1, 1, 0));
		// same camera
		assertEquals(6, constructRay(camera, plane).size(), "The count of intersections are not correct");

	}

	public void constructRayTriangle() {
		// TC01:1 intersection points
		Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
		assertEquals(1, constructRay(camera, triangle).size(), "The count of intersections are not correct");

		// TC02:2 intersection points
		triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		// same camera
		assertEquals(2, constructRay(camera, triangle).size(), "The count of intersections are not correct");

	}

}
