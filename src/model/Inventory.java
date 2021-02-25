package model;

/** @author ASaunders */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * This is the Inventory class
 */
public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCount = 0;
    private static int productIdCount = 1;


    /**The addPart method is used to return a list of parts after one has been added by user.
     @param newPart part added by user. */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**The addProduct method is used to return a list of products after one has been added by user.
     @param newProduct product added by user*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**The lookupPart method is used by the search method in the Main Screen Controller
     to search the list of all parts by integer and return a single match.
     @param partId the numeric identification (auto-generated) of each part
     @return any single part found*/
    public static Part lookupPart(int partId) {
        for(Part part : allParts){
            //Part part = allParts.get(i); // find part by index since we're searching by int
            if(part.getId() == partId) {
                return part; //because we should only have one exact match or else return null.
            }
        }
        return null;
    }


    /***
     * The lookupProduct method is used by the search method in the Main Screen Controller
     * to search the list of all products by integer and return a single match.
     * @param productId product id
     * @return the product
     */
    public static Product lookupProduct(int productId) {
        for(Product product : allProducts){
            //Product product = allProducts.get(i);
            if(product.getId() == productId){
                return product;
            }
        }
        return null;

    }


    /**The lookupPart method is used by the search method in the Main Screen Controller
     * to search the list of all parts by string (full or partial) and return matches.
     * @param searchPartName search part name by string input, partial or full
     * @return a list of found parts
     * * <p><b>
     * SECTION G LOGICAL ERROR COMMENT: I confused the parameter and the method in the second if statement
     * in the lookupPart method, which returned an empty list (of course). I could tell
     * the list was empty by running the program in debug mode with a stop at line 74, and
     * then walking the program over that line. Wanda found the error (thank you!) and I
     * corrected the statement, which corrected my search method.
     * </b></p>
     */
    public static ObservableList<Part> lookupPart(String searchPartName) {
       ObservableList<Part> foundPart = FXCollections.observableArrayList();//new, empty list for any parts found
       if(searchPartName.length() == 0){
           foundPart = allParts;//field populates with entire list when search field is blank
       } else {
           for (Part part : allParts) {
               if (part.getName().contains(searchPartName)) {
                   foundPart.add(part);
               }
           }
       }
        return foundPart;
    }



    /**The lookupProduct method is used by the search method in the Main Screen Controller
     to search the list of all products by string (full or partial) and return matches.
     @param searchProductName The search string.
     @return a list of found parts*/
    public static ObservableList<Product> lookupProduct(String searchProductName) {
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        if(searchProductName.length() == 0){
            foundProduct = allProducts;//if field is blank, entire list should be passed in.
        } else {
            for(Product product : allProducts){
                if(product.getName().contains(searchProductName)){
                    foundProduct.add(product);
                }
            }
        }
        return foundProduct;
    }

    /**The updatePart method is used to update part information after modified by user.
     @param index the index of the selectedPart in the ObservableList.
     @param selectedPart the part selected */
    public static void updatePart(int index, Part selectedPart){
        for(Part part : allParts)
            if(allParts.get(index).getId() == selectedPart.getId()){
                allParts.set(index, selectedPart);
            }
    }

    /**The updateProduct method is used to update a product after it is modified by user.
     @param index the index of the newProduct in the list
     @param newProduct the modified product*/
    public static void updateProduct(int index, Product newProduct) {
        for(Product product : allProducts){
            if(allProducts.get(index).getId() == newProduct.getId());
            allProducts.set(index, newProduct);
        }

    }

    /**The deletePart method is used to remove the selected part from the observable list of parts.
     @param selectedPart The selected part from the list.
     @return boolean value*/
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**The deleteProduct method is used to remove the selected product from the observable list of products.
     @param selectedProduct selected product
     @return boolean value*/
    public static  boolean deleteProducts(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /** Returns the data as an observable list of parts.
     @return part list*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Returns the data as an observable list of products.
     @return product list*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** The getPartIdCount method auto-assigns the partId (even numbers).
     @return part Id numbers*/
    public static int getPartIdCount(){
        partIdCount += 2;
        return partIdCount;
    }

    /** The getProductIdCount method auto-assigns the productId (odd numbers).
     @return product Id numbers*/
    public static int getProductIdCount(){
        productIdCount += 2;
        return productIdCount;
    }
}
