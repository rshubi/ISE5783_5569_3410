package unittests.renderer;

import primitives.Point;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import renderer.Camera;

/**
 * Tests for integration tests between creating rays from a camera and
 * Calculation of sections of a beam with geometric bodies
 * 
 * @author Maayan &amp; Renana
 *
 */
public class CameraRayIntersectionTests {
	/**
	 * A function that produces rays that exit the camera and pass through the
	 * center of each pixel in the view plane.
	 * 
	 * @param camera A camera from which the rays go out towards the view plane
	 * @param pixelX The number of rows of the view plane
	 * @param pixelY The number of columns of the view plane
	 * @return A list of rays that exit the camera and pass through the center of
	 *         each pixel in the view plane.
	 */
	private List<Ray> createRays(Camera camera, int pixelX, int pixelY) {
		List<Ray> raysFromCamera = new LinkedList<Ray>();
		for (int i = 0; i < pixelX; i++) {
			for (int j = 0; j < pixelY; j++) {
				try {
					raysFromCamera.add(camera.constructRay(pixelX, pixelY, j, i));
				} catch (Exception e) {
					fail("There can be no zero rays");
				}
			}
		}
		return raysFromCamera;
	}

	/**
	 * A function that shoots rays from the camera through the center of each pixel
	 * and returns a list of intersection points with the geometric body
	 * 
	 * @param camera  Shoots rays through the center of each pixel towards the
	 *                geometric body
	 * @param geomety A geometric body that checks how many intersection points it
	 *                has with the rays that the camera shoots
	 * @return a list of intersection points with the geometric body
	 */
	private int findIntersectionPointsWithCamera(Camera camera, Intersectable geomety) {
		List<Ray> raysList = createRays(camera, 3, 3);
		int count = 0;
		for (Ray ray : raysList) {
			List<GeoPoint> temp = geomety.findGeoIntersections(ray);
			if (temp != null)
				count += temp.size();
		}
		return count;
	}

	private static final Point ZERO_POINT = new Point(0, 0, 0);
/**
 * A test that checks the intersection points of the sphere with a ray 
 */
	@Test
	public void constructRaySphere() {
		// TC01: 2 intersection points between the camera and the sphere
		Sphere sphere = new Sphere(1, new Point(0, 0, -3));
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,
				3);
		assertEquals(2, findIntersectionPointsWithCamera(camera, sphere), "The count of intersections are not correct");

		// TC02:18 intersection points between the camera and the sphere
		sphere = new Sphere(2.5, new Point(0, 0, -2.5));
		Camera camera1 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);
		assertEquals(18, findIntersectionPointsWithCamera(camera1, sphere),
				"The count of intersections are not correct");

		// TC03:10 intersection points between the camera and the sphere
		sphere = new Sphere(2, new Point(0, 0, -2));
		assertEquals(10, findIntersectionPointsWithCamera(camera1, sphere),
				"The count of intersections are not correct");

		// TC04:9 intersection points between the camera and the sphere
		sphere = new Sphere(4, ZERO_POINT);
		Camera camera2 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,
				3);
		assertEquals(9, findIntersectionPointsWithCamera(camera2, sphere),
				"The count of intersections are not correct");

		// TC05: 0 intersection points between the camera and the sphere
		sphere = new Sphere(0.5, new Point(0, 0, 1));
		Camera camera3 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
				.setVPSize(3, 3);
		assertEquals(0, findIntersectionPointsWithCamera(camera3, sphere),
				"The count of intersections are not correct");
	}
	/**
	 * A test that checks the intersection points of the plane with a ray 
	 */
	@Test
	public void constructRayPlane() {
		// TC01:9 intersection points between the camera and the plane that the plane is
		// parallel to the viewing plane
		// 2-3
		Plane plane = new Plane(new Point(0, 3, 0), new Vector(0, 1, 0));
		Camera camera4 = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1)).setVPDistance(1).setVPSize(3,
				3);
		assertEquals(9, findIntersectionPointsWithCamera(camera4, plane), "The count of intersections are not correct");

		// TC02:9 intersection points between the camera and the plane that the plane is
		// not parallel to the viewing plane
		plane = new Plane(new Point(2, 0, 0), new Vector(0.7, 1.8, -0.4));
		assertEquals(9, findIntersectionPointsWithCamera(camera4, plane), "The count of intersections are not correct");

		// TC03:6 intersection points between the camera and the plane
		plane = new Plane(new Point(2, 0, 0), new Vector(1.2, 1.2, 0));
		assertEquals(6, findIntersectionPointsWithCamera(camera4, plane), "The count of intersections are not correct");

	}

	/**
	 * A test that checks the intersection points of the triangle with a ray 
	 */
	@Test
	public void constructRayTriangle() {
		// TC01:1 intersection points between the camera and the triangle
		Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		Camera camera5 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,
				3);
		assertEquals(1, findIntersectionPointsWithCamera(camera5, triangle),
				"The count of intersections are not correct");

		// TC02:2 intersection points between the camera and the triangle
		triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		assertEquals(2, findIntersectionPointsWithCamera(camera5, triangle),
				"The count of intersections are not correct");

	}

}
