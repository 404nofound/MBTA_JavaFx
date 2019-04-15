package controllers;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import json.ScheduleBean;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class ScheduleDetailUIController implements Initializable {

    @FXML
    private Pane Pane;
    @FXML
    private JFXListView<HBox> listview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Achieve Schedule data from parent controller
        ScheduleBean scheduleItem = ScheduleUIController.ScheduleItem;
        
        for (int i = 0; i < scheduleItem.getData().size(); i++) {
            //Decode the Schedule data
            String route_id = scheduleItem.getData().get(i).getRelationships().getRoute().getData().getId();
            String arr_time_node = scheduleItem.getData().get(i).getAttributes().getArrival_time();
            String dep_time_node = scheduleItem.getData().get(i).getAttributes().getDeparture_time();
            String image_name = "small_stop128.png";
            String arr_time = "";
            String dep_time = "";
            
            //If the time data isn't detail, ignore this piece data
            if (arr_time_node != null && dep_time_node != null) {
                arr_time = arr_time_node.substring(0, 19).replace("T", " ");
                dep_time = dep_time_node.substring(0, 19).replace("T", " ");
            } else {
                continue;
            }
            
            //Judge the train name and set icons path for this train
            if (route_id.startsWith("B")) {
                image_name = "s_blue128.png";
            } else if (route_id.startsWith("O")) {
                image_name = "s_orange128.png";
            } else if (route_id.startsWith("R")) {
                image_name = "s_red128.png";
            } else if (route_id.startsWith("M")) {
                image_name = "s_mattapan128.png";
            } else if (route_id.startsWith("G")) {
                image_name = "s_green128.png";
            }
            
            HBox childhbox = new HBox();
            
            //Train Name Label
            Label routeLabel = new Label(route_id);
            routeLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            ImageView image = new ImageView("/icons/" + image_name);
            image.setFitWidth(64);
            image.setFitHeight(64);
            routeLabel.setGraphic(image);
            routeLabel.setGraphicTextGap(40);
            
            //Train Arriving Time Label
            Label arrLabel = new Label("Arrive: " + arr_time);
            arrLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            arrLabel.setGraphicTextGap(40);
            
            //Train Departing Time Label
            Label depLabel = new Label("Depart: " + dep_time);
            depLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            depLabel.setGraphicTextGap(40);
            
            childhbox.setAlignment(Pos.CENTER);
            childhbox.setSpacing(40);
            childhbox.getChildren().addAll(routeLabel, arrLabel);
            
            HBox hbox = new HBox();
            
            Region filler = new Region();
            hbox.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(filler, Priority.ALWAYS);
            
            hbox.getChildren().addAll(childhbox, filler, depLabel);
            
            listview.getItems().add(hbox);
        }
        listview.setFocusTraversable(false);
    }    
}
