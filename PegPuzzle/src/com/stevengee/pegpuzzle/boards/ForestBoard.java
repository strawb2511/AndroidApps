package com.stevengee.pegpuzzle.boards;

import com.stevengee.pegpuzzle.util.CanvasImages;
import com.stevengee.pegpuzzle.util.Tree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Shader.TileMode;

public class ForestBoard extends APuzzleBoardEV  
{
  private CanvasImages _cI;
  private Tree[] _trees;
	  
  public ForestBoard(Context context)
  {
    super(context);
    _cI = new CanvasImages();
  }
 
  public ForestBoard(Context context, int numPieces) 
  {
    super(context, numPieces);
    _cI = new CanvasImages();
  }               

  protected void drawBackground(Canvas canvas)
  {
	int horizon = (int)Math.round(_boardHeight * 0.25);  

    Paint gradGrassPaint = new Paint();
    gradGrassPaint.setShader(new LinearGradient(0,horizon,0,_boardHeight,Color.rgb(80,50,30),Color.rgb(70,45,30),TileMode.CLAMP));
    canvas.drawPaint(gradGrassPaint);

    
    Paint gradSkyPaint = new Paint();
    gradSkyPaint.setStyle(Style.FILL);
    gradSkyPaint.setColor(Color.BLUE);
    gradSkyPaint.setShader(new LinearGradient(0,0,0,horizon,Color.rgb(160,230,255),Color.rgb(230,250,255),TileMode.CLAMP));
    canvas.drawRect(0, 0, _boardWidth, horizon, gradSkyPaint); //Side and Base Lines
    
    drawTrees(canvas);
  }
  
  protected void setPieceLocations()
  {
    _locations = new Point[10][2];
    int secWidth = (int)Math.floor(_boardWidth/5);
    int topRow = (int)Math.round(_boardHeight * 0.25);
    int btmRow = (int)Math.round(_boardHeight * 0.75);
    
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

  private void drawTrees(Canvas canvas)
  {
	if (_trees == null)
	{
	  _trees = new Tree[50];
	  int skyLine = (int)(_boardHeight*(0.05));
	  int halfHeight = (int)(_boardHeight/2);
	  int minWidth = (int)(_boardWidth*(0.08));
	  int minHeight = (int)(_boardHeight*(0.2));
	  int heightDelta = (int)(_boardHeight*(0.3));
	  
      for(int i = 0; i < 50; i++)
      {
        Tree t = new Tree();
        t.SetShape(_cI.GetRandomTreeShape());
        t.SetColour(_cI.GetRandomTreeColour());
        Point p = new Point();
        p.x = (int)(Math.random()*_boardWidth) - 50;
        p.y = (int)(Math.random()*halfHeight) + skyLine;
        t.SetTopCorner(p);
        t.SetWidth((int)(Math.random()*minWidth) + minWidth);
        t.SetHeight((int)(Math.random()*heightDelta) + minHeight);        
        _trees[i] = t;
      }
	}
	
	for(Tree tree: _trees)
	{
	  _cI.DrawTree(tree, canvas);
	}
  }
}