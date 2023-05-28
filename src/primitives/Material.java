package primitives;

/**
 * A class of the material
 * 
 * @author Maayan &amp; Renana
 */
public class Material {
	/**
	 * The shine of the material-initialized to 0 (default value)
	 */
	public int nShininess = 0;
	/**
	 * The attenuation coefficient kD- initialized to 0 (default value)
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * The attenuation coefficient kS- initialized to 0 (default value)
	 */
	public Double3 kS = Double3.ZERO;

	/**
	 * A set function for the shininess of the material
	 * 
	 * @param nShininess1 the nShininess to set
	 * @return the object - builder
	 */
	public Material setnShininess(int nShininess1) {
		this.nShininess = nShininess1;
		return this;
	}

	/**
	 * A set function for the kD
	 * 
	 * @param kD1 the kD to set-double
	 * @return the object - builder
	 */
	public Material setKd(double kD1) {
		kD = new Double3(kD1);
		return this;
	}

	/**
	 * A set function for the kS
	 * 
	 * @param kS1 the kS to set-double
	 * @return the object - builder
	 */
	public Material setKs(double kS1) {
		kS = new Double3(kS1);
		return this;
	}

	/**
	 * A set function for the kD
	 * 
	 * @param kD1 the kD to set-Double3
	 * @return the object - builder
	 */
	public Material setKd(Double3 kD1) {
		kD = kD1;
		return this;
	}

	/**
	 * A set function for the kS
	 * 
	 * @param kS1 the kS to set-Double3
	 * @return the object - builder
	 */
	public Material setKs(Double3 kS1) {
		kS = kS1;
		return this;
	}

}
