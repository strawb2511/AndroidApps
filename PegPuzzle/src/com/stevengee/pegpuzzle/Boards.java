package com.stevengee.pegpuzzle;

import com.stevengee.pegpuzzle.boards.APuzzleBoardEV;
import com.stevengee.pegpuzzle.boards.ForestBoard;
import com.stevengee.pegpuzzle.boards.GrassBoard;
import com.stevengee.pegpuzzle.boards.NightBoard;
import com.stevengee.pegpuzzle.boards.SnowBoard;

import android.content.Context;

public class Boards 
{
  public enum BoardTypes { Beach , City, Flowers, Forest, Grass, Lake, Night, Snow, Underwater }
	
  public static APuzzleBoardEV getBoardEV (BoardTypes type, Context ctx, int numPieces)
  {
    APuzzleBoardEV board;
		
    switch (type)
    {
      case Forest:
        board = new ForestBoard(ctx, numPieces);
        break;
      case Night:
        board = new NightBoard(ctx, numPieces);
        break;
      case Snow:
        board = new SnowBoard(ctx, numPieces);
        break;
      case Grass:
      default:
        board = new GrassBoard(ctx, numPieces);
        break;
    }
    return board;
  }
	
  public static APuzzleBoardEV getRandomBoard(Context ctx, int numPieces)
  {
    int numTypes = BoardTypes.values().length;

    double rnd = Math.random() * (numTypes-1);
    int boardNum = (int)Math.round(rnd);

    BoardTypes bt = BoardTypes.values()[boardNum];
    return getBoardEV(bt, ctx, numPieces);
  }
}
