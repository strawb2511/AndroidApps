package com.stevengee.pegpuzzle.boards;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.stevengee.pegpuzzle.Piece;

public abstract class APuzzleBoardEV extends View
{
  private Piece _selectedPiece = null;
  private Piece[] _pieces;
  private int _currentNumPieces = 0;
  private int _numPieces = 3;
  private Point _piecePos = new Point(5, 5);
  protected int _boardHeight, _boardWidth;
  private boolean _allPiecesInPos = false;
  protected Paint _newBtnPaint, _newTextPaint;
  private OnResetEventListener _resetListener;
  protected Point[][] _locations;
  protected Point[][] _finalLocations;
  
  public interface OnResetEventListener
  {
    public void onReset();
  }
  
  APuzzleBoardEV(Context context)
  {
    super(context);
    setFocusable(true);
    setFocusableInTouchMode(true);
    
    _pieces = new Piece[_numPieces];

    setAllPaint();
  }
  
  public APuzzleBoardEV(Context context, int numPieces) 
  {
    super(context);
    setFocusable(true);
    setFocusableInTouchMode(true);

    _numPieces = numPieces;
    _pieces = new Piece[_numPieces];

    setAllPaint();
  } 
  
  protected void setAllPaint()
  {
    _newBtnPaint = new Paint();
    _newBtnPaint.setDither(true);
    _newBtnPaint.setAntiAlias(true);
    _newBtnPaint.setColor(Color.WHITE);
    _newBtnPaint.setStyle(Paint.Style.STROKE);
    _newBtnPaint.setStrokeJoin(Paint.Join.MITER);
    _newBtnPaint.setStrokeWidth(5f);
    _newBtnPaint.setStyle(Style.FILL);

    _newTextPaint = new Paint();
    _newTextPaint.setDither(true);
    _newTextPaint.setAntiAlias(true);
    _newTextPaint.setColor(Color.BLACK);
    _newTextPaint.setStyle(Paint.Style.STROKE);
    _newTextPaint.setStrokeJoin(Paint.Join.MITER);
    _newTextPaint.setStrokeWidth(15f);
    _newTextPaint.setStyle(Style.FILL);
  }
  
  public void AddPiece(Piece piece) throws Exception 
  {
    if (_currentNumPieces == _numPieces)
      throw new Exception("Too many pieces");

    _pieces[_currentNumPieces] = piece;
    _currentNumPieces ++;

    piece.SetInitPosition(_piecePos);

    _piecePos.y += piece.GetHeight() + 20;
  }

  @Override
  protected void onDraw(Canvas canvas) 
  {
    _boardHeight = getHeight();
    _boardWidth = getWidth();
    
    if (_locations == null)
    {
      setPieceLocations();
      setFinalLocations();
    }
    
    drawBackground(canvas);

    int i = 0;
    for (Piece p : _pieces)
    {
      if (p != null)
      {
        if (!p.HasFinalPosition())
        {
          Point pt = getExactLocation(_finalLocations[i][0], _finalLocations[i][1], p.GetWidth(), p.GetHeight());
          p.SetFinalPosition(pt);
        }
        p.DrawPiece(canvas);
      }
      i++;
    }
    
    if (_allPiecesInPos)
    {
      canvas.drawRect(20, 20, 170, 170, _newBtnPaint);
      canvas.drawText("New", 80, 80, _newTextPaint);
    }
  }

  abstract protected void drawBackground(Canvas canvas);
  abstract protected void setPieceLocations();
  
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
   	int pointX = (int)event.getX();
    int pointY = (int)event.getY();

    switch (event.getAction()) 
    {
      case MotionEvent.ACTION_DOWN:
        determinePlayerSelected(pointX, pointY);
        break;
      case MotionEvent.ACTION_MOVE:
    	if (_selectedPiece != null)
    	{
          _selectedPiece.moveTo(pointX, pointY);
          invalidate();
    	}
        break;
      case MotionEvent.ACTION_UP:
    	  checkPiecesPosition(pointX, pointY);
      	break;
    }
    return true;
  }

  private void checkPiecesPosition(int x, int y)
  {
  	if (_selectedPiece != null)
  	{
      if (_selectedPiece.IsInPosition(x, y))
        CheckAllPiecesInPos();
      invalidate();
	  _selectedPiece = null; //deselect current player
  	}
  }

  private void determinePlayerSelected(int pointX, int pointY)
  {
    _selectedPiece = null;

    if (_allPiecesInPos &&
    	(pointX >= 20 && pointX <= 170 && pointY >= 20 && pointY <= 150))
    {
    	_resetListener.onReset();
    	return;
    }
    	
    for (Piece p : _pieces)
    {
      if (p == null)
        continue;
    	  
      if (p.IsTouched(pointX, pointY))
        _selectedPiece = p;
    }
  }
  
  private void CheckAllPiecesInPos()
  {
    _allPiecesInPos = false;
	  
    for (Piece p : _pieces)
    {
      if (p != null && !p.IsInFinalPosition())
        return;
    }
    
    _allPiecesInPos = true;
  }

  public void UpdateTolerance(int difficulty)
  {
    for(Piece p: _pieces)
    {
      if (p!= null)
        p.SetTolerance(difficulty);
    }
  }
  
  public void ResetBoard()
  {
    _selectedPiece = null;
    _pieces = new Piece[_numPieces];
    _currentNumPieces = 0;
    _piecePos = new Point(5, 5);
    invalidate();
  }

  public void setCustomEventListener(OnResetEventListener eventListener) 
  {
	  _resetListener = eventListener;
  }

  private void setFinalLocations()
  {
    _finalLocations = new Point[_numPieces][2];

    if (_numPieces >= _locations.length)
    {
      int i;
      for(i = 0; i < _locations.length; i++)
        _finalLocations[i] = _locations[i];

      for (int j = i; j < _numPieces; j++)
        _finalLocations[i] = null;
    }
    else
    {
      ArrayList<Integer> locationNums = new ArrayList<Integer>();
      for (int i = 0; i < _locations.length; i++)
        locationNums.add(i);

      for(int i = 0; i < _numPieces; i++)
      {
        double rnd = Math.random() * (locationNums.size());
        int locationNumber = (int)Math.floor(rnd);
        int locNum = locationNums.get(locationNumber);
        locationNums.remove(locationNumber);
        locationNums.trimToSize();
	        
        _finalLocations[i] = _locations[locNum];
      }
    }
  }

  private Point getExactLocation(Point p1, Point p2, int pieceWidth, int pieceHeight)
  {
    //Random between p1 and p2-pieceWidth
	int locWidth = p2.x - p1.x - pieceWidth;
	int p3X = p1.x + (int)(Math.random()*locWidth);

	int locHeight = p2.y - p1.y - pieceHeight;
	int p3Y = p1.y + (int)(Math.random()*locHeight);

	return new Point(p3X, p3Y);
  }
}
