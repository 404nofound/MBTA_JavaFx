package controllers;

import com.jfoenix.controls.JFXListView;
import main.Application;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import json.NearbyStopBean;
import json.StopObject;
import utils.JsonPostUtil;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class NearbyStopsUIController implements Initializable {

    @FXML
    private JFXListView<HBox> nearbylistview;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Achieve location variable
            String lat = Application.location.get(0);
            String lng = Application.location.get(1);
            
            //Send a http request to MBTA API for getting nearby stops info
            NearbyStopBean nearbyStopItem = JsonPostUtil.NearbyStopOKHttp(lat, lng);
            
            Application.nearbyStops.clear();
            
            for (int j = 0; j < nearbyStopItem.getIncluded().size(); j++) {
                //Get Stops' name and ID
                String name = nearbyStopItem.getIncluded().get(j).getAttributes().getName();
                String id = nearbyStopItem.getIncluded().get(j).getId();
                
                //Store those nearby stops for future using
                StopObject object = new StopObject(name, id);
                Application.nearbyStops.add(object);
                
                HBox hBox = new HBox();
                
                Label label = new Label(name);
                ImageView image = new ImageView("/icons/small_stop128.png");
                image.setFitWidth(48);
                image.setFitHeight(48);
                label.setGraphic(image);
                label.setGraphicTextGap(50);
                label.getStyleClass().add("speciallabel");
                hBox.getChildren().add(label);
                
                nearbylistview.getItems().add(hBox);
            }
        } catch (IOException | JsonSyntaxException ex) {
        }
        nearbylistview.depthProperty().set(10);
        nearbylistview.setExpanded(true);
        //nearbylistview.setMouseTransparent(true);
        nearbylistview.setFocusTraversable(false);
    }    
}
