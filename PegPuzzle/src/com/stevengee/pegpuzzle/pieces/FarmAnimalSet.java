package com.stevengee.pegpuzzle.pieces;

import com.stevengee.pegpuzzle.R;

import android.content.Context;

public class FarmAnimalSet extends APieceSet 
{
	public FarmAnimalSet(int tolerance, Context ctx)
	{
		super(tolerance, ctx);
		pics = new int[][] { {R.drawable.pig, R.drawable.pig_shadow} }; 
	}	
}
