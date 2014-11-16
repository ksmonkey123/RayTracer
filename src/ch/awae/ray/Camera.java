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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

public class Camera {

    private float    FOV;
    private float    roll, yaw, pitch;
    private Vector3d position;
    private Matrix3d rotationMatrix;
    private double   screenDistance;

    /**
     *
     * @param pos
     *            camera position in world
     * @param FOV
     *            camera view angle in radians
     * @param yaw
     *            camera yaw angle in radians (0 is north [Z axis])
     * @param pitch
     *            camera pitch angle in radians ( 0 is horizon)
     * @param roll
     *            camera roll angle in radians (0 is upright)
     */
    public Camera(Vector3d pos, float FOV, float yaw, float pitch, float roll) {
        this.FOV = FOV;
        this.position = pos;
        this.roll = roll;
        this.yaw = yaw;
        this.pitch = pitch;
        this.buildRotationMatrix();
        this.updateScreenDistance();
    }

    private final void updateScreenDistance() {
        this.screenDistance = 1 / Math.tan(this.FOV / 2);
    }

    private final void buildRotationMatrix() {
        // TODO: FIX
        Matrix3d mr = new Matrix3d();
        Matrix3d mp = new Matrix3d();
        Matrix3d my = new Matrix3d();

        mr.rotZ(this.roll);
        mp.rotX(this.pitch);
        my.rotY(this.yaw);

        my.mul(mp);
        my.mul(mr);
        this.rotationMatrix = my;
    }

    public Ray getRay(double screenX, double screenY) {
        if (Math.abs(screenY) > 1 || Math.abs(screenX) > 1)
            throw new IllegalArgumentException(
                    "screen coordinates out of bounds!");

        Vector3d direction = new Vector3d(screenX, screenY, this.screenDistance);
        this.rotationMatrix.transform(direction);
        return new Ray(this.position, direction);
    }

    public void setFOV(float fOV) {
        this.FOV = fOV;
        this.updateScreenDistance();
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        this.buildRotationMatrix();
    }

    public void setRoll(float roll) {
        this.roll = roll;
        this.buildRotationMatrix();
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.buildRotationMatrix();
    }

    public void setLookAtVector(Vector3d lav) {
        lav.sub(this.position);
        lav.normalize();

        Vector3d vec = new Vector3d();

        Vector3d y = new Vector3d(0, 1, 0);

        vec.cross(lav, y);
        vec.cross(y, vec);

        vec.normalize();

        this.pitch = (float) Math.acos(vec.dot(lav));
        this.yaw = (float) Math.acos(vec.dot(new Vector3d(0, 0, 1)));

        System.out.println("YAW:   " + this.yaw);
        System.out.println("PITCH: " + this.pitch);

        this.buildRotationMatrix();
    }

}
