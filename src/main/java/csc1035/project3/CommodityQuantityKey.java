package csc1035.project3;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
@Embeddable
public class CommodityQuantityKey implements Serializable {
    private Commodity commodity;
    private Transaction transaction;

    @ManyToOne(cascade = CascadeType.ALL)
    public Commodity getCommodity(){
        return commodity;
    }

    public void setCommodity(Commodity commodity) {

        this.commodity = commodity;
    }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Transaction getTransaction(){
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
