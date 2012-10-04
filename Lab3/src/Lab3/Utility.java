package Lab3;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.jiu.codecs.CodecMode;
import net.sourceforge.jiu.codecs.PNMCodec;
import net.sourceforge.jiu.color.data.ArrayHistogram1D;
import net.sourceforge.jiu.data.Gray8Image;
import net.sourceforge.jiu.data.PixelImage;

public class Utility {

	public static void writeHistogram(ArrayHistogram1D hist, String fileName){
		try{
			FileWriter writer = new FileWriter(new File(fileName));
			for(int i=0;i<=hist.getMaxValue();i++){
				if(hist.getEntry(i)!=0)
					writer.write(i+", "+hist.getEntry(i)+"\n");
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void writeMap(Map<Integer, Float> mapToPrint, String fileName){
		try{
			FileWriter writer = new FileWriter(new File(fileName));
			for(Entry<Integer, Float> entry:mapToPrint.entrySet()){
					writer.write(entry.getKey()+", "+entry.getValue()+"\n");
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void writeImage(String filename, PixelImage pixelImage){
		PNMCodec codec = new PNMCodec();
		try {
			codec.setFile(filename, CodecMode.SAVE);
			codec.setImage(pixelImage);
			codec.setAscii(true);
			codec.process(); // actual reading of the file
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Gray8Image getGrayImageFromFile(String fileName) {
		if (fileName == null || fileName.trim().length() == 0)
			return null;
		PNMCodec codec = new PNMCodec();
		try {
			codec.setFile(fileName, CodecMode.LOAD);
			codec.process(); // actual reading of the file
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			codec.close();
		}

		PixelImage image = codec.getImage();
		if (image instanceof Gray8Image)
			return (Gray8Image) image;
		return null;
	}

}
