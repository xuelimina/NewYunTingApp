package com.yuanting.n2share.user;

/**
 * Created on 2018/10/11 11:24
 * Created by 薛立民
 * TEL 13262933389
 */
public class Basic {
    private String Id;
    private String Type;
    private String Account;
    private String Password;
    private String Email;
    private String NickName;
    private String Avatar;
    private String Sign;
    private String FollowedCount;
    private String FollowerCount;
    private String Experience;
    private String Points;
    private String Status;
    private String CreateTime;
    private String UpdateTime;
    @Override
    public String toString() {
        return "Basic [Id=" + Id + ", Type=" + Type + ", Account=" + Account +
                ", Password=" + Password +
                ", Email=" + Email +
                ", NickName=" + NickName +
                ", Avatar=" + Avatar +
                ", Sign=" + Sign +
                ", FollowedCount=" + FollowedCount +
                ", FollowerCount=" + FollowerCount +
                ", Experience=" + Experience +
                ", Points=" + Points +
                ", Status=" + Status +
                ", CreateTime=" + CreateTime +
                ", UpdateTime=" + UpdateTime +"]";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getFollowedCount() {
        return FollowedCount;
    }

    public void setFollowedCount(String followedCount) {
        FollowedCount = followedCount;
    }

    public String getFollowerCount() {
        return FollowerCount;
    }

    public void setFollowerCount(String followerCount) {
        FollowerCount = followerCount;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
