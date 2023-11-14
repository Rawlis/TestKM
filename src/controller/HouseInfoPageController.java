package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class HouseInfoPageController implements Initializable {

    @FXML
    private Label FarmerName;

    @FXML
    private TableView<?> HouseRecordTable;

    @FXML
    private ListView<?> WarningList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
