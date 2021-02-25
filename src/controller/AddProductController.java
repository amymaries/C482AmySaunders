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
import model.Inventory;
import model.Part;
import model.Product;
import model.InHouse;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getProductIdCount;

/**author ASaunders
 This controller controls the Add Product form*/
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    private Product product;

    @FXML private TextField AddProdIDField;
    @FXML private TextField AddProdNameField;
    @FXML private TextField AddProdInventoryField;
    @FXML private TextField AddProdPriceField;
    @FXML private TextField AddProdMaxField;
    @FXML private TextField AddProdMinField;
    @FXML private TextField AddProdSearchField;
    @FXML private TableView<Part> AddProdTable1;
    @FXML private TableColumn<Part, Integer> AddProdT1IDCol;
    @FXML private TableColumn<Part, String> AddProdT1NameCol;
    @FXML private TableColumn<Part, Integer> AddProdT1InventoryCol;
    @FXML private TableColumn<Part, Double> AddProdT1PriceCol;
    @FXML private TableView<Part> AddProdTable2;
    @FXML private TableColumn<Product, Integer> AddProdT2IDCol;
    @FXML private TableColumn<Product, String> AddProdT2NameCol;
    @FXML private TableColumn<Product, Integer> AddProdT2InventoryCol;
    @FXML private TableColumn<Product, Double> AddProdT2PriceCol;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    /***
     * OnActionCancel method closes the scene, returning user to the Main Screen.
     * @param event user clicks the cancel button.
     * @throws IOException possible exception.
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
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

    /**OnActionAdd method adds the part selected in Table1 to Table2.
     @param event user clicks the 'Add' button.*/
    @FXML
    void OnActionAddPart(ActionEvent event) {
        //warning dialog box if something is not selected?
        //if part is selected, add it to the box below.
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();
        Part selectedPart = AddProdTable1.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            associatedParts.add(selectedPart);
            AddProdTable2.setItems(associatedParts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to delete from the product.");
            alert.showAndWait();
        }
    }



    /**
     * OnActionRemovePart method disassociates the selected part from the product associatedPart list.
     * @param event user clicks the 'Remove Associated Part' button.
     * @throws IOException possible IO exception
     */
    @FXML
    void OnActionRemovePart(ActionEvent event) throws IOException {
        Part selectedPart = AddProdTable2.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part from the product?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                //why is it removing all associated parts and not just the selectedPart?
                //product.deleteAssociatedPart(selectedPart);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to delete from the product.");
            alert.showAndWait();
        }
    }

    /***
     * OnActionSave method takes the user-entered information and casts it to the specified data types
     * to create a product object that is then added to the Inventory.
     * @param event when user clicks 'save'.
     * @throws IOException possible exception.
     */
    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException {
       //int prodId = Integer.parseInt(AddProdIDField.getText());
       String name = AddProdNameField.getText();
       int stock = 0;
       double price = 0;
       int max = 0;
       int min = 0;

       try {
           stock = Integer.parseInt(AddProdInventoryField.getText());
       } catch (NumberFormatException e){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be a number.");
           alert.showAndWait();
       } catch (NullPointerException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a value in the inventory field.");
           alert.showAndWait();
       }
       try {
           price = Double.parseDouble(AddProdPriceField.getText());

       } catch (NumberFormatException e){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter price in format X.XX.");
           alert.showAndWait();
       } catch (NullPointerException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a value in the price field.");
           alert.showAndWait();
       }
       try {
           max = Integer.parseInt(AddProdMaxField.getText());
       } catch (NumberFormatException e){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum number of inventory must be an integer.");
           alert.showAndWait();
       } catch (NullPointerException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a value in the max field.");
           alert.showAndWait();
       }
        try {
            min = Integer.parseInt(AddProdMinField.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum number of inventory must be an integer.");
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a value in the max field.");
            alert.showAndWait();
        }

        Product newProduct = new Product(getProductIdCount(), name, price, stock, min, max);
        for(Part part : associatedParts){
            newProduct.addAssociatedPart(part);
            Inventory.addProduct(newProduct);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /**OnActionSearch method searches the part list by name (including partial) and Id.
     * @param event user clicks 'Search' button.
     */
    @FXML
    void OnActionSearch(ActionEvent event) {
        String searchText = AddProdSearchField.getText();
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
        AddProdTable1.setItems(foundParts);
        AddProdSearchField.setText("");
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Search term not found.");
            Optional<ButtonType> result = alert.showAndWait();
            AddProdTable1.setItems(Inventory.getAllParts());
        }

    }

    /**The initialize method initializes TableViews and populates them with the part objects.
     @param url required
     @param resourceBundle required*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Table and column values for Table 1.
        AddProdT1IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdT1NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdT1InventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdT1PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProdTable1.setItems(Inventory.allParts);

        //Table and column values for Table 2, the associated parts.
        AddProdT2IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdT2NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdT2InventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdT2PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //AddProdTable2.setItems(Product.associatedParts);

    }
}
