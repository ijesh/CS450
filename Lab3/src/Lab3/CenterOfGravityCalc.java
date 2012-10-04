package Lab3;

import net.sourceforge.jiu.data.Gray8Image;
import net.sourceforge.jiu.data.MemoryGray8Image;

public class CenterOfGravityCalc {

	Gray8Image image;
	Gray8Image imageMin1;

	public CenterOfGravityCalc(Gray8Image image, Gray8Image imageMin1) {
		this.image = image;
		this.imageMin1 = imageMin1;
	}

	public Gray8Image getObjectImage() {
		long size = 0; // image.getWidth() * image.getHeight();
		if (image.getWidth() != imageMin1.getWidth()
				|| image.getHeight() != imageMin1.getHeight())
			return null;
		Gray8Image objImage = new MemoryGray8Image(image.getWidth(),
				image.getHeight());
		long x = 0L;
		long y = 0L;
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				int diff = image.getSample(i, j) - imageMin1.getSample(i, j);
				objImage.putSample(i, j, Math.abs(diff));
				int diffSq = diff;
				x += i * diffSq;
				y += j * diffSq;
				size += diffSq;
			}
		}
		System.out.println("Center of gravity is (x,y)= (" + x / size + ", "
				+ y / size + ")");

		return objImage;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 3) {
			System.err
					.println("Usage: CenterGravityCalc inputFile inputMin1File outputFile");
			return;
		}
		Gray8Image diffImage = new CenterOfGravityCalc(
				Utility.getGrayImageFromFile(args[0]),
				Utility.getGrayImageFromFile(args[1])).getObjectImage();

		Utility.writeImage(args[2], diffImage);
	}

}
