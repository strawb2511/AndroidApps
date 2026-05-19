package com.stevengee.pegpuzzle.boards;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Shader.TileMode;

public class GrassBoard extends APuzzleBoardEV  
{
  public GrassBoard(Context context)
  {
    super(context);
  }
 
  public GrassBoard(Context context, int numPieces) 
  {
    super(context, numPieces);
  }               

  protected void drawBackground(Canvas canvas)
  {
	int horizon = (int)Math.round(_boardHeight * 0.25);  

    Paint gradGrassPaint = new Paint();
    gradGrassPaint.setShader(new LinearGradient(0,horizon,0,_boardHeight,Color.rgb(0,110,0),Color.rgb(0,50,0),TileMode.CLAMP));
    canvas.drawPaint(gradGrassPaint);

    
    Paint gradSkyPaint = new Paint();
    gradSkyPaint.setStyle(Style.FILL);
    gradSkyPaint.setColor(Color.BLUE);
    //gradSkyPaint.setStyle(Style.STROKE);
    //gradSkyPaint.setStrokeWidth(2);
    gradSkyPaint.setShader(new LinearGradient(0,0,0,horizon,Color.rgb(160,230,255),Color.rgb(230,250,255),TileMode.CLAMP));
    canvas.drawRect(0, 0, _boardWidth, horizon, gradSkyPaint); //Side and Base Lines
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
}