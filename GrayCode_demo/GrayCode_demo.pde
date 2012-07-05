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
int count = 4;

void setup() {

  size( W, H );

  graycode = new GrayCode( 11 );
  
  frameRate(30);
}

void draw() {
  graycode.drawCode( int(count), 1 );
  if( count > 10 ){
    graycode.drawCode( int(count-11), 2 );
  }
  noFill();
  stroke(255,0,0);
  strokeWeight(4);
  rect(207, 207, 18, 18 );
}

void keyPressed(){
  count++;
  if( count == 10 ) count = 15;
  if(count>20) count = 4;
}

