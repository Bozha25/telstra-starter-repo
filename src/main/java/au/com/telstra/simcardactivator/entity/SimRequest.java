package au.com.telstra.simcardactivator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SimRequest {
    public String iccid;
    public String customerEmail;

    public SimRequest(String iccid, String customerEmail){
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }
}
