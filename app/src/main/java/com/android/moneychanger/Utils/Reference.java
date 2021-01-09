package com.android.moneychanger.Utils;

public class Reference {

    //Release
    //or
    //Development

    public static String rootNode = "Release";
    //public static String rootNode = "SandBox";


    public static String viewProfileClickNode= "vp";

    public static String whatsAppClickNode = "WAc";
    public static String callClickNode = "callc";
    public static String gloxMeClickNode = "gloxc";
    public static String msgClickNode = "msgc";
    public static String instaClickNode = "IGc";
    public static String websiteClickNode = "webc";

    //you may put these data in sharedPrefs of user
    //or after every 10 visits remind user to update
    //at a certain count.. make the dialog unexitable

    public static String appVersion = "v.1.2";
    private String TnC_date;
    public void setTnCdate(){

    }
    public String getTnCdate(){
        return "";
    }
}
