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
