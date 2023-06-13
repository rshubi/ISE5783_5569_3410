package unittests.renderer;

import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

class myFlower {

	private Scene scene = new Scene(" scene");

	@Test
	/**
	 * @Test that create a flower picture
	 */
	public void test() {

		Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0));
		camera.setVPDistance(1000).setVPSize(200, 200);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
		scene.geometries.add(

				new Sphere(25d, new Point(0, 0, 0)).setEmission(new Color(0, 0, 0))
						.setMaterial(new Material().setKd(0.5).setkT(0).setKs(0.2).setnShininess(60)),

				new Sphere(20d, new Point(0, 40, 0)).setEmission(new Color(102, 0, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(0, -40, 0)).setEmission(new Color(255, 255, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(40, 0, 0)).setEmission(new Color(51, 153, 255))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(-40, 0, 0)).setEmission(new Color(255, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(-25, 25, 0)).setEmission(new Color(102, 255, 102))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(25, -25, 0)).setEmission(new Color(255, 255, 255))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(-25, -25, 0)).setEmission(new Color(255, 102, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.2).setkT(0).setnShininess(60)),

				new Sphere(20d, new Point(25, 25, 0)).setEmission(new Color(255, 102, 102))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setkT(0).setnShininess(60)),

				new Plane(new Point(200, -1000, 0), new Point(30, 1000, 0), new Point(0, 0, 100))
						.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setnShininess(100).setkR(1)),

				new Triangle(new Point(0, 55, 0), new Point(30, 200, 0), new Point(-30, 200, 0))
						.setEmission(new Color(0, 153, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setkT(1).setnShininess(60)),

				new Triangle(new Point(-68, 68, 0), new Point(-8, 98, 0), new Point(-14, 127, 0))
						.setEmission(new Color(0, 153, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setkT(1).setnShininess(60)),

				new Triangle(new Point(68, 68, 0), new Point(14, 127, 0), new Point(8, 98, 0))
						.setEmission(new Color(0, 153, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setkT(1).setnShininess(60))

		// new Sphere(20d, new Point(-68,-64,0)).setEmission(new Color(255, 255, 0))
		// .setMaterial(new Material().setKd(0.5).setKs(0.5).setkT(1).setnShininess(60))
		);
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(20, 30, 0), new Vector(-1, 1, 4)).setKc(1)
				.setKl(4E-4).setKq(2E-5));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, -40, -20), new Vector(-2, 3, 3)).setKc(1)
				.setKl(4E-4).setKq(2E-5));
		camera.setImageWriter(new ImageWriter("flower", 600, 600)).setRayTracer(new RayTracerBasic(scene));
		camera.renderImage(); //
		camera.writeToImage();
	}
}
