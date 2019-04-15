package controllers;

import com.jfoenix.controls.JFXListView;
import main.Application;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import json.PredictionBean;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class PredictionDetailUIController implements Initializable {

    @FXML
    private Pane Pane;
    @FXML
    private JFXListView<HBox> listview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Get prediction object from parent controller
        PredictionBean predictionItem = PredictionUIController.predictionItem;
        
        for (int i = 0; i < predictionItem.getData().size(); i++) {
            
            //Train line name
            String route_id = predictionItem.getData().get(i).getRelationships().getRoute().getData().getId();
            //Train line direction
            int direction = predictionItem.getData().get(i).getAttributes().getDirection_id();
            
            //Define variables
            String image_name = "small_stop64.png";
            String labelText = "";
            String directionText = "";
            String[] dir;
            
            //Direction Judgement
            if (direction == 1) {
                dir = Application.directions;
            } else {
                dir = Application.oppose_directions;
            }
            
            //Name and image/icon Judgement
            if (route_id.startsWith("B")) {
                image_name = "s_blue128.png";
                directionText = dir[7];
            } else if (route_id.startsWith("O")) {
                image_name = "s_orange128.png";
                directionText = dir[2];
            } else if (route_id.startsWith("R")) {
                image_name = "s_red128.png";
                directionText = dir[0];
            } else if (route_id.startsWith("M")) {
                image_name = "s_mattapan128.png";
                directionText = dir[1];
            } else if (route_id.startsWith("G")) {
                image_name = "s_green128.png";
                if (route_id.endsWith("B")) {
                    directionText = dir[3];
                } else if (route_id.endsWith("C")) {
                    directionText = dir[4];
                } else if (route_id.endsWith("D")) {
                    directionText = dir[5];
                } else if (route_id.endsWith("E")) {
                    directionText = dir[6];
                }
            }
            
            //Arriving time, departing time, how many stops away
            String arrTimeNode = predictionItem.getData().get(i).getAttributes().getArrival_time();
            String depTimeNode = predictionItem.getData().get(i).getAttributes().getDeparture_time();
            String status = predictionItem.getData().get(i).getAttributes().getStatus();
            
            //Set time format
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            String nowTime = df.format(new Date());
            String preTime = "";
            String arrTime = "";
            String depTime = "";
            long timeDifference = 0;
            
            //Processing time format and change then into human-readable format
            if ((arrTimeNode == null) && (depTimeNode == null)) {
                if (status == null) {
                    continue;
                } else {
                    labelText = status;
                }
            } else if (!(arrTimeNode == null) && (depTimeNode == null)) {
                arrTime = arrTimeNode.substring(0, 19).replace("T", " ");
                preTime = arrTime;
            } else if ((arrTimeNode == null) && !(depTimeNode == null)) {
                depTime = depTimeNode.substring(0, 19).replace("T", " ");
                preTime = depTime;
            } else if (!(arrTimeNode == null) && !(depTimeNode == null)) {
                arrTime = arrTimeNode.substring(0, 19).replace("T", " ");
                preTime = arrTime;
            }
            
            //Calculate how many minutes the train will arriving
            if (!(arrTimeNode == null) || !(depTimeNode == null)) {
                try {
                    Date nowDate = df.parse(nowTime);
                    Date preDate = df.parse(preTime);

                    //System.out.println(preDate);
                    timeDifference = (preDate.getTime() - nowDate.getTime())/60000;

                    labelText = timeDifference + " Min";
                } catch (ParseException ex) {
                }
            }

            //Build UI Pane
            HBox hBox = new HBox();
            HBox childhBox = new HBox();
            
            //Train Name Label
            Label lineLabel = new Label(predictionItem.getData().get(i).getRelationships().getRoute().getData().getId());
            lineLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            ImageView image = new ImageView("/icons/" + image_name);
            image.setFitWidth(64);
            image.setFitHeight(64);
            lineLabel.setGraphic(image);
            lineLabel.setGraphicTextGap(40);

            //Time Label
            Label timeLabel = new Label(labelText);
            timeLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            timeLabel.setAlignment(Pos.CENTER);

            //Direction Label
            Label directionLabel = new Label(directionText);
            directionLabel.setFont(Font.font("Roboto Cn", FontWeight.BOLD, 18));
            directionLabel.setAlignment(Pos.CENTER);

            childhBox.setAlignment(Pos.CENTER);
            childhBox.setSpacing(50);
            childhBox.getChildren().addAll(lineLabel, timeLabel);
            
            Region filler = new Region();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(filler, Priority.ALWAYS);
            
            hBox.getChildren().addAll(childhBox, filler, directionLabel);
            listview.getItems().add(hBox);
        }
        listview.setFocusTraversable(false);
    }
}
