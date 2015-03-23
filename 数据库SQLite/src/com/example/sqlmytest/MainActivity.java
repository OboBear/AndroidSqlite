package com.example.sqlmytest;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{

	String userID = "123";
	
	private MessageDB messageDB;
	
	private Button addButton;
	private Button deleteButton;
	private Button clearAlButton;
	
	private EditText editText;
	private ListView listView;
	
	private Cursor mCursor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initActions();
	}
	
	
	private void initViews()
	{
		addButton = (Button)findViewById(R.id.button1);
		
		deleteButton = (Button)findViewById(R.id.button2);
		
		clearAlButton = (Button)findViewById(R.id.button3);
		
		editText = (EditText)findViewById(R.id.editText1);
		 
		listView = (ListView)findViewById(R.id.listView1);
	}
	
	private void initActions()
	{
		messageDB = new MessageDB(this);
		
		mCursor = messageDB.select();
		
		addButton.setOnClickListener(this);
		
		deleteButton.setOnClickListener(this);
		
		clearAlButton.setOnClickListener(this);

		listView.setAdapter(new MessageListAdapter(this,mCursor));
	}
	
	@Override
	public void onClick(View arg0)
	{
		
		if(arg0.equals(addButton))
		{
			
			String message = editText.getText().toString();
			
			messageDB.insert(userID,getDate(), message);
			
			mCursor.requery();
			
			listView.invalidateViews();
			
		}
		else if(arg0.equals(deleteButton))
		{
			if(mCursor.getCount()>0)
			{
				mCursor.moveToPosition(mCursor.getCount()-1);
				
				messageDB.delete(Integer.parseInt(mCursor.getString(0)));
				
				mCursor.requery();
				
				listView.invalidateViews();
				
				
			}
		}
		else if(arg0.equals(clearAlButton))
		{
			//messageDB.dropTable("message_table");
			messageDB.deleteAll("message_table");
			mCursor.requery();
			listView.invalidateViews();

		}
			
		
	}
	
	
	
	// 获取日期时间 String 类型
		private String getDate()
		{
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(Calendar.YEAR));
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
			String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			String mins = String.valueOf(c.get(Calendar.MINUTE));
			String sec = String.valueOf(c.get(Calendar.SECOND));
			StringBuffer sbBuffer = new StringBuffer();
			sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
					+ mins + ":" + sec);
			return sbBuffer.toString();
		}
		
		
	
	public class MessageListAdapter extends BaseAdapter {
		private Context mContext;
		private Cursor mCursor;

		public MessageListAdapter(Context context, Cursor cursor) {

			mContext = context;
			mCursor = cursor;
		}

		@Override
		public int getCount() {
			return mCursor.getCount();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView mTextView = new TextView(mContext);
			mCursor.moveToPosition(position);
			mTextView.setText(mCursor.getString(0)+"____"+mCursor.getString(1) + "___"
					+ mCursor.getString(2)+"____"+mCursor.getString(3));
			return mTextView;
		}

	}
	
}
