package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import static model.Inventory.getPartIdCount;


/**author ASaunders
 This controller controls the Add Part form*/

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton AddPartInHouseRadBtn;

    @FXML
    private ToggleGroup AddPartSource;

    @FXML
    private RadioButton AddPartOutsourcedRadBtn;

    @FXML
    private Label labelPartSource;

    @FXML
    private TextField AddPartIDField;

    @FXML
    private TextField AddPartNameField;

    @FXML
    private TextField AddPartInventoryField;

    @FXML
    private TextField AddPartPriceField;

    @FXML
    private TextField AddPartMaxField;

    @FXML
    private TextField AddPartMinField;

    @FXML
    private TextField partSourceField;


    /***
     * onActionCancel method returns user to the Main Screen form after verification.
     * @param event user clicks the 'Cancel' button on the Add Part Controller form.
     * @throws IOException possible IO exception
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear text field values. Would you like to proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**onActionSave method is used to save user-entered information which modifies a part.
     @param event user clicks the 'Save' button on the Modify Part Controller form.
     @throws IOException handled with try/catch Number Format exception e.*/
    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException {
        try {
            String name = AddPartNameField.getText();
            int stock = Integer.parseInt(AddPartInventoryField.getText()); //wrapper to convert string to int
            double price = Double.parseDouble(AddPartPriceField.getText());
            int max = Integer.parseInt(AddPartMaxField.getText());
            int min = Integer.parseInt(AddPartMinField.getText());

            if (AddPartInHouseRadBtn.isSelected()) { //assigns value based on which radio button is selected
                labelPartSource.setText("Machine ID");
                int machineID = Integer.parseInt(partSourceField.getText());
                Inventory.addPart(new InHouse(getPartIdCount(), name, price, stock, max, min, machineID));
            }

            else if(AddPartOutsourcedRadBtn.isSelected()){
                labelPartSource.setText("Company Name");
                String companyName = partSourceField.getText();
                Inventory.addPart(new Outsourced(getPartIdCount(), name, price, stock, max, min, companyName));
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }


        catch (NumberFormatException e) {
            //setup error dialog box
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        }
    }

    /**onActionInHouse method is used to set the text of the partSource label to "Machine ID".
     @param event user clicks the 'In-House' radio button.*/
    @FXML
    void onActionInHouse(ActionEvent event){
        labelPartSource.setText("Machine ID");
    }

    /**onActionOutsourced method is used to set the text of the partSource label to "Company Name".
     @param event user clicks the 'Outsourced' radio button.*/
    @FXML
    void onActionOutsourced(ActionEvent event){
        labelPartSource.setText("Company Name");
    }


    /**initialize method.
     @param url is required.
     @param resourceBundle is required*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}