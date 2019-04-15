package controllers;

import com.jfoenix.controls.JFXListView;
import main.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class SubwayLineUIController implements Initializable {

    @FXML
    private Pane pane;
    
    @FXML
    private JFXListView<HBox> listview;

    public static int selector = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 8; i++) {
            try {
                //Item in Listview
                HBox hBox = new HBox();
                
                //Train line icon
                ImageView image = new ImageView("/icons/s_" + Application.icons[i] + "128.png");
                image.setFitWidth(64);
                image.setFitHeight(64);
                //Train Name Label
                Label nameLabel = new Label(Application.names[i]);
                nameLabel.setGraphic(image);
                nameLabel.setGraphicTextGap(50);
                nameLabel.getStyleClass().add("speciallabel");
                nameLabel.setAlignment(Pos.CENTER);
                
                //Train Direction Label
                Label directionLabel = new Label(Application.directions[i]);
                directionLabel.setFont(Font.font("Roboto Cn", FontPosture.REGULAR, 14));
                directionLabel.setAlignment(Pos.CENTER);
                
                //Blank Pane
                Region filler = new Region();
                hBox.setAlignment(Pos.CENTER);
                HBox.setHgrow(filler, Priority.ALWAYS);

                hBox.getChildren().addAll(nameLabel, filler, directionLabel);
                listview.getItems().add(hBox);
            } catch (Exception e) {
            }
        }
        listview.depthProperty().set(10);
        listview.setExpanded(Boolean.TRUE);
        listview.setFocusTraversable(false);
    }    
    
    //After Clicking Train Line
    @FXML
    private void open_stop(MouseEvent event) throws IOException {
        //Which train user clicked
        selector = listview.getSelectionModel().getSelectedIndex();
        Parent fxml = FXMLLoader.load(getClass().getResource("/views/StopItemUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }
}