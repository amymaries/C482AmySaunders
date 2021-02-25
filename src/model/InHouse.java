package model;

/** @author ASaunders */

/***
 * The inHouse class extends part class
 */
public class InHouse extends Part{

    private int machineId;

    /***
     * The InHouse constructor extends from the Part class.
     * @param id inHouse part id
     * @param name inHouse name
     * @param price inHouse price
     * @param stock inHouse inventory
     * @param min inHouse inventory minimum
     * @param max inHouse inventory maximum
     * @param machineId machineId for inHouse part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**@return machine ID*/
    public int getMachineId() {
        return machineId;
    }

    /**@param machineId the machine ID to set*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
