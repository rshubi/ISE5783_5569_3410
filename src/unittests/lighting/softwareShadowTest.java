
package unittests.lighting;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * @author Maayan &amp; Renana 
 *
 */

class softwareShadowTest {

	private Scene scene = new Scene("Test scene");

	@Test
	public void testSoftShadows() {
 
			Camera camera = new Camera(new Point(0, 0, 800), new Vector(0, 0, -1), new Vector(0, 1, 0));
			camera.setVPDistance(1000).setVPSize(200, 200).setRayTracer(new RayTracerBasic(scene));
			scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)); /* scene.setSoftShade(5); */
			scene.geometries.add(

					new Sphere(15d, new Point(0, 0, 0)).setEmission(new Color(BLACK))
							.setMaterial(new Material().setKd(0).setKs(0).setnShininess(30)),
					new Sphere(10d, new Point(0, -22, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(0, 22, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(22, 0, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(-22, 0, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(16, 16, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(-16, 16, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(16, -16, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Sphere(10d, new Point(-16, -16, 0)).setEmission(new Color(YELLOW))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)),
					new Plane(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
							.setMaterial(new Material().setKs(0.7).setnShininess(10)));

			scene.lights
					.add(new PointLight(new Color(700, 400, 400), new Point(40, 40, 115) /* ,new Vector(-1, -1, -4) */)
							.setKl(4E-4).setKq(2E-5));
			scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(20, 30, 0), new Vector(-1, 1, 4))
					.setKc(1).setKl(4E-4).setKq(2E-5));
			scene.lights.add(new DirectionalLight(new Color(700, 400, 400), new Vector(-2, 3, 3)));

			camera.setImageWriter(new ImageWriter("flowerWithSoftShadows", 600, 600))
					.setRayTracer(new RayTracerBasic(scene));
			camera.renderImage();
			camera.writeToImage();

	}

}
