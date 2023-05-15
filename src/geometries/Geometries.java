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
 * A class for a union of geometric bodies
 * 
 * @author Maayan & Renana
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometriesList;

	/**
	 * The constructor function gets
	 */
	public Geometries() {
		super();
		geometriesList = new LinkedList<Intersectable>();
	}

	/**
	 * The constructor function gets
	 * 
	 * @param Intersectable geometries
	 */
	public Geometries(Intersectable... geometries) {
		geometriesList = new LinkedList<Intersectable>(Arrays.asList(geometries));
	}

	/**
	 * The operation of adding a body to the collection of existing bodies
	 * 
	 * @param Intersectable geometries
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
	 * @return the geometriesList
	 */
	public List<Intersectable> getGeometriesList() {
		return geometriesList;
	}

}
