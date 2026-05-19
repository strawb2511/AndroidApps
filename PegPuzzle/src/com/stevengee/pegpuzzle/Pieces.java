package com.stevengee.pegpuzzle;

import com.stevengee.pegpuzzle.pieces.APieceSet;
import com.stevengee.pegpuzzle.pieces.FarmAnimalSet;

import android.content.Context;

public class Pieces {
	
	private APieceSet _pSet;
	private Context _ctx;
	private int _numPieces;
	private int _difficulty;
	private Piece[] _pieces;
	
	public Pieces (Context ctx, APieceSet ps, int numberOfPieces, int difficulty)
	{
		_pSet = ps;
		_ctx = ctx;
		_numPieces = numberOfPieces;
		_difficulty = difficulty;
	}
	
	public Pieces (Context ctx, int numberOfPieces, int difficulty)
	{
		_ctx = ctx;
		_numPieces = numberOfPieces;
		_difficulty = difficulty;
		PieceSets ps = new PieceSets();
		_pSet = ps.getRandomPieceSet(difficulty, ctx);
		_pieces = _pSet.getPieces(_numPieces);
	}
	
	public Piece[] GetPieces()
	{
		//Get Random piece set
		APieceSet pset = new FarmAnimalSet(_difficulty, _ctx);
		if (!pset.HasEnoughPieces(_numPieces))
		{
			//pset = this.GetRandomSet()
		}
		
		_pieces = pset.getPieces(_numPieces);		
		return _pieces;
	}

}
