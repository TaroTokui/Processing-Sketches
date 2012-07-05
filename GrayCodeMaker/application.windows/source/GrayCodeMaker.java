import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class GrayCodeMaker extends PApplet {

/**************************************************************
 
 		GrayCodeMaker
 
 			2009/10/03	\u3068\u304f\u3044
 
 			2009/10/18	\u8ffd\u8a18
 
 			2009/10/19\u3000\u30d1\u30bf\u30fc\u30f3\u751f\u6210\u3068\u540c\u6642\u306b\u6570\u5024\u5316\u3055\u308c\u305f\u30b0\u30ec\u30a4\u30b3\u30fc\u30c9\u3082\u4f5c\u6210
 
 			2009/11/15	\u4eca\u307e\u3067\u306e\u306fbinaryCode\u3067\u3057\u305f\u3002
 
 2012/5/1    processing ni ishoku
 
 
 		\u6982\u8981\uff1a\u30b0\u30ec\u30a4\u30b3\u30fc\u30c9\u30d1\u30bf\u30fc\u30f3\u306e\u751f\u6210\u3092\u884c\u3046
 
 
 **************************************************************/

GrayCode graycode;

PImage codeImage;
int count = 0;

public void setup() {

  size( W, H );

  graycode = new GrayCode( 11 );
  
  frameRate(30);
}

public void draw() {
  graycode.drawCode( PApplet.parseInt(count/10), 1 );
  count++;
  if( count > 110 ){
    graycode.drawCode( PApplet.parseInt( (count-110)/10), 2 );
  }
  if( count > 220 ) count = 0;
  
}

static int W = 800;
static int H = 800;

public class GrayCode{
  // member
  int n;
  int lineWidth, lineWidth2;
  boolean colorFlag; // true:red false:blue
  int all;	// \u5857\u308a\u3064\u3076\u3057\u30010:\u5857\u308a\u3064\u3076\u3055\u306a\u3044 1:\u9752 2:\u8d64
  boolean saveFlag;	// \u753b\u50cf\u4fdd\u5b58\u306e\u305f\u3081\u306e\u30d5\u30e9\u30b0
  boolean line1;		// true:\u4e00\u5217\u76ee\u306f\u5e45n\u306e\u7dda\u3092\u5f15\u304f\u3001false:2\u5217\u76ee\u4ee5\u964d\u306f\u5e452\u306e\u7dda\u3092\u5f15\u304f
  boolean autoFlag;	// \u81ea\u52d5\u7684\u306b\u7e1e\u306e\u9593\u9694\u3092\u5909\u66f4
  int count;
  int Black, White;
  
  int[] patternX;	// \u30b0\u30ec\u30a4\u30b3\u30fc\u30c9\u30d1\u30bf\u30fc\u30f3\u3092\u683c\u7d0d\u3059\u308b\u914d\u5217\u30010010101\u3068\u304b\u304c\u5165\u308b
  int[] patternY;
  
  // constructor
  GrayCode( int n ){
    Black = color( 0, 0, 0 );
    White = color(255, 255, 255);
    	n=0;

	all=1;	// \u5857\u308a\u3064\u3076\u3057\u30010:\u5857\u308a\u3064\u3076\u3055\u306a\u3044 1:\u9752 2:\u8d64

	saveFlag = false;	// \u753b\u50cf\u4fdd\u5b58\u306e\u305f\u3081\u306e\u30d5\u30e9\u30b0

	autoFlag = false;	// \u81ea\u52d5\u7684\u306b\u7e1e\u306e\u9593\u9694\u3092\u5909\u66f4

	count = 0;

	

	// patternX, Y\u3067\u4f7f\u3046\u30e1\u30e2\u30ea\u3092\u78ba\u4fdd

	patternX = new int[W];

	patternY = new int[H];

	// \u5168\u90e80\u3067\u521d\u671f\u5316

	for(int i=0; i<W; i++){

		patternX[i] = 0;

	}

	for(int i=0; i<H; i++){

		patternY[i] = 0;

	}



	// \u305d\u308c\u305e\u308c\u306e\u30d1\u30bf\u30fc\u30f3\u3092\u5165\u308c\u308b

	/* \u7e26\u30d1\u30bf\u30fc\u30f3(X\u65b9\u5411\u306b\u5909\u5316) */

	for(int m=0; m<11; m++){

		colorFlag = true;	// \u4e00\u5217\u76ee\u306e\u8272\u306f\u8d64

		lineWidth = (int)pow((float)2,(float)m);	// 2^n\u306e\u592a\u3055\u306e\u7dda\u3092\u5f15\u304f

		lineWidth2 = 2*lineWidth;

		line1 = true;

		for(int i=0; i<W; i++){

			if(line1){	// \u4e00\u884c\u76ee\u306a\u3089

				if( (i%lineWidth==0) && i!=0 ){

					colorFlag = !colorFlag;	// \u7dda\u306e\u592a\u3055\u3067\u5272\u308a\u5207\u308c\u305f\u3089\u8272\u3092\u5909\u3048\u308b

					line1 = false;

				}

			}else{

				if( (i%lineWidth2==lineWidth) && i!=0 ){

					colorFlag = !colorFlag;	// \u7dda\u306e\u592a\u3055\u3067\u5272\u308a\u5207\u308c\u305f\u3089\u8272\u3092\u5909\u3048\u308b

				}

			}

			if(colorFlag) patternX[i] += lineWidth;

		}

	}



	/* \u6a2a\u30d1\u30bf\u30fc\u30f3(Y\u65b9\u5411\u306b\u5909\u5316) */

	for(int m=0; m<11; m++){

		colorFlag = true;	// \u4e00\u5217\u76ee\u306e\u8272\u306f\u8d64

		lineWidth = (int)pow((float)2,(float)m);	// 2^n\u306e\u592a\u3055\u306e\u7dda\u3092\u5f15\u304f

		lineWidth2 = 2*lineWidth;					// 2^n*2\u306e\u5e45\u306e\u7dda

		for(int j=0; j<H; j++){

			if(line1){	// \u4e00\u884c\u76ee\u306a\u3089

				if( (j%lineWidth==0) && j!=0 ){

					colorFlag = !colorFlag;	// \u7dda\u306e\u592a\u3055\u3067\u5272\u308a\u5207\u308c\u305f\u3089\u8272\u3092\u5909\u3048\u308b

					line1 = false;

				}

			}else{

				if( (j%lineWidth2==lineWidth) && j!=0 ){

					colorFlag = !colorFlag;	// \u7dda\u306e\u592a\u3055\u3067\u5272\u308a\u5207\u308c\u305f\u3089\u8272\u3092\u5909\u3048\u308b

				}

			}

			if(colorFlag) patternY[j] += lineWidth;

		}

	}

    println( "initialized" );

  }
  
  public void drawCode( int w, int dir ){
    loadPixels();
    n = w;
    lineWidth = (int)pow((float)2,(float)n);	// 2^n\u306e\u592a\u3055\u306e\u7dda\u3092\u5f15\u304f
    switch(dir){
      case 0:
        // \u4e00\u8272\u3067\u5857\u308a\u3064\u3076\u3059
        println( W );
        for(int i=0; i<W; i++){
          for(int j=0; j<H; j++){
            if(all==1){
              pixels[j*W+i] = Black;
            }else if(all==2){
              pixels[j*W+i] = White;
            }
          }
        }
        break;
        
      case 1:
        for(int j=0; j<H; j++){
          if((patternY[j] & lineWidth) == lineWidth){	// pattern.x\u306enBit\u76ee\u306e\u6570\u5b57\u304c1\u3060\u3063\u305f\u3089
            for(int i=0; i<W; i++){
              pixels[j*W+i] = Black;
            }
          }else{
            for(int i=0; i<W; i++){
              pixels[j*W+i] = White;
            }
          }
        }
        break;
        
      case 2:
        for(int i=0; i<W; i++){
          if((patternX[i] & lineWidth) == lineWidth){
            for(int j=0; j<H; j++){
              pixels[j*W+i] = Black;
            }
          }else{
            for(int j=0; j<H; j++){
              pixels[j*W+i] = White;
            }
          }
        }
        break;
      default:
      break;
    }
    updatePixels();
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "GrayCodeMaker" });
  }
}
