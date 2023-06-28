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
	 * Attenuation coefficient of transparency
	 */
	public Double3 kT = Double3.ZERO;
	/**
	 * attenuation coefficient of reflection
	 */
	public Double3 kR = Double3.ZERO;
	 // public double kG=0.0;
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

	/**
	 * A set function for the kD
	 * 
	 * @param d the kD to set-Double3
	 * @return the object - builder
	 */
	public Material setkD(Double3 d) {
		this.kD = d;
		return this;
	}

	/**
	 * A set function for the kT
	 * 
	 * @param kT the kT to set-Double3
	 * @return the current material
	 */
	public Material setkT(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * A set function for the kT
	 * 
	 * @param kT the kT to set-double
	 * @return the current material
	 */
	public Material setkT(double kT) {
		this.kT = new Double3(kT);
		return this;
	}

	/**
	 * A set function for the kR
	 * 
	 * @param kR the kR to set-Double3
	 * @return the current material
	 */
	public Material setkR(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * A set function for the kR
	 * 
	 * @param kR the kR to set-double
	 * @return the current material
	 */
	public Material setkR(double kR) {
		this.kR = new Double3(kR);
		return this;
	}
//	 /**
//     * Builder patterns setter for field kG
//     * @param kG parameter for kG
//     * @return Material object
//     */
//    public Material setKG(double kG) {
//        this.kG = kG;
//        return this;
//    }

//    /**
//     * Builder patterns getter for field kG
//     * @return Double3 object
//     */
//    public double getKG() {
//        return kG;
//    }
}
