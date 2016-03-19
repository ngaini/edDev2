package edu.scu.mapproject;

import android.widget.EditText;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by avidekar on 2016-02-26.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Users {
    private int role;
    private String fullName;
    private String age;
    private String phoneNumber;
    private String password;
    private String education;
    private String description;
    private String emailID;
    private String gender;
    private String interests;

    private String image;

    private double lat;
    private double lng;

    //    private String address;
//    private String pincode;
    private String userID;


    public Users(String userID, int role, String fullName, String age,String emailID, String phoneNumber, String password, String education, String description, String gender, String interests, double lat, double lng, String image ){
        this.userID = userID;
        this.role = role;
        this.fullName = fullName;
        this.age = age;
        this.emailID = emailID;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.education = education;
        this.description = description;
        this.gender = gender;
        this.interests = interests;
        this.lat=lat;
        this.lng = lng;
        this.image = image;
    }
    // constructor without parameters essential for firebase to work
    public Users() {
    }

    public String getImage()
    {
        return  this.image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
    public int getRole()
    {
        return this.role;
    }

    public String getFullName()
    {
        return this.fullName;
    }

    public String getAge()
    {
        return this.age;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }


    public String getPassword()
    {
        return this.password;
    }


//    public String toString() { return "User{handle='"+handle+“', name='"+name+"', stackId="+stackId+"\’}”; }


    //    @Override
//    public String toString() { return "User{fullName='"+fullName+"', name='"+phoneNumber+"', stackId='"+age+"'}"; }
    public String getEmailID()
    {
        return this.emailID;
    }

    public String getEducation()
    {
        return this.education;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getGender()
    {
        return this.gender;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getInterests()
    {
        return this.interests;
    }

//    public String getAddress(){
//        return this.address;
//    }
//
//    public String getPincode()
//    {
//        return this.pincode;
//    }


    public String getUserID()
    {
        return this.userID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

//    public void setPincode(String pincode) {
//        this.pincode = pincode;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {

        this.lat = lat;
    }

//    public void setIdOfInterestedParty(int idOfInterestedParty) {
//        this.idOfInterestedParty = idOfInterestedParty;
//    }
//
//    public void setList(int list) {
//
//        this.list = list;
//    }
//
//    public int getList() {
//
//        return list;
//    }
//
//    public int getIdOfInterestedParty() {
//
//        return idOfInterestedParty;
//    }
}
