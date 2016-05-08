package com.util;

import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.RenderedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

public class DisplayTwoSynchronizedImages extends JPanel implements AdjustmentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -758837901506185556L;
	protected DisplayJAI dj1;
	protected DisplayJAI dj2;
	protected JScrollPane jsp1;
	protected JScrollPane jsp2;

	public DisplayTwoSynchronizedImages(RenderedImage im1, RenderedImage im2) {
		super();
		// Cria componente com duas imagens com JScrollPanes
		setLayout(new GridLayout(1,2));
		dj1 = new DisplayJAI(im1);
		dj2 = new DisplayJAI(im2);
		jsp1 = new JScrollPane(dj1);
		jsp2 = new JScrollPane(dj2);
		add(jsp1);
		add(jsp2);
		// Registra listeners para os scroll bars do JScrollPanes
		jsp1.getHorizontalScrollBar().addAdjustmentListener(this);
		jsp1.getVerticalScrollBar().addAdjustmentListener(this);
		jsp2.getHorizontalScrollBar().addAdjustmentListener(this);
		jsp2.getVerticalScrollBar().addAdjustmentListener(this);
	}

	public void adjustmentValueChanged(AdjustmentEvent e)	{
		if (e.getSource() == jsp1.getHorizontalScrollBar())
			jsp2.getHorizontalScrollBar().setValue(e.getValue());
		if (e.getSource() == jsp1.getVerticalScrollBar())
			jsp2.getVerticalScrollBar().setValue(e.getValue());
		if (e.getSource() == jsp2.getHorizontalScrollBar())
			jsp1.getHorizontalScrollBar().setValue(e.getValue());
		if (e.getSource() == jsp2.getVerticalScrollBar())
			jsp1.getVerticalScrollBar().setValue(e.getValue());
	}

}
