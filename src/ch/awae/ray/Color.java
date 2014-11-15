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

import javax.vecmath.Vector3f;

public class Color {

    private Vector3f color;

    public Color(float r, float g, float b) {
        this.color = new Vector3f(r, g, b);
    }

    public Color(Color c) {
        this.color = new Vector3f(c.color);
    }

    public float getR() {
        return this.color.x;
    }

    public float getG() {
        return this.color.y;
    }

    public float getB() {
        return this.color.z;
    }

    private void clamp() {
        this.color.clamp(0, 1);
    }

    public int getRGB() {
        Color c = new Color(this);
        c.clamp();
        return new java.awt.Color(c.getR(), c.getG(), c.getB()).getRGB();
    }

    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED   = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE  = new Color(0, 0, 1);
    public static final Color GRAY  = new Color(0.5f, 0.5f, 0.5f);

    public void merge(Color c) {
        this.color.add(c.color);
    }

    public void filter(Color c) {
        this.color.x *= c.color.x;
        this.color.y *= c.color.y;
        this.color.z *= c.color.z;
    }

    public Color copy() {
        return new Color(this);
    }

    public void scale(float scalar) {
        this.color.scale(scalar);
    }

    @Override
    public String toString() {
        return this.color.toString();
    }

}
