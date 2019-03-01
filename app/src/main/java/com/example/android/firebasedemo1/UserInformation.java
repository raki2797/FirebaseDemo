package com.example.android.firebasedemo1;

public class UserInformation {
    public String name;
    public String address;
    public String age;
    public String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserInformation(){
    }

    public UserInformation(String name, String address, String age, String type){
        this.name = name;
        this.address  = address;
        this.age = age;
        this.type = type;
    }
}
