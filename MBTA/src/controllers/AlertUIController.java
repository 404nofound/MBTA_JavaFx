package controllers;

import main.Application;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import json.AlertBean;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class AlertUIController implements Initializable {

    @FXML
    private Pane Pane;
    @FXML
    private JFXListView<VBox> listview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Application.alert_url).build();
            okhttp3.Response response = client.newCall(request).execute();

            Gson gson = new Gson();
            AlertBean alertItem = gson.fromJson(response.body().string().trim(), AlertBean.class);
            
            for (int i = 0; i < alertItem.getData().size(); i++) {
                String cause = alertItem.getData().get(i).getAttributes().getCause();
                String header = alertItem.getData().get(i).getAttributes().getHeader();
                
                VBox vBox = new VBox();
                
                Label causeLabel = new Label(cause);
                ImageView image = new ImageView("/icons/b_alert128.png");
                image.setFitWidth(48);
                image.setFitHeight(48);
                causeLabel.setGraphic(image);
                causeLabel.setGraphicTextGap(40);
                causeLabel.getStyleClass().add("causelabel");
                
                TextArea text = new TextArea(header);
                text.setEditable(false);
                text.setWrapText(true);
                text.getStyleClass().add("specialtext");
                
                vBox.setSpacing(20);
                vBox.getChildren().addAll(causeLabel, text);
                vBox.setPrefWidth(770);
                vBox.setPrefHeight(170);
                listview.getItems().add(vBox);
            }
        } catch (IOException | JsonSyntaxException ex) {
        }
        listview.depthProperty().set(10);
        listview.setExpanded(true);
        listview.setFocusTraversable(false);
    }     
}