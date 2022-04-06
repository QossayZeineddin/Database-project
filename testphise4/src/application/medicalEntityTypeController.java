package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;



public class medicalEntityTypeController implements Initializable {

	private ArrayList<medicalEntityType> data;
	private ObservableList<medicalEntityType> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	
	@FXML   private TableView<medicalEntityType> TVmedicalEntityType;

	 @FXML   private TableColumn<medicalEntityType, Integer> id;

	 @FXML   private TableColumn<medicalEntityType, String> mETName;

	 @FXML   private TextField metname;
	 
		@Override
		public void initialize(URL url, ResourceBundle rb) {

			data = new ArrayList<>();

			try {
				// TODO
				getData();
				dataList = FXCollections.observableArrayList(data);

			} catch (SQLException ex) {
				Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
			}

			id.setCellValueFactory(new PropertyValueFactory<medicalEntityType, Integer>("id"));
			mETName.setCellValueFactory(new PropertyValueFactory<medicalEntityType, String>("typeName"));

			TVmedicalEntityType.setItems(dataList);
			TVmedicalEntityType.setEditable(true);

			id.setCellFactory(TextFieldTableCell.<medicalEntityType, Integer>forTableColumn(new IntegerStringConverter()));
			mETName.setCellFactory(TextFieldTableCell.<medicalEntityType>forTableColumn());

			TVmedicalEntityType.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		}	 
	 
	 
		private void getData() throws SQLException, ClassNotFoundException {
			// TODO Auto-generated method stub

			String SQL;

			connectDB();
			System.out.println("Connection established");

			SQL = "select * from medical_entity_type order by id";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			data.clear();
			int i = 0;
			while (rs.next()) {
				data.add(new medicalEntityType (Integer.parseInt(rs.getString(1)),rs.getString(2)));

			}
			rs.close();
			stmt.close();

			con.close();
			System.out.println("Connection closed" + data.size());

		}

		private void connectDB() throws ClassNotFoundException, SQLException {

			dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
			Properties p = new Properties();
			p.setProperty("user", dbUsername);
			p.setProperty("password", dbPassword);
			p.setProperty("useSSL", "false");
			p.setProperty("autoReconnect", "true");
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(dbURL, p);

		}

		public void ExecuteStatement(String SQL) throws SQLException {

			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(SQL);
				stmt.close();

			} catch (SQLException s) {
				s.printStackTrace();
				System.out.println("SQL statement is not executed!");

			}
		}	 
	 
		@FXML
		void addNewMedicalEntityType(ActionEvent event) throws ClassNotFoundException, SQLException {

			medicalEntityType rc = new medicalEntityType(metname.getText());

			connectDB();
			ExecuteStatement("insert into medical_entity_type (type_name) value (" + "'"
					+ rc.getTypeName() +"')");

			TVmedicalEntityType.getItems().add(rc);
			
			metname.clear();
			try {

				getData();

				// convert data from arraylist to observable arraylist
				dataList = FXCollections.observableArrayList(data);

				// really bad method
				initialize(null, null);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}

		@FXML
		void deleteSelectedMedicalEntityType() throws ClassNotFoundException, SQLException {

			try {
				ObservableList<medicalEntityType> selectedRows, allPerson;
				allPerson = TVmedicalEntityType.getItems();
				selectedRows = TVmedicalEntityType.getSelectionModel().getSelectedItems();

				connectDB();
				for (medicalEntityType e : selectedRows) {
					ExecuteStatement("delete from  medical_entity_type where id=" + e.getId() + ";");
					allPerson.remove(e);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		

		@FXML
		private void handleBackAction(ActionEvent event) throws IOException {
			Parent rootB = FXMLLoader.load(getClass().getResource("tablesMenu.fxml"));
			Scene sceneB = new Scene(rootB);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(sceneB);
			stage.show();
		}
	 
	 
		@FXML
		public void changemETNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
			medicalEntityType per =  TVmedicalEntityType.getSelectionModel().getSelectedItem();
	        per.setTypeName(e.getNewValue().toString());
	        String a =per.getTypeName();
	        
	        connectDB();
	        ExecuteStatement("update  medical_entity_type set type_name = '" +a+"' where id = " + per.getId() + ";");
	        con.close();

	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
