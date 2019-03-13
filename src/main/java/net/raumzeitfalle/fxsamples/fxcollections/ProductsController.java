package net.raumzeitfalle.fxsamples.fxcollections;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProductsController implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button quitButton;

    @FXML
    private Button sortAscending;

    @FXML
    private Button sortDescending;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    // This list will hold all available data.
    private final ObservableList<String> products;

    // This one applies a predicate to filter sorted data.
    private final FilteredList<String> filtered;

    // This list will provide a sorted view on the data in products list.
    private final SortedList<String> sorted;

    // The sorted list will use this comparator by default
    private final Comparator<? super String> comparator;

    public ProductsController() {
        products = FXCollections.observableArrayList();
        filtered = new FilteredList<>(products);

        comparator = Comparator.naturalOrder(); // for String its the same like: (a,b)->a.compareTo(b)
        sorted = new SortedList<>(filtered, comparator);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // populate the initial observable list
        products.add("Air Pressure Sensor");
        products.add("Soap");
        products.add("Paint Wax");
        products.add("Rear View Mirror (middle)");
        products.add("Z28 exhaust");
        products.add("Oil");
        products.add("Back seats");
        products.add("Front seats");
        products.add("Rear View Mirror (right)");
        products.add("Oil Catch Can");
        products.add("Rear View Mirror (left)");
        products.add("Cylinder Head");
        products.add("Cylinder Head Gasket");
        products.add("Brake Caliper (front right)");
        products.add("Turbo Charger");
        products.add("Super Charger");
        products.add("Stroker Kit");
        products.add("Oil Cooler");
        products.add("Brake Caliper (front left)");

        // assign only the filtered list to the list view
        listView.setItems(sorted);

        // visibility control for the list view
        BooleanBinding sortingButtonsAreFocused = Bindings.or(sortAscending.focusedProperty(), sortDescending.focusedProperty());
        BooleanBinding listViewVisibility = Bindings.or(textField.focusedProperty(), sortingButtonsAreFocused)
                .or(addButton.focusedProperty())
                .or(listView.focusedProperty())
                .or(clearButton.focusedProperty());

        listView.visibleProperty().bind(listViewVisibility);

        // selection of comparator for sorting
        sortDescending.setOnAction(actionEvent-> sorted.setComparator(comparator.reversed()));
        sortAscending.setOnAction(actionEvent-> sorted.setComparator(comparator));

        // filter mechanism
        textField.textProperty().addListener((o,a,b)->{
            if (null != b && !b.equalsIgnoreCase(a)) {
                Predicate<String> filterCriterion = value->value.toLowerCase()
                        .contains(textField.textProperty().getValueSafe().toLowerCase());
                filtered.setPredicate(filterCriterion);
            }
        });

        // update text field on selection in list view
        listView.getSelectionModel().selectedItemProperty().addListener((o,a,b)->{
            if (null != b && !b.equalsIgnoreCase(a) && !"".equalsIgnoreCase(b)) {
                Platform.runLater(()->textField.setText(b));
            }
        });

        // add new item to list from text field
        addButton.setOnAction(actionEvent -> {
            String value = textField.getText();
            if (null != value && !"".equals(value) && sorted.isEmpty()){
                products.add(value);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Value not unique");
                alert.setContentText("Only unique new values can be added.");
                Platform.runLater(alert::showAndWait);
            }
        });

        // add new item to list from text field
        clearButton.setOnAction(actionEvent -> {
            Platform.runLater(()->textField.setText(""));
        });


        quitButton.setOnAction(actionEvent -> Platform.exit());
    }

}
