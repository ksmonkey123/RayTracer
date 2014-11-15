package ch.awae.ray.shapes;

import javax.vecmath.Vector3d;

import ch.awae.ray.Material;
import ch.awae.ray.Ray;
import ch.awae.ray.rendering.IPointRenderer;

public interface IShape {

    public default boolean intersectsRay(Ray ray) {
        return false;
    }

    public default Vector3d getIntersectionPoint(Ray ray) {
        return null;
    }

    public default Material getMaterial(Vector3d point) {
        return null;
    }

    public default Vector3d getNormal(Vector3d point) {
        return null;
    }

    public default IPointRenderer getSpecialRenderer() {
        return null;
    }

}
