/**************************************************************
 
 		GrayCodeMaker
 
 			2009/10/03	とくい
 
 			2009/10/18	追記
 
 			2009/10/19　パターン生成と同時に数値化されたグレイコードも作成
 
 			2009/11/15	今までのはbinaryCodeでした。
 
 2012/5/1    processing ni ishoku
 
 
 		概要：グレイコードパターンの生成を行う
 
 
 **************************************************************/

GrayCode graycode;

PImage codeImage;
int count = 0;

void setup() {

  size( W, H );

  graycode = new GrayCode( 11 );
  
  frameRate(30);
}

void draw() {
  graycode.drawCode( int(count/10), 1 );
  count++;
  if( count > 110 ){
    graycode.drawCode( int( (count-110)/10), 2 );
  }
  if( count > 220 ) count = 0;
  
}

