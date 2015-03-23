package com.example.sqlmytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** ���ݿ������*/
public class MessageDB extends SQLiteOpenHelper
{
	private final static String DATABASE_NAME = "USER1.db";
	private final static int DATABASE_VERSION = 2;
	private final static String TABLE_NAME = "message_table";
	public final static String MESSAGE_ID = "message_id";
	public final static String USER_ID = "user_id";
	public final static String MESSAGE_TIME = "message_time";
	public final static String MESSAGE_MESSAGE = "message_message";

	public MessageDB(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ����table
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		creatNewTable(db, TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select()
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "CREATE TABLE " + TABLE_NAME + " (" + MESSAGE_ID
				+ " INTEGER primary key autoincrement, " + USER_ID + " text, "
				+ MESSAGE_TIME + " text, " + MESSAGE_MESSAGE + " text) ;";
		try
		{
			db.execSQL(sql);
		}
		catch (SQLException se)
		{
			Log.i("", "SQLException ");
		}
		catch (Exception e)
		{
			Log.i("", "Exception ");
		}
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		return cursor;
	}

	/** �����µ�Ԫ�� */
	public long insert(String userID, String messageTime, String messageMessage)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(USER_ID, userID);
		cv.put(MESSAGE_TIME, messageTime);
		cv.put(MESSAGE_MESSAGE, messageMessage);

		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	/** ɾ������ */
	public void delete(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = MESSAGE_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}

	/** ɾ��ȫ������ */
	public void deleteAll(String tableName)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, null, null);

		dropTable(tableName);
		creatNewTable(db, tableName);
	}

	/** �޸Ĳ��� */
	public void update(int id, String userID, String bookname, String author)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = MESSAGE_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(USER_ID, userID);
		cv.put(MESSAGE_TIME, bookname);
		cv.put(MESSAGE_MESSAGE, author);
		db.update(TABLE_NAME, cv, where, whereValue);
	}

	/** �����µ�Table */
	private boolean creatNewTable(SQLiteDatabase db, String tableName)
	{

		String sql = "CREATE TABLE " + TABLE_NAME + " (" + MESSAGE_ID
				+ " INTEGER primary key autoincrement, " + USER_ID + " text, "
				+ MESSAGE_TIME + " text, " + MESSAGE_MESSAGE + " text);";

		try
		{
			db.execSQL(sql);
		}
		catch (SQLException se)
		{
			return false;
		}

		return true;

	}

	/** ɾ��ָ����Table */
	public boolean dropTable(String tableName)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		try
		{
			db.execSQL(sql);
		}
		catch (SQLException se)
		{
			return false;
		}

		return true;
	}
}
