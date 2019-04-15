package controllers;

import main.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import json.LocationBean;

/**
 *
 * @author Eddy
 */
public class MainUIController implements Initializable {

    @FXML
    private HBox parent;
    
    @FXML
    private Pane content;
    
    @FXML
    private Label latlog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Call okhttp function to get lat/lng from Google API
            LocationBean item = utils.MacLatLogUtil.LatLog();
            
            //Assign value to variables
            String lat = item.getLocation().getLat() + "";
            String lng = item.getLocation().getLng() + "";
            
            latlog.setText(lat + "," + lng);
            
            //Store location into List
            Application.location.clear();
            Application.location.add(lat);
            Application.location.add(lng);
        } catch (Exception ex) {
        }
    }    
    
    //Click My Location Button
    @FXML
    private void open_location(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/MainUI.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(fxml);
    }
    
    //Click Nearby Stop Button
    @FXML
    private void open_nearby(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/NearbyStopsUI.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }

    //Click Train Stops Button
    @FXML
    private void open_stop(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/SubwayLineUI.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }
    
    //Click Prediction Button
    @FXML
    private void open_prediction(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/PredictionUI.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }
    
    //Click Schedule Button
    @FXML
    private void open_schedule(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/ScheduleUI.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }
    
    //Click Alerts Button
    @FXML
    private void open_alert(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/AlertUI.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }
    
    //Click Close Button
    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }
    
    //Click Minimize Button
    @FXML
    private void minimize_app(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void github(MouseEvent event) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.net.URI uri = java.net.URI.create("https://github.com/404nofound");
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    dp.browse(uri);
                }
            } catch (java.lang.NullPointerException | java.io.IOException e) {
            }
        }
    }
}
