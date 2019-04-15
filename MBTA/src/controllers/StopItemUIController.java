package controllers;

import com.jfoenix.controls.JFXListView;
import main.Application;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import utils.JDBCUtil;

/**
 * FXML Controller class
 *
 * @author Eddy
 */
public class StopItemUIController implements Initializable {
    
    @FXML
    private JFXListView<HBox> itemlistview;
    
    @FXML
    private Label subway_title;
    
    @FXML
    private ImageView subway_icon;
    
    int index = SubwayLineUIController.selector;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Stops' name and address
        List<String> names = new ArrayList();
        List<String> addresses = new ArrayList();
        List<String> wheel = new ArrayList();
        
        subway_icon.setImage(new Image("/icons/b_" + Application.icons[index] + "256.png"));
        subway_icon.setFitWidth(100);
        subway_icon.setFitHeight(100);
        subway_title.setText("BOSTON  TRAIN  " + Application.names[index].toUpperCase());
        
        //Call JDBC function to load stop item detailed info
        JDBCUtil.connect(names, addresses, wheel, index);
        
        for (int i = 0; i < names.size(); i++) {
            HBox hBox = new HBox();
            
            String route = "";
            if (wheel.get(i).equals("1")) {
                route = "wheel128";
            } else {
                route = "nowheel128";
            }
            
            //Stop Name Label
            Label nameLabel = new Label(names.get(i));
            ImageView image = new ImageView("/icons/stop_" + Application.colors[index] + "128.png");
            image.setFitHeight(48);
            image.setFitWidth(48);
            nameLabel.setGraphic(image);
            nameLabel.setGraphicTextGap(30);
            nameLabel.getStyleClass().add("speciallabel");
            nameLabel.setAlignment(Pos.CENTER);
            
            //Stop Address Label
            Label addressLabel = new Label(addresses.get(i));
            ImageView wheelImageView = new ImageView("/icons/" + route + ".png");
            wheelImageView.setFitHeight(48);
            wheelImageView.setFitWidth(48);
            addressLabel.setGraphic(wheelImageView);
            addressLabel.setContentDisplay(ContentDisplay.RIGHT);
            addressLabel.setGraphicTextGap(30);
            addressLabel.setFont(Font.font("Roboto Cn", FontPosture.REGULAR, 14));
            addressLabel.setAlignment(Pos.CENTER);
            
            Region filler = new Region();
            hBox.setAlignment(Pos.CENTER);
            HBox.setHgrow(filler, Priority.ALWAYS);
            
            hBox.getChildren().addAll(nameLabel, filler, addressLabel);
            itemlistview.getItems().add(hBox);
        }
        itemlistview.depthProperty().set(10);
        itemlistview.setExpanded(true);
        itemlistview.setFocusTraversable(false);
    }    
}