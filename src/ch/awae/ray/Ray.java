package ch.awae.ray;

import javax.vecmath.Vector3d;

public class Ray {

    private Vector3d direction;
    private Vector3d origin;

    public Ray(Vector3d origin, Vector3d direction) {
        this.direction = new Vector3d(direction);
        this.direction.normalize();
        this.origin = origin;
    }

    public Vector3d getOrigin() {
        return this.origin;
    }

    public Vector3d getDirection() {
        return this.direction;
    }

}
