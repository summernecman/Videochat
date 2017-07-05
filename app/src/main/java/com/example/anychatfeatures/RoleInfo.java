package com.example.anychatfeatures;

import java.io.Serializable;

public class RoleInfo implements Serializable{
	private String mStrName;
	private String mStrUserID;
	private int mRoleIconID;

	public void setName(String strName) {
		mStrName = strName;
	}

	public String getName() {
		return mStrName;
	}

	public void setUserID(String strUserID) {
		mStrUserID = strUserID;
	}

	public String getUserID() {
		return mStrUserID;
	}

	public int getRoleIconID() {
		return mRoleIconID;
	}

	public void setRoleIconID(int resID) {
		mRoleIconID = resID;
	}

	@Override
	public String toString() {
		return "RoleInfo{" +
				"mStrName='" + mStrName + '\'' +
				", mStrUserID='" + mStrUserID + '\'' +
				", mRoleIconID=" + mRoleIconID +
				'}';
	}
}
