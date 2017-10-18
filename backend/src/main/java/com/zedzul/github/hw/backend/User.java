package com.zedzul.github.hw.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

@Entity
public class User {

    @Id
    private long mId;

    private String mName;
    private String mAvatarUrl;
    private long mDateOfBirth;

    public User() {
    }

    public User(final long pId, final String pName, final String pAvatar_url, final long pDateOfBirth) {
        mId = pId;
        mName = pName;
        mAvatarUrl = pAvatar_url;
        mDateOfBirth = pDateOfBirth;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Date getDateOfBirth() throws Exception {
        return new Date(mDateOfBirth);
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }
}
