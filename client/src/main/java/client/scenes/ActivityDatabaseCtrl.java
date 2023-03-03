package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.Activity;
import commons.User;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ActivityDatabaseCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private User user;

    private ObservableList<Activity> data;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Activity, String> Title;

    @FXML
    private TableColumn<Activity, Integer> Consumption_Wh;

    @FXML
    private TableColumn<Activity, String> Source;

    @FXML
    private TableColumn<Activity, String> Image;

    @FXML
    private Label instructions;

    @Inject
    public ActivityDatabaseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Title.setCellValueFactory(a -> new SimpleStringProperty(
                a.getValue().title));
        Consumption_Wh.setCellValueFactory(a -> new SimpleIntegerProperty(
                a.getValue().consumption_in_wh).asObject());
        Source.setCellValueFactory(a -> new SimpleStringProperty(
                a.getValue().source));
        Image.setCellValueFactory(a -> new SimpleStringProperty(
                a.getValue().image_path));
        List<Activity> activities = server.getAllActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
        instructions.setText("Click on \"Import all\" and select "
                + "a folder containing .json file and images.");
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public void back() {
        mainCtrl.showMenu(user);
    }

    /**
     * refresh button for table.
     */
    public void refresh() {
        List<Activity> activities = server.getAllActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
    }

    /**
     * Imports all activities from a file and adds them to server database.
     * Client has to choose folder containing activities and corresponding immages.
     */
    public void importFromFile() {
        File actFolder = mainCtrl.selectActivityFolder();
        String folderPath = actFolder.getAbsolutePath();
        String filePath = folderPath + "\\activities.json";
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Activity>> typeReference = new TypeReference<List<Activity>>() {};
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Activity> activities =
                null;
        try {
            activities = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addAllActivities(activities);
        instructions.setText("Activities have been imported. Now click"
                + " refresh to display them in the table.");
    }
}
