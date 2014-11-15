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
package ch.awae.ray.rendering;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import ch.awae.ray.Color;
import ch.awae.ray.Material;
import ch.awae.ray.Ray;
import ch.awae.ray.RayTraveller;
import ch.awae.ray.World;
import ch.awae.ray.lights.ILightSource;
import ch.awae.ray.shapes.IShape;

public class BasicRenderer implements IPointRenderer {

    @Override
    public Color renderPoint(Ray ray, Vector3d point, IShape shape, World w,
            RayTraveller delegator) {
        Material material = shape.getMaterial(point);

        // EMISSIVE LIGHT
        Color c = material.getEmissiveColor();

        // LIGHT SOURCE DEPENDANT LIGHTING
        ArrayList<ILightSource> lights = w.getLights();
        Vector3d norm = shape.getNormal(point);
        Vector3d toCam = new Vector3d(ray.getDirection());
        toCam.scale(-1);
        Vector3d camReflection = new Vector3d(norm);
        camReflection.scale(2 * norm.dot(toCam));
        camReflection.sub(toCam);

        for (ILightSource light : lights) {

            // Step 1: apply ambient light
            Color ambient = light.getAmbientColor();
            ambient.filter(material.getAmbientColor());
            c.merge(ambient);

            // Step 2: check if light is on correct side of surface
            Vector3d lightPos = new Vector3d(light.getObservedPosition(point));
            Vector3d lightDir = new Vector3d(lightPos);
            lightDir.sub(point);
            lightDir.normalize();

            double lightDirScalar = lightDir.dot(norm);

            if (lightDirScalar <= 0)
                continue;
            // light is "above" surface

            // STEP 3: check light visibility
            if (!delegator.checkPointPassage(new Ray(point, lightDir), lightPos))
                continue;

            // light source reaches surface

            // STEP 4: apply diffuse light
            Color diffuse = light.getDiffuseColor();
            diffuse.scale((float) lightDirScalar);
            diffuse.filter(material.getDiffuseColor());
            // c.merge(new Color((float)lightDirScalar, (float)lightDirScalar,
            // (float)lightDirScalar));
            c.merge(diffuse);

            // STEP 5: direct specular reflection
            double specularCoefficient = Math.pow(
                    Math.max(0, camReflection.dot(lightDir)),
                    material.getSpecularIntensity());

            Color specular = light.getSpecularColor();
            specular.filter(material.getSpecularColor());
            specular.scale((float) specularCoefficient);
            c.merge(specular);

        }

        // INTER-OBJECT REFLECTIONS


        // STEP 6: reflections
        Color reflect = material.getSpecularColor();

        Ray subRay = new Ray(point, camReflection);
        reflect.filter(delegator.travelRay(subRay));
        c.merge(reflect);


        return c;
    }
}