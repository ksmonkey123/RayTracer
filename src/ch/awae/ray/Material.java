/*
 * Copyright (C) 2014 Andreas Wälchli (andreas.waelchli@me.com)
 *
 * This file is part of RayTracer.
 *
 * RayTracer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * RayTracer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RayTracer.  If not, see <http://www.gnu.org/licenses/>.
 */
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
