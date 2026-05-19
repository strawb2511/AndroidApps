package strawb.software;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

public class ExerciseDiaryActivity extends Activity 
{
	Button viewBtn1;
	Button viewBtn2;
	Button viewBtn3;
	Button viewBtn4;
	Button viewBtn5;
	Button addGymBtn;
	Button addBtn2;
	Button addBtn3;
	Button addBtn4;
	Button addBtn5;
	Button saveBtn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        viewBtn1 = (Button)findViewById(R.id.btnMainOption1View);
        viewBtn2 = (Button)findViewById(R.id.btnMainOption2View);
        viewBtn3 = (Button)findViewById(R.id.btnMainOption3View);
        viewBtn4 = (Button)findViewById(R.id.btnMainOption4View);
        viewBtn5 = (Button)findViewById(R.id.btnMainOption5View);
        
        addGymBtn = (Button)findViewById(R.id.btnMainOption1Add);
        addBtn2 = (Button)findViewById(R.id.btnMainOption2Add);
        addBtn3 = (Button)findViewById(R.id.btnMainOption3Add);
        addBtn4 = (Button)findViewById(R.id.btnMainOption4Add);
        addBtn5 = (Button)findViewById(R.id.btnMainOption5Add);

        viewBtn1.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onViewClick(1);
        	}
        });
       
        viewBtn2.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onViewClick(2);
        	}
        });
        
        viewBtn3.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onViewClick(3);
        	}
        });
        
        viewBtn4.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onViewClick(4);
        	}
        });
        
        viewBtn5.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onViewClick(5);
        	}
        });

        
        addGymBtn.setOnClickListener(new Button.OnClickListener() 
        {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewGymSession.class);
                startActivityForResult(myIntent, 0);
            }
        });

        addBtn2.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onAddClick(2);
        	}
        });
        
        addBtn3.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onAddClick(3);
        	}
        });
        
        addBtn4.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onAddClick(4);
        	}
        });
        
        addBtn5.setOnClickListener(new Button.OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		onAddClick(5);
        	}
        });

    }

	private void onViewClick(int btnID) 
	{
		String x = "View " + btnID;
	
		Toast.makeText(ExerciseDiaryActivity.this, x, Toast.LENGTH_SHORT).show();
	}
	
	public void onAddClick(int btnID)
	{
		String x = "Add" + btnID;
		
		Toast.makeText(ExerciseDiaryActivity.this, x, Toast.LENGTH_SHORT).show();
	}
	
}