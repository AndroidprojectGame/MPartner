package com.luminous.mpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Parcelable {

    public int id;
    public boolean isread;
    public String Subject;
    public String Text;

    public Notification(int id, String subject, String text, boolean isread) {
        this.id = id;
        Subject = subject;
        Text = text;
        this.isread = isread;
    }

    protected Notification(Parcel in) {
        id = in.readInt();
        isread = in.readByte() != 0x00;
        Subject = in.readString();
        Text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isread ? 0x01 : 0x00));
        dest.writeString(Subject);
        dest.writeString(Text);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
