package ch.awae.ray.shapes;

import javax.vecmath.Vector3d;

import ch.awae.ray.Material;
import ch.awae.ray.Ray;

public class Sphere implements IShape {

    private Vector3d center;
    private float    radius;
    private Material material;

    public Sphere(Vector3d center, float radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Vector3d getCenter() {
        return this.center;
    }

    public float getRadius() {
        return this.radius;
    }

    // RAY ANALYSIS CACHE
    private Ray    lastRay;
    private double coef[] = new double[4];

    @Override
    public boolean intersectsRay(Ray ray) {
        // Vector3d distByRay = Vector3d.sub(this.center, ray.getOrigin());
        Vector3d distByRay = new Vector3d(this.center);
        distByRay.sub(ray.getOrigin());
        // ray distance to center
        double rayDist = distByRay.dot(ray.getDirection());
        if (rayDist < 0)
            return false;
        // ray approaches sphere
        Vector3d relRay = new Vector3d(ray.getOrigin());
        relRay.sub(this.center);
        double a = 1;
        double b = 2 * ray.getDirection().dot(relRay);
        double c = relRay.lengthSquared() - (this.radius * this.radius);
        double Q = (b * b) - (4 * a * c);

        if (Q <= 0)
            return false;

        this.lastRay = ray;
        this.coef = new double[] { a, b, c, Q };

        return true;
    }

    @Override
    public Vector3d getIntersectionPoint(Ray ray) {
        if (!ray.equals(this.lastRay))
            if (!this.intersectsRay(ray))
                return null;

        double t0 = (-this.coef[1] + Math.sqrt(this.coef[3]))
                / (2 * this.coef[0]);
        double t1 = (-this.coef[1] - Math.sqrt(this.coef[3]))
                / (2 * this.coef[0]);

        double t = t0;
        if (t < 0|| (t1 >= 0 && t1 < t))
            t = t1;

        Vector3d secPoint = new Vector3d(ray.getDirection());
        secPoint.scale(t);
        secPoint.add(ray.getOrigin());
        return secPoint;
    }

    @Override
    public Material getMaterial(Vector3d point) {
        return this.material;
    }

    @Override
    public Vector3d getNormal(Vector3d point) {
        Vector3d norm = new Vector3d(point);
        norm.sub(this.center);
        norm.normalize();
        return norm;
    }
}
