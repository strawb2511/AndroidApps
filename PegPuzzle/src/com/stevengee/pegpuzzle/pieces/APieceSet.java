package com.stevengee.pegpuzzle.pieces;

import java.util.ArrayList;

import com.stevengee.pegpuzzle.Piece;

import android.content.Context;

public abstract class APieceSet 
{
  protected int[][] pics; 
  private Context _ctx;
  private int _tolerance;

  public APieceSet(int tolerance, Context ctx)
  {
    _tolerance = tolerance;
    _ctx = ctx;
  }

  public boolean HasEnoughPieces(int numPieces)
  {
    return (pics.length <= numPieces);
  }

  public Piece[] getPieces(int numPieces) 
  {
    Piece[] pieces = new Piece[numPieces];

    if (numPieces >= pics.length)
    {
      int i;
      for(i = 0; i < pics.length; i++)
        pieces[i] = new Piece(pics[i][0],pics[i][1], _tolerance, _ctx);

      for (int j = i; j < numPieces; j++)
        pieces[i] = null;
    }
    else
    {
    	ArrayList<Integer> pieceNums = new ArrayList<Integer>();
    	for (int i = 0; i < pics.length; i++)
    		pieceNums.add(i);
    	
    	for(int i = 0; i < numPieces; i++)
    	{
          double rnd = Math.random() * (pieceNums.size());
          int pieceNumber = (int)Math.floor(rnd);
          int picNum = pieceNums.get(pieceNumber);
          pieceNums.remove(pieceNumber);
          pieceNums.trimToSize();
        
          pieces[i] = new Piece(pics[picNum][0],pics[picNum][1], _tolerance, _ctx);
    	}
    }

    return pieces;
  }
}
