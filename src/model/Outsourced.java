package model;

/** @author ASaunders */

/***
 * The outsourced class extends the part class.
 */
public class Outsourced extends Part {
    private String companyName;

    /***
     * Outsourced parts extend Part
     * @param id outsourced part id
     * @param name outsourced part name
     * @param price outsourced part price
     * @param stock outsourced part inventory
     * @param min outsourced parts minimum
     * @param max outsourced parts maximum
     * @param companyName name of company providing outsourced parts
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**@return company name*/
    public String getCompanyName() {
        return companyName;
    }

    /**@param companyName the company name to set*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
