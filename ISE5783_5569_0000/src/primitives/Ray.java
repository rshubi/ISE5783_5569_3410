package primitives;

public class Ray {

	final Point p0;
	final Vector dir;
	Ray(Point p0In,Vector dirIn)
	{
		p0=p0In;
		dir=dirIn.normalize();
	}
	@Override
	 public boolean equals(Object obj) {
	 if (this == obj) return true;
	 if (obj instanceof Vector other)
	 return p0.equals(obj)&&dir.equals(obj);
	 return false;
	 }
	 @Override
	 public String toString()
	 {
		return p0.toString()+"+"+dir.toString();
	 }
	public Point getP0() {
		return p0;
	}
	public Vector getDir() {
		return dir;
	}
}
