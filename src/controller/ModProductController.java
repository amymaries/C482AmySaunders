package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**author ASaunders
 This controller controls the Modify Product form*/

public class ModProductController implements Initializable {

    Stage stage;
    Parent scene;
    Product selectedProduct;


    @FXML private TextField ModProdIDField;

    @FXML private TextField ModProdNameField;

    @FXML private TextField ModProdInventoryField;

    @FXML private TextField ModProdPriceField;

    @FXML private TextField ModProdMaxField;

    @FXML private TextField ModProdMinField;

    @FXML private TextField ModProdSearchField;

    @FXML private Button ModProdSaveBtn;

    @FXML private TableView<Part> ModProdTable1;

    @FXML private TableColumn<Part, Integer> ModProdT1PartIDCol;

    @FXML private TableColumn<Part, String> ModProdT1PartNameCol;

    @FXML private TableColumn<Part, Integer> ModProdT1InventoryCol;

    @FXML private TableColumn<Part, Double> ModProdT1PriceCol;

    @FXML private TableView<Part> ModProdTable2;

    @FXML private TableColumn<Part, Integer> ModProdT2PartIDCol;

    @FXML private TableColumn<Part, String> ModProdT2PartNameCol;

    @FXML private TableColumn<Part, Integer> ModProdT2InventoryCol;

    @FXML private TableColumn<Part, Double> ModProdT2PriceCol;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**OnActionAdd method adds the part selected in Table1 to Table2.
     @param event user clicks the 'Add' button.*/
    @FXML void OnActionAdd(ActionEvent event) {
        //if part is selected, add it to the second table.
        Part selectedPart = ModProdTable1.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            associatedParts.add(selectedPart);
            ModProdTable2.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to add to the product.");
            alert.showAndWait();
        }
    }

    /**OnActionCancel method closes the scene, returning user to the Main Screen.
     @param event user clicks the cancel button.
     @throws IOException from the FXMLLoader*/
    @FXML void OnActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This action will clear all fields without saving. Would you like to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()== ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**OnActionRemovePart method disassociates the selected part from the product associatedPart list.
     @param event user clicks the 'Remove Associated Part' button.*/
    @FXML void OnActionRemovePart(ActionEvent event) {
        Part selectedPart = ModProdTable2.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part from the product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                ModProdTable2.setItems(associatedParts);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to delete from the product.");
            alert.showAndWait();
        }
    }


    /**OnActionSave method replaces the existing product with the modified product, then returns user to MainScreen.
     @param event when user clicks 'Save'.
     @throws IOException from the FXMLLoader*/
    @FXML void OnActionSave(ActionEvent event) throws IOException {
        int prodId = Integer.parseInt(ModProdIDField.getText());
        String name = ModProdNameField.getText();
        int stock = Integer.parseInt(ModProdInventoryField.getText()); //wrapper to convert string to int
        double price = Double.parseDouble(ModProdPriceField.getText());
        int max = Integer.parseInt(ModProdMaxField.getText());
        int min = Integer.parseInt(ModProdMinField.getText());

        try {
            if(max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max number of parts should be larger than min number of parts.");
                alert.showAndWait();
            } else if ((stock < min) || (stock > max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory levels should fall between max and min.");
                alert.showAndWait();
            } else if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Name field cannot be empty.");
                alert.showAndWait();
            } else {
                Product modifiedProduct = new Product(prodId, name, price, stock, min, max);
                for (Part part : associatedParts) {
                    modifiedProduct.addAssociatedPart(part);
                }
                Inventory.addProduct(modifiedProduct);
                Inventory.allProducts.remove(selectedProduct);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Check your information.");
            alert.showAndWait();
        }
    }

    /**validateTextFields methods ensures that text fields are not empty*/
    /*private boolean validateTextFields(){
            //ensure that textFields are not empty.
            List<TextField> textFields = Arrays.asList(ModProdNameField, ModProdInventoryField, ModProdPriceField, ModProdMaxField, ModProdMinField);
            boolean isEmpty = false;
            for (TextField textField : textFields) {
                if (textField.getText().length() == 0)
                    isEmpty = true;
                else
                    isEmpty = false;
            }
            return isEmpty;
    }*/

    /**OnActionSearch method searches the part list by name (including partial) and Id.
     @param event user clicks 'Search' button.*/
    @FXML void OnActionSearch(ActionEvent event) {
        String searchText = ModProdSearchField.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(searchText);
        if(foundParts.size() == 0){
            try {
                int partID = Integer.parseInt(searchText);
                Part p = Inventory.lookupPart(partID);
                if(p != null){
                    foundParts.add(p);
                }
            } catch (NumberFormatException e){

            }
        }
        ModProdTable1.setItems(foundParts);
        ModProdSearchField.setText("");
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Search term not found.");
            alert.showAndWait();
            ModProdTable1.setItems(Inventory.getAllParts());
        }
    }

    /**getSelectedProduct method sets the Product textFields on the Modify Product screen once the user selects a product and decides to modify it.
     @param selectedProduct the product highlighted in the MainScreen Products table.*/
    public void getSelectedProduct(Product selectedProduct){
        this.selectedProduct = selectedProduct;

        ModProdIDField.setText(Integer.toString(this.selectedProduct.getId()));
        ModProdNameField.setText(this.selectedProduct.getName());
        ModProdInventoryField.setText(Integer.toString(this.selectedProduct.getStock()));
        ModProdPriceField.setText(Double.toString(this.selectedProduct.getPrice()));
        ModProdMaxField.setText(Integer.toString(this.selectedProduct.getMax()));
        ModProdMinField.setText(Integer.toString(this.selectedProduct.getMin()));
        //ModProdTable1.setItems(Inventory.allParts);
        ModProdTable2.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**The initialize method initializes TableViews and populates them with the part objects.
     @param url required
     @param resourceBundle required*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Table and column values for Table 1.
        ModProdT1PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdT1PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdT1InventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdT1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProdTable1.setItems(Inventory.getAllParts());

        //Table and column values for Table 2, the associated parts.
        ModProdT2PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdT2PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdT2InventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdT2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProdTable2.setItems(associatedParts);


    }
}
