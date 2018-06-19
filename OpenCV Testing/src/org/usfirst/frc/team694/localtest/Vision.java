package org.usfirst.frc.team694.localtest;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

@SuppressWarnings("serial")
public class Vision extends Canvas implements Runnable {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	// The frame displayed in this JPanel.
	// Draw to the window by drawing to this frame
	private BufferedImage frame;

	// Whether our application is running
	private boolean running;

	// Our video camera
	private VideoCapture camera;

	// The Mat frame that the camera captures
	private Mat cameraFrame;

	// The swing Window that we'll be drawing to
	private JFrame window;

	public Vision() {
		camera = new VideoCapture(1);

		cameraFrame = new Mat();
        camera.read(cameraFrame);

        frame = new BufferedImage(cameraFrame.width(), cameraFrame.height(), BufferedImage.TYPE_INT_RGB);
        this.setSize(cameraFrame.width(), cameraFrame.height());

        // Set up our jframe window
        window = new JFrame("OpenCV Test");
        window.setSize(cameraFrame.width(), cameraFrame.height());
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.add(this);
        window.pack();
        window.setVisible(true);

        // Make sure we stop running when we press the X button
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        		running = false;
            }
        });
	}

	protected void filter(Mat frame) {
		// Fill me!
	}

	// Starts our window frame
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		running = true;
		while(running) {
			updateFrame();
			paint(this.getGraphics());
			window.revalidate();
			window.repaint();
		}
		camera.release();
		cameraFrame.release();
		System.exit(0);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(frame, 0, 0, null);
	}

	/** void updateFrame()
	 * 		Reads an image from the camera and stores it in both cameraFrame and frame,
	 * 		and applies a filter
	 */
	private void updateFrame() {
        camera.read(cameraFrame);
        filter(cameraFrame);
		MatToBufferedImage(cameraFrame, frame);
	}

	/** void MatToBufferedImage(img, dest)
	 * 
	 * Fills a BufferedImage "dest" with the contents of Mat "img"
	 * 
	 * @param img
	 * @param dest
	 */
	public static void MatToBufferedImage(Mat img, BufferedImage dest) {
		for(int yy = 0; yy < img.height(); yy++) {
			for(int xx = 0; xx < img.width(); xx++) {
				double[] data = img.get(yy, xx);
				int r = (int)data[2];
				int g = (int)data[1];
				int b = (int)data[0];

				int rgb = (255 << 24) | (r << 16) | (g << 8) | b;
				dest.setRGB(xx, yy, rgb);
			}
		}
	}


	public static void main(String[] args) {
		new Vision().start();
	}
}
