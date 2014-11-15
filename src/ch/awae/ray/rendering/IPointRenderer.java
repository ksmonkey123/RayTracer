package ch.awae.ray.rendering;

import javax.vecmath.Vector3d;

import ch.awae.ray.Color;
import ch.awae.ray.Ray;
import ch.awae.ray.RayTraveller;
import ch.awae.ray.World;
import ch.awae.ray.shapes.IShape;

public interface IPointRenderer {

    public default Color renderPoint(Ray ray, Vector3d point, IShape shape,
            World w, RayTraveller delegator) {
        return Color.BLACK;
    }

}
