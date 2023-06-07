package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;


class myGameTable {

	private Scene scene = new Scene(" scene");

	@Test
	public void test() {
		try {
		Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0));
		camera.setVPDistance(1000).setVPSize(200,200);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
		scene.geometries.add( //
				new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
				.setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6)), //
				new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
				.setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(60)),
				new Sphere(10,new Point(0, 0, 115)).setEmission(new Color(java.awt.Color.BLUE))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30).setkT(0).setkR(0.2)), 
				new Sphere(10,new Point(50, 50, 115)).setEmission(new Color(java.awt.Color.BLACK))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30)), 
				new Sphere(10,new Point(-50, -50, 115)).setEmission(new Color(java.awt.Color.RED))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30)),
				new Sphere(10,new Point(50, -50, 115)).setEmission(new Color(java.awt.Color.BLUE))
				.setMaterial(new Material().setKd(0.2).setKs(0.2).setnShininess(30).setkT(0.6).setkR(0)),
				new Triangle(new Point(-50, 50, 115), new Point(150, 150, 135), new Point(200, 200, 130))
				.setEmission(new Color(218,165,32)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(60)) 
						
				);
		scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(40,-40,-160), new Vector(-1, 1, 4))
				.setKc(1).setKl(4E-4).setKq(2E-5));
		scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(300, 30,0), new Vector(-2, 3, 3))
				.setKc(1).setKl(4E-4).setKq(2E-5));
		camera.setImageWriter(new ImageWriter("bil", 600, 600)) 
		.setRayTracer(new RayTracerBasic(scene)); 
camera.renderImage(); //
camera.writeToImage();
		}
		catch(Exception ex) {}
		
	}
}













/**
 * 
 */
/*package unittests.renderer;




import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

*//**
 * @author Shilat and Avigail
 *
 *//*
class EffectsTests {
	private Scene scene = new Scene(" scene");

	@Test
	public void MyPicture() {
		try {
		Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0));
		camera.setVPDistance(1000).setVPSize(200,200);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
		scene.geometries.add( //
				new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
				.setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6)), //
				new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
				.setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(60)),
				new Sphere(10,new Point(0, 0, 115)).setEmission(new Color(java.awt.Color.BLUE))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30).setkT(0).setkR(0.2)), 
				new Sphere(10,new Point(50, 50, 115)).setEmission(new Color(java.awt.Color.BLACK))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30)), 
				new Sphere(10,new Point(-50, -50, 115)).setEmission(new Color(java.awt.Color.RED))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30)),
				new Sphere(10,new Point(50, -50, 115)).setEmission(new Color(java.awt.Color.BLUE))
				.setMaterial(new Material().setKd(0.2).setKs(0.2).setnShininess(30).setkT(0.6).setkR(0)),
				new Triangle(new Point(-50, 50, 115), new Point(150, 150, 135), new Point(200, 200, 130))
				.setEmission(new Color(218,165,32)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(60)) 
						
				);
		scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(40,-40,-160), new Vector(-1, 1, 4))
				.setKc(1).setKl(4E-4).setKq(2E-5));
		scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(300, 30,0), new Vector(-2, 3, 3))
				.setKc(1).setKl(4E-4).setKq(2E-5));
		camera.setImageWriter(new ImageWriter("bil", 600, 600)) 
		.setRayTracer(new RayTracerBasic(scene)); 
camera.renderImage(); //
camera.writeToImage();
		}
		catch(Exception ex) {}
		
	}
}*/