package com.stevengee.pegpuzzle.util;

import android.graphics.Point;

import com.stevengee.pegpuzzle.util.CanvasImages.TreeColour;
import com.stevengee.pegpuzzle.util.CanvasImages.TreeShape;

public class Tree 
{
  private TreeShape _shape;
  private TreeColour _colour;
  private Point _topCnr;
  private int _width;
  private int _height;
  
  public TreeShape GetShape() { return _shape;}
  public TreeColour GetColour() { return _colour;}
  public Point GetTopCorner() { return _topCnr;}
  public int GetWidth() { return _width;}
  public int GetHeight() { return _height;}

  public void SetShape(TreeShape shape) { _shape = shape;}
  public void SetColour(TreeColour colour) { _colour = colour;}
  public void SetTopCorner(Point topCnr) { _topCnr = topCnr;}
  public void SetWidth(int width) { _width = width;}
  public void SetHeight(int height) { _height = height;}
}