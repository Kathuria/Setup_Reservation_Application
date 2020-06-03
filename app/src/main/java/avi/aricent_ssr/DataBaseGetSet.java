package avi.aricent_ssr;

public class DataBaseGetSet {
    
    //private variables
    int _id;
    String _name,_mName,_tName,_from,_to,_userid;
     
    // Empty constructor
    public DataBaseGetSet(){
    }
    
    // constructor
    public DataBaseGetSet(int id, String name, String mName, String tName, String from, String to, String userid){
        this._id = id;
        this._name = name;
        this._mName = mName;
        this._tName = tName;
        this._from = from;
        this._to = to;
        this._userid = userid;
    }
     
    // constructor
    public DataBaseGetSet(String name,String from, String to){
        this._name = name;
        this._from = from;
        this._to = to;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getmName(){
        return this._mName;
    }
     
    // setting name
    public void setmName(String mName){
        this._mName = mName;
    }
    
 // getting name
    public String gettName(){
        return this._tName;
    }
     
    // setting name
    public void settName(String tName){
        this._tName = tName;
    }
 // getting name
    public String getName(){
        return this._name;
    }
     
    // setting name
    public void setName(String name){
        this._name = name;
    }
    
    // getting name
    public String getFrom(){
        return this._from;
    }
     
    // setting name
    public void setFrom(String from){
        this._from = from;
    }
    
    // getting name
    public String getTo(){
        return this._to;
    }
     
    // setting name
    public void setTo(String to){
        this._to = to;
    }
    
    public String getUserID(){
        return this._userid;
    }
     
    // setting name
    public void setUserID(String userid){
        this._userid = userid;
    }
}