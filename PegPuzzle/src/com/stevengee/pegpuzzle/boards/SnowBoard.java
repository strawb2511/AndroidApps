package com.stevengee.pegpuzzle.boards;

import com.stevengee.pegpuzzle.util.CanvasImages;
import com.stevengee.pegpuzzle.util.CanvasImages.TreeColour;
import com.stevengee.pegpuzzle.util.CanvasImages.TreeShape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;

public class SnowBoard extends APuzzleBoardEV  
{
  Paint _gradGroundPaint, _gradSkyPaint, _snowFlakePaint;
  Paint _snowManBasePaint, _snowManBodyPaint, _snowManHeadPaint;
  Paint _snowManBlackPaint, _snowManOrangePaint, _snowManBrownPaint;
  int _horizon = 0, _treeWidth, _treeHeight; 
  float _snowManX, _snowManBaseY, _snowManBodyY, _snowManHeadY;
  Point _tree1, _tree2;
  TreeShape _tree1Shape, _tree2Shape;
  CanvasImages _cI;

  public SnowBoard(Context context)
  {
    super(context);
    _cI = new CanvasImages();
    _tree1Shape = _cI.GetRandomTreeShape();
    _tree2Shape = _cI.GetRandomTreeShape();
  }
		 
  public SnowBoard(Context context, int numPieces) 
  {
    super(context, numPieces);
    _cI = new CanvasImages();
    _tree1Shape = _cI.GetRandomTreeShape();
    _tree2Shape = _cI.GetRandomTreeShape();
  }               

  private void setPaints()
  {
    if (_gradGroundPaint != null)
      return;
    
    _gradGroundPaint = new Paint();
    _gradGroundPaint.setShader(new LinearGradient(0,_horizon,0,_boardHeight,Color.rgb(255,255,255),Color.rgb(220,220,220),TileMode.CLAMP));

    _gradSkyPaint = new Paint();
    _gradSkyPaint.setStyle(Style.FILL);
    _gradSkyPaint.setColor(Color.BLACK);
    _gradSkyPaint.setShader(new LinearGradient(0,0,0,_horizon,Color.rgb(100,100,100),Color.rgb(130,130,130),TileMode.CLAMP));
    
    _snowFlakePaint = new Paint();
    _snowFlakePaint.setStyle(Style.FILL);
    _snowFlakePaint.setColor(Color.WHITE);
    _snowFlakePaint.setStrokeWidth(2);

    _snowManBasePaint = new Paint();
    _snowManBasePaint.setStyle(Style.FILL);
    _snowManBasePaint.setStrokeWidth(2);
    _snowManBasePaint.setColor(Color.WHITE);
    _snowManBasePaint.setShader(new RadialGradient(_snowManX, _snowManBaseY,50,Color.rgb(255,255,255),Color.rgb(230,230,230),TileMode.CLAMP));

    _snowManBodyPaint = new Paint();
    _snowManBodyPaint.setStyle(Style.FILL);
    _snowManBodyPaint.setStrokeWidth(2);
    _snowManBodyPaint.setColor(Color.WHITE);
    _snowManBodyPaint.setShader(new RadialGradient(_snowManX, _snowManBodyY,50,Color.rgb(255,255,255),Color.rgb(230,230,230),TileMode.CLAMP));

    _snowManHeadPaint = new Paint();
    _snowManHeadPaint.setStyle(Style.FILL);
    _snowManHeadPaint.setStrokeWidth(2);
    _snowManHeadPaint.setColor(Color.WHITE);
    _snowManHeadPaint.setShader(new RadialGradient(_snowManX, _snowManHeadY,50,Color.rgb(255,255,255),Color.rgb(230,230,230),TileMode.CLAMP));

    _snowManBlackPaint = new Paint();
    _snowManBlackPaint.setStyle(Style.FILL);
    _snowManBlackPaint.setColor(Color.BLACK);
    _snowManBlackPaint.setStrokeWidth(1);

    _snowManOrangePaint = new Paint();
    _snowManOrangePaint.setStyle(Style.FILL);
    _snowManOrangePaint.setStrokeWidth(1);
    _snowManOrangePaint.setColor(Color.rgb(255,80,0));

    _snowManBrownPaint = new Paint();
    _snowManBrownPaint.setStyle(Style.FILL);
    _snowManBrownPaint.setColor(Color.rgb(145,50,5));
    _snowManBrownPaint.setStrokeWidth(2);
  }
  
  protected void drawBackground(Canvas canvas)
  {
    setCoordinates();
    setPaints();
    
    canvas.drawPaint(_gradGroundPaint); //ground
    canvas.drawRect(0, 0, _boardWidth, _horizon, _gradSkyPaint); //Sky
	    
    //Snow
    for(int i = 0; i < 250; i++)
    {
      int x = (int)(Math.random()*_boardWidth);
      int y = (int)(Math.random()*(_boardHeight*0.35));
      canvas.drawCircle(x, y, 2, _snowFlakePaint);
    }
    
    //Snowman body
    canvas.drawCircle(_snowManX, _snowManBaseY, 50, _snowManBasePaint);
    canvas.drawCircle(_snowManX, _snowManBodyY, 35, _snowManBodyPaint);
    canvas.drawCircle(_snowManX, _snowManHeadY, 20, _snowManHeadPaint);
    
    //Snowman buttons 
    canvas.drawCircle(_snowManX, _snowManBodyY+15, 3, _snowManBlackPaint);
    canvas.drawCircle(_snowManX, _snowManBodyY, 3, _snowManBlackPaint);
    canvas.drawCircle(_snowManX, _snowManBodyY-15, 3, _snowManBlackPaint);
    
    //Snowman Eyes
    canvas.drawCircle(_snowManX-5, _snowManHeadY-5, 3, _snowManBlackPaint);
    canvas.drawCircle(_snowManX+5, _snowManHeadY-5, 3, _snowManBlackPaint);

    //Snowman Mouth
    canvas.drawCircle(_snowManX, _snowManHeadY+9, 2, _snowManBlackPaint);
    canvas.drawCircle(_snowManX-4, _snowManHeadY+8, 2, _snowManBlackPaint);
    canvas.drawCircle(_snowManX+4, _snowManHeadY+8, 2, _snowManBlackPaint);
    canvas.drawCircle(_snowManX-8, _snowManHeadY+6, 2, _snowManBlackPaint);
    canvas.drawCircle(_snowManX+8, _snowManHeadY+6, 2, _snowManBlackPaint);

    //Snowman Nose
    canvas.drawCircle(_snowManX, _snowManHeadY, 3, _snowManOrangePaint);

    //Snowman arms
    canvas.drawLine(_snowManX-30, _snowManBodyY-5, _snowManX-80, _snowManBodyY+5, _snowManBrownPaint);
    canvas.drawLine(_snowManX+30, _snowManBodyY-5, _snowManX+80, _snowManBodyY+5, _snowManBrownPaint);
    
    _cI.DrawTree(_tree1Shape, TreeColour.White, _tree1,_treeWidth, _treeHeight, canvas);
    _cI.DrawTree(_tree2Shape, TreeColour.White, _tree2, _treeWidth, _treeHeight, canvas);
  }
		  
  protected void setPieceLocations()
  {
    _locations = new Point[10][2];
    int secWidth = (int)Math.floor(_boardWidth/5);
    int topRow = (int)Math.round(_boardHeight * 0.35);
    int btmRow = (int)Math.round(_boardHeight * 0.725);
		    
    int i;
    for(i = 0; i < 5; i++)
    {
      _locations[i][0] = new Point(i*secWidth, topRow);
      _locations[i][1] = new Point((i+1)*secWidth-1,btmRow-1);
    }
    for(i = 0; i < 5; i++)
    {
      _locations[i+5][0] = new Point(i*secWidth, btmRow);
      _locations[i+5][1] = new Point((i+1)*secWidth-1,_boardHeight);
    }
  }

  private void setCoordinates()
  {
    if (_horizon > 0)
      return;
    
    _horizon = (int)Math.round(_boardHeight * 0.35);  
    _snowManX = _boardWidth/2;
    _snowManBaseY = (float)(_boardHeight*0.35-45);
    _snowManBodyY = (float)(_boardHeight*0.35-120);
    _snowManHeadY = (float)(_boardHeight*0.35-170);
    _treeWidth = _boardWidth/6;
    _treeHeight = (int)(_horizon*0.8);
    _tree1 = new Point((int)(_boardWidth*0.25 - _treeWidth/2), (int)(_horizon*0.2));
    _tree2 = new Point((int)(_boardWidth*0.75 - _treeWidth/2), (int)(_horizon*0.2));
  }
}