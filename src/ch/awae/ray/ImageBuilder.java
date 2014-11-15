package ch.awae.ray;

import java.awt.image.BufferedImage;

public class ImageBuilder {

    private Camera        c;
    private int           imageWidth, imageHeight;
    private RayTraveller  traveller;
    private float         unitsPerPixel;
    private int           AAcount;

    private BufferedImage image;

    public ImageBuilder(Camera c, int imageWidth, int imageHeight,
            RayTraveller traveller) {
        super();
        this.c = c;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.traveller = traveller;
        this.unitsPerPixel = 2f / this.imageWidth;
        this.image = new BufferedImage(this.imageWidth, this.imageHeight,
                BufferedImage.TYPE_INT_RGB);
        this.AAcount = 1;
    }

    /**
     * configure the Anti-Aliasing to perform {@code amt^2} samples per pixel.
     * While increasing the overall picture quality, the processing time is
     * increased as well.
     *
     * @param amt
     */
    public void setMultiSamlingAmount(int amt) {
        this.AAcount = amt;
    }

    /**
     * maps an image pixel to the corresponding screen coordinate.
     *
     * @param x
     * @param y
     * @return
     */
    private float[] picToScreen(float x, float y) {
        // move origin to center
        x -= this.imageWidth / 2;
        y -= this.imageHeight / 2;
        // scale down so that (-imW/2 | -imH/2) maps to (-1|-imH/imW)
        // -> simply divide by imW/2
        return new float[] { x * this.unitsPerPixel, y * this.unitsPerPixel };
    }

    private final static int THREAD_COUNT = 4;

    public void buildImage(int args) {
        CoordinateProvider p = new CoordinateProvider(this.image.getWidth(),
                this.image.getHeight());

        Thread[] runnerThreads = new Thread[ImageBuilder.THREAD_COUNT];

        for (int i = 0; i < ImageBuilder.THREAD_COUNT; i++) {
            runnerThreads[i] = new Thread(new Runner(p));
            runnerThreads[i].start();
        }

        for (int i = 0; i < ImageBuilder.THREAD_COUNT; i++) {
            try {
                runnerThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
         * for (int y = 0; y < this.image.getHeight(); y++) { for (int x = 0; x
         * < this.image.getWidth(); x++) { this.buildPixel(x, y); } }
         */
    }

    private void buildPixel(int x, int y) {
        Color[] subColors = new Color[this.AAcount * this.AAcount];
        // cycle through AA steps
        for (int AAx = 0; AAx < this.AAcount; AAx++) {
            for (int AAy = 0; AAy < this.AAcount; AAy++) {
                float[] screenXY = this.picToScreen(x + ((float) AAx)
                        / this.AAcount, y + ((float) AAy) / this.AAcount);
                Ray ray = this.c.getRay(screenXY[0], screenXY[1]);
                Color rayColor = this.traveller.travelRay(ray);
                subColors[AAx + AAy * this.AAcount] = rayColor;
            }
        }
        // combine colors and render
        Color finalC = new Color(0, 0, 0);

        for (int i = 0; i < subColors.length; i++) {
            finalC.merge(subColors[i]);
        }
        finalC.scale(1f / (this.AAcount * this.AAcount));

        // output
        this.image.setRGB(x, y, finalC.getRGB());

    }

    public BufferedImage getImage() {
        return this.image;
    }

    private class Runner implements Runnable {

        private final CoordinateProvider prov;

        public Runner(CoordinateProvider prov) {
            this.prov = prov;
        }

        @Override
        public void run() {
            int[] coords;
            while ((coords = this.prov.next()) != null) {
                ImageBuilder.this.buildPixel(coords[0], coords[1]);
            }
        }
    }

    private class CoordinateProvider {
        private int       x;
        private int       y;
        private final int xCnt;
        private final int yCnt;
        private boolean   done;

        public CoordinateProvider(int xCnt, int yCnt) {
            this.xCnt = xCnt;
            this.yCnt = yCnt;
            this.x = this.y = 0;
            this.done = false;
        }

        public synchronized int[] next() {
            if (this.done)
                return null;
            int[] pxl = { this.x++, this.y };
            if (this.x == this.xCnt) {
                this.x = 0;
                if (++this.y == this.yCnt)
                    this.done = true;
            }
            return pxl;
        }
    }
}
