package com.stevengee.pegpuzzle;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

public class MenuDialog extends DialogFragment
{
  public static final int DIALOG_DIFFICULTY = 1;
  public static final int DIALOG_NUM_PIECES = 2;
	  
  public interface MenuDialogListener 
  {
    void onFinishEditDialog(int result, int dialogType);    
  }

  private int _layout;
  private int _dialogType = 1;
  private int _difficulty = 2;
  private int _pieces = 4;
  private Button _submitBtn;
  
  public MenuDialog() 
  {

  }    
 
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    _dialogType = getArguments().getInt("type");
    _difficulty = getArguments().getInt("difficulty");
    _pieces = getArguments().getInt("pieces");
    
    switch (_dialogType)
    {
      case DIALOG_NUM_PIECES:
        _layout = R.layout.fragment_num_pieces;
        break;
      case DIALOG_DIFFICULTY:
        _layout = R.layout.fragment_difficulty;
        break;
    }
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
  {
    View view = inflater.inflate(_layout, container); 

    switch (_dialogType)
    {
      case DIALOG_NUM_PIECES:
   	    getDialog().setTitle("Number of Pieces");
   	    SetNumPiecesRB(view);
        break;
      case DIALOG_DIFFICULTY:
   	    getDialog().setTitle("Difficulty"); 
        SetDifficultyRB(view);
   	    break;
    }

    _submitBtn = (Button)view.findViewById(R.id.menu_submit);
    _submitBtn.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        returnResult(v);
      }
    });

    return view;
  }

  public boolean returnResult(View v) 
  {
    int result = 0;
    
    switch (_dialogType)
    {
      case DIALOG_NUM_PIECES:
        result = GetNumPieces(v);
        break;
      case DIALOG_DIFFICULTY:
        result = GetDifficulty(v);
        break;
    }

    MenuDialogListener activity = (MenuDialogListener) getActivity(); 
    activity.onFinishEditDialog(result, _dialogType); 
    this.dismiss();
    return true;
  }
  
  private int GetDifficulty(View view)
  {
	View parentView = (View)view.getParent();
	View rdGroup = parentView.findViewById(R.id.difficulty_rb_group);
		
    RadioButton rbBegin = (RadioButton)rdGroup.findViewById(R.id.difficulty_rb_beginner);
	if (rbBegin != null && rbBegin.isChecked()) return 1;

    RadioButton rbEasy = (RadioButton)rdGroup.findViewById(R.id.difficulty_rb_easy);
	if (rbEasy != null && rbEasy.isChecked()) return 2;
    
	RadioButton rbMed = (RadioButton)rdGroup.findViewById(R.id.difficulty_rb_medium);
	if (rbMed != null && rbMed.isChecked()) return 3;

	RadioButton rbHard = (RadioButton)rdGroup.findViewById(R.id.difficulty_rb_hard);
	if (rbHard != null && rbHard.isChecked()) return 4;

	return 2;
  }

  private int GetNumPieces(View view)
  {
	View parentView = (View)view.getParent();
	View rdGroup = parentView.findViewById(R.id.pieces_rb_group);
	
    RadioButton rb3 = (RadioButton)rdGroup.findViewById(R.id.pieces_rb_3);
	if (rb3 != null && rb3.isChecked()) return 3;

    RadioButton rb4 = (RadioButton)rdGroup.findViewById(R.id.pieces_rb_4);
	if (rb4 != null && rb4.isChecked()) return 4;
    
	RadioButton rb5 = (RadioButton)rdGroup.findViewById(R.id.pieces_rb_5);
	if (rb5 != null && rb5.isChecked()) return 5;

	return 3;
  }

  private void SetDifficultyRB(View view)
  {
    switch (_difficulty)
    {
      case 1:
   	    RadioButton rbBeginner = (RadioButton)view.findViewById(R.id.difficulty_rb_beginner);
        rbBeginner.setChecked(true);
   	    break;
      case 2:
   	    RadioButton rbEasy = (RadioButton)view.findViewById(R.id.difficulty_rb_easy);
        rbEasy.setChecked(true);
        break;
      case 3:
        RadioButton rbMed = (RadioButton)view.findViewById(R.id.difficulty_rb_medium);
        rbMed.setChecked(true);
        break;
      case 4:
        RadioButton rbHard = (RadioButton)view.findViewById(R.id.difficulty_rb_hard);
        rbHard.setChecked(true);
        break;
    }
  }

  private void SetNumPiecesRB(View view)
  {
    switch (_pieces)
    {
      case 3:
   	    RadioButton rb3 = (RadioButton)view.findViewById(R.id.pieces_rb_3);
   	    rb3.setChecked(true);
   	    break;
      case 4:
   	    RadioButton rb4 = (RadioButton)view.findViewById(R.id.pieces_rb_4);
        rb4.setChecked(true);
        break;
      case 5:
        RadioButton rb5 = (RadioButton)view.findViewById(R.id.pieces_rb_5);
        rb5.setChecked(true);
        break;
    }
  }
}