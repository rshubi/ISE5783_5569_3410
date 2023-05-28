package geometries;

import primitives.Ray;
import java.util.List;
import primitives.Point;

/**
 * An interface for calculating the intersection points between the beam and the
 * geometric body
 * 
 * @author Maayan &amp; Renana
 * 
 */
public abstract class Intersectable {

	/**
	 * A function that calculates the intersection points of the ray in a certain
	 * body
	 * 
	 * @param ray A ray that cuts through the body
	 * @return a list of the points that the ray intersects the body in case there
	 *         is no intersection point the function will return null
	 */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * A function that calculates the intersection points of the ray in a certain
	 * body
	 * 
	 * @param ray the ray that cuts through the body
	 * @return a list of the geoPoints that the ray intersects the body in case
	 *         there is no intersection point the function will return null
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Auxiliary function for calculating the intersection points of the beam in a
	 * certain body
	 * 
	 * @param ray the ray that cuts through the body
	 * @return List of GeoPoints that the ray intersect geometry body
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

	/**
	 * Static Internal Auxiliary Department (as a completely passive data structure
	 * - PDS)
	 */
	public static class GeoPoint {
		/** geometry body */
		public Geometry geometry;
		/** point on the geometry body */
		public Point point;

		/**
		 * constructor for geo point
		 * 
		 * @param geo Geometry
		 * @param p   Point
		 */
		public GeoPoint(Geometry geo, Point p) {
			this.geometry = geo;
			this.point = p;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint other = (GeoPoint) obj;
			return this.geometry.equals(other.geometry) && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
		}
	}
}
