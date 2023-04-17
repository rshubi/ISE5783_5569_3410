/**
 * 
 */
package unittests;
import primitives.Point;
import geometries.Tube;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Tube class
 * @author Maayan & Renana
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		//TC01:Test that checks whether the normal of the tube is correct
				Ray r= new Ray(new Point(0,0,0),new Vector(0,1,0));
				Tube t= new Tube(r,1);
				Point p = new Point(1,0,1);
				Vector n= t.getNormal(p);
				assertTrue(isZero(r.getDir().dotProduct(n)),"normal to tube is incorrect");
				// =============== Boundary Values Tests ==================
		        // TC11:Test that checks whether it is possible to calculate a normal for a tube with a radius of 0
				 assertThrows(IllegalArgumentException.class, () -> new Tube(r,0).getNormal(p),
						 "GetNormal() should throw an exception, but it failed");
				/**
				 *  try 
		        {
		        	new Tube(r,0).getNormal(p);
		            fail("GetNormal() should throw an exception, but it failed");
		        } catch (Exception e) {}
				 */
		       
			}
	}

}
