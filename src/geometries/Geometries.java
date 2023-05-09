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
 * @author נעמי
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometriesList;

	public Geometries() {
		super();
		geometriesList = new LinkedList<Intersectable>();
	}

	public Geometries(Intersectable... geometries) {
		geometriesList = new LinkedList<Intersectable>(Arrays.asList(geometries));
	}

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
