package ch.awae.ray;

public class Material {

    private Color ambientColor;
    private Color diffuseColor;
    private Color emissiveColor;
    private Color specularColor;
    private float opticalDensity;
    private float specularIntensity;

    public Material(Color ambientColor, Color diffuseColor,
            Color emissiveColor, Color specularColor, Color reflectiveColor,
            float opticalDensity, float specularIntensity) {
        super();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.emissiveColor = emissiveColor;
        this.specularColor = specularColor;
        this.opticalDensity = opticalDensity;
        this.specularIntensity = specularIntensity;
    }

    public Material(Material original) {
        this.ambientColor = original.ambientColor;
        this.diffuseColor = original.diffuseColor;
        this.emissiveColor = original.emissiveColor;
        this.specularColor = original.specularColor;
        this.opticalDensity = original.opticalDensity;
        this.specularIntensity = original.specularIntensity;
    }

    public Color getAmbientColor() {
        return this.ambientColor.copy();
    }

    public Color getDiffuseColor() {
        return this.diffuseColor.copy();
    }

    public Color getEmissiveColor() {
        return this.emissiveColor.copy();
    }

    public Color getSpecularColor() {
        return this.specularColor.copy();
    }

    public float getOpticalDensity() {
        return this.opticalDensity;
    }

    public float getSpecularIntensity() {
        return this.specularIntensity;
    }

}
