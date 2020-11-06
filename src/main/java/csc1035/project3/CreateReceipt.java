package csc1035.project3;

        import java.util.Iterator;
        import java.util.List;
        import java.util.Set;

public class CreateReceipt {
    /**
     * Print the receipt of a given transaction
     *
     * @param transactionID the ID of the transaction to print a receipt of
     * @param quantityList the list of quantities involved in the transaction
     */
    public static void createReceipt(int transactionID, List<Integer> quantityList, List<Commodity> commodityList) {
        Transaction transaction = ManipulateData.readTransaction(transactionID);
        Set<Commodity> commodities = transaction.getCommodities();
        System.out.println(commodities);
        String date = transaction.getDate();
        System.out.println("+--------------------------------------------+-------------------------------+");
        System.out.println("|TransactionID: " + transactionID + "                           | Date: " + date + "              |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("| Product Name               | Product Price | Product Quantity | Line Total |");
        System.out.println("+----------------------------+---------------+------------------+------------+");

        String productTableFormat = "| %-26s | %-13s | %-16s | %-10s | %n";

        for (int i = 0; i < quantityList.size(); i++) {
            int quantity = quantityList.get(i);
            System.out.format(productTableFormat, commodityList.get(i).getName(), commodityList.get(i).getSellPrice(), quantity, quantity * commodityList.get(i).getSellPrice());
        }


        double totalCost = transaction.getTotalcost();
        double paid = transaction.getPaid();
        double change = transaction.getChange_given();
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|Total Cost: " + totalCost + " | Amount Paid: " + paid +  " | Change Given: " + change + "                |");
        System.out.println("+----------------------------------------------------------------------------+");
    }
}
