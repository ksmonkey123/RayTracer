/*
 * This file is part of RayTracer.

    RayTracer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    RayTracer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RayTracer.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.awae.ray.shapes;

import javax.vecmath.Vector3d;

import ch.awae.ray.Material;
import ch.awae.ray.Ray;

public class Plane implements IShape {

    private Vector3d origin;
    private Vector3d normal;

    private Material material;

    public Plane(Vector3d origin, Vector3d normal, Material material) {
        super();
        this.origin = origin;
        this.normal = normal;
        this.material = material;
        this.normal.normalize();
    }

    @Override
    public Material getMaterial(Vector3d point) {
        return this.material;
    }

    @Override
    public Vector3d getNormal(Vector3d point) {
        return new Vector3d(this.normal);
    }

    @Override
    public boolean intersectsRay(Ray ray) {
        Vector3d relOrPos = new Vector3d(ray.getOrigin());
        relOrPos.sub(this.origin);
        return this.normal.dot(ray.getDirection()) < 0
                && this.normal.dot(relOrPos) > 0;
    }

    @Override
    public Vector3d getIntersectionPoint(Ray ray) {
        Vector3d relOrPos = new Vector3d(ray.getOrigin());
        relOrPos.sub(this.origin);
        double lambda = -this.normal.dot(relOrPos)
                / this.normal.dot(ray.getDirection());
        Vector3d intersection = new Vector3d(ray.getDirection());
        intersection.scale(lambda);
        intersection.add(ray.getOrigin());
        return intersection;
    }

}
