package com.stevengee.pegpuzzle.pieces;

import com.stevengee.pegpuzzle.R;

import android.content.Context;

public class OctonautSet extends APieceSet 
{
	public OctonautSet(int tolerance, Context ctx)
	{
		super(tolerance, ctx);
		pics = new int[][] { 
				{R.drawable.octonaut1, R.drawable.octonaut1_shadow}, 
				{R.drawable.octonaut2, R.drawable.octonaut2_shadow}, 
				{R.drawable.octonaut3, R.drawable.octonaut3_shadow}, 
				{R.drawable.octonaut4, R.drawable.octonaut4_shadow},
				{R.drawable.octonaut5, R.drawable.octonaut5_shadow},
				{R.drawable.octonaut6, R.drawable.octonaut6_shadow},
				{R.drawable.captain, R.drawable.captain_shadow},
				{R.drawable.quasi, R.drawable.quasi_shadow}}; 
	}	
}
