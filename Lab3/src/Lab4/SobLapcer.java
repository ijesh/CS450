package Lab4;

import Lab3.Utility;
import net.sourceforge.jiu.data.Gray8Image;
import net.sourceforge.jiu.data.MemoryGray8Image;

public class SobLapcer {
//ad
	private static final int NumOfArgs=3; 
	
	int windowSize, oldWidth, newWidth, oldHeight, newHeight;
	Gray8Image input;	
	int[][] extendedInput;
	
	public SobLapcer(int windowSize, Gray8Image input){
		this.windowSize=windowSize;
		this.input=input;
		this.oldWidth=input.getWidth();
		this.oldHeight=input.getHeight();
		this.newHeight=oldHeight+windowSize-1;
		this.newWidth=oldWidth+windowSize-1;
		generateExtendedImage2DArray(windowSize, input);
		
	}

	private void generateExtendedImage2DArray(int windowSize, Gray8Image input) {
		this.extendedInput=new int[newWidth][newHeight];
		
		//create extended input image's 2d array
		for (int j = 0; j < newHeight; j++) {
			for (int i = 0; i < newWidth; i++) {
				int mappedI=i-windowSize/2;
				int mappedJ=j-windowSize/2;
				if( mappedJ < 0 && mappedI<0){
						extendedInput[i][j]=input.getSample(0, 0);
				}else if(mappedJ<0 && mappedI>=0){
					if(mappedI>=oldWidth)
						mappedI=oldWidth-1;
					extendedInput[i][j]=input.getSample(mappedI, 0);
				}else if(mappedI<0 && mappedJ>=0){
					if(mappedJ>=oldHeight)
						mappedJ=oldHeight-1;
					extendedInput[i][j]=input.getSample(0, mappedJ);
				}else if(mappedJ>=oldHeight && mappedI>=oldWidth){
					extendedInput[i][j]=input.getSample(oldWidth-1, oldHeight-1);
				}else if(mappedJ>=oldHeight && mappedI<oldWidth){
					extendedInput[i][j]=input.getSample(mappedI, oldHeight-1);
				}else if(mappedJ<oldHeight && mappedI>=oldWidth){
					extendedInput[i][j]=input.getSample(oldWidth-1, mappedJ);
				}else{
					extendedInput[i][j]=input.getSample(mappedI, mappedJ);
				}
			}			
		}
	}
	
	
	//Laplacian kernel implementation
	public Gray8Image maskLaplacian(){
		Gray8Image output=new MemoryGray8Image(oldWidth, oldHeight);
		int[][] lapMask = {{0,1,0},{1,-4,1},{0,1,0}};
		int[][] lapImage = applyMask(extendedInput, lapMask, oldWidth, newHeight);
		return output;
	}
	

	public Gray8Image maskSobel(){
		Gray8Image output=new MemoryGray8Image(oldWidth, oldHeight);
		int[][] sobXMask = {{-1,0,1},{-2,0,2},{-1,0,1}};
		int[][] sobImageX = applyMask(extendedInput, sobXMask, oldWidth, newHeight);
		int[][] sobYMask = {{-1,-2,-1},{0,0,0},{1,2,1}};
		int[][] sobImageY = applyMask(extendedInput, sobYMask, oldWidth, newHeight);
		return output;
	} 
	
	public static int[][] applyMask(int[][] input, int[][] mask, int imageWidth, int imageHeight){
		int[][] output = new int[imageWidth][imageHeight];
		for(int j=0;j<imageHeight;j++){
			for(int i=0;j<imageWidth;i++){
				int total=0;
				for(int maskJ=0;maskJ<mask.length;maskJ++){
					for(int maskI=0;maskI<mask[maskI].length;maskI++){
						total += input[i+maskI][j+maskJ] * mask[maskI][maskJ];
					}
				}
				output[i][j]=total;
			}
		}
		return output;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length !=NumOfArgs){
			System.err.println("Usage: SobLapcer [-s|-l|-sg] inputFilePath outputFilePath");
			System.exit(0);
		}
		int windowSize = 3;
		SobLapcer soblapcer= new SobLapcer(windowSize, Utility.getGrayImageFromFile(args[2]));
		if(args[0].equals("-s")){
		}
		else if(args[0].equals("-l")){
		}
		else if(args[0].equals("-sg")){
			
		}
	}

}
