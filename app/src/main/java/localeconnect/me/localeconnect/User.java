package localeconnect.me.localeconnect;

import java.util.Date;

/**
 * Created by admin on 10/25/2017.
 */

public class User {

    private String id;
    private String userName;
    private String password;
    private String email;
    private String deviceId;
    private Date joinDate;
    private boolean emailVerified;
    private String videoProfileRef;
    private String profilePicRef;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getVideoProfileRef() {
        return videoProfileRef;
    }

    public void setVideoProfileRef(String videoProfileRef) {
        this.videoProfileRef = videoProfileRef;
    }

    public String getProfilePicRef() {
        return profilePicRef;
    }

    public void setProfilePicRef(String profilePicRef) {
        this.profilePicRef = profilePicRef;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", joinDate=" + joinDate +
                ", emailVerified=" + emailVerified +
                ", videoProfileRef='" + videoProfileRef + '\'' +
                ", profilePicRef='" + profilePicRef + '\'' +
                '}';
    }
}
