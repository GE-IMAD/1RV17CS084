package com.rvcollege.onlinevoting.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String adhaar;
    private String voterId;
    private String phone;
    private String constituency;
    private String partyname;
    private String url;
    private int partyvotes;
    private int secretKey;
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }
    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }
    public String getPartyName() {
        return partyname;
    }

    public void setPartyName(String partyname) {
        this.partyname = partyname;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public int getVotes() {
        return partyvotes;
    }

    public void setVotes(int partyvotes) {
        this.partyvotes = partyvotes;
    }


    public void setSecretKey(int secretKey) {
        this.secretKey=secretKey;

    }
    public int getSecretKey(){
        return secretKey;
    }
}
