package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**author ASaunders
 This controller controls the Modify Part form*/

public class ModPartController implements Initializable {

    Stage stage;
    Parent scene;
    Part part;

    @FXML private RadioButton inHouseRadBtn;
    @FXML private RadioButton OutsourcedRadBtn;
    @FXML private ToggleGroup ModifyPart;
    @FXML private TextField ModPartIDField;
    @FXML private TextField ModPartNameField;
    @FXML private TextField ModPartInventoryField;
    @FXML private TextField ModPartPriceField;
    @FXML private TextField ModPartMaxField;
    @FXML private TextField ModPartMinField;
    @FXML private TextField partSourceField;
    @FXML private Label labelPartSource;



    /**onActionCancel method returns user to the Main Screen form after verification.
     @param event user clicks the 'Cancel' button on the Modify Part Controller form.
     @throws IOException is possible*/
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
     @throws IOException handled with try/catch Number Format exception e*/
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
            int partId = Integer.parseInt(ModPartIDField.getText());
            String name = ModPartNameField.getText();
            int stock = Integer.parseInt(ModPartInventoryField.getText()); //wrapper to convert string to int
            double price = Double.parseDouble(ModPartPriceField.getText());
            int max = Integer.parseInt(ModPartMaxField.getText());
            int min = Integer.parseInt(ModPartMinField.getText());

            //create new part from modified fields
            try {
                if (inHouseRadBtn.isSelected()) { //assigns value based on which radio button is selected
                     //labelPartSource.setText("Machine ID");
                     int machineID = Integer.parseInt(partSourceField.getText());
                     InHouse modifiedPart = new InHouse(partId, name, price, stock, max, min, machineID);
                     isValid();
                     Inventory.addPart(modifiedPart);
                } else if(OutsourcedRadBtn.isSelected()){
                    //labelPartSource.setText("Company Name");
                    String companyName = partSourceField.getText();
                    Outsourced modifiedPart = new Outsourced(partId, name, price, stock, max, min, companyName);
                    isValid();
                    Inventory.addPart(modifiedPart);
                }
                //remove former part when replaced with modified part
                Inventory.allParts.remove(part);
                //onActionSave should return to MainScreen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene(scene));
                stage.show();

            }
            //in case inventory does not fall in between min and max
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid value for each text field.");
                alert.showAndWait();
             }

    }

    public boolean isValid(){
        int min = Integer.parseInt(ModPartMinField.getText());
        int max = Integer.parseInt(ModPartMaxField.getText());
        int stock = Integer.parseInt(ModPartInventoryField.getText());
        if((stock < min) || (stock > max)){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory should be greater than min and less than max. Please enter valid values.");
            alert.showAndWait();
        }
        return true;
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


    /**getSelectedPart method is used to auto-populate details from selectedPart on the MainScreen to the modify screen.
     @param selectedPart is the part selected from the mainScreen parts table.*/
    public void getSelectedPart(Part selectedPart){
        this.part = selectedPart;
        if(selectedPart instanceof InHouse){
            labelPartSource.setText("Machine ID");
            InHouse inHouse = (InHouse)selectedPart;
            ModPartIDField.setText(Integer.toString(inHouse.getId()));
            ModPartNameField.setText(inHouse.getName());
            ModPartInventoryField.setText(Integer.toString(inHouse.getStock()));
            ModPartPriceField.setText(Double.toString(inHouse.getPrice()));
            ModPartMaxField.setText(Integer.toString(inHouse.getMax()));
            ModPartMinField.setText(Integer.toString(inHouse.getMin()));
            partSourceField.setText(Integer.toString(inHouse.getMachineId()));
            inHouseRadBtn.setSelected(true);
        } else if (selectedPart instanceof Outsourced){
            labelPartSource.setText("Company Name");
            Outsourced outsourced = (Outsourced) selectedPart;
            ModPartIDField.setText(Integer.toString(outsourced.getId()));
            ModPartNameField.setText(outsourced.getName());
            ModPartInventoryField.setText(Integer.toString(outsourced.getStock()));
            ModPartPriceField.setText(Double.toString(outsourced.getPrice()));
            ModPartMaxField.setText(Integer.toString(outsourced.getMax()));
            ModPartMinField.setText(Integer.toString(outsourced.getMin()));
            partSourceField.setText(outsourced.getCompanyName());
            OutsourcedRadBtn.setSelected(true);
        }
    }

    /**initialize method.
     @param url is required.
     @param resourceBundle is required*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
