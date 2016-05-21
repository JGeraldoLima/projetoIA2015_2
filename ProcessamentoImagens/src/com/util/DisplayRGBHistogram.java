package com.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.Histogram;
import javax.swing.JComponent;

import com.features.TrainingHistograms;

/**
 * This class displays a histogram (instance of Histogram) as a component. Only
 * the first histogram band ins considered for plotting. The component has a
 * tooltip which displays the bin index and bin count for the bin under the
 * mouse cursor.
 */
public class DisplayRGBHistogram extends JComponent implements MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8769348721959389029L;
	private Histogram histogram;
	private String title;
	private int band;
	// Some data and hints for the histogram plot.
	private int[] countsRed;
	private int[] countsBlue;
	private int[] countsGreen;
	private double maxCount;
	private int indexMultiplier = 1;
	private int skipIndexes = 8;
	// The components' dimensions.
	private int width, height = 250;
	// Some constants for this component. Some may be changed through set
	// methods.
	private int verticalTicks = 10;
	private Insets border = new Insets(40, 70, 40, 30);
	private int binWidth = 3;
	private Font fontSmall = new Font("monospaced", 0, 10);
	private Font fontLarge = new Font("default", Font.ITALIC, 20);
	private Color backgroundColor = Color.BLACK;
	private Color barColor;
	private Color marksColor;
	// Values of points of histogram.
	private List<Double> bars = new ArrayList<Double>();
	private String barsValues;

	/**
	 * The constructor for this class, which will set its fields' values and get
	 * some information about the histogram.
	 * 
	 * @param histogram
	 *            the histogram to be plotted.
	 * @param band 
	 * 			  which band of the histogram will be plotted.
	 * @param title
	 *            the title of the plot.
	 */
	public DisplayRGBHistogram(Histogram histogram, String title) {
		this.histogram = histogram;
		this.title = title;
		// Use default colors.
		backgroundColor = Color.BLACK;
		barColor = new Color(255, 255, 200);
		marksColor = new Color(100, 180, 255);
		// Calculate the components dimensions.
		width = histogram.getNumBins(band) * binWidth;
		// Get the histogram data.
		countsRed = histogram.getBins(0);
		countsBlue = histogram.getBins(1);
		countsGreen = histogram.getBins(2);
		// Get the max and min counts.
		maxCount = 0.0;
		int length = Math.max(countsRed.length, Math.max(countsBlue.length, countsGreen.length));
		for (int c = 0; c < length; c++){
			
			double redR = countsRed[c];
	        double blueR = countsBlue[c];
	        double greenR = countsGreen[c];
	        double valueR = Math.sqrt(redR*redR + blueR*blueR + greenR*greenR);
	        maxCount = Math.max(maxCount, valueR);
		}

		addMouseMotionListener(this);
	}

	/**
	 * Returns the values of each bar of histogram.
	 * 
	 * @return List of values.
	 */
	public List<Double> getBars() {
		return bars;
	}
	

	/**
	 * Returns the values of all bars of histogram.
	 * 
	 * @return String content values
	 */
	public String getBarsValues() {
		return barsValues;
	}

	/**
	 * Override the default bin width (for plotting)
	 */
	public void setBinWidth(int newWidth) {
		binWidth = newWidth;
		width = histogram.getNumBins(band) * binWidth;
	}

	/**
	 * Override the default height for the plot.
	 * 
	 * @param h
	 *            the new height.
	 */
	public void setHeight(int h) {
		height = h;
	}

	/**
	 * Override the index multiplying factor (for bins with width != 1)
	 */
	public void setIndexMultiplier(int i) {
		indexMultiplier = i;
	}

	/**
	 * Override the index skipping factor (determines how many labels will be
	 * printed on the index axis).
	 */
	public void setSkipIndexes(int i) {
		skipIndexes = i;
	}

	/**
	 * Set the maximum value (used to scale the histogram y-axis). The default
	 * value is defined in the constructor and can be overriden with this
	 * method.
	 */
	public void setMaxCount(double m) {
		maxCount = m;
	}

	/**
	 * Returns the max count for this histogram.
	 */
	public double getMaxCount() {
		return maxCount;
	}

	/**
	 * This method informs the maximum size of this component, which will be the
	 * same as the preferred size.
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * This method informs the minimum size of this component, which will be the
	 * same as the preferred size.
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * This method informs the preferred size of this component, which will be
	 * constant.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(width + border.left + border.right, height + border.top + border.bottom);
	}

	/**
	 * Sets the components' bars color.
	 */
	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}

	/**
	 * Sets the components' marks color.
	 */
	public void setMarksColor(Color marksColor) {
		this.marksColor = marksColor;
	}

	/**
	 * This method will paint the component.
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Draw the background.
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		// Draw some marks.
		g2d.setColor(marksColor);
		g2d.drawRect(border.left, border.top, width, height);
		// Draw the histogram bars.
		g2d.setColor(barColor);
		// Temporary array of bars values of the histogram.
		for (int red = 0; red < histogram.getNumBins(0); red++) {
			for (int blue = 0; blue < histogram.getNumBins(1); blue++) {
				for (int green = 0; green < histogram.getNumBins(2); green++) {
					int x = border.left + (red*4*4 + blue*4 + green) * binWidth/16;
					
					double redR = countsRed[red];
			        double blueR = countsBlue[blue];
			        double greenR = countsGreen[green];
			        double valueR = Math.sqrt(redR*redR + blueR*blueR + greenR*greenR);

					double barStarts = border.top + height * (maxCount - valueR)/maxCount;
					double barEnds = Math.ceil(height * valueR/maxCount);

					bars.add(barEnds);
					g2d.drawRect(x, (int) barStarts, binWidth/16, (int) barEnds);
				}
			}
		}
		try {
			TrainingHistograms.writeLine(getListBars(bars)+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Draw the values on the horizontal axis. We will plot only 1/8th of them.
		g2d.setColor(marksColor);
		g2d.setFont(fontSmall);
		FontMetrics metrics = g2d.getFontMetrics();
		int halfFontHeight = metrics.getHeight() / 2;
		for (int bin = 0; bin <= histogram.getNumBins(band); bin++) {
			if (bin % skipIndexes == 0) {
				String label = "" + (indexMultiplier * bin);
				int textHeight = metrics.stringWidth(label); // remember it will
																// be rotated!
				@SuppressWarnings("unused")
				int x = border.left + bin * binWidth + binWidth / 2;
				g2d.translate(border.left + bin * binWidth + halfFontHeight, border.top + height + textHeight + 2);
				g2d.rotate(-Math.PI / 2);
				g2d.drawString(label, 0, 0);
				g2d.rotate(Math.PI / 2);
				g2d.translate(-(border.left + bin * binWidth + halfFontHeight),
						-(border.top + height + textHeight + 2));
			}
		}
		// Draw the values on the vertical axis. Let's draw only some of them.
		double step = (int) (maxCount / verticalTicks);
		for (int l = 0; l <= verticalTicks; l++) // last will be done separately
		{
			String label;
			if (l == verticalTicks)
				label = "" + maxCount;
			else
				label = "" + (l * step);
			int textWidth = metrics.stringWidth(label);
			g2d.drawString(label, border.left - 2 - textWidth, border.top + height - l * (height / verticalTicks));
		}
		// Draw the title.
		g2d.setFont(fontLarge);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		metrics = g2d.getFontMetrics();
		int textWidth = metrics.stringWidth(title);
		g2d.drawString(title, (border.left + width + border.right - textWidth) / 2, 28);
	}

	/**
	 * This method does not do anything, it is here to keep the
	 * MouseMotionListener interface happy.
	 */
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * This method will be called when the mouse is moved over the component. It
	 * will set the tooltip text on the component to show the histogram data.
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// Don't show anything out of the plot region.
		if ((x > border.left) && (x < border.left + width) && (y > border.top) && (y < border.top + height)) {
			// Convert the X to an index on the histogram.
			x = (x - border.left) / binWidth;
			y = countsRed[x];
			setToolTipText((indexMultiplier * x) + ": " + y);
		} else {
			setToolTipText(null);
		}
	}

	private String getListBars(List<Double> list) throws IOException {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s += list.get(i) + ", ";
		}
		barsValues = s;
		return s;
	}

}
