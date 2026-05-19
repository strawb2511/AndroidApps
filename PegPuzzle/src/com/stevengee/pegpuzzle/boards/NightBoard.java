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
import android.graphics.Shader.TileMode;

public class NightBoard extends APuzzleBoardEV  
{
  private Point[] _stars;
  private CanvasImages _cI;
  private int _horizon = 0, _treeWidth, _treeHeight; 
  private Point _tree1, _tree2;
  TreeShape _tree1Shape, _tree2Shape;
  TreeColour _tree1Colour, _tree2Colour;
  
  public NightBoard(Context context)
  {
    super(context);
    _cI = new CanvasImages();
    _tree1Shape = _cI.GetRandomTreeShape();
    _tree2Shape = _cI.GetRandomTreeShape();
    _tree1Colour = _cI.GetRandomTreeColour();
    _tree2Colour = _cI.GetRandomTreeColour();
  }
	 
  public NightBoard(Context context, int numPieces) 
  {
    super(context, numPieces);
    _cI = new CanvasImages();
    _tree1Shape = _cI.GetRandomTreeShape();
    _tree2Shape = _cI.GetRandomTreeShape();
    _tree1Colour = _cI.GetRandomTreeColour();
    _tree2Colour = _cI.GetRandomTreeColour();
  }               

  protected void drawBackground(Canvas canvas)
  {
	  setCoordinates();  

    Paint gradGrassPaint = new Paint();
    gradGrassPaint.setShader(new LinearGradient(0,_horizon,0,_boardHeight,Color.rgb(0,110,0),Color.rgb(0,50,0),TileMode.CLAMP));
    canvas.drawPaint(gradGrassPaint);

    Paint gradSkyPaint = new Paint();
    gradSkyPaint.setStyle(Style.FILL);
    gradSkyPaint.setColor(Color.BLACK);
    gradSkyPaint.setShader(new LinearGradient(0,0,0,_horizon,Color.rgb(0,0,0),Color.rgb(10,10,10),TileMode.CLAMP));
    canvas.drawRect(0, 0, _boardWidth, _horizon, gradSkyPaint); //Side and Base Lines
	    
    Paint starPaint = new Paint();
    starPaint.setStyle(Style.FILL);
    starPaint.setColor(Color.WHITE);
    starPaint.setStrokeWidth(2);
	    
    if (_stars == null)
      CreateStars();
    
    for(Point p: _stars)
      canvas.drawCircle(p.x, p.y, 2, starPaint);
    
    
    _cI.DrawTree(_tree1Shape, _tree1Colour, _tree1, _treeWidth, _treeHeight, canvas);
    _cI.DrawTree(_tree2Shape, _tree2Colour, _tree2, _treeWidth, _treeHeight, canvas);
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

  private void CreateStars()
  {
    _stars = new Point[100];
    
    for(int i = 0; i < 100; i++)
    {
      int x = (int)(Math.random()*_boardWidth);
      int y = (int)(Math.random()*(_boardHeight*0.35));
      _stars[i] = new Point(x, y);
    }
  }
  
  private void setCoordinates()
  {
    if (_horizon > 0)
      return;
    
    _horizon = (int)Math.round(_boardHeight * 0.35);  
    _treeWidth = _boardWidth/6;
    _treeHeight = (int)(_horizon*0.8);
    _tree1 = new Point((int)(_boardWidth*0.25 - _treeWidth/2), (int)(_horizon*0.2));
    _tree2 = new Point((int)(_boardWidth*0.75 - _treeWidth/2), (int)(_horizon*0.2));
  }
}
