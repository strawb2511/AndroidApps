package com.stevengee.pegpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Piece 
{
  private int _width;
  private int _height;
  private int _pic, _shadow;
  private boolean _inFinalPos = false;
  private Point _init, _final, _pos1, _pos2, _tol;
  private Point _touchOffset;
  private Bitmap _bmpPic, _bmp_shdw;

  public Piece(int picture, int shadow, int difficulty, Context ctx) 
  {
    _pic = picture;
    _shadow = shadow;

    //Load Pic
    _bmpPic = BitmapFactory.decodeResource(ctx.getResources(), _pic);
    _bmp_shdw = BitmapFactory.decodeResource(ctx.getResources(), _shadow);

    _width = _bmpPic.getWidth();
    _height = _bmpPic.getHeight();

    SetTolerance(difficulty);
  }

  public int GetHeight()
  {
    return _height;
  }

  public int GetWidth()
  {
    return _width;
  }

  public boolean IsInFinalPosition()
  {
    return _inFinalPos;
  }
  
  public boolean HasFinalPosition()
  {
	  return (_final != null);
  }
  
  public void SetInitPosition(Point p)
  {
    _init = new Point(p.x, p.y);
    _pos1 = new Point(p.x, p.y);
    _pos2 = new Point(p.x + _width, p.y + _height);
  }

  public void SetFinalPosition(Point p)
  {
    _final = new Point(p.x, p.y);
  }
	
  public void SetTolerance(int difficulty)
  {
	  switch (difficulty)
      {
        case 1:
          _tol = new Point(_width, _height);
          break;
        case 2:
          _tol = new Point((int)Math.round(_width * 0.75), (int)Math.round(_height * 0.75));
          break;
        case 3:
          _tol = new Point((int)Math.round(_width * 0.5), (int)Math.round(_height * 0.5));
          break;
        case 4:
          _tol = new Point((int)Math.round(_width * 0.25), (int)Math.round(_height * 0.25));
          break;
      }
  }

  public void DrawPiece(Canvas canvas)
  {
    if (_final == null)
      return;
    
    Paint p = new Paint();
    if (!_inFinalPos)
      canvas.drawBitmap(_bmp_shdw, _final.x, _final.y, p);	  
    canvas.drawBitmap(_bmpPic, _pos1.x, _pos1.y, p);	  
  }
	
  public boolean IsTouched(int pointX, int pointY)
  {
    boolean touched = (pointX >= _pos1.x && pointX <= _pos2.x && pointY >= _pos1.y && pointY <= _pos2.y);
    if (touched)
      _touchOffset = new Point(pointX - _pos1.x, pointY - _pos1.y);

    return touched;
  }

  public void moveTo(int x, int y)
  {
    _pos1.x = x - _touchOffset.x;
    _pos1.y = y - _touchOffset.y;
    _pos2.x = _pos1.x + _width;
    _pos2.y = _pos1.y + _height;
  }

  public boolean IsInPosition(int x, int y)
  {
	if (_inFinalPos)
      return true;
	
	x -= _touchOffset.x;
	y -= _touchOffset.y;

    if (x > (_final.x - _tol.x) && x < (_final.x + _tol.x) &&
        y > (_final.y - _tol.y) && y < (_final.y + _tol.y))
    {
      _pos1 = _final;
      _inFinalPos = true;
      return true;
    }
    else
    {
      _pos1 = _init;
      return false;
    }		
  }
}
