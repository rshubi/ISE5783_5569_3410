package scene;

import java.util.List;
import java.util.LinkedList;
import geometries.Geometries;
import primitives.Color;
import lighting.*;

/**
 * A class of type PDF that represents a scene
 * 
 * @author Maayan &amp; Renana
 */
public class Scene {
	/** the name of the scene */
	public final String name;
	/** default background color, black color */
	public Color background = Color.BLACK;
	/** Ambient lighting is initialized to none */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/** The 3D model is initialized to empty */
	public Geometries geometries = new Geometries();
	/** A list of light sources is initialized to an empty list */
	public List<LightSource> lights = new LinkedList<>();
	private int softShade = 10;
	/**
	 * Constructor for Scene
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * A set function for Background
	 * 
	 * @param background the background color of the scene
	 * @return this for builder pattern
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * set function for AmbientLight
	 * 
	 * @param ambientLight the ambientLight of the scene
	 * @return this for builder pattern
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}
//
	/**
	 * set function for Geometries
	 * 
	 * @param geometries the 3D model
	 * @return this for builder pattern
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * set function for Light
	 * 
	 * @param lights list of LightSource
	 * @return this for builder pattern
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}
	public Scene setSoftShade(int other) {
        this.softShade = other;//
        return this;
    }
	
	public int getSoftShade()
	{
		return softShade;
	}
}
