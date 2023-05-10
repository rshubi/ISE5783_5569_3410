/**
 * 
 */
package unittests.renderer;

import primitives.Point;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable;
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
public class CameraRayIntersectionTests {
	/**
	 * 
	 * @param camera
	 * @param pixelX
	 * @param pixelY
	 * @return
	 */
	private List<Ray> CreateRays(Camera camera, int pixelX, int pixelY){
		List<Ray> raysFromCamera = new ArrayList<Ray>();
		for (int i = 0; i < pixelX; i++)
		{
			for (int j = 0; j < pixelY; j++)
			{
				try{
					raysFromCamera.add(camera.constructRay(pixelX, pixelY, j, i));
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					fail("There can be no zero rays");
				}
			}
		}
		return raysFromCamera;
	}
	
	/**
	 * 
	 * @param camera
	 * @param geomety
	 * @return
	 */
	private List<Point> findIntersectionPointsWithCamera(Camera camera, Intersectable geomety){
		
		List<Ray> raysList = CreateRays(camera,3,3);
		
		List<Point> intersectionPoints = new ArrayList<Point>();
		for (Ray ray : raysList) 
		{
			List<Point> temp;
			
			temp = geomety.findIntersections(ray);
		
			if (temp != null)
			{
				intersectionPoints.addAll(temp);
			}
		}
		if(intersectionPoints.size() == 0)
			return null;
		return intersectionPoints;
	}
	static final Point ZERO_POINT = new Point(0, 0, 0);

	public void constructRaySphere() {
		// TC01: 2 intersection points
		Sphere sphere = new Sphere(1, new Point(0, 0, -3));
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
		assertEquals(2,findIntersectionPointsWithCamera(camera, sphere).size(), "The count of intersections are not correct");

		// TC02:18 intersection points
		sphere = new Sphere(2.5, new Point(0, 0, -2.5));
		Camera camera1 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
		assertEquals(18, findIntersectionPointsWithCamera(camera1, sphere).size(), "The count of intersections are not correct");

		// TC03:10 intersection points
		sphere = new Sphere(2, new Point(0, 0, -2));
		// same camera like tc02
		assertEquals(10, findIntersectionPointsWithCamera(camera1, sphere).size(), "The count of intersections are not correct");

		// TC04:9 intersection points
		sphere = new Sphere(4, ZERO_POINT);
		Camera camera2 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
		assertEquals(9, findIntersectionPointsWithCamera(camera2, sphere).size(), "The count of intersections are not correct");

		// TC05:0 intersection points
		sphere = new Sphere(0.5, new Point(0, 0, 1));
		Camera camera3 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
		assertNull(findIntersectionPointsWithCamera(camera3, sphere), "The count of intersections are not correct");
	}

	public void constructRayPlane() {
		// TC01:9 intersection points
		//2-3
		Plane plane = new Plane(new Point(0, 3, 0), new Vector(0, 1, 0));
		Camera camera4 = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1)).setVPDistance(1).setVPSize(3,3);
		assertEquals(9, findIntersectionPointsWithCamera(camera4, plane).size(), "The count of intersections are not correct");

		// TC02:9 intersection points
		plane = new Plane(new Point(2, 0, 0), new Vector(0.7,1.8,-0.4));
		// same camera
		assertEquals(9, findIntersectionPointsWithCamera(camera4, plane).size(), "The count of intersections are not correct");

		// TC03:6 intersection points
		plane = new Plane(new Point(2, 0, 0), new Vector(1.2, 1.2, 0));
		// same camera
		assertEquals(6, findIntersectionPointsWithCamera(camera4, plane).size(), "The count of intersections are not correct");

	}

	public void constructRayTriangle() {
		// TC01:1 intersection points
		Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		Camera camera5 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3,3);
		assertEquals(1, findIntersectionPointsWithCamera(camera5, triangle).size(), "The count of intersections are not correct");

		// TC02:2 intersection points
		triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		// same camera
		assertEquals(2, findIntersectionPointsWithCamera(camera5, triangle).size(), "The count of intersections are not correct");

	}

}
