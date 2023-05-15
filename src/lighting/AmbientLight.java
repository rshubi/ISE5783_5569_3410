package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * A class for ambient lighting
 * 
 * @author Maayan & Renana
 *
 */
public class AmbientLight {
	private Color Ia;
	private Double3 Ka;
	private Color intensity;
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * 
	 * A constructor that save the intensity=Ia*Ka
	 * 
	 * @param Ia Color value
	 * @param Ka Double3 value
	 */
	public AmbientLight(Color Ia, Double3 Ka) {
		intensity = Ia.scale(Ka);

	}

	/**
	 * A constructor that save the intensity=Ia*Ka
	 * 
	 * @param Ia Color value
	 * @param Ka double value
	 */
	public AmbientLight(Color Ia, double Ka) {
		intensity = Ia.scale(Ka);
	}

	/**
	 * A function that returns the value of the ambient lighting intensity of type
	 * Color
	 * 
	 * @return Ambient lighting intensity value
	 */
	public Color getIntensity() {
		return intensity;
	}

}
