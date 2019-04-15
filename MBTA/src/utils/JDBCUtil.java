package utils;

import main.Application;
import com.jfoenix.controls.JFXComboBox;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import json.StopObject;

/**
 *
 * @author Eddy
 */

//This Util Class is for JDBC function call
public class JDBCUtil {

    public static void connect(JFXComboBox<Label> box, int index) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
        }

        try {
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://00.00.00.00/mbta", "username", "password");

            Statement stmt = (Statement) connect.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery("select name,id from " + Application.tables[index]);

            Application.specialStops.clear();

            while (rs.next()) {
                box.getItems().add(new Label(rs.getString("name")));

                StopObject object = new StopObject(rs.getString("name"), rs.getString("id"));
                Application.specialStops.add(object);
            }
        } catch (SQLException e2) {
        }
    }
    
    public static void connect(List name, List address, List wheel, int index) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
        }

        try {
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://00.00.00.00/mbta", "username", "password");

            Statement stmt = (Statement) connect.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery("select name,address,id,wheel from " + Application.tables[index]);

            while (rs.next()) {
                name.add(rs.getString("name"));
                address.add(rs.getString("address"));
                wheel.add(rs.getString("wheel"));
            }
        } catch (SQLException e2) {
        }
    }
}
