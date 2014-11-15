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
package ch.awae.ray.test;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.Vector3d;

import ch.awae.ray.Camera;
import ch.awae.ray.Color;
import ch.awae.ray.ImageBuilder;
import ch.awae.ray.Material;
import ch.awae.ray.RayTraveller;
import ch.awae.ray.World;
import ch.awae.ray.lights.DistantLight;
import ch.awae.ray.rendering.BasicRenderer;
import ch.awae.ray.shapes.Plane;
import ch.awae.ray.shapes.Sphere;

public class Test {

    public final static int AA_COUNT = 2;

    public static void main(String[] args) {

        Material mat0 = new Material(new Color(0.05f, 0.05f, 0.05f), Color.RED,
                Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);
        Material mat1 = new Material(new Color(0.05f, 0.05f, 0.05f),
                Color.GREEN, Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);
        Material mat2 = new Material(new Color(0.05f, 0.05f, 0.05f),
                Color.BLUE, Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);

        Sphere s0 = new Sphere(new Vector3d(0, 0, 5), 2, mat0);
        // shadow test: (3.5|1|4) with light (-1|0|0)
        Sphere s1 = new Sphere(new Vector3d(0, 1, -1), 0.5f, mat1);
        Sphere s2 = new Sphere(new Vector3d(2, 0, 10), 0.5f, mat2);
        Plane p0 = new Plane(new Vector3d(0, 4, 0), new Vector3d(0, -1, 0),
                new Material(new Color(0.05f, 0.05f, 0.05f),
                Color.BLUE, Color.BLACK, new Color(.4f, .4f, .4f), Color.BLACK, 1.3f, 50));
        Plane p1 = new Plane(new Vector3d(0, 0, 20), new Vector3d(0, 0, -1),
                new Material(Color.BLACK, Color.BLACK, Color.BLACK,
                        Color.WHITE, Color.BLACK, 1, 60));

        DistantLight l0 = new DistantLight(new Vector3d(0, 1, 5), Color.GRAY,
                Color.WHITE, Color.WHITE);
        DistantLight l1 = new DistantLight(new Vector3d(0, 1, 0), Color.GRAY,
                new Color(0f, 0f, 0.2f), new Color(0.5f, 0.5f, 0.5f));

        World w = new World();
        w.addBody(s0);
        w.addBody(s1);
        w.addBody(p0);
        w.addBody(s2);
        w.addBody(p1);
        w.addLight(l0);
        w.addLight(l1);

        RayTraveller tr = new RayTraveller(w, new BasicRenderer());

        Camera c = new Camera(new Vector3d(5, 0, -10), (float) (Math.PI / 6),
                -0.4f, 0, 0);

        c.setLookAtVector(new Vector3d(0, 0, 5));

        ImageBuilder builder = new ImageBuilder(c, 800, 600, tr);

        builder.setMultiSamlingAmount(Test.AA_COUNT);

        long startTime = System.currentTimeMillis();

        builder.buildImage(0);

        long endTime = System.currentTimeMillis();

        System.out.println("Finished Image Rendering after "
                + ((endTime - startTime) / 1000f) + " seconds");

        JFrame f = new JFrame("RayTracer (Anti-Alias: "
                + (Test.AA_COUNT * Test.AA_COUNT) + "x)");
        f.setLayout(new BoxLayout(f.getContentPane(), 0));
        f.add(new JLabel(new ImageIcon(builder.getImage())));
        f.pack();
        f.setVisible(true);
        f.repaint();
    }
}
