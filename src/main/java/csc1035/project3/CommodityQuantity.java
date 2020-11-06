package csc1035.project3;

import javax.persistence.*;

@Entity
@Table(name = "TRANSACTION_COMMODITIES")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.commodity",
                joinColumns = @JoinColumn(name = "commodityID")),
        @AssociationOverride(name = "primaryKey.transaction",
                joinColumns = @JoinColumn(name = "transactionID")) })

public class CommodityQuantity {
    private CommodityQuantityKey primaryKey = new CommodityQuantityKey();

    private int quantity;

    @EmbeddedId
    public CommodityQuantityKey getPrimaryKey(){
        return primaryKey;
    }

    public void setPrimaryKey(CommodityQuantityKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient
    public Commodity getCommodity() {
        return getPrimaryKey().getCommodity();
    }

    public void setCommodity(Commodity commodity){
        getPrimaryKey().setCommodity(commodity);
    }

    @Transient
    public Transaction getTransaction(){
        return  getPrimaryKey().getTransaction();
    }

    public void setTransaction(Transaction transaction){
        getPrimaryKey().setTransaction(transaction);
    }

    @Column(name = "quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }







}
