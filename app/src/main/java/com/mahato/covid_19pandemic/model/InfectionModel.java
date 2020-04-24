package com.mahato.covid_19pandemic.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InfectionModel implements Parcelable {
    private String agebracket;
    private String backupnotes;
    private String currentstatus;
    private String dateannounced;
    private String detectedcity;
    private String detecteddistrict;
    private String detectedstate;
    private String patientnumber;
    private String gender;
    private String nationality;
    private String notes;
    private String statuschangedate;
    private String typeoftransmission;

    public InfectionModel(String agebracket, String backupnotes, String currentstatus, String dateannounced, String detectedcity, String detecteddistrict, String detectedstate, String patientnumber, String gender, String nationality, String notes, String statuschangedate, String typeoftransmission) {
        this.agebracket = agebracket;
        this.backupnotes = backupnotes;
        this.currentstatus = currentstatus;
        this.dateannounced = dateannounced;
        this.detectedcity = detectedcity;
        this.detecteddistrict = detecteddistrict;
        this.detectedstate = detectedstate;
        this.patientnumber = patientnumber;
        this.gender = gender;
        this.nationality = nationality;
        this.notes = notes;
        this.statuschangedate = statuschangedate;
        this.typeoftransmission = typeoftransmission;
    }

    protected InfectionModel(Parcel in) {
        agebracket = in.readString();
        backupnotes = in.readString();
        currentstatus = in.readString();
        dateannounced = in.readString();
        detectedcity = in.readString();
        detecteddistrict = in.readString();
        detectedstate = in.readString();
        patientnumber = in.readString();
        gender = in.readString();
        nationality = in.readString();
        notes = in.readString();
        statuschangedate = in.readString();
        typeoftransmission = in.readString();
    }

    public static final Creator<InfectionModel> CREATOR = new Creator<InfectionModel>() {
        @Override
        public InfectionModel createFromParcel(Parcel in) {
            return new InfectionModel(in);
        }

        @Override
        public InfectionModel[] newArray(int size) {
            return new InfectionModel[size];
        }
    };

    public String getAgebracket() {
        return agebracket;
    }

    public String getBackupnotes() {
        return backupnotes;
    }

    public String getCurrentstatus() {
        return currentstatus;
    }

    public String getDateannounced() {
        return dateannounced;
    }

    public String getDetectedcity() {
        return detectedcity;
    }

    public String getDetecteddistrict() {
        return detecteddistrict;
    }

    public String getDetectedstate() {
        return detectedstate;
    }

    public String getPatientnumber() {
        return patientnumber;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getNotes() {
        return notes;
    }

    public String getStatuschangedate() {
        return statuschangedate;
    }

    public String getTypeoftransmission() {
        return typeoftransmission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(agebracket);
        dest.writeString(backupnotes);
        dest.writeString(currentstatus);
        dest.writeString(dateannounced);
        dest.writeString(detectedcity);
        dest.writeString(detecteddistrict);
        dest.writeString(detectedstate);
        dest.writeString(patientnumber);
        dest.writeString(gender);
        dest.writeString(nationality);
        dest.writeString(notes);
        dest.writeString(statuschangedate);
        dest.writeString(typeoftransmission);
    }

}
