/**
 * 
 */
package geometries;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import geometries.Intersectable;
import primitives.Point;
import primitives.Ray;

/**
 * Geometries class
 *  @author Maayan & Renana 
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometriesList;
	/**
	 * The constructor function 
	 */
	public Geometries() {
		super();
		geometriesList = new LinkedList<Intersectable>();
	}
	/**
	 * The constructor function gets
	 * 
	 * @param Intersectable geometries collection of geometries bodies
	 */
	public Geometries(Intersectable... geometries) {
		geometriesList = new LinkedList<Intersectable>(Arrays.asList(geometries));
	}
	/**
	 * The function receives a collection of geometries and adds them to the list of geometries
	 * @param geometries collection of geometries to add
	 */

	public void add(Intersectable... geometries) {
		if (geometries != null) {
			geometriesList.addAll(Arrays.asList(geometries));
		}

	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> temp = new LinkedList<Point>();
		for (Intersectable intersectable : geometriesList) {
			List<Point> intersection = intersectable.findIntersections(ray);
			if (intersection != null)
				temp.addAll(intersection);
		}

		if (temp.isEmpty())
			return null;
		return temp;
	}

	/**
	 * A get function
	 * @return the geometriesList A list of the geometric bodies
	 */
	public List<Intersectable> getGeometriesList() {
		return geometriesList;
	}

}
