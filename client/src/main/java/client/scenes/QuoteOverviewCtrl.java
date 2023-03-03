package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Quote;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class QuoteOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Quote> data;

    @FXML
    private TableView<Quote> table;
    @FXML
    private TableColumn<Quote, String> colFirstName;
    @FXML
    private TableColumn<Quote, String> colLastName;
    @FXML
    private TableColumn<Quote, String> colQuote;

    @Inject
    public QuoteOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFirstName.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().person.firstName));
        colLastName.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().person.lastName));
        colQuote.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().quote));
    }

    public void addQuote() {
        mainCtrl.showAdd();
    }

    /**
     * Add proper javadoc later.
     */
    public void refresh() {
        var quotes = server.getQuotes();
        data = FXCollections.observableList(quotes);
        table.setItems(data);
    }
}