package com.springapp.mvc.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Max on 08.11.2016.
 */
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "confirmPassword")
    private String confirmPassword;

    @Column(name = "sex")
    private String sex;

    @Column(name = "grants")
    private String grantsAdapter;

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getGrants() {

        if(grantsAdapter == null || grantsAdapter.isEmpty()) {
            return new ArrayList<String>();
        }

        List<String> result = new ArrayList<String>();
        for(int i = 0; i < grantsAdapter.length(); i++) {
            result.add(String.valueOf(grantsAdapter.indexOf(i)));
        }

        return result;
    }

    public void setGrants(List<String> grants) {

        if(grants == null || grants.isEmpty()) {
            return;
        }

        List<String> appliableGrants = getAppliableGrants();

        StringBuilder sb = new StringBuilder();
        for (String grant : grants) {
            if(appliableGrants.contains(grant)) {
                sb.append(grant);
            }
        }

        this.grantsAdapter = sb.toString();
    }

    public String getGrantsAdapter() {
        return grantsAdapter;
    }

    public void setGrantsAdapter(String grantsAdapter) {
        this.grantsAdapter = grantsAdapter;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address
                + ", password=" + password + ", confirmPassword=" + confirmPassword
                + ", sex=" + sex + ", grants=" + grantsAdapter + "]" + isNew();
    }

    //todo make configurable
    protected List<String> getAppliableGrants () {
        return Arrays.asList("R", "W", "G");
    }

}
