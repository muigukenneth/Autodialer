package com.autodialer;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerHistory extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "historyManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "history";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NOTE = "takenote";
    private final ArrayList<History> contact_list = new ArrayList<History>();

    public DatabaseHandlerHistory(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
		+ KEY_PH_NO + " TEXT,"+ KEY_NOTE + " TEXT," + KEY_EMAIL + " TEXT" + ")";
	db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	// Create tables again
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_History(History history) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(KEY_NAME, history.getName()); // Contact Name
	values.put(KEY_PH_NO, history.getPhoneNumber()); // Contact Phone
	values.put(KEY_NOTE, history.getNote());
	values.put(KEY_EMAIL, history.getEmail()); // Contact Email
	// Inserting Row
	db.insert(TABLE_CONTACTS, null, values);
	db.close(); // Closing database connection
    }

    // Getting single contact
    Contact Get_Contact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
		KEY_NAME, KEY_PH_NO,KEY_NOTE, KEY_EMAIL }, KEY_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null)
	    cursor.moveToFirst();

	Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2), cursor.getString(3));
	// return contact
	cursor.close();
	db.close();

	return contact;
    }

    // Getting All Contacts
    public ArrayList<History> Get_History() {
	try {
	    contact_list.clear();

	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);

	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
		do {
		    History history = new History();
		    history.setID(Integer.parseInt(cursor.getString(0)));
		    history.setName(cursor.getString(1));
		    history.setPhoneNumber(cursor.getString(2));
		    history.setNote(cursor.getString(3));
		    history.setEmail(cursor.getString(4));
		    
		    // Adding contact to list
		    contact_list.add(history);
		} while (cursor.moveToNext());
	    }

	    // return contact list
	    cursor.close();
	    db.close();
	    return contact_list;
	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("all_contact", "" + e);
	}

	return contact_list;
    }

    // Updating single contact
    public int Update_History(History history) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_NAME, history.getName());
	values.put(KEY_PH_NO, history.getPhoneNumber());
	values.put(KEY_NOTE, history.getNote());
	values.put(KEY_EMAIL, history.getEmail());

	// updating row
	return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
		new String[] { String.valueOf(history.getID()) });
    }

    // Deleting single contact
    public void Delete_Contact(int id) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
		new String[] { String.valueOf(id) });
	db.close();
    }

    // Getting contacts Count
    public int Get_Total_History() {
	String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }
}
