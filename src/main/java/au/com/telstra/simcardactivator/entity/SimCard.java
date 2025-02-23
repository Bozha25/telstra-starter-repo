package au.com.telstra.simcardactivator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iccid;
    private String customerEmail;
    private Boolean active;

    public SimCard(String iccid, String customerEmail, Boolean active){
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public SimCard(){}

    public Long getId() { return id; }

    public String getIccid(){ return iccid;}

    public String getCustomerEmail(){ return customerEmail;}

    public Boolean getActive(){ return active;}

    public String toString(){
        return String.format("ICCID: %s, Customer Email: %s, Active: %s", iccid, customerEmail, active);
    }

}
