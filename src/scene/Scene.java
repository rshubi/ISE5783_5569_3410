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
 * @author Maayan &amp; Renana
 */
public class Scene {
	/** the name of the scene */
	public final String name;
	/** default background color, black color*/
	public Color background = Color.BLACK;
	/** Ambient lighting is initialized to none */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/** The 3D model is initialized to empty */
	public Geometries geometries = new Geometries();

	/**
	 * Constructor function
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
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
