package com.service.bus.models;

/**
 * Created by Ambika on 12-May-18.
 */

public class OrganizationModel {

    String organizationName;
    String organizationAddress;
    String organizationEmailId;
    String organizationContactNo;
    String organizationProof;


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationEmailId() {
        return organizationEmailId;
    }

    public void setOrganizationEmailId(String organizationEmailId) {
        this.organizationEmailId = organizationEmailId;
    }

    public String getOrganizationContactNo() {
        return organizationContactNo;
    }

    public void setOrganizationContactNo(String organizationContactNo) {
        this.organizationContactNo = organizationContactNo;
    }

    public String getOrganizationProof() {
        return organizationProof;
    }

    public void setOrganizationProof(String organizationProof) {
        this.organizationProof = organizationProof;
    }

}
