package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.HouseData;
import connection.DBConnection;

public class MainPageController implements Initializable {
	//임시로 현재 로그인 파머 ID 
	private String fid="1";
	
	//DB 필요 객체 선언
	DBConnection dbConn = new DBConnection();
	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
    PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
    ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체
    ObservableList<HouseData> houseBuffer=FXCollections.observableArrayList();
	
    @FXML
    private Label FarmerName;

    @FXML
    private Button HouseInfoButton;

    @FXML
    private TableView<HouseData> HouseTableView;
    
    @FXML
    private ListView<String> WarningList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//소유 하우스 테이블뷰 세팅
    	TableView<HouseData> tableView = new TableView<>();
        TableColumn<HouseData, Integer> houseIdColumn = new TableColumn<>("하우스ID");
        TableColumn<HouseData, String> nameColumn = new TableColumn<>("작물");
        TableColumn<HouseData, String> regionColumn = new TableColumn<>("지역");
    	
        houseIdColumn.setCellValueFactory(new PropertyValueFactory<>("houseId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        HouseTableView.getColumns().addAll(houseIdColumn,nameColumn,regionColumn);
        
        HouseTableRefresh();
	}
	
	@FXML
    void HouseInfoViewButton(ActionEvent event) {
		//하우스 상세뷰 오픈
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HouseInfoPage.fxml"));
        Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Scene scene = new Scene(root, 1080, 720);
        Stage secondStage = new Stage();
        //전페이지 조작 못함
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("하우스 상세 정보");
        secondStage.setScene(scene);
        secondStage.show();
    }
	
	void HouseTableRefresh() {
		//하우스 테이블뷰 정보 db 불러오기
        houseBuffer = FXCollections.observableArrayList();
        conn = dbConn.getConnection();
    	String farmerQuery = "SELECT * FROM HOUSE";
    	try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(farmerQuery);
            rs = pstm.executeQuery();
            while(rs.next()){
                int houseId = rs.getInt(1);
                //int empno = rs.getInt("empno"); 숫자 대신 컬럼 이름을 적어도 된다.
                String name = rs.getString(2);
                String region = rs.getString(3);
                int farmerId=rs.getInt(4);
                if(Integer.toString(farmerId).equals(fid)) {
                	houseBuffer.add(new HouseData(houseId,name,region));
                	System.out.println("test444");
                }
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // ResultSet을 명시적으로 닫아주는 것이 좋습니다.
			try{
			    if ( rs != null ){rs.close();}   
			    if ( pstm != null ){pstm.close();}   
			    if ( conn != null ){conn.close(); }
			}catch(Exception e){
			    throw new RuntimeException(e.getMessage());
			}
	    }
    	HouseTableView.setItems(houseBuffer);
	}
}
