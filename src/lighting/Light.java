/**
 * 
 */
package lighting;

import primitives.Color;

/**
 * abstract class for Light.
 * 
 * @author Maayan &amp; Renana
 *
 */
abstract class Light { 
	/** original intensity of the light I<sub>0</sub> */
	protected final Color intensity;

	/**
	 * constructor for light
	 * 
	 * @param intensity the light intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * A get function to return the light Intensity
	 * 
	 * @return the light intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
