package avi.aricent_ssr;

public class FeedGetSet {
    
    //private variables
    int _id;
    String _rate,_feed;
     
    // Empty constructor
    public FeedGetSet(){   
    }
    
    // constructor
    public FeedGetSet(int id, String rate, String feed){
    	this._id = id;
        this._rate = rate;
        this._feed = feed;
    }
    
 // constructor
    public FeedGetSet(String rate, String feed){
        this._rate = rate;
        this._feed = feed;
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
    public String getRate(){
        return this._rate;
    }
     
    // setting name
    public void setRate(String rate){
        this._rate= rate;
    }
    
    // getting name
    public String getFeed(){
        return this._feed;
    }
     
    // setting name
    public void setFeed(String feed){
        this._feed = feed;
    }
    
}