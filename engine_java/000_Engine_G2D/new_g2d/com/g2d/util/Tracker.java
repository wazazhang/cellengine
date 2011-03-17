package com.g2d.util;

import com.g2d.Color;
import com.g2d.Graphics2D;

public class Tracker
{
	final double[] track;
	
	double HistoryMin = Double.MAX_VALUE;
	double HistoryMax = Double.MIN_VALUE;
	
	public Tracker(int tracklen) {
		track = new double[tracklen];
	}
	
	synchronized public void record(double value) {
		for (int i=1; i<track.length; i++) {
			track[i-1] = track[i];
		}
		track[track.length-1] = value;
		HistoryMin = Math.min(HistoryMin, value);
		HistoryMax = Math.max(HistoryMax, value);
	}
	
	synchronized public double[] getGrap() {
		return track.clone();
	}
	
	synchronized public double getLastValue() {
		return track[track.length-1];
	}
	
	synchronized public double getMax() {
		double max = Double.MIN_VALUE;
		for (int i=0; i<track.length; i++) {
			if (max < track[i]) {
				max = track[i];
			}
		}
		return max;
	}
	
	synchronized public double getMin() {
		double min = Double.MAX_VALUE;
		for (int i=0; i<track.length; i++) {
			if (min > track[i]) {
				min = track[i];
			}
		}
		return min;
	}
	
	public double getHistoryMin() {
		return HistoryMin;
	}

	public double getHistoryMax() {
		return HistoryMax;
	}
	
	protected void drawMap(Graphics2D g, int x, int y, int w, int h) 
	{
		int[][] points = new int[track.length][2];
		
		double pw = w / (double)points.length;
		
		double vh = HistoryMax;
		
		for (int i = 0; i<points.length; i++) 
		{
			double px = x + i * pw;
			double py = y + h - h * track[i] / vh;
			
			points[i][0] = (int)px;
			points[i][1] = (int)py;
		}
		for (int i = 1; i<points.length; i++) {
			g.drawLine(points[i-1][0], points[i-1][1], points[i][0], points[i][1]);
		}
	}
	
	protected void drawInfo(Graphics2D g, String infohead, int x, int y)
	{
		g.drawString(infohead + getLastValue(), x, y);
	}
	
	synchronized public void drawMap(Graphics2D g, Color lineColor, int x, int y, int w, int h)
	{
		g.setColor(lineColor);
		drawMap(g, x+1, y+1, w-2, h - 2 - 20);
	}
	
	synchronized public void drawGrap(Graphics2D g, Color boardColor, Color lineColor, String infohead, int x, int y, int w, int h) 
	{
		g.setColor(lineColor);
		drawMap(g, x+1, y+1, w-2, h - 2 - 20);
		
		drawInfo(g, infohead, x+2, y + h - 20);
		
		g.setColor(boardColor);
		g.drawRect(x, y, w-1, h-1);
		
	}
	
	
	public static class SecondRateTracker extends Tracker
	{
		double prewTime = 0;
		double prewValue;
		
		public SecondRateTracker(int tracklen) {
			super(tracklen);
		}
		
		synchronized public void record(double value) {
			long curTime = System.currentTimeMillis();
			if (prewTime == 0) {
				super.record(0);
			}else{
				double v = (value - prewValue) / ((curTime - prewTime) / 1000d);
				//System.out.println("recort second rate : " + v + " " + prewTime + " " + prewValue);
				super.record(v);
			}
			prewTime = curTime;
			prewValue = value;
		}
		
		protected void drawInfo(Graphics2D g, String infohead, int x, int y)
		{
			int val = (int)(getLastValue() * 100);
			int ved = val % 100;
			val = val / 100;
			
			g.drawString(infohead + val + "." + ved + "/s", x, y);
		}
	}




}
