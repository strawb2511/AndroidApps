package strawb.software;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Date;

import android.widget.Toast;

public class NewGymSession extends Activity implements OnClickListener
{
	private ArrayList<String[]> exerciseList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_gym_session);
        exerciseList = new ArrayList<String[]>();
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" );  
        ((EditText)findViewById(R.id.txtDate)).setText(sdf.format( new Date() ));

        ((Button)findViewById(R.id.btnAdd)).setOnClickListener(this);
        
        Button saveBtn = (Button)findViewById(R.id.btnSave);

        saveBtn.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onSaveClick();
        	}
        });

    }

	public void onClick(View v)
	{
		if (this.addExerciseToList())
		{
			this.ClearFields();
			this.DisplayExerciseList();
		}
		else
			Toast.makeText(NewGymSession.this, "You must supply an exercise name before adding another exercise", Toast.LENGTH_SHORT).show();
	}

	public void onSaveClick()
	{
		if (this.addExerciseToList())
		{
			this.ClearFields();
			this.DisplayExerciseList();
		}
		this.saveToDB();
	}
	
	private void saveToDB()
	{
		try 
		{
			DBHelper dbhelp = new DBHelper(this);
			SQLiteDatabase db = dbhelp.getWritableDatabase();

			try
			{
				db.beginTransaction();

				String sDate = ((EditText)findViewById(R.id.txtDate)).getText().toString();
				Date dDate = new Date(sDate);
				
				ContentValues workVal = new ContentValues();
				workVal.put("workout_type", "Gym");
				workVal.put("workout_date", dDate.getTime());
				long workoutID = db.insertOrThrow("Workout", null, workVal);
				
				int exercises = exerciseList.size();
				for (int i = 0; i < exercises; i++ ) 
				{
					String[] exercise = (String[])exerciseList.get(i);

					ContentValues exerciseVal = new ContentValues();
					exerciseVal.put("workout", workoutID);
					exerciseVal.put("exercise", exercise[0]);
					exerciseVal.put("weight", Float.parseFloat(exercise[1]));
					exerciseVal.put("unit_measure", exercise[2]);
					exerciseVal.put("reps", Integer.parseInt(exercise[3]));
					exerciseVal.put("sets", Integer.parseInt(exercise[4]));
					db.insertOrThrow("GymExercises", null, exerciseVal);
				}
				db.setTransactionSuccessful();
				Toast.makeText(NewGymSession.this, "Save Successful", Toast.LENGTH_SHORT).show();
			}
			catch (SQLiteException sex)
			{
				Toast.makeText(NewGymSession.this, "Save Error: " + sex.getMessage(), Toast.LENGTH_LONG).show();				
			}
			finally
			{
				db.endTransaction();
			}

			db.close();
		}
		catch (SQLException e) 
		{
			Toast.makeText(NewGymSession.this, "Error Saving " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private Boolean addExerciseToList()
	{
		String[] thisExercise = new String[5];
		String exName = ((EditText)findViewById(R.id.txtExerciseName)).getText().toString();
	
		if (exName == "") return false;
			
		thisExercise[0] = exName;
		thisExercise[1] = ((EditText)findViewById(R.id.txtExerciseWeight)).getText().toString();
		thisExercise[2] = ((Spinner)findViewById(R.id.listWeightOptions)).getSelectedItem().toString();
		thisExercise[3] = ((EditText)findViewById(R.id.txtSets)).getText().toString();
		thisExercise[4] = ((EditText)findViewById(R.id.txtReps)).getText().toString();
	
		if (thisExercise[1].isEmpty()) thisExercise[1] = "0";
		if (thisExercise[3].isEmpty()) thisExercise[3] = "0";
		if (thisExercise[4].isEmpty()) thisExercise[4] = "0";

		exerciseList.add(thisExercise);
		return true;
	}
	
	private void ClearFields()
	{
		EditText exercise = ((EditText)findViewById(R.id.txtExerciseName));
		exercise.setText("");

		EditText weight = ((EditText)findViewById(R.id.txtExerciseWeight));
		weight.setText("");

		EditText sets = ((EditText)findViewById(R.id.txtSets));
		sets.setText("");
		
		EditText reps = ((EditText)findViewById(R.id.txtReps));
		reps.setText("");
	}

	private void DisplayExerciseList()
	{
		String[] exercise = (String[])exerciseList.get(exerciseList.size()-1);
		this.displayOneRow(exercise);
	}

	private void displayOneRow(String[] exercise)
	{
		TableLayout tl = (TableLayout)findViewById(R.id.tableExercise);
		int rows = tl.getChildCount();

		TextView lblName = new TextView(this);
		lblName.setTextSize(10);
		lblName.setText(exercise[0]);

		TextView lblWeight = new TextView(this);
		lblWeight.setTextSize(10);
		lblWeight.setText(String.format("%s %s", exercise[1], exercise[2]));

		TextView lblSets = new TextView(this);
		lblSets.setTextSize(10);
		lblSets.setText(String.format("%s sets", exercise[3]));
	
		TextView lblReps = new TextView(this);
		lblReps.setTextSize(10);
		lblReps.setText(String.format("%s reps", exercise[4]));
	
		TableRow tr = new TableRow(this);
		tr.addView(lblName);
		tr.addView(lblWeight);
		tr.addView(lblSets);
		tr.addView(lblReps);
		tl.addView(tr, rows-5);
		rows++;
	}
	
	/*
	private void createNewRow()
	{
		TableLayout tl = (TableLayout)findViewById(R.id.tableExercise);
		int rows = tl.getChildCount();

		int nameWidth = ((EditText)findViewById(R.id.txtExerciseName)).getWidth();
		EditText txtName = new EditText(this);
		txtName.setTextSize(10);
		txtName.setInputType(InputType.TYPE_CLASS_TEXT);
		txtName.setWidth(nameWidth);
		//txtName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 60, 30));
        //android:layout_weight="30"

		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 60, 10); 
		int colWidth = ((EditText)findViewById(R.id.txtExerciseWeight)).getWidth();

		EditText txtWeight = new EditText(this);
		txtWeight.setTextSize(10);
		txtWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		txtWeight.setWidth(colWidth);
		//txtWeight.setLayoutParams(params);
        //android:layout_weight="10"

		 
		String options[] = {"kg","lbs"," "}; 
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options); 
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view 

        Spinner listWeightOpt = new Spinner(this);
        listWeightOpt.setClickable(true);
        listWeightOpt.setAdapter(spinnerArrayAdapter); 
        listWeightOpt.setMinimumWidth(colWidth);
        //android:width="0dp"
        
		TableRow.LayoutParams spinParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 60, 10); 
		spinParams.gravity = Gravity.CENTER_VERTICAL;
        //listWeightOpt.setLayoutParams(spinParams);
		
		EditText txtSets = new EditText(this);
		txtSets.setTextSize(10);
		txtSets.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtSets.setWidth(colWidth);
		//txtSets.setLayoutParams(params);
   		
		EditText txtReps = new EditText(this);
		txtReps.setTextSize(10);
		txtReps.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtReps.setWidth(colWidth);
		//txtReps.setLayoutParams(params);
    
		TableRow tr = new TableRow(this);
		tr.addView(txtName);
		tr.addView(txtWeight);
		tr.addView(listWeightOpt);
		tr.addView(txtSets);
		tr.addView(txtReps);
		tl.addView(tr, rows-2);
	}
	*/
}