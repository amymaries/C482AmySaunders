package controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import static model.Inventory.*;

/**author ASaunders
 This controller controls the Main Screen of the Inventory System.*/

public class MainScreenController implements Initializable {

  Stage stage;
  Parent scene;

  @FXML private TextField MSPartsSearchField;
  @FXML private TextField MSProdSearch;
  @FXML private TableView<Part> MSPartsTable;
  @FXML private TableColumn<Part, Integer> MSPartIDCol;
  @FXML private TableColumn<Part, String> MSPartNameCol;
  @FXML private TableColumn<Part, Integer> MSPartInventoryCol;
  @FXML private TableColumn<Part, Double> MSPartPriceCol;
  @FXML private TableView<Product> MSProdTable;
  @FXML private TableColumn<Product, Integer> MSProdIDCol;
  @FXML private TableColumn<Product, String> MSProdNameCol;
  @FXML private TableColumn<Product, Integer> MSProdInventoryCol;
  @FXML private TableColumn<Product, Double> MSProdPriceCol;
  @FXML private ObservableList<Part> allParts = FXCollections.observableArrayList();
  @FXML private ObservableList<Product> allProducts = FXCollections.observableArrayList();


  /**onActionAddPart method switches the scene to the Add Part form so user can add all requisite
   information to add a part to the ObservableList.
   @param event user clicks the 'Add' button on the Parts pane of the Main Screen.
   @throws IOException exception possible
   */

  @FXML
  void onActionAddPart(ActionEvent event) throws IOException {
    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
    scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
    stage.setTitle("Add Part");
    stage.setScene(new Scene(scene));
    stage.show();
  }

  /**onActionAddProduct method switches the scene to the Add Product form so user can add all requisite
   information to add a product to the ObservableList.
   @param event user clicks the 'Add' button on the Products pane of the Main Screen.
   @throws IOException possible exception
   */
  @FXML
  void onActionAddProduct(ActionEvent event) throws IOException {
    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
    scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
    stage.setTitle("Add Product");
    stage.setScene(new Scene(scene));
    stage.show();
  }

  /**This method will remove the selected part from the parts table and displays a
   warning message. The delete button only works if a part is selected.
   @param event user clicks 'Delete' button on the Parts pane on the Main Screen.
   */
  @FXML
  void onActionDeletePart(ActionEvent event) {
    Part selectPart = MSPartsTable.getSelectionModel().getSelectedItem();
    if(selectPart != null){
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
      Optional<ButtonType> result = alert.showAndWait();

      if(result.isPresent() && result.get() == ButtonType.OK){
        Inventory.deletePart(selectPart);
        MSPartsTable.setItems(getAllParts());
      }
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to be deleted.");
      alert.showAndWait();
    }

  }

  /**OnActionDelete method deletes the selected product from Products TableView and
   displays warning. Delete button only works if a part is selected.
   @param event user clicks 'Delete' on the Products pane of the Main Screen.
   */

  @FXML
  void onActionDeleteProduct(ActionEvent event) {
    Product selectedProduct = MSProdTable.getSelectionModel().getSelectedItem();
    if(selectedProduct != null){
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
      Optional<ButtonType> result = alert.showAndWait();
      if(result.isPresent() && result.get() == ButtonType.OK){
        Inventory.deleteProducts(selectedProduct);
        MSProdTable.setItems(Inventory.getAllProducts());
      }
    } else{
      Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to be deleted.");
      alert.showAndWait();
    }
  }

  /**OnActionExit method shuts down program.
   @param event user clicks 'Exit' on the Main Screen.
   */
  @FXML
  void onActionExit(ActionEvent event) {
    System.exit(0);
  }


  /***
   * OnActionModifyPart method switches the user to the Modify Parts screen and auto-populates the data
   * from the selectedPart into the correct textFields.
   * @param event user clicks 'Modify' on the Parts pane of the Main Screen.
   * @throws IOException possible IO exception.
   */
  @FXML
  void onActionModifyPart(ActionEvent event) throws IOException {
    if(MSPartsTable.getSelectionModel().getSelectedItem() != null){
      Stage stage;
      Parent root;
      stage = (Stage)((Button)event.getSource()).getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModPart.fxml"));
      root = loader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      ModPartController controller = loader.getController();
      Part selectedPart = MSPartsTable.getSelectionModel().getSelectedItem();
      controller.getSelectedPart(selectedPart);
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify.");
      alert.showAndWait();
    }
  }


  /***
   * OnActionModifyProduct method switches the user to the Modify Products screen and auto-populates the data
   * from the selectedProduct into the correct textFields.
   * @param event user clicks 'Modify' on the Products pane of the Main Screen.
   * @throws IOException possible exception.
   */
  @FXML
  void onActionModifyProduct(ActionEvent event) throws IOException {
    if(MSProdTable.getSelectionModel().getSelectedItem() != null){
      Stage stage;
      Parent root;
      stage = (Stage)((Button)event.getSource()).getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModProduct.fxml"));
      root = loader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
      ModProductController controller = loader.getController();
      Product selectedProduct = MSProdTable.getSelectionModel().getSelectedItem();
      controller.getSelectedProduct(selectedProduct);
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify.");
      alert.showAndWait();
    }
  }



  /** When user searches for parts by ID or name (including partial),
  the app displays matching results in Parts TableView. If parts aren't found,
  app displays error message in the UI or a dialog box. If search field is empty,
  TableView should repopulate with all available parts.
   @param event user clicks 'Search' button from the Parts Pane of the Main Screen.
   */
  @FXML
  public void OnActionSearchParts(ActionEvent event) {
    String searchText = MSPartsSearchField.getText();
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
    MSPartsTable.setItems(foundParts);
    MSPartsSearchField.setText("");
    if(foundParts.isEmpty()){
      Alert alert = new Alert(Alert.AlertType.ERROR, "Search term not found.");
      alert.showAndWait();
      MSPartsTable.setItems(Inventory.getAllParts());
    }
  }

  /** When user searches for parts by ID or name (including partial),
   the app displays matching results in Products TableView. If parts aren't found,
   app displays error message in the UI or a dialog box. If search field is empty,
   TableView should repopulate with all available parts.
   @param event user clicks 'Search' button from the Products Pane of the Main Screen.
   */
  @FXML
  public void OnActionSearchProducts(ActionEvent event) {
    String searchText = MSProdSearch.getText();
    ObservableList<Product> foundProducts = Inventory.lookupProduct(searchText);
    if(foundProducts.size() == 0){
      try {
        int productID = Integer.parseInt(searchText);
        Product p = Inventory.lookupProduct(productID);
        if(p != null){
          foundProducts.add(p);
        }
      } catch (NumberFormatException e){

      }
    }
    MSProdTable.setItems(foundProducts);
    MSProdSearch.setText("");
    if(foundProducts.isEmpty()){
      Alert alert = new Alert(Alert.AlertType.ERROR, "Search term not found.");
      alert.showAndWait();
      MSProdTable.setItems(Inventory.getAllProducts());
    }
  }

  /**The empty constructor.*/
  public MainScreenController(){
  }

  /**The initialize method initializes TableViews and populates them with the part and product objects.
   @param url This parameter is required.
   @param resourceBundle Also required.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //sets TableView to automatically update whenever the parts list changes.

    //Table and columns for "Parts"
    //MSPartsTable.refresh();
    MSPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    MSPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    MSPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    MSPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    MSPartsTable.setItems(Inventory.getAllParts());

    //Table and columns for "Products"
    //MSProdTable.refresh();
    MSProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    MSProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    MSProdInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    MSProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    MSProdTable.setItems(Inventory.getAllProducts());

  }
}
