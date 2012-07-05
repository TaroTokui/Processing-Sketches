static int W = 800;
static int H = 800;

public class GrayCode{
  // member
  int n;
  int lineWidth, lineWidth2;
  boolean colorFlag; // true:red false:blue
  int all;	// 塗りつぶし、0:塗りつぶさない 1:青 2:赤
  boolean saveFlag;	// 画像保存のためのフラグ
  boolean line1;		// true:一列目は幅nの線を引く、false:2列目以降は幅2の線を引く
  boolean autoFlag;	// 自動的に縞の間隔を変更
  int count;
  color Black, White;
  
  int[] patternX;	// グレイコードパターンを格納する配列、0010101とかが入る
  int[] patternY;
  
  // constructor
  GrayCode( int n ){
    Black = color( 0, 0, 0 );
    White = color(255, 255, 255);
    	n=0;

	all=1;	// 塗りつぶし、0:塗りつぶさない 1:青 2:赤

	saveFlag = false;	// 画像保存のためのフラグ

	autoFlag = false;	// 自動的に縞の間隔を変更

	count = 0;

	

	// patternX, Yで使うメモリを確保

	patternX = new int[W];

	patternY = new int[H];

	// 全部0で初期化

	for(int i=0; i<W; i++){

		patternX[i] = 0;

	}

	for(int i=0; i<H; i++){

		patternY[i] = 0;

	}



	// それぞれのパターンを入れる

	/* 縦パターン(X方向に変化) */

	for(int m=0; m<11; m++){

		colorFlag = true;	// 一列目の色は赤

		lineWidth = (int)pow((float)2,(float)m);	// 2^nの太さの線を引く

		lineWidth2 = 2*lineWidth;

		line1 = true;

		for(int i=0; i<W; i++){

			if(line1){	// 一行目なら

				if( (i%lineWidth==0) && i!=0 ){

					colorFlag = !colorFlag;	// 線の太さで割り切れたら色を変える

					line1 = false;

				}

			}else{

				if( (i%lineWidth2==lineWidth) && i!=0 ){

					colorFlag = !colorFlag;	// 線の太さで割り切れたら色を変える

				}

			}

			if(colorFlag) patternX[i] += lineWidth;

		}

	}



	/* 横パターン(Y方向に変化) */

	for(int m=0; m<11; m++){

		colorFlag = true;	// 一列目の色は赤

		lineWidth = (int)pow((float)2,(float)m);	// 2^nの太さの線を引く

		lineWidth2 = 2*lineWidth;					// 2^n*2の幅の線

		for(int j=0; j<H; j++){

			if(line1){	// 一行目なら

				if( (j%lineWidth==0) && j!=0 ){

					colorFlag = !colorFlag;	// 線の太さで割り切れたら色を変える

					line1 = false;

				}

			}else{

				if( (j%lineWidth2==lineWidth) && j!=0 ){

					colorFlag = !colorFlag;	// 線の太さで割り切れたら色を変える

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
    lineWidth = (int)pow((float)2,(float)n);	// 2^nの太さの線を引く
    switch(dir){
      case 0:
        // 一色で塗りつぶす
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
          if((patternY[j] & lineWidth) == lineWidth){	// pattern.xのnBit目の数字が1だったら
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
