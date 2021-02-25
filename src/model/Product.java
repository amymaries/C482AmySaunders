package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author ASaunders */

/***
 * This is the product class
 */

public class Product<id, name, price, stock, min, max> {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /***
     * Product constructor
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock inventory of product
     * @param min product minimum in inventory
     * @param max product maximum in inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**@return the min*/
    public int getMin() {
        return min;
    }

    /**@param min the min to set*/
    public void setMin(int min) {
        this.min = min;
    }

    /**@return the max*/
    public int getMax() {
        return max;
    }

    /**@param max the max to set*/
    public void setMax(int max) {
        this.max = max;
    }

    /**The addAssociatedPart method is used to add a part object to the list of associatedParts.
     @param part object of type Part*/
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }


    /***
     * The deleteAssociatedPart method is used to remove a selected part object from the list of associatedParts.
     * @param selectedAssociatedPart selected object of type Part
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return true;
    }

    /**The getAllAssociatedPart method is used to return a list of parts.
     @return ObservableList of associatedParts*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}


