package controllers;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import main.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import json.NearbyStopBean;
import json.PredictionBean;
import json.StopObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.CommonUtils;
import utils.JDBCUtil;
import utils.JsonPostUtil;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class PredictionUIController implements Initializable {

    @FXML
    private Pane PredictionPane;
    
    @FXML
    private JFXComboBox<Label> nearbystop;
    
    @FXML
    private JFXComboBox<Label> subwayline;
    
    @FXML
    private JFXComboBox<Label> specialstop;
    
    public int nearbyIndex;
    public int subwayIndex;
    public int specialIndex;
    
    public static PredictionBean predictionItem;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize nearby stop ComboBox
        nearbystop.getItems().add(new Label("Select Nearby Stop"));
        
        if (Application.nearbyStops.isEmpty()) {
            try {
                //Get location
                String lat = Application.location.get(0);
                String lng = Application.location.get(1);
                
                //Get nearby stop Object
                NearbyStopBean nearbyStopItem = JsonPostUtil.NearbyStopOKHttp(lat, lng);
                
                Application.nearbyStops.clear();
                
                for (int j = 0; j < nearbyStopItem.getIncluded().size(); j++) {
                    //Decode nearby stop object
                    String name = nearbyStopItem.getIncluded().get(j).getAttributes().getName();
                    String id = nearbyStopItem.getIncluded().get(j).getId();
                    
                    StopObject object = new StopObject(name, id);
                    Application.nearbyStops.add(object);
                    
                    nearbystop.getItems().add(new Label(name));
                }
            } catch (IOException ex) {
            }
        } else {
            //Achieve nearby stops info from local storage
            for (int i = 0; i < Application.nearbyStops.size(); i++) {
                nearbystop.getItems().add(new Label(Application.nearbyStops.get(i).getName()));
            }
        }
        
        //Initialize subway line ComboBox
        subwayline.getItems().add(new Label("Select Train Line"));
        for (int i = 0; i < 8; i++) {
            subwayline.getItems().add(new Label(Application.names[i]));
        }
    }    
    
    //After user choose a train line
    @FXML
    private void loadstops(ActionEvent event) {

        //Initialize special stop ComboBox
        specialstop.getItems().clear();
        specialstop.getItems().add(new Label("Select Special Stop"));
        
        subwayIndex = subwayline.getSelectionModel().getSelectedIndex();
        
        if (subwayIndex == 0) {
            nearbystop.setDisable(false);
        } else {
            nearbystop.setDisable(true);
            //Call JDBC Function to load stops after user chooses the train line
            JDBCUtil.connect(specialstop, subwayIndex - 1);
        }
    }
    
    //User can only choose one from nearbystop box and specialstop box
    //Choose nearbystop, specialstop will be disable, opposite, the same.
    @FXML
    private void loadnearbystop(ActionEvent event) {
        nearbyIndex = nearbystop.getSelectionModel().getSelectedIndex();

        if (nearbyIndex == 0) {
            subwayline.setDisable(false);
            specialstop.setDisable(false);
        } else {
            subwayline.setDisable(true);
            specialstop.setDisable(true);
        }
    }

    //When user click search button
    @FXML
    private void search(ActionEvent event) throws IOException {
        nearbyIndex = nearbystop.getSelectionModel().getSelectedIndex();
        subwayIndex = subwayline.getSelectionModel().getSelectedIndex();
        specialIndex = specialstop.getSelectionModel().getSelectedIndex();
        
        //Check whether user's choice is reasonable
        if (nearbyIndex > 0 || (subwayIndex > 0 && specialIndex > 0)) {
            
            //Get stop's id
            String id;
            if (nearbyIndex > 0) {
                id = Application.nearbyStops.get(nearbyIndex - 1).getId();
            } else {
                id = Application.specialStops.get(specialIndex - 1).getId();
            }
            
            //Send OKHttp request to MBTA API for train's real time prediction
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Application.prediction_start + id).build();
            okhttp3.Response response = client.newCall(request).execute();
            
            //Decode json data returned back
            Gson gson = new Gson();
            predictionItem = gson.fromJson(response.body().string().trim(), PredictionBean.class);
            
            //Jump to next detailed page
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/PredictionDetailUI.fxml"));
            PredictionPane.getChildren().removeAll();
            PredictionPane.getChildren().setAll(fxml);
        } else {
            String title = "Tips";
            String text = "Please choose a nearby stop or choose subway to find a special stop!";
            CommonUtils.Dialog(PredictionPane, title, text);
        }
    }
}