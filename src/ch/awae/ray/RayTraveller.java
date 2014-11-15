package ch.awae.ray;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import ch.awae.ray.rendering.IPointRenderer;
import ch.awae.ray.shapes.IShape;

/**
 * MAIN RAY TRACING STEP MANAGMENT SPECIFIC RENDERING IS DELEGATED
 */
public class RayTraveller {

    private World          world;
    private IPointRenderer defaultRenderer;

    {
        this.defaultRenderer = new IPointRenderer() {
        };
    }

    public RayTraveller(World world, IPointRenderer defaultRenderer) {
        this.world = world;
        if (defaultRenderer != null)
            this.defaultRenderer = defaultRenderer;
    }

    public Color travelRay(Ray ray) {

        // FIND NEAREST COLLIDER
        double sqrDistance = Double.MAX_VALUE;
        IShape frontShape = null;
        Vector3d intersection = null;
        ArrayList<IShape> shapes = this.world.getBodies();
        for (IShape shape : shapes) {
            if (shape.intersectsRay(ray)) {
                Vector3d intersect = shape.getIntersectionPoint(ray);
                // comparing squares of distances is more efficient
                Vector3d intDistV = new Vector3d(intersect);
                intDistV.sub(ray.getOrigin());
                double sqrDist = intDistV.lengthSquared();
                if (sqrDist < sqrDistance) {
                    sqrDistance = sqrDist;
                    frontShape = shape;
                    intersection = intersect;
                }
            }
        }

        // NO INTERSECTOR FOUND
        if (frontShape == null)
            return this.renderBackGround(ray);

        // RENDER INTERSECTION POINT
        IPointRenderer renderer = frontShape.getSpecialRenderer();
        if (renderer == null)
            renderer = this.defaultRenderer;

        return renderer.renderPoint(ray, intersection, frontShape, this.world,
                this);
    }

    private Color renderBackGround(Ray r) {
        return Color.BLACK;
    }

    public boolean checkPointPassage(Ray ray, Vector3d target) {
        double targetSquareDist = target.lengthSquared();

        ArrayList<IShape> shapes = this.world.getBodies();
        for (IShape shape : shapes) {
            if (shape.intersectsRay(ray)) {
                Vector3d sect = shape.getIntersectionPoint(ray);
                sect.sub(ray.getOrigin());

                if (sect.lengthSquared() < targetSquareDist)
                    return false;

            }
        }

        return true;
    }

}
