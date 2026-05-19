package com.stevengee.pegpuzzle.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

public class CanvasImages 
{
  private Paint _treeTrunkPaint;
  private Paint _treeTopPaint;
  
  public enum TreeShape {Round, Pine, UpsideDownTriangle};
  public enum TreeColour {Green, MidGreen, DarkGreen, Red, Orange, Yellow, White}; //White must remain the last option
  
  public TreeShape GetRandomTreeShape()
  {
    double shapeNum = Math.floor(Math.random() * TreeShape.values().length);
    return TreeShape.values()[(int)shapeNum];
  }

  //Get any one of the tree colours except White
  public TreeColour GetRandomTreeColour()
  {
    double colourNum = Math.floor(Math.random() * (TreeColour.values().length-1));
    return TreeColour.values()[(int)colourNum];
  }
  
  public void DrawTree(Tree tree, Canvas canvas)
  {
    this.DrawTree(tree.GetShape(), tree.GetColour(), tree.GetTopCorner(), tree.GetWidth(), tree.GetHeight(), canvas);
  }
  
  public void DrawTree(TreeShape shape, TreeColour colour, Point topCorner, int width, int height, Canvas canvas)
  {
    switch (shape)
    {
      case UpsideDownTriangle:
        DrawTriTree(colour, topCorner, width, height, canvas);
        break;
      case Pine:
        DrawPineTree(colour, topCorner, width, height, canvas);
        break;
      case Round:
      default:
        DrawRoundTree(colour, topCorner, width, height, canvas);
        break;
    }
  }
  
  private void DrawRoundTree(TreeColour colour, Point topCorner, int width, int height, Canvas canvas)
  {
    SetTreePaint(colour);

    canvas.drawRect(topCorner.x+(width/2)-10, (float)(topCorner.y+(height*0.7)), topCorner.x+(width/2)+10, topCorner.y+height+5, _treeTrunkPaint);

    int radius;
    if (width < (height*0.7))
      radius = width/2;
    else
      radius = (int)(height*0.35);
    
    canvas.drawCircle(topCorner.x+(width/2), (float)(topCorner.y+(height*0.7)-radius)+2, radius, _treeTopPaint);
  }

  private void DrawTriTree(TreeColour colour, Point topCorner, int width, int height, Canvas canvas)
  {
    SetTreePaint(colour);

    canvas.drawRect(topCorner.x+(width/2)-10, (float)(topCorner.y+(height*0.7)), topCorner.x+(width/2)+10, topCorner.y+height+5, _treeTrunkPaint);

    int[] colors = new int[] {
    		Color.rgb(0, 40,0),
    		Color.rgb(0, 40,0),
    		Color.rgb(0, 40,0),
            0xFF000000, 0xFF000000, 0xFF000000
    };

    float verts[] = {topCorner.x, topCorner.y, topCorner.x+(width/2), (float)(topCorner.y+(height*0.7)+20), topCorner.x + width, topCorner.y};
    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 0, null, 0, 0, _treeTopPaint);
  }
  
  private void DrawPineTree(TreeColour colour, Point topCorner, int width, int height, Canvas canvas)
  {
    SetTreePaint(colour);

    canvas.drawRect(topCorner.x+(width/2)-10, (float)(topCorner.y+(height*0.7)), topCorner.x+(width/2)+10, topCorner.y+height+5, _treeTrunkPaint);

    int[] colors = new int[] {
    		Color.rgb(0, 40,0),
    		Color.rgb(0, 40,0),
    		Color.rgb(0, 40,0),
            0xFF000000, 0xFF000000, 0xFF000000
    };

    float verts[] = {topCorner.x+(width/2), topCorner.y, topCorner.x, (float)(topCorner.y+(height*0.7)+2), topCorner.x + width, (float)(topCorner.y+(height*0.7)+2)};
    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 0, null, 0, 0, _treeTopPaint);
  }

  private void SetTreePaint(TreeColour colour)
  {
    if (_treeTrunkPaint == null)
    {
      _treeTrunkPaint = new Paint();
      _treeTrunkPaint.setStyle(Style.FILL);
      _treeTrunkPaint.setColor(Color.rgb(145,50,5));
      _treeTrunkPaint.setStrokeWidth(2);
    
      _treeTopPaint = new Paint();
      _treeTopPaint.setStyle(Style.FILL);
      _treeTopPaint.setStrokeWidth(2);
      _treeTopPaint.setAntiAlias(true);
    }
    
    switch (colour)
    {
      case White:
        _treeTopPaint.setColor(Color.WHITE);
        break;
      case DarkGreen:
        _treeTopPaint.setColor(Color.rgb(0,40,0));
        break;
      case Red:
        _treeTopPaint.setColor(Color.rgb(140,35,25));
        break;
      case Orange:
        _treeTopPaint.setColor(Color.rgb(190,80,0));
        break;
      case MidGreen:
        _treeTopPaint.setColor(Color.rgb(0,180,0));
        break;
      case Green:
   	  default:
        _treeTopPaint.setColor(Color.rgb(0,100,0));
        break;
    }
  }
}
