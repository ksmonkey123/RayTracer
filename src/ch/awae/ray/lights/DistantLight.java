package ch.awae.ray.lights;

import javax.vecmath.Vector3d;

import ch.awae.ray.Color;

public class DistantLight implements ILightSource {

    private Vector3d lightDirection;
    private Color    ambientAspect;
    private Color    diffuseAspect;
    private Color    specularAspect;

    public DistantLight(Vector3d direction, Color ambient, Color diffuse,
            Color specular) {
        this.lightDirection = new Vector3d(direction);
        this.lightDirection.normalize();
        this.ambientAspect = ambient;
        this.diffuseAspect = diffuse;
        this.specularAspect = specular;
    }

    @Override
    public Color getAmbientColor() {
        return this.ambientAspect.copy();
    }

    @Override
    public Color getDiffuseColor() {
        return this.diffuseAspect.copy();
    }

    @Override
    public Color getSpecularColor() {
        return this.specularAspect.copy();
    }

    @Override
    public boolean isLighted(Vector3d target) {
        return true;
    }

    @Override
    public double getLightIntensity(Vector3d target) {
        return 1;
    }

    @Override
    public Vector3d getObservedPosition(Vector3d target) {
        Vector3d dist = new Vector3d(this.lightDirection);
        dist.scale(-1000);
        dist.add(target);
        return dist;
    }

}
