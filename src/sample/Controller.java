package sample;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
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

public class Controller implements Initializable {

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

    // This list will hold all available data.
    private final ObservableList<String> productsList = FXCollections.observableArrayList();

    // This list will provide a sorted view on the data in products list.
    private final SortedList<String> productsButSorted = new SortedList<>(productsList);

    // This one applies a predicate to filter sorted data.
    private final FilteredList<String> productsFiltered = new FilteredList<>(productsButSorted);

    private final Comparator<String> ascending = (a,b)->a.compareTo(b);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // populate the initial observable list
        productsList.add("Air Pressure Sensor");
        productsList.add("Soap");
        productsList.add("Paint Wax");
        productsList.add("Rear View Mirror (middle)");
        productsList.add("Z28 exhaust");
        productsList.add("Oil");
        productsList.add("Back seats");
        productsList.add("Front seats");
        productsList.add("Rear View Mirror (right)");
        productsList.add("Rear View Mirror (left)");
        productsList.add("Cylinder Head");
        productsList.add("Cylinder Head Gasket");

        // assign only the filtered list to the list view
        listView.setItems(productsFiltered);

        // visibility control for the list view
        BooleanBinding sortingButtonsAreFocused = Bindings.or(sortAscending.focusedProperty(), sortDescending.focusedProperty());
        BooleanBinding listViewVisibility = Bindings.or(textField.focusedProperty(), sortingButtonsAreFocused);
        listView.visibleProperty().bind(listViewVisibility);

        // selection of comparator for sorting
        sortDescending.setOnAction(actionEvent->productsButSorted.setComparator(ascending.reversed()));
        sortAscending.setOnAction(actionEvent->productsButSorted.setComparator(ascending));

        // filter mechanism
        textField.textProperty().addListener((o,a,b)->{
            if (null != b && b != a) {
                Predicate<String> filterCriterion = value->value.toLowerCase()
                        .contains(textField.textProperty().getValueSafe().toLowerCase());
                productsFiltered.setPredicate(filterCriterion);
            }
        });

        // update text field on selection in list view
        listView.getSelectionModel().selectedItemProperty().addListener((o,a,b)->{
            if (null != b && b != a && !"".equalsIgnoreCase(b)) {
                Platform.runLater(()->textField.setText(b));
            }
        });

        quitButton.setOnAction(actionEvent -> Platform.exit());
    }

}
