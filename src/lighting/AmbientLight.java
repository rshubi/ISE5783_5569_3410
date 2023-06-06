package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * A class for ambient lighting
 * 
 * @author Maayan &amp; Renana
 *
 */
public class AmbientLight extends Light {
	/** The constant of absence of ambient lighting */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * 
	 * A constructor that save the intensity=Ia*Ka
	 * 
	 * @param iA Intensity of the light
	 * @param kA Double3 value of attenuation by RGB
	 */
	public AmbientLight(Color iA, Double3 kA) {
		super(iA.scale(kA));
	}

	/**
	 * A constructor that save the intensity=Ia*Ka
	 * 
	 * @param iA intensity of the light
	 * @param kA value of attenuation factor
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));
	}

}
