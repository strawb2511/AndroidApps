package com.stevengee.pegpuzzle;

import com.stevengee.pegpuzzle.pieces.APieceSet;
import com.stevengee.pegpuzzle.pieces.FarmAnimalSet;
import com.stevengee.pegpuzzle.pieces.OctonautSet;

import android.content.Context;

public class PieceSets {

  public enum PieceTypes { FarmAnimals, Octonauts }
	
  public APieceSet getPieceSet (int difficulty, PieceTypes type, Context ctx)
  {
    APieceSet board;
		
    switch (type)
    {
      case Octonauts:
        board = new OctonautSet(difficulty, ctx);
        break;
      case FarmAnimals:
        default:
        board = new FarmAnimalSet(difficulty, ctx);
        break;
    }
    return board;
  }

  public APieceSet getRandomPieceSet(int difficulty, Context ctx)
  {
    int numTypes = PieceTypes.values().length;
    double rnd = Math.random() * (numTypes);
    int boardNum = (int)Math.floor(rnd);

    PieceTypes bt = PieceTypes.values()[boardNum];
    return getPieceSet(difficulty, bt, ctx);
  }
}
