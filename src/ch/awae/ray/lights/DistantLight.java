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
