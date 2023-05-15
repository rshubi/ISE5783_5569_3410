/**
 * 
 */
package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * A class of type PDF that represents a scene
 * 
 * @author Maayan & Renana
 */
public class Scene {
	public String name;
	public Color background;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();

	/**
	 * Constructor function
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
		background = Color.BLACK;
	}

	/**
	 * set function
	 * 
	 * @param background the background color of the scene
	 * @return this for builder pattern
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * set function
	 * 
	 * @param ambientLight the ambientLight of the scene
	 * @return this for builder pattern
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;

	}

	/**
	 * set function
	 * 
	 * @param geometries the 3D model
	 * @return this for builder pattern
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
}
