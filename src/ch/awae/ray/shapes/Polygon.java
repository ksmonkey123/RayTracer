package ch.awae.ray.shapes;

import javax.vecmath.Vector3d;

import ch.awae.ray.Material;
import ch.awae.ray.Ray;
import ch.awae.util.math.Vec3d;

public class Polygon extends Plane {

	private Vec3d[] vertices;
	private Vec3d[] directions;
	private double[] coefficients;

	public Polygon(Material material, Vector3d normal, Vector3d... vertices) {
		super(vertices[0], normal, material);
		// this.vertices = vertices;
		this.vertices = new Vec3d[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = new Vec3d(vertices[i].x, vertices[i].y,
					vertices[i].z);
		}
		this.directions = new Vec3d[vertices.length];
		this.coefficients = new double[vertices.length];
		this.preprocess();
	}

	private void preprocess() {
		for (int i = 0; i < vertices.length; i++) {
			directions[i] = ((vertices[(i + 1) < vertices.length ? (i + 1) : 0])
					.$minus(vertices[i])).norm();
			coefficients[i] = (((vertices[i > 0 ? (i - 1)
					: (vertices.length - 1)]).$minus(vertices[i])).norm())
					.$times(directions[i]);
		}
	}

	// CACHE
	private Ray lastRay = null;
	private Vec3d lastIntersect = null;

	@Override
	public boolean intersectsRay(Ray ray) {
		lastRay = null;
		if (!super.intersectsRay(ray))
			return false;
		Vector3d intersectRaw = this.getIntersectionPoint(ray);
		Vec3d sect = new Vec3d(intersectRaw.x, intersectRaw.y, intersectRaw.z);
		for (int i = 0; i < vertices.length; i++) {
			if (sect.$minus(vertices[i]).norm().$times(directions[i]) < coefficients[i])
				return false;
		}
		this.lastIntersect = sect;
		this.lastRay = ray;
		return true;
	}

	@Override
	public Vector3d getIntersectionPoint(Ray ray) {
		if (lastRay != null)
			return new Vector3d(lastIntersect.x(), lastIntersect.y(),
					lastIntersect.z());
		return super.getIntersectionPoint(ray);
	}
}
