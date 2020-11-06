package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;


import java.util.*;

public class UI {
    /**
     * Print the main menu
     */
    public static void mainMenu() {
        int choice = -1;

        //while user's choice isn't 0 keep running interface
        while (choice != 0) {
            Scanner sc = new Scanner(System.in);


            System.out.println("Type 1, if you want to import the commodity into database, using the csv file.");
            System.out.println("Type 2, if you want to create a new transaction.");
            System.out.println("Type 3, if you want a list of the available stock.");
            System.out.println("Type 4, if you want to update a commodity.");
            System.out.println("Type 5, if you want add a new commodity");
            System.out.println("Type 6, if you want to update the stock of a commodity.");
            System.out.println("Type 7, if you want to view the commodity table.");
            System.out.println("Type 8, if you want to view the transaction table.");
            System.out.println("Else, type 0 to exit.");

            //user's choice validation
            do {
                //while input not integer, keep asking user input
                while (!sc.hasNextInt()) {
                    String inputInvalid = sc.next();
                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                    System.out.println("Type 1, if you want to import the commodity into database, using the csv file.");
                    System.out.println("Type 2, if you want to create a new transaction.");
                    System.out.println("Type 3, if you want a list of the available stock.");
                    System.out.println("Type 4, if you want to update a commodity.");
                    System.out.println("Type 5, if you want add a new commodity");
                    System.out.println("Type 6, if you want to update the stock of a commodity.");
                    System.out.println("Type 7, if you want to view the commodity table.");
                    System.out.println("Type 8, if you want to view the transaction table.");
                    System.out.println("Else, type 0 to exit.");
                }
                //else read user's input
                choice = sc.nextInt();

                //if user's input is an invalid integer, keep asking user input
                if (choice < 0 || choice > 8) {
                    System.out.printf("\"%s\" is not a valid choice.\n", choice);
                    System.out.println("Type 1, if you want to import the commodity into database, using the csv file.");
                    System.out.println("Type 2, if you want to create a new transaction.");
                    System.out.println("Type 3, if you want a list of the available stock.");
                    System.out.println("Type 4, if you want to update a commodity.");
                    System.out.println("Type 5, if you want create a add commodity");
                    System.out.println("Type 6, if you want to update the stock of a commodity.");
                    System.out.println("Type 7, if you want to view the commodity table.");
                    System.out.println("Type 8, if you want to view the transaction table.");
                    System.out.println("Else, type 0 to exit.");
                }
            } while (choice < 0 || choice > 8);

            if (choice == 1) {
                ManipulateData.readCsv();
            } else if (choice == 2) {
                createTransactionMenu();
            }else if (choice == 3){
                ManipulateData.readCommodityStock();
            }else if(choice == 4){
                updateCommodityMenu();
            }else if (choice == 5){
               addCommodityMenu();
            }else if (choice == 6){
                updateStockMenu();
            }else if (choice == 7){
                ManipulateData.printCommodity();
            } else if (choice == 8){
                ManipulateData.printTransaction();
            }
        }
    }


    /**
     * Print the Create Transaction menu
     */
    public static void createTransactionMenu() {

        int productID = 0;
        Scanner sc = new Scanner(System.in);
        Commodity commod = new Commodity();
        int quantity;
        double total_cost = 0;
        double money_paid;
        int answer;
        List<Commodity> commodityList = new ArrayList();
        List<Integer> quantityList = new ArrayList();

        //start new session
        Session session = HibernateUtil.getSessionFactory().openSession();
        //create list of commodities from the Commodity Table
        List commodity = session.createQuery("FROM Commodity ").list();


        do {
            System.out.println("To add new item in the transaction press 1.");
            System.out.println("Else, press 0 to end transaction.");
            //user input validation
            do {
                //while input not integer, keep asking user input
                while (!sc.hasNextInt()) {
                    String inputInvalid = sc.next();
                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                    System.out.println("To add new item in the transaction press 1.");
                    System.out.println("Else, press 0 to end transaction.");
                }
                //else read user's input
                answer = sc.nextInt();
                sc.nextLine();

                //if user's input is an invalid integer, keep asking user input
                if (answer < 0 || answer > 1) {
                    System.out.printf("\"%s\" is not a valid choice.\n", answer);
                    System.out.println("To add new item in the transaction press 1.");
                    System.out.println("Else, press 0 to end transaction.");
                }
            } while (answer < 0 || answer > 1);

            //if user input is 0, then break;
            if (answer == 0) {
                break;
            }

            do {
                //ask user for commodityID number
                System.out.println("Please type product id number:");
                //while input not integer, keep asking user input
                while (!sc.hasNextInt()) {
                    String inputInvalid = sc.next();
                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                    System.out.println("Please type product id number:");
                }

                //else read user's input
                productID = sc.nextInt();
                sc.nextLine();

                //ask user for the quantity of the product
                System.out.println("Please type the quantity of the product:");
                //while input not integer, keep asking user input
                while (!sc.hasNextInt()) {
                    String inputInvalid = sc.next();
                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                    System.out.println("Please type the quantity of the product:");
                }

                //else read user's input
                quantity = sc.nextInt();
                sc.nextLine();

                //productID validation
                //check if there is the productID in the Commodity table
                try {
                    //for every commodity in the Commodity Table, look if there is the commodityID that the user just typed
                    for (Iterator<Commodity> iterator = commodity.iterator(); iterator.hasNext(); ) {
                        commod = iterator.next();
                        //if productID is valid
                        if (commod.getCommodityID() == productID) {
                            //if product id is valid and quantity is valid, add its sell_price*quantity of it, to the total_cost
                            if (commod.getStock() >= quantity) {
                                total_cost = (commod.getSellPrice() * quantity) + total_cost;
                                //add the new commodity to the list where we keep all the products of current transaction
                                commodityList.add(commod);
                                //add the new quantity to a list where we keep the quantities of the products of this transaction
                                quantityList.add(quantity);
                                //update stock
                                ManipulateData.updateCommodity(commod.getCommodityID(), commod.getName(), commod.getCategory(),
                                        commod.getPerishable(), commod.getCost(), commod.getStock() - quantity, commod.getSellPrice());
                                //if quantity is valid, break;
                                break;
                            }
                            //if commodityID is valid, break;
                            break;
                        }
                    }
                } catch (HibernateException e) {
                    if (session != null) session.getTransaction().rollback();
                    e.printStackTrace();
                } finally {
                    //close session
                    session.close();
                }
                //if productID is not valid inform user and start loop again
                if (commod.getCommodityID() != productID) {
                    System.out.println("Cannot find product id number. Please try again.");
                }
                //if quantity is not valid inform user and start loop again
                if (commod.getStock() < quantity) {
                    System.out.println("Product isn't available for this quantity.Please try again.");
                }
                //loop doesn't stop until user gives a valid productID and a valid quantity
            } while (commod.getCommodityID() != productID || commod.getStock() < quantity);

            //inform user that the product was added into the transaction
            System.out.println("Item with id number " + productID + " is added.");
        } while (answer == 1);

        //inform user for the total cost of the new order
        System.out.println("The total cost of the order is: " + total_cost + "£.");
        //ask user for the amount of money paid by the costumer
        System.out.println("Please give the amount of money paid by the costumer:");
        do {
            //while input not a double, keep asking user input
            while (!sc.hasNextDouble()) {
                String inputInvalid = sc.next();
                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                System.out.println("The total cost of the order is:" + total_cost + "£.");
                System.out.println("Please give the amount of money paid by the costumer:");
            }
            //else read user's input
            money_paid = sc.nextDouble();
            sc.nextLine();

            //if user's input is invalid, keep asking user input
            if (money_paid < total_cost) {
                System.out.printf("The amount of money you just typed is less than the total cost of the order.Please try again.");
                System.out.println("The total cost of the order is:" + total_cost + "£.");
                System.out.println("Please give the amount of money paid by the costumer:");
            }
            //loop doesn't stop until money paid is more than the total cost of the order
        } while (money_paid < total_cost);
        //call createTransaction method and pass all the user's inputs into it
        ManipulateData.createTransaction(productID, money_paid, total_cost, commodityList, quantityList);
    }

    /**
     * Print the Update Commodity menu
     */
    public static void updateCommodityMenu() {
        //start new session
        Session session = HibernateUtil.getSessionFactory().openSession();
        //create list of commodities from the Commodity Table
        List commodity = session.createQuery("FROM Commodity ").list();
        Scanner sc = new Scanner(System.in);
        int productID;
        int stock=0;
        int answer;
        String name="";
        String category="";
        Boolean perishable=true;
        double cost=0.0;
        double sell_price=0.0;
        Commodity commod = new Commodity();


        do {
            //ask user for commodityID number
            System.out.println("Please type product id:");

            //while input not integer, keep asking user input
            while (!sc.hasNextInt()) {
                String inputInvalid = sc.next();
                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                System.out.println("Please type product id:");
            }

            //else read user's input
            productID = sc.nextInt();
            sc.nextLine();

            //productID validation
            //check if there is the productID in the Commodity table
            try {
                //for every commodity in the Commodity Table, look if there is the commodityID that the user just typed
                for (Iterator<Commodity> iterator = commodity.iterator(); iterator.hasNext(); ) {
                    commod = iterator.next();
                    //if productID is valid
                    if (commod.getCommodityID() == productID) {

                        //update category
                        do{
                            System.out.println("If you want to update the category of the item press 1, else press 0.");

                            //while input not integer, keep asking user input
                            while (!sc.hasNextInt()) {
                                String inputInvalid = sc.next();
                                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                System.out.println("If you want to update the category of the item press 1, else press 0.");
                            }

                            //else read user's input
                            answer = sc.nextInt();
                            sc.nextLine();
                            if(answer==1){
                                //ask user for new category
                                System.out.println("Please type the new category of the product");
                                category=sc.nextLine();
                            }else{category=commod.getCategory();}

                            //if user's input is an invalid integer, keep asking user input
                            if (answer < 0 || answer > 1) {
                                System.out.printf("\"%s\" is not a valid choice.\n", answer);
                            }
                        }while(answer < 0 || answer > 1);

                        //update cost
                        do{
                            System.out.println("If you want to update the cost of the item press 1, else press 0.");

                            //while input not integer, keep asking user input
                            while (!sc.hasNextInt()) {
                                String inputInvalid = sc.next();
                                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                System.out.println("If you want to update the cost of the item press 1, else press 0.");
                            }

                            //else read user's input
                            answer = sc.nextInt();
                            sc.nextLine();
                            if(answer==1){
                                //ask user for new cost
                                System.out.println("Please type the new cost of the product");
                                //cost input validation
                                //while input not double, keep asking user input
                                while (!sc.hasNextDouble()) {
                                    String inputInvalid = sc.next();
                                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                    System.out.println("Please type the new cost of the product");
                                }
                                //else read user's input
                                cost =sc.nextDouble();
                            }else{cost=commod.getCost();}

                            //if user's input is an invalid integer, keep asking user input
                            if (answer < 0 || answer > 1) {
                                System.out.printf("\"%s\" is not a valid choice.\n", answer);
                            }
                        }while(answer < 0 || answer > 1);

                        //update name
                        do {
                            System.out.println("If you want to update the name of the item press 1, else press 0.");

                            //while input not integer, keep asking user input
                            while (!sc.hasNextInt()) {
                                String inputInvalid = sc.next();
                                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                System.out.println("If you want to update the name of the item press 1, else press 0.");
                            }

                            //else read user's input
                            answer = sc.nextInt();
                            sc.nextLine();
                            if (answer == 1) {
                                //ask user for new name
                                System.out.println("Please type the new name of the product");
                                name = sc.nextLine();
                            }else{name=commod.getName();}

                            //if user's input is an invalid integer, keep asking user input
                            if (answer < 0 || answer > 1) {
                                System.out.printf("\"%s\" is not a valid choice.\n", answer);
                            }
                        }while(answer < 0 || answer > 1);

                            //update sell_price
                            do{
                                System.out.println("If you want to update the sell price of the item press 1, else press 0.");

                                //while input not integer, keep asking user input
                                while (!sc.hasNextInt()) {
                                    String inputInvalid = sc.next();
                                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                    System.out.println("If you want to update the sell price of the item press 1, else press 0.");
                                }


                                //else read user's input
                                answer = sc.nextInt();
                                sc.nextLine();
                                if(answer==1){
                                    //ask user for new sell_price
                                    System.out.println("Please type the new sell price of the product");
                                    //sell_price input validation
                                    //while input not double, keep asking user input
                                    while (!sc.hasNextDouble()) {
                                        String inputInvalid = sc.next();
                                        System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                        System.out.println("Please type the new sell price of the product");
                                    }
                                    //else read user's input
                                    sell_price =sc.nextDouble();
                                }else{sell_price=commod.getSellPrice();}

                                //if user's input is an invalid integer, keep asking user input
                                if (answer < 0 || answer > 1) {
                                    System.out.printf("\"%s\" is not a valid choice.\n", answer);
                                }
                            }while(answer < 0 || answer > 1);

                                //update perishable
                                do{
                                    System.out.println("If you want to update the perishable option of the item press 1, else press 0.");

                                    //while input not integer, keep asking user input
                                    while (!sc.hasNextInt()) {
                                        String inputInvalid = sc.next();
                                        System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                        System.out.println("If you want to update the perishable option of the item press 1, else press 0.");
                                    }

                                    //else read user's input
                                    answer = sc.nextInt();
                                    sc.nextLine();

                                    if(answer==1){
                                        //ask user for new category
                                        System.out.println("Please type the new perishable option of the product");
                                        //perishable input validation
                                        //while input not boolean, keep asking user input
                                        while (!sc.hasNextBoolean()) {
                                            String inputInvalid = sc.next();
                                            System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                            System.out.println("Please type the new perishable option of the product");
                                        }
                                        //else read user's input
                                        perishable =sc.nextBoolean();
                                    }else{perishable=commod.getPerishable();}

                                    //if user's input is an invalid integer, keep asking user input
                                    if (answer < 0 || answer > 1) {
                                        System.out.printf("\"%s\" is not a valid choice.\n", answer);
                                    }
                                }while(answer < 0 || answer > 1);

                        //update stock
                        do{
                            System.out.println("If you want to add number to the stock of the item press 1, else press 0.");

                            //while input not integer, keep asking user input
                            while (!sc.hasNextInt()) {
                                String inputInvalid = sc.next();
                                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                System.out.println("If you want to add number to the stock of the item press 1, else press 0.");
                            }

                            //else read user's input
                            answer = sc.nextInt();
                            sc.nextLine();
                            if(answer==1){
                                //ask user for the stock
                                System.out.println("Please type the number of stock you want to add:");

                                //while input not integer, keep asking user input
                                while (!sc.hasNextInt()) {
                                    String inputInvalid = sc.next();
                                    System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                                    System.out.println("Please type the number of stock you want to add:");
                                    }

                                //else read user's input
                                stock = sc.nextInt();
                                sc.nextLine();
                            }else{stock=commod.getStock();}
                            //if user's input is an invalid integer, keep asking user input
                            if (answer < 0 || answer > 1) {
                                System.out.printf("\"%s\" is not a valid choice.\n", answer);
                            }
                        }while(answer < 0 || answer > 1);

                        break;
                    }
                }
            } catch (HibernateException e) {
                if (session != null) session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                //close session
                session.close();
            }
            //if productID is not valid inform user and start loop again
            if (commod.getCommodityID() != productID) {
                System.out.println("Cannot find product id number. Please try again.");
            }

            //loop doesn't stop until user gives a valid productID and a valid quantity
        } while (commod.getCommodityID() != productID);
        ManipulateData.updateCommodity(commod.getCommodityID(), name, category,
                perishable, cost, commod.getStock() + stock, sell_price);


    }

    /**
     * Print the Add Commodity menu
     */
    public static void addCommodityMenu() {
        int stock;
        int commodityID=0;
        String name;
        String category;
        Boolean perishable;
        double cost;
        double sell_price;
        Scanner sc = new Scanner(System.in);
        ManipulateData manipulateData = new ManipulateData();

        System.out.println("Please give the name of the new commodity");
        name=sc.nextLine();

        System.out.println("Please give the category of the new commodity");
        category=sc.nextLine();

        //perishable input validation
        System.out.println("Please give perishable option of the new commodity");
        //while input not perishable, keep asking user input
        while (!sc.hasNextBoolean()) {
            String inputInvalid = sc.next();
            System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
            System.out.println("Please give perishable option of the new commodity");
        }
        perishable=sc.nextBoolean();
        sc.nextLine();

        //cost input validation
        System.out.println("Please give the cost of the new commodity");
        //while input not double, keep asking user input
        while (!sc.hasNextDouble()) {
            String inputInvalid = sc.next();
            System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
            System.out.println("Please give the cost of the new commodity");
        }
        cost = sc.nextDouble();
        sc.nextLine();

        //sell_price input validation
        System.out.println("Please give the sell price of the new commodity");
        //while input not double, keep asking user input
        while (!sc.hasNextDouble()) {
            String inputInvalid = sc.next();
            System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
            System.out.println("Please give the sell price of the new commodity");
        }
        sell_price = sc.nextDouble();

        //stock input validation
        System.out.println("Please give the stock number of the new commodity");
        //while input not integer, keep asking user input
        while (!sc.hasNextInt()) {
            String inputInvalid = sc.next();
            System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
            System.out.println("Please give the stock number of the new commodity");
        }
        stock = sc.nextInt();
        sc.nextLine();

        manipulateData.addCommodity(commodityID,name,category,perishable,cost,stock,sell_price);
    }

    public static void updateStockMenu() {
        //start new session
        Session session = HibernateUtil.getSessionFactory().openSession();
        //create list of commodities from the Commodity Table
        List commodity = session.createQuery("FROM Commodity ").list();
        Scanner sc = new Scanner(System.in);
        int productID;
        int stock;
        Commodity commod = new Commodity();

        do {
            //ask user for commodityID number
            System.out.println("Please type product id:");

            //while input not integer, keep asking user input
            while (!sc.hasNextInt()) {
                String inputInvalid = sc.next();
                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                System.out.println("Please type product id:");
            }

            //else read user's input
            productID = sc.nextInt();
            sc.nextLine();

            //ask user for the stock
            System.out.println("Please type the number of stock you want to add:");
            //while input not integer, keep asking user input
            while (!sc.hasNextInt()) {
                String inputInvalid = sc.next();
                System.out.printf("\"%s\" is not a valid choice.\n", inputInvalid);
                System.out.println("Please type the number of stock you want to add:");
            }

            //else read user's input
            stock = sc.nextInt();
            sc.nextLine();

            //productID validation
            //check if there is the productID in the Commodity table
            try {
                //for every commodity in the Commodity Table, look if there is the commodityID that the user just typed
                for (Iterator<Commodity> iterator = commodity.iterator(); iterator.hasNext(); ) {
                    commod = iterator.next();
                    //if productID is valid
                    if (commod.getCommodityID() == productID) {
                        //update stock
                        ManipulateData.updateCommodity(commod.getCommodityID(), commod.getName(), commod.getCategory(),
                                commod.getPerishable(), commod.getCost(), commod.getStock() + stock, commod.getSellPrice());
                        //if commodityID is valid, break;
                        break;
                    }
                }
            } catch (HibernateException e) {
                if (session != null) session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                //close session
                session.close();
            }
            //if productID is not valid inform user and start loop again
            if (commod.getCommodityID() != productID) {
                System.out.println("Cannot find product id number. Please try again.");
            }

            //loop doesn't stop until user gives a valid productID and a valid quantity
        } while (commod.getCommodityID() != productID);


    }
}