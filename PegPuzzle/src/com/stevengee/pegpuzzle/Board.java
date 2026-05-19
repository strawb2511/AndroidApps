package com.stevengee.pegpuzzle;

import com.stevengee.pegpuzzle.MenuDialog.MenuDialogListener;
import com.stevengee.pegpuzzle.boards.APuzzleBoardEV;
import com.stevengee.pegpuzzle.boards.APuzzleBoardEV.OnResetEventListener;
import com.stevengee.pegpuzzle.boards.ForestBoard;
import com.stevengee.pegpuzzle.pieces.APieceSet;
import com.stevengee.pegpuzzle.util.SystemUiHider;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;

public class Board extends Activity implements MenuDialogListener
{
  private APuzzleBoardEV _boardEV;
  private int _difficulty;
  private int _numPieces;
  private SystemUiHider mSystemUiHider;
  private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
  private SharedPreferences _settings;
  
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);

    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    _settings = getSharedPreferences("KidsPuzzle", 0);
    _difficulty = _settings.getInt("difficulty", 2);
    _numPieces = _settings.getInt("pieces", 4);

    _boardEV = Boards.getRandomBoard(this, _numPieces);
    setContentView((View)_boardEV);
    registerForContextMenu(_boardEV);    
    _boardEV.setCustomEventListener(changeListener);    
    
	mSystemUiHider = SystemUiHider.getInstance(this, (View)_boardEV, HIDER_FLAGS);
	mSystemUiHider.setup();

    getPieces();
  }
  
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mSystemUiHider.hide();
  }

@Override
  public boolean onCreateOptionsMenu(Menu menu) 
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) 
  {
    switch (item.getItemId()) 
    {
      case R.id.difficulty:
          this.showMenuDialog(com.stevengee.pegpuzzle.MenuDialog.DIALOG_DIFFICULTY);
         	return true;
      case R.id.num_pieces:
    	  this.showMenuDialog(com.stevengee.pegpuzzle.MenuDialog.DIALOG_NUM_PIECES);
         	return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void getPieces()
  {
    PieceSets ps = new PieceSets();
    APieceSet pSet = ps.getRandomPieceSet(_difficulty, this);
    Piece[] pieces = pSet.getPieces(_numPieces);
    
    try
    {
      for(Piece p: pieces)
      {
        _boardEV.AddPiece(p);
      }
    }
    catch (Exception e)
    {
    }
  }

  private void showMenuDialog(int dialogType) 
  {
    FragmentManager fm = getFragmentManager();
    MenuDialog menuDialog = new MenuDialog();
    
    Bundle args = new Bundle();
    args.putInt("type", dialogType);
    args.putInt("difficulty", _difficulty);
    args.putInt("pieces", _numPieces);
    menuDialog.setArguments(args);
    menuDialog.show(fm, "fragment_menu");
  }
  
  @Override
  public void onFinishEditDialog(int result, int dialogType) 
  {
    switch(dialogType)
    {
      case com.stevengee.pegpuzzle.MenuDialog.DIALOG_DIFFICULTY:
        _difficulty = result;
        _boardEV.UpdateTolerance(_difficulty);
        break;
      case com.stevengee.pegpuzzle.MenuDialog.DIALOG_NUM_PIECES:
        _numPieces = result;
        ResetBoard();
        break;
    }
    
    SharedPreferences.Editor editor = _settings.edit();
    editor.putInt("difficulty", _difficulty);
    editor.putInt("pieces", _numPieces);
    editor.commit();
  }
  
  private void ResetBoard()
  {
	  ///TODO: Select Random Board
    _boardEV = new ForestBoard(this, _numPieces);
    setContentView((View)_boardEV);
    _boardEV.setCustomEventListener(changeListener);
    getPieces();
  }

  private OnResetEventListener changeListener = new OnResetEventListener() 
  {
    public void onReset() { ResetBoard();}
  };
}
