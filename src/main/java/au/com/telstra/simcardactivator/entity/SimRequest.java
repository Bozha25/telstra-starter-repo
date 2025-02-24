package au.com.telstra.simcardactivator.entity;

public class SimRequest {
    private String iccid;
    private String customerEmail;

    public SimRequest(String iccid, String customerEmail){
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

}
