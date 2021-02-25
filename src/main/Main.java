package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import static model.Inventory.getPartIdCount;
import static model.Inventory.getProductIdCount;

/**Author ASaunders
 This is the main method. It is called first.*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 700, 350));
        primaryStage.show();
    }

    /**Main method runs first. It contains the initial part/product information.*/
    public Main(){
        Part p1 = new InHouse(getPartIdCount(), "Sprocket", 34.99, 10, 1, 24, 100);
        Part p2 = new InHouse(getPartIdCount(), "Pedal", 23.99, 10, 1, 24, 101);
        Part p3 = new InHouse(getPartIdCount(), "Seat Post", 19.99, 10, 1, 24, 102);
        Part p4 = new InHouse(getPartIdCount(), "Derailleurs", 13.99, 10, 1, 24, 103);
        Part p5 = new InHouse(getPartIdCount(), "Chain", 25.00, 10, 1, 24, 104);

        Part p6 = new Outsourced(getPartIdCount(), "Rim", 25.99, 10, 1,24, "Schwinn");
        Part p7 = new Outsourced(getPartIdCount(), "Suspension Forks", 29.99, 10, 1, 24, "Huffy");
        Part p8 = new Outsourced(getPartIdCount(), "Tire", 7.99, 10, 1, 24, "Bianchi");
        Part p9 = new Outsourced(getPartIdCount(), "Shifters", 19.99, 10, 1, 24, "Cannondale");
        Part p10 = new Outsourced(getPartIdCount(), "Saddle", 15.99, 10, 1, 24, "Kent");

        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Inventory.addPart(p3);
        Inventory.addPart(p4);
        Inventory.addPart(p5);
        Inventory.addPart(p6);
        Inventory.addPart(p7);
        Inventory.addPart(p8);
        Inventory.addPart(p9);
        Inventory.addPart(p10);

        Product p11 = new Product(getProductIdCount(), "Wheel Assembly", 34.99, 10, 1, 10);
        Product p12 = new Product(getProductIdCount(), "Crank Assembly", 38.99, 10, 1, 10);
        Product p13 = new Product(getProductIdCount(), "Brake Assembly", 41.99, 10, 1, 10);
        Product p14 = new Product(getProductIdCount(), "Gear Assembly", 32.99, 10, 1, 10);

        Inventory.addProduct(p11);
        Inventory.addProduct(p12);
        Inventory.addProduct(p13);
        Inventory.addProduct(p14);

        p11.addAssociatedPart(p6);
        p11.addAssociatedPart(p8);
        p12.addAssociatedPart(p4);
        p12.addAssociatedPart(p5);
        p13.addAssociatedPart(p7);
        p13.addAssociatedPart(p9);
        p14.addAssociatedPart(p1);
        p14.addAssociatedPart(p5);
        p14.addAssociatedPart(p9);

    }


    /**SECTION G EXTEND FUNCTIONALITY COMMENT: In an updated version of this application, I would
     *  extend functionality of the search by not requiring a separate button to handle the search
     *  event. Rather, users would search (by full name, partial name or ID) and then the search
     *  field itself would handle the event. I think it would most user-friendly if the field didn't
     *  require use of the search button or the enter key within the search field and would just
     *  return results triggered by user typing within the field. I'm sure there is a way to
     *  add that functionality because most search fields interact that way, so that is what I'd
     *  work toward as a way to extend functionality.
     *  */

    public static void main(String[] args) {



        launch(args);
    }
}
