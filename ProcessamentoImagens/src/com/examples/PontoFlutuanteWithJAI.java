package com.examples;

import java.awt.Point;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferFloat;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;

public class PontoFlutuanteWithJAI {

	public static void main(String[] args) throws IOException {
		int width = 256;
		int height = 256;
		float[] imageData = new float[width * height];
		int count = 0;
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				imageData[count++] = 20f * w * h;
			}
			DataBufferFloat dbuffer = new DataBufferFloat(imageData, width * height);
			SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_FLOAT, width, height, 1);
			ColorModel colorModel = PlanarImage.createColorModel(sampleModel);
			WritableRaster raster =
					RasterFactory.createWritableRaster(sampleModel,dbuffer,new Point(0,0));
			TiledImage tiledImage = new TiledImage(0,0,width,height,0,0,sampleModel,colorModel);
			tiledImage.setData(raster);
			JAI.create("filestore",tiledImage,"floatpattern.tif","TIFF");
		}

	}

}
