package controllers;

import main.Application;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import json.ScheduleBean;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.CommonUtils;
import utils.JDBCUtil;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class ScheduleUIController implements Initializable {

    @FXML
    private Pane schedulePane;
    
    @FXML
    private VBox vbox;
    
    public JFXDatePicker datePickerFX = new JFXDatePicker();
    public JFXTimePicker startTimePicker = new JFXTimePicker();
    public JFXTimePicker endTimePicker = new JFXTimePicker();
    
    public JFXComboBox<Label> lineBox = new JFXComboBox<>();
    public JFXComboBox<Label> stopBox = new JFXComboBox<>();
    
    public static ScheduleBean ScheduleItem;
    
    @FXML
    private JFXRadioButton opposite;
    
    @FXML
    private JFXRadioButton forward;
    
    public final ToggleGroup group = new ToggleGroup();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        HBox hBox = new HBox();
        
        //Date Picker Box
        datePickerFX.setPromptText("Date");
        datePickerFX.getStyleClass().add("combo-box-popup");
        datePickerFX.setPrefSize(124, 50);
        
        //Start Time Picker Box
        startTimePicker.setDefaultColor(Color.valueOf("#3f51b5"));
        startTimePicker.setPromptText("Start Time");
        startTimePicker.getStyleClass().add("combo-box-popup");
        startTimePicker.setPrefSize(124, 50);
        
        //End Time Picker Box
        endTimePicker.setDefaultColor(Color.valueOf("#3f51b5"));
        endTimePicker.setPromptText("End Time");
        endTimePicker.getStyleClass().add("combo-box-popup");
        endTimePicker.setPrefSize(124, 50);
        
        hBox.setSpacing(34);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(datePickerFX, startTimePicker, endTimePicker);
        
        //Train Line Picker Box
        lineBox.getStyleClass().add("combo-box-popup");
        lineBox.setPromptText("Select Subway Line");
        lineBox.setPrefSize(440, 50);
        
        //Stop Picker Box
        stopBox.getStyleClass().add("combo-box-popup");
        stopBox.setPromptText("Select Special Stop");
        stopBox.setPrefSize(440, 50);
        
        //Initialize train line box
        for (int i = 0; i < 8; i++) {
            lineBox.getItems().add(new Label(Application.names[i]));
        }
        
        //direction radio buttons, setting toggle group
        forward.setToggleGroup(group);
        opposite.setToggleGroup(group);
        
        //Set Click Action Event for date picker and train line picker box
        datePickerFX.setOnAction(date_event);
        lineBox.setOnAction(stop_event);
        
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().add(0, hBox);
        vbox.getChildren().add(1, lineBox);
        vbox.getChildren().add(2, stopBox);
    }    
    
    //Check and make sure user choose a date no moew than a week from today
    public EventHandler date_event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String nowTime = df.format(new Date());
                Date dateNow = df.parse(nowTime);

                if (datePickerFX.getValue() != null) {
                    String date = datePickerFX.getValue().toString();
                    Date datePick = df.parse(date);
                    
                    //Calculate the day time interval
                    double day = ((datePick.getTime() - dateNow.getTime()) / (24 * 60 * 60 * 1000));
                    
                    //Make warning if the date is unreasonable
                    if (day > 7) {
                        String title = "Tips";
                        String text = "You can only choose date among 7 days!";
                        CommonUtils.Dialog(schedulePane, title, text);
                        datePickerFX.setValue(null);
                    } else if (day < 0) {
                        String title = "Tips";
                        String text = "You cannot choose date before today!";
                        CommonUtils.Dialog(schedulePane, title, text);
                        datePickerFX.setValue(null);
                    }
                }
            } catch (ParseException ex) {
            }
        }
    };
    
    //When user choose a train line, load all stops of this train
    public EventHandler stop_event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            
            //The train index user choose
            int subwayIndex = lineBox.getSelectionModel().getSelectedIndex();
            
            //Assign direction text for two radio buttons
            forward.setText(Application.directions[subwayIndex]);
            opposite.setText(Application.oppose_directions[subwayIndex]);
            
            stopBox.getItems().clear();
            
            if (subwayIndex >= 0) {
                //Call JDBC function to load stop data from server database
                JDBCUtil.connect(stopBox, subwayIndex);
            }
        }
    };

    //When user click the Search button
    @FXML
    private void search(ActionEvent event) throws IOException, ParseException {
        int stopIndex = stopBox.getSelectionModel().getSelectedIndex();

        //Check whether user choose all the Combo Box
        if (group.getSelectedToggle() != null && stopIndex > 0 && datePickerFX.getValue() != null && startTimePicker.getValue() != null) {
            int direction = 0;
            
            //judge direction of train line
            if (opposite.isSelected()) {
                direction = 0;
            } else if (forward.isSelected()) {
                direction = 1;
            }
            
            //Get comboBoxs' values
            String date = datePickerFX.getValue().toString();
            String start_time = startTimePicker.getValue().toString();
            String end_time = endTimePicker.getValue().toString();

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date start_date = df.parse(start_time);
            Date end_date = df.parse(end_time);

            double check_time = end_date.getTime() - start_date.getTime();

            //If end time earlier than start time, warning dialog
            if (check_time < 0) {
                String title = "Tips";
                String text = "End time must be later than start time!";
                CommonUtils.Dialog(schedulePane, title, text);
                System.out.println(check_time/(1000*60));
                startTimePicker.setValue(null);
                endTimePicker.setValue(null);
            } else {
                String id = Application.specialStops.get(stopIndex).getId();
                
                String url = Application.schedule_start + date + Application.schedule_min_time
                        + start_time + Application.schedule_max_time + end_time
                        + Application.schedule_stop + id + Application.schedule_direction + direction;
                
                //System.out.println(url);
                
                //Send OKHttp request to achieve schedule data from API
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                okhttp3.Response response = client.newCall(request).execute();

                Gson gson = new Gson();
                ScheduleItem = gson.fromJson(response.body().string().trim(), ScheduleBean.class);

                Parent fxml = FXMLLoader.load(getClass().getResource("/views/ScheduleDetailUI.fxml"));
                schedulePane.getChildren().removeAll();
                schedulePane.getChildren().setAll(fxml);
            }
        } else {
            String title = "Tips";
            String text = "Please choose the Date, Start Time, End Time, Stop and Direction!";
            CommonUtils.Dialog(schedulePane, title, text);
        }
    }
}