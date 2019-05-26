package com.service.bus.models;

/**
 * Created by Ambika on 12-May-18.
 */

public class RenewPassModel {

    String renewId;
    String renewSource;
    String renewDestination;
    String renewPeriod;
    String renewDistance;
    String renewDepotName;

    public String getRenewId() {
        return renewId;
    }

    public void setRenewId(String renewId) {
        this.renewId = renewId;
    }

    public String getRenewSource() {
        return renewSource;
    }

    public void setRenewSource(String renewSource) {
        this.renewSource = renewSource;
    }

    public String getRenewDestination() {
        return renewDestination;
    }

    public void setRenewDestination(String renewDestination) {
        this.renewDestination = renewDestination;
    }

    public String getRenewPeriod() {
        return renewPeriod;
    }

    public void setRenewPeriod(String renewPeriod) {
        this.renewPeriod = renewPeriod;
    }

    public String getRenewDistance() {
        return renewDistance;
    }

    public void setRenewDistance(String renewDistance) {
        this.renewDistance = renewDistance;
    }

    public String getRenewDepotName() {
        return renewDepotName;
    }

    public void setRenewDepotName(String renewDepotName) {
        this.renewDepotName = renewDepotName;
    }
}
