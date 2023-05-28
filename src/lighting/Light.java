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
	private Color intensity;

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
