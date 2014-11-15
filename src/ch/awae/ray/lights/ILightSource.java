package ch.awae.ray.lights;

import javax.vecmath.Vector3d;

import ch.awae.ray.Color;

public interface ILightSource {

    public default Color getAmbientColor() {
        return Color.BLACK;
    }

    public default Color getDiffuseColor() {
        return Color.BLACK;
    }

    public default Color getSpecularColor() {
        return Color.BLACK;
    }

    /**
     * returns a vector from the given point to this light source. The magnitude
     * of this vector determines the light intensity, where the resulting
     * intensity is proportional to the magnitude.
     *
     * @param target
     * @return the light intensity scaling factor at the target point
     */
    public default double getLightIntensity(Vector3d target) {
        return 0;
    }

    public default boolean isLighted(Vector3d target) {
        return true;
    }

    public default Vector3d getObservedPosition(Vector3d target) {
        return new Vector3d(0, 0, 0);
    }

}
