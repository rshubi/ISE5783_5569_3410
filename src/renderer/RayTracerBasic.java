




/**
 *
 */
package renderer;

import primitives.*;

import scene.Scene;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import lighting.PointLight;

import static primitives.Util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

/**
 * A class inherits from an abstract class (RayTracerBase)
 *
 * @author Maayan &amp; Renana
 */
public class RayTracerBasic extends RayTracerBase {
/**
* DELTA static field for ray head offset size for shading rays
*/
private static final double DELTA = 0.1;
/**
* Constants for stopping conditions in the recursion of
* transparencies/reflections
*/
private static final int MAX_CALC_COLOR_LEVEL = 10;
private static final double MIN_CALC_COLOR_K = 0.001;
private static final double INITIAL_K = 1.0;

/**
* A constructor that invokes the constructor of the parent class
* (RayTracerBase)
*
* @param scene scene
*/
public RayTracerBasic(Scene scene) {
super(scene);
}

/**
* A function that looks for intersections between the ray and the 3D model of
* the scene
*
* @param ray the ray for trace
* @return the intersections between the rays and the 3D model, or the
*         background color if there are no intersection points
*/
@Override
public Color traceRay(Ray ray,int numOfRays) {
if (ray == null)
return scene.background;
GeoPoint closestPoint = findClosestIntersection(ray);
return closestPoint == null ? scene.background : calcColor(closestPoint, ray, numOfRays);

}

@Override
   public Color traceRays(List<Ray> rays,int numOfRays) {

       Color color = Color.BLACK;
       for (Ray ray : rays) {
           color = color.add(traceRay(ray,numOfRays));
       }
       return color.reduce(rays.size());

   }
/**
* A function that calculates the fill lighting color
*
* @param intersection a point on the scene
* @param ray          The ray that intersects at the point
* @return the color of lighting fill or environment of the scene
*/

private Color calcColor(GeoPoint intersection, Ray ray,int numOfRays) {
return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K),numOfRays)
.add(scene.ambientLight.getIntensity());

}

/**
* A function for checking partial shading
*
* @param gp    A geometric body that the point is on
* @param light The lighting of the scene
* @param l     opposite direction vector of light source
* @param n     value vector
* @return 1 if the point on the body has no shading and 0 if there is shading
*         or number to represent partial shading
*/
 

private Double3 transparency(GeoPoint geoPoint, Vector l, Vector n, LightSource light, double nv) {

   {
       Vector lightDirection = l.scale(-1); // from point to light source
       Ray lightRay;

       if(nv<0){
           lightRay=new Ray(geoPoint.point,lightDirection, n);
       }
       else
           lightRay=new Ray(geoPoint.point,lightDirection,n.scale(-1));

       double lightDistance = light.getDistance(geoPoint.point);

       List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
       if (intersections == null) return Double3.ONE;

       Double3 ktr = Double3.ONE;//transparency initial

       for (GeoPoint gp : intersections) //move on all the geometries in the way
       {
           //if there are geometries between the point to the light- calculate the transparency
           //in order to know how much shadow there is on the point
           if (Util.alignZero(gp.point.distance(geoPoint.point)-lightDistance) <= 0)
           {
               ktr = ktr.product(gp.geometry.getMaterial().kT);//add this transparency to ktr
               if (ktr.lowerThan( MIN_CALC_COLOR_K)) //stop the loop- shadow "atum", black. very small transparency
                   return Double3.ZERO;
           }
       }
       return ktr;
   }


}





/**
* help function that calculate the specolar effect
*
* @param material Material value
* @param l        Vector value
* @param n        Vector value
* @param v        Vector value
* @param nl       double value
* @return the calculate the specolar effect
*/
private Color calcSpecular(Material material, Vector l, Vector n, Vector v, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n)); //ln=l*n
        Vector r = l.subtract(n.scale(2 * ln)); //r=l-2*(l*n)*n
        double vr = alignZero(v.dotProduct(r)); //vr=v*r
        double vrnsh = Math.pow(Math.max(0, -vr), material.nShininess); //vrnsh=max(0,-vr)^nshininess
        return lightIntensity.scale(material.kS.scale(vrnsh)); //Ks * (max(0, - v * r) ^ Nsh) * Il
    }

/**
* help function that calculate the difusive effect
*
* @param material Material value
* @param nl       double value
* @return double value for calcDiffusive
*/
private Color calcDiffusive(Material material, Vector l, Vector n, Color lightIntensity) {
       double ln = alignZero(Math.abs(l.dotProduct(n))); //ln=|l*n|
       return lightIntensity.scale(material.kD.scale(Math.abs(ln))); //Kd * |l * n| * Il
   }

/**
* A method of checking non-shading between a point and the light source
*
* @param gp    A geometric body that the point is on
* @param light The lighting of the scene
* @param l     opposite direction vector of light source
* @param n     value vector
* @return True if the point on the body has no shading and false if there is
*         shading
*/
@SuppressWarnings("unused")
private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n,double nv) {
Vector lightDirection = l.scale(-1);
Vector epsV = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
Point point = gp.point.add(epsV);
Ray lightRay = new Ray(point, lightDirection);
List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
if (intersections == null)
return true;
double lightDistance = light.getDistance(point);
for (GeoPoint geoPoint : intersections) {
if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0
&& gp.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K))
return false;
}
return true;
}

/**
* A function that calculates the refracted rays.
*
* @param nor0mal Vector value
* @param point   Point3D value
* @param ray     Ray value
* @return ray for refracted
*/
private Ray constructRefractedRay(GeoPoint gp, Vector vRay, Vector normal) {
return new Ray(gp.point, vRay, normal);
}

/**
* A function that find the most closet point to the ray
*
* @param ray Ray value
* @return the closet point
*/
private GeoPoint findClosestIntersection(Ray ray) {
List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
if (intersections == null)
return null;
return ray.findClosestGeoPoint(intersections);
}

/**
* A function that calculates the reflected rays.
*
* @param normal Vector value
* @param point  Point3D value
* @param ray    Ray value
* @return ray for reflected
*/
private Ray constructReflectedRay(GeoPoint gp, Vector vRay, Vector normal) {
// Vector v = ray.getDir();
double nv = Util.alignZero(normal.dotProduct(vRay));
if (Util.isZero(nv))
return null;
Vector r = vRay.subtract(normal.scale(nv * 2));
return new Ray(gp.point, r, normal);
}

/**
* A function that calculate the globals effects of the color
*
* @param gp    geo point
* @param ray   of the lighting direction
* @param level of Recursive function
* @param k     low cumulative attenuation coefficient value
* @return the color of global effects
*/

 private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k,int numOfRays) {
 Vector v = ray.getDir(); Vector n = gp.geometry.getNormal(gp.point); Material
 material = gp.geometry.getMaterial(); return
 calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR,numOfRays)
 .add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k,
 material.kT,numOfRays)); }
 
/**
* A function that calculate the globals effects of the color
*
* @param ray   of the lighting direction
* @param level of Recursive function
* @param k     low cumulative attenuation coefficient value
* @param kx    cumulative attenuation coefficient value
* @return the color of global effects
*/
 private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx,int numOfRays) {
 Double3 kkx = k.product(kx); if (kkx.lowerThan(MIN_CALC_COLOR_K)) return
 Color.BLACK; GeoPoint gp = findClosestIntersection(ray); if (gp == null)
 return scene.background.scale(kx); return
 isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ?
 Color.BLACK : calcColor(gp, ray, level - 1, kkx,numOfRays).scale(kx); }

 /**
* Auxiliary function for calculating the light color
*
* @param gp  a point on the scene
* @param ray The ray that intersects at the point
* @return the color on the point
*/

private Color calcLocalEffect(GeoPoint intersection, Ray ray,Double3 k,int numOfRays) {
       Vector v = ray.getDir();
       Vector n = intersection.geometry.getNormal(intersection.point);
       double nv = alignZero(n.dotProduct(v)); //nv=n*v
       if (nv == 0) {
           return Color.BLACK;
       }
       Material material = intersection.geometry.getMaterial();
       Color color = intersection.geometry.
               getEmission(); //base color
       int beam= numOfRays;//for the number of beams
       Double3 ktr;
       //for each light source in the scene
       for (LightSource lightSource : scene.lights)
       {
           Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
           double nl = alignZero(n.dotProduct(l)); //nl=n*l
           //if sign(nl) == sing(nv) (if the light hits the point add it, otherwise don't add this light)
           if (nl * nv > 0)
           {
               if(softShadows)
                   ktr=transparencyBeam(lightSource,n,intersection,beam);
            	   
            	  
               else
                   ktr =transparency(intersection,l,n,lightSource,nv);
               if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K))
               {
                   Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                   color = color.add(calcDiffusive(material, l, n, lightIntensity),
                           calcSpecular(material, l, n, v, lightIntensity));
               }
           }
       }
       return color;
   }


/**
 * Recursive function for calculating a point color
 *
 * @param intersection geometric body and point
 * @param ray
 * @param level        level of Recursive function stops that level=1
 * @param k
 * @return
 */

private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k,int numOfRays) {
Color color = calcLocalEffect(intersection, ray, k, numOfRays);
return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k,numOfRays));

}
private Double3 transparencyBeam(LightSource lightSource, Vector n, GeoPoint geoPoint,int beam) {
	
    Double3 tempKtr = new Double3(0d);
   
    List<Vector> beamL= lightSource.getBeamL(geoPoint.point,beamRadius,beam);
    

    for (Vector vl : beamL) {
        tempKtr = tempKtr.add(transparency(geoPoint,vl,n,lightSource,n.dotProduct(vl)));
    }
    tempKtr = tempKtr.reduce(beamL.size());

    return tempKtr;
}

/**
 * Super Sampling
 * function that return the average color of the pixel recursively
 * @param p0 corner upLeft
 * @param p1 corner upRight
 * @param p2 corner downRight
 * @param p3 corner downLeft
 * @param cameraP0 camera position
 * @param level level of the recursion
 * @return the average of the colors of the rays
 */
@Override
public Color traceRaySS(Point p0, Point p1, Point p2, Point p3, Point cameraP0, int level,int numOfRays) {
    List<Color> listColors=new ArrayList<>();
    List<Point> listOfCorners=List.of(p0, p1, p2, p3);
    Ray ray;

    for(int i=0; i<4;i++){
        ray=new Ray(cameraP0, listOfCorners.get(i).subtract(cameraP0).normalize());
        GeoPoint gp= findClosestIntersection(ray);
        if(gp!=null)
            listColors.add(calcColor(gp,ray,numOfRays));
        else
            listColors.add(scene.background);
    }
    // condition for stopping the recursion
    if(level==0 || (listColors.get(0).isAlmostEquals(listColors.get(1)) && listColors.get(0).isAlmostEquals(listColors.get(2))
            && listColors.get(0).isAlmostEquals(listColors.get(3)) && listColors.get(2).isAlmostEquals(listColors.get(3))
            && listColors.get(1).isAlmostEquals(listColors.get(3))
            && listColors.get(1).isAlmostEquals(listColors.get(2))))
        return listColors.get(0);

    Point midUp=findMiddlePoint(p0, p1);
    Point midDown=findMiddlePoint(p2, p3);
    Point midRight=findMiddlePoint(p2, p1);
    Point midLeft=findMiddlePoint(p0, p3);
    Point center=findMiddlePoint(midRight, midLeft);

    // recursive call, call the function 4 times (corners square)
    return traceRaySS(p0, midUp, center, midLeft, cameraP0, level-1,numOfRays).scale(0.25)
            .add(traceRaySS(midUp, p1, midRight, center, cameraP0, level-1,numOfRays).scale(0.25))
            .add(traceRaySS(center, midRight, p2, midDown, cameraP0, level-1,numOfRays).scale(0.25))
            .add(traceRaySS(midLeft, center, midDown, p3, cameraP0, level-1,numOfRays).scale(0.25));
}
/**
 * help function to find the middle point between 2 points
 * @param p1 point1
 * @param p2 point2
 * @return
 */
public Point findMiddlePoint(Point p1, Point p2){
    double x= (p1.getX()+ p2.getX())/2d;
    double y= (p1.getY()+ p2.getY())/2d;
    double z= (p1.getZ()+ p2.getZ())/2d;
    return new Point(x, y, z);
}

}
