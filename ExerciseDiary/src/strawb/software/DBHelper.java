package strawb.software;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper
{
	Context context;
	
	public DBHelper(Context context)
	{
		super(context, "ExerciseDiary.db", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		try 
		{
			String sql = "Create Table Workout (ID integer primary key autoincrement, workout_type text, workout_date int)"; 
			db.execSQL(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		try 
		{
			String sql = "Create Table if not exists GymExercises (ID integer primary key autoincrement, workout references Workout (ID), exercise text, weight number, unit_measure text, reps int, sets int)"; 
			db.execSQL(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
	}	
}