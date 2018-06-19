package org.usfirst.frc.team694.localtest;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

@SuppressWarnings("serial")
public class VisionFilterTest extends Vision {

	private ArrayList<Mat> channels;

	public VisionFilterTest() {
		super();
		channels = new ArrayList<>(3);
	}

	@Override
	public void filter(Mat frame) {
		Mat filter = new Mat();
		Imgproc.cvtColor(frame, filter, Imgproc.COLOR_BGR2HSV);
		Core.split(frame, channels);

	}

	public static void main(String[] args) {
		new VisionFilterTest().start();
	}
}
