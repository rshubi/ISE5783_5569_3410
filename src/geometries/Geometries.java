package geometries;

import java.util.List;
import java.util.LinkedList;
import primitives.Ray;

/**
 * A class for a union of geometric bodies
 * 
 * @author Maayan &amp; Renana
 *
 */
public class Geometries extends Intersectable {
	private final List<Intersectable> geometriesList = new LinkedList<>();

	/**
	 * The default constructor
	 */
	public Geometries() {
	}

	/**
	 * The constructor function gets list of geometries
	 * 
	 * @param geometries as many geometries as you desire
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * The operation of adding a body to the collection of existing bodies
	 * 
	 * @param geometries as many geometries as you desire to add
	 */
	public void add(Intersectable... geometries) {
		if (geometries != null)
			geometriesList.addAll(List.of(geometries));
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> result = null;
		for (Intersectable intersectable : geometriesList) {
			var intersection = intersectable.findGeoIntersections(ray);
			if (intersection != null) {
				if (result == null)
					result = new LinkedList<>();
				result.addAll(intersection);
			}
		}
		return result;
	}

	/**
	 * Getter for the list
	 * 
	 * @return the list geometries
	 */
	public List<Intersectable> getGeometriesList() {
		return geometriesList;
	}

}
