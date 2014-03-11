package com.autodialer;

public class History {
	 // private variables
    public int _id;
    public String _name;
    public String _phone_number;
    public String _email;
   public String _takenote;
    public History() {
    }

    // constructor
    public History(int id, String name, String _phone_number, String _email,String _takenote) {
	this._id = id;
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
	this._takenote=_takenote;
    }

    // constructor
    public History(String name, String _phone_number, String _email,String _takenote) {
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
	this._takenote=_takenote;
    }

    // getting ID
    public int getID() {
	return this._id;
    }

    // setting id
    public void setID(int id) {
	this._id = id;
    }

    // getting name
    public String getName() {
	return this._name;
    }

    // setting name
    public void setName(String name) {
	this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
	return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
	this._phone_number = phone_number;
    }

    // getting email
    public String getEmail() {
	return this._email;
    }

    // setting email
    public void setEmail(String email) {
	this._email = email;
    }
    // getting email
    public String getNote() {
	return this._takenote;
    }

    // setting email
    public void setNote(String takenote) {
	this._takenote = takenote;
    }
}
