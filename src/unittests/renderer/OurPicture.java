/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;
import lighting.*;

import org.junit.jupiter.api.Test;

/**
 * @author נעמי
 *
 */
class OurPicture {

	private Scene scene = new Scene("Test scene")//
			.setAmbientLight(new AmbientLight(new Color(255, 255, 255), //
					new Double3(1, 1, 1))) //
			.setBackground(new Color(java.awt.Color.gray));

	/**
	 * 
	 */
	@Test
	public void OurPicture() {
		try {
			Point a=new Point(140, 0, 0);
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setVPDistance(1z).setVPSize(200,200);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.15)));
		scene.geometries.add( //
				 //
				
				new Triangle(a, new Point(160, 140,0), new Point(120, 40, 0))
				.setEmission(new Color(0 ,0,255)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6)),
				new Triangle(a, new Point(120, 40,0), new Point(100, 0, 0))
				.setEmission(new Color(0 ,0,204)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6)),
				new Triangle(a, new Point(100, 0,0), new Point(120, -40, 0))
				.setEmission(new Color(102, 0 ,153)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6)),
				new Triangle(a, new Point(120, -40, 0), new Point(160, -40, 0))
				.setEmission(new Color(255,153,51)).setMaterial(new Material().setKd(0).setKs(0.8).setnShininess(6))
				
				
				);
		ImageWriter imageWriter = new ImageWriter("status", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
		}
		
		
//		/* scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(1.4, 0.2, 0), new Vector(-1, 1, 4)));
//	                scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(1.4,0.2,0), new Vector(-1, 1, 4))
//	                    .setKc(1).setKl(4E-4).setKq(2E-5));
//		camera.setImageWriter(new ImageWriter("status", 600, 600)) 
//		.setRayTracer(new RayTracerBasic(scene)); 
//camera.renderImage(); //
//camera.writeToImage();
//}*/
	catch(Exception ex) {}
		
	}

}