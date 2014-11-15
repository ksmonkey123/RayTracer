package ch.awae.ray.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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

public class VideoTest {

    public final static int    AA_COUNT        = 2;

    public final static double radiansPerFrame = (0.0785398163 / 4);

    public final static int    frameCount      = 320;

    private static Vector3d getPosition(int frame) {
        double z = 5 - Math.cos(VideoTest.radiansPerFrame * frame) * 6;
        double x = Math.sin(VideoTest.radiansPerFrame * frame) * 6;
        return new Vector3d(x, 1, z);
    }

    public static void main(String[] args) throws IOException {

        long START_TIME = System.currentTimeMillis();

        Material mat0 = new Material(new Color(0.05f, 0.05f, 0.05f), Color.RED,
                Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);
        Material mat1 = new Material(new Color(0.05f, 0.05f, 0.05f),
                Color.GREEN, Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);
        Material mat2 = new Material(new Color(0.05f, 0.05f, 0.05f),
                Color.BLUE, Color.BLACK, Color.WHITE, Color.BLACK, 1.3f, 50);

        for (int FRAME = 0; FRAME < VideoTest.frameCount; FRAME++) {

            Sphere s0 = new Sphere(new Vector3d(0, 0, 5), 2, mat0);
            // shadow test: (3.5|1|4) with light (-1|0|0)
            Sphere s1 = new Sphere(VideoTest.getPosition(FRAME), 0.5f, mat1);
            Sphere s2 = new Sphere(new Vector3d(2, 0, 10), 0.5f, mat2);
            Plane p0 = new Plane(new Vector3d(0, 4, 0), new Vector3d(0, -1, 0),
                    new Material(new Color(0.05f, 0.05f, 0.05f), Color.BLUE,
                            Color.BLACK, new Color(.4f, .4f, .4f), Color.BLACK,
                            1.3f, 50));
            Plane p1 = new Plane(new Vector3d(0, 0, 20),
                    new Vector3d(0, 0, -1), new Material(Color.BLACK,
                            Color.BLACK, Color.BLACK, Color.WHITE, Color.BLACK,
                            1, 60));
            DistantLight l0 = new DistantLight(new Vector3d(0, 1, 5),
                    Color.GRAY, Color.WHITE, Color.WHITE);
            DistantLight l1 = new DistantLight(new Vector3d(0, 1, 0),
                    Color.GRAY, new Color(0f, 0f, 0.2f), new Color(0.5f, 0.5f,
                            0.5f));

            World w = new World();
            w.addBody(s0);
            w.addBody(s1);
            w.addBody(p0);
            w.addBody(p1);
            w.addBody(s2);
            w.addLight(l0);
            w.addLight(l1);

            RayTraveller tr = new RayTraveller(w, new BasicRenderer());

            Camera c = new Camera(new Vector3d(5, 0, -10),
                    (float) (Math.PI / 6), 0, -0.3f, 0);

            ImageBuilder builder = new ImageBuilder(c, 800, 600, tr);

            builder.setMultiSamlingAmount(VideoTest.AA_COUNT);

            long startTime = System.currentTimeMillis();

            builder.buildImage(0);

            long endTime = System.currentTimeMillis();

            String frameString = "" + FRAME;
            while (frameString.length() < 3)
                frameString = "0" + frameString;

            System.out.println("Finished Frame " + frameString
                    + " Rendering after " + ((endTime - startTime) / 1000f)
                    + " seconds");

            ImageIO.write(builder.getImage(), "png",
                    new File("/Users/andreas/Desktop/Clip/Frame_" + frameString
                            + ".png"));
        }

        System.out.println("Rendering completed. Elapsed time: "
                + (System.currentTimeMillis() - START_TIME));

    }
}
