package csc1035.project3;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COMMODITY")
public class Commodity {
    private int commodityID;
    private String name;
    private String category;
    private boolean perishable;
    private double cost;
    private int stock;
    private double sellPrice;

    private Set<CommodityQuantity> commodityQuantities = new HashSet<CommodityQuantity>();

    public Commodity(){}

    /**
     * Create a new Commodity
     *
     * @param commodityID the ID of the Commodity
     * @param name the name
     * @param category the category
     * @param perishable if the Commodity is perishable
     * @param cost the cost
     * @param stock the stock level
     * @param sellPrice the sell price
     */
    public Commodity(int commodityID, String name, String category, boolean perishable, double cost, int stock, double sellPrice){
        this.commodityID = commodityID;
        this.name = name;
        this.category = category;
        this.perishable = perishable;
        this.cost = cost;
        this.stock = stock;
        this.sellPrice = sellPrice;
    }

    public void addTransaction(CommodityQuantity transaction){
        this.commodityQuantities.add(transaction);
    }

    @Id
    @GeneratedValue
    @Column(name = "commodityID")
    public int getCommodityID(){
        return commodityID;
    }

    public void setCommodityID(int commodityID){
        this.commodityID=commodityID;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }


    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public boolean getPerishable() {
        return perishable;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    @OneToMany(mappedBy = "primaryKey.commodity",
            cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<CommodityQuantity> getCommodityQuantities() {
        return commodityQuantities;
    }

    public void setCommodityQuantities(Set<CommodityQuantity> transactions) {
        this.commodityQuantities = transactions;
    }

    public void addCommodityQuantity(CommodityQuantity commodityQuantity) {
        this.commodityQuantities.add(commodityQuantity);
    }
}
