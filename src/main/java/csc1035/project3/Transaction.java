package csc1035.project3;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "transactionID",updatable = false, nullable = false)
    private int transactionID;

    @Column(name = "total_cost")
    private double total_cost;

    @Column(name = "date")
    private String date;

    @Column(name = "paid")
    private double paid;

    @Column(name = "change_given")
    private double change_given;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TRANSACTION_COMMODITIES",
            joinColumns = {@JoinColumn(name="transactionID")},
            inverseJoinColumns = {@JoinColumn(name="commodityID")})
    private Set<Commodity> commodities = new HashSet<>();

    /**
     * Create a new Transaction
     *
     * @param transactionID the ID of the transaction
     */
    public Transaction(int transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Create a new Transaction
     *
     * @param transactionID the ID of the transaction
     * @param total_cost the total cost
     * @param paid the amount paid
     * @param change_given the change given
     * @param date the date of the transaction
     */
    public Transaction(int transactionID, double total_cost, double paid, double change_given, String date) {

        this.transactionID = transactionID;
        this.paid = paid;
        this.change_given = change_given;
        this.total_cost = total_cost;
        this.date= date;
    }

    public Transaction() {
    }

    public int getTransactionID() {

        return transactionID;
    }

    public void setTotal_cost(double total_cost) {

        this.total_cost = total_cost;
    }

    public double getTotalcost(){
        return total_cost;
    }

    public void setDate(String date){
        this.date=date;
    }

    public String getDate(){
        return date;
    }

    public void setPaid(double paid){
        this.paid=paid;
    }

    public double getPaid(){
        return paid;
    }

    public void setChange_given(double change_given){
        this.change_given=change_given;
    }

    public double getChange_given(){
        return change_given;
    }

    public Set<Commodity> getCommodities(){
        return commodities;
    }

    public void setCommodities(Set<Commodity> commodities){
        this.commodities = commodities;
    }


}

