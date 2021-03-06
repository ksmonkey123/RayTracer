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

import java.util.ArrayList;
import java.util.HashMap;

import ch.awae.ray.lights.ILightSource;
import ch.awae.ray.shapes.IShape;

public class World {

    private HashMap<Integer, IShape>       bodies;
    private HashMap<Integer, ILightSource> lights;

    {
        this.bodies = new HashMap<>();
        this.lights = new HashMap<>();
    }

    public ArrayList<IShape> getBodies() {
        return new ArrayList<>(this.bodies.values());
    }

    public ArrayList<ILightSource> getLights() {
        return new ArrayList<>(this.lights.values());
    }

    public void addBody(IShape body) {
        this.bodies.put(body.hashCode(), body);
    }

    public void addLight(ILightSource light) {
        this.lights.put(light.hashCode(), light);
    }

    public void removeBody(IShape body) {
        this.bodies.remove(body.hashCode());
    }

    public void removeLight(ILightSource light) {
        this.lights.remove(light.hashCode());
    }

    public void removeBody(int hashCode) {
        this.bodies.remove(hashCode);
    }

    public void removeLight(int hashCode) {
        this.lights.remove(hashCode);
    }

    public IShape getBody(int hashCode) {
        return this.bodies.get(hashCode);
    }

    public ILightSource getLight(int hashCode) {
        return this.lights.get(hashCode);
    }
}
