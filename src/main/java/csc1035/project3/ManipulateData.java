package csc1035.project3;

import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.HibernateException;


// Here comes CREATE/READ/UPDATE/DELETE method
public class ManipulateData {

    /**
     * Read in commodity data from a CSV file to create new Commodity objects
     */
    public static void readCsv() {
        String csvPath = "src\\main\\resources\\stock.sample.csv";
        String line = "";

        //creates a session object
        Session session = HibernateUtil.getSessionFactory().openSession();

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

            while ((line = br.readLine()) != null) {

                //split to comma
                String[] data = line.split(",");

                //create new Trinket and convert every attribute into proper type
                Commodity newCom = new Commodity();
                newCom.setName(data[1]);
                newCom.setCategory(data[2]);
                newCom.setPerishable(Boolean.parseBoolean(data[3]));
                newCom.setCost(Double.parseDouble(data[4]));
                newCom.setStock(Integer.parseInt(data[5]));
                newCom.setSellPrice(Double.parseDouble(data[6]));


                session.beginTransaction();
                //save new commodity object
                session.save(newCom);
                //commit the transaction
                session.getTransaction().commit();
            }


            // close session
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete a Commodity
     *
     * @param commodityID the ID of the commodity to delete
     */
    public static void deleteCommodity(int commodityID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Commodity commodity = session.get(Commodity.class, commodityID);
            session.delete(commodity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Delete a transaction
     *
     * @param transactionID the ID of the transaction to delete
     */
    public static void deleteTransaction(int transactionID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, transactionID);
            session.delete(transaction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    /**
     * Read the Commodity table then print it in an ascii table format
     */
    public static void printCommodity() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List Commodities = session.createQuery("FROM Commodity ").list();
            String commodityTableFormat = "|%-14s |%-16s |%-7s |%-26s |%-11s |%-11s |%-6s |%n";
            System.out.println("+---------------+-----------------+--------+---------------------------+------------+------------+-------+");
            System.out.println("|CommodityID    |Category         |Cost    |Name                       |Perishable  |Sell Price  |Stock  |");
            System.out.println("+---------------+-----------------+--------+---------------------------+------------+------------+-------+");
            for (Iterator<Commodity> iterator = Commodities.iterator(); iterator.hasNext(); ) {
                Commodity commodity = iterator.next();
                System.out.format(commodityTableFormat, commodity.getCommodityID(),commodity.getCategory(),commodity.getCost(),commodity.getName(),commodity.getPerishable(),commodity.getSellPrice(),commodity.getStock());
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("+---------------+-----------------+--------+---------------------------+------------+------------+-------+");
    }
    /**
     * Read a Commodity
     *
     * @param commodityID the ID of the Commodity to be read
     * @return the Commodity
     */
    /**
     * Read a Commodity
     *
     * @param commodityID the ID of the Commodity to be read
     * @return the Commodity
     */
    public static Commodity readCommodity(int commodityID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List Commodities = session.createQuery("FROM Commodity ").list();
            for (Iterator<Commodity> iterator = Commodities.iterator(); iterator.hasNext(); ) {
                Commodity commodity = iterator.next();
                if(commodity.getCommodityID() == commodityID){
                    return commodity;
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Read a transaction, returning transaction from ID
     *
     * @param transactionID the ID of the transaction to be read
     * @return the Transaction
     */
    public static Transaction readTransaction(int transactionID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List Transactions = session.createQuery("FROM Transaction ").list();
            for (Iterator<Transaction> iterator = Transactions.iterator(); iterator.hasNext(); ) {
                Transaction transaction = iterator.next();
                if (transaction.getTransactionID() == transactionID) {
                    return transaction;
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Read the transaction table then print the table in an ascii table
     */
    public static void printTransaction() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            List Transactions = session.createQuery("FROM Transaction ").list();
            System.out.println("+---------------+-----------------+------------+-------+-------------+");
            System.out.println("|TransactionID  | Change Given    | Date       | Paid  | Total Cost  |" );
            System.out.println("+---------------+-----------------+------------+-------+-------------+");
            String transactionTableFormat = "| %-13s | %-15s | %-10s | %-5s | %-11s |%n";
            for (Iterator<Transaction> iterator = Transactions.iterator(); iterator.hasNext(); ) {
                Transaction transaction = iterator.next();
                System.out.format(transactionTableFormat, transaction.getTransactionID(),transaction.getChange_given(),transaction.getDate(),transaction.getPaid(),transaction.getTotalcost());
            }
            System.out.println("+---------------+-----------------+------------+-------+-------------+");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }



    /**
     * Add a new Commodity
     *
     * @param commodityID the ID of the Commodity
     * @param name the name
     * @param category the category
     * @param perishable if the Commodity is perishable
     * @param cost the cost
     * @param stock the stock level
     * @param sellPrice the sell price
     */
    public static void addCommodity(int commodityID, String name, String category, boolean perishable, double cost,
                                    int stock, double sellPrice){
        // Creates a new session
        Session session = HibernateUtil.getSessionFactory().openSession();


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Commodity c1 = new Commodity();
            // adding a new item with parameters such as: name, category, etc. based on parameters defined in Commodity class
            c1.setCommodityID(commodityID);
            c1.setName(name);
            c1.setCategory(category);
            c1.setPerishable(perishable);
            c1.setCost(cost);
            c1.setStock(stock);
            c1.setSellPrice(sellPrice);

            session.save(c1);
            // commiting changes
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            if (session!=null){
                // If transaction fails, the rollback will be executed
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        finally{
            session.close();
        }

    }

    /**
     * Update a Commodity
     *
     * @param commodityID the ID of the Commodity
     * @param name the name
     * @param category the category
     * @param perishable if the Commodity is perishable
     * @param cost the cost
     * @param stock the stock level
     * @param sellPrice the sell price
     */
    public static void updateCommodity(int commodityID, String name, String category, boolean perishable, double cost,
                                       int stock, double sellPrice){
        // Creates a new session
        Session session = HibernateUtil.getSessionFactory().openSession();


        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Commodity commodity = (session.get(Commodity.class, commodityID));// get item based on its ID
            // setting name, category, etc. based on parameters defined in Commodity class
            commodity.setName(name);
            commodity.setCategory(category);
            commodity.setPerishable(perishable);
            commodity.setCost(cost);
            commodity.setStock(stock);
            commodity.setSellPrice(sellPrice);
            session.update(commodity);
            // commiting changes
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            if (session != null){
                // If transaction fails, the rollback will be executed
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        finally {
            session.close();
        }
    }

    /**
     * Create a new transaction
     *
     * @param productID the ID of the product
     * @param money_paid the money paid for the Commodities
     * @param total_cost the total cost of the Commodities
     * @param commodityList the list of Commodities purchased
     * @param quantityList the list of quantities purchased
     */
    public static void createTransaction(int productID, double money_paid,double total_cost, List<Commodity> commodityList,
                                   List<Integer> quantityList) {
        Transaction newTrans = new Transaction();
        CommodityQuantity newQuant = new CommodityQuantity();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //set date to today's date
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            newTrans.setDate(dateFormat.format(date));

            //create the transaction
            newTrans.setPaid(money_paid);
            newTrans.setTotal_cost(total_cost);
            newTrans.setChange_given(money_paid-total_cost);
            //begin new transaction
            session.beginTransaction();
            //save new transaction
            session.save(newTrans);
            //commit new transaction
            session.getTransaction().commit();

            //for every commodity inside the commodityList create new transaction-commodity relationship
            for ( int i=0;i<commodityList.size();i++){
                //create new session
                Session session1 = HibernateUtil.getSessionFactory().openSession();
                // begin new transaction
                session1.beginTransaction();
                newQuant.setCommodity(commodityList.get(i));
                newQuant.setTransaction(newTrans);
                newQuant.setQuantity(quantityList.get(i));
                //save new relationship
                session1.saveOrUpdate(newQuant);
                //commit new relationship
                session1.getTransaction().commit();
                //close session1
                session1.close();
            }
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            //close session
            session.close();
        }
        CreateReceipt.createReceipt(newTrans.getTransactionID(),quantityList,commodityList);
    }

    /**
     * Read the Commodity stock levels
     */
    public static void readCommodityStock() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        session.beginTransaction();
        List Commodities = session.createQuery("FROM Commodity ").list();
        for (Iterator<Commodity> iterator = Commodities.iterator(); iterator.hasNext(); ) {
            Commodity commodity = iterator.next();
            String name = commodity.getName();
            int stock = commodity.getStock();
            System.out.println("Product Name: " + name + ", Quantity: " + stock + "\n");
            }
        session.getTransaction().commit();
        } catch (HibernateException e) {
        if (session != null) session.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        session.close();
    }
    }

}