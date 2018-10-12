package com.yuanting.n2share.user;

/**
 * Created on 2018/10/11 11:24
 * Created by 薛立民
 * TEL 13262933389
 */
public class PersonalUserInfo {
    private String Id;
    private String UserId;
    private String Sex;
    private String BirthDate;
    private String Province;
    private String City;

    @Override
    public String toString() {
        return "PersonalUserInfo [Id=" + Id + ", UserId=" + UserId + ", Sex=" + Sex +
                ", BirthDate=" + BirthDate + ", Province=" + Province + ", City=" + City + "]";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
