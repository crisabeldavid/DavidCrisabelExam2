package com.david.crisabel;

public class Student {
    String fname, lname;
    double sE1, sE2, average;

    public Student(String fname, String lname, double sE1, double sE2) {
        this.fname = fname;
        this.lname = lname;
        this.sE1 = sE1;
        this.sE2 = sE2;
        average = (sE1 + sE2) / 2;
    }

    public Student() {
    }

    public String getfname() {
        return fname;
    }

    public String getlname() {
        return lname;
    }

    public double getaverage() {
        return average;
    }
}


