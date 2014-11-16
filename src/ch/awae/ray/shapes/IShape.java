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
