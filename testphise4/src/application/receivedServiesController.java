package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class receivedServiesController implements Initializable {

	private ArrayList<receivedServies> data;
	private ObservableList<receivedServies> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML	private TableView<receivedServies> TvReceivedServies;
	@FXML	private TableColumn<receivedServies, Integer> id;
	@FXML	private TableColumn<receivedServies, Integer> VisitId;
	@FXML	private TableColumn<receivedServies, Integer> TypeServiesId;
	@FXML	private TableColumn<receivedServies, String> Detels;

	@FXML	private TextField visitid;
	@FXML	private TextField tyoeserviesid;
	@FXML	private TextField detels;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		data = new ArrayList<>();

		try {
			// TODO
			getData();
			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(receivedServiesController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(receivedServiesController.class.getName()).log(Level.SEVERE, null, ex);
		}

		id.setCellValueFactory(new PropertyValueFactory<receivedServies, Integer>("id"));
		VisitId.setCellValueFactory(new PropertyValueFactory<receivedServies, Integer>("visitId"));
		TypeServiesId.setCellValueFactory(new PropertyValueFactory<receivedServies, Integer>("typeServiesId"));
		Detels.setCellValueFactory(new PropertyValueFactory<receivedServies, String>("detels"));

		TvReceivedServies.setItems(dataList);
		TvReceivedServies.setEditable(true);

		id.setCellFactory(TextFieldTableCell.<receivedServies, Integer>forTableColumn(new IntegerStringConverter()));
		VisitId.setCellFactory(
				TextFieldTableCell.<receivedServies, Integer>forTableColumn(new IntegerStringConverter()));
		TypeServiesId.setCellFactory(
				TextFieldTableCell.<receivedServies, Integer>forTableColumn(new IntegerStringConverter()));
		Detels.setCellFactory(TextFieldTableCell.<receivedServies>forTableColumn());

		TvReceivedServies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "select * from received_servies ";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.print(rs);

		data.clear();
		int i = 0;
		while (rs.next()) {
			data.add(new receivedServies(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
					Integer.parseInt(rs.getString(3)), rs.getString(4)));
			System.out.print("*********************************\n");
			System.out.print(data.get(i).toString());
			i++;
			System.out.print("*********************************\n");
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
	private void handleBackAction(ActionEvent event) throws IOException {
		Parent rootB = FXMLLoader.load(getClass().getResource("tablesMenu.fxml"));
		Scene sceneB = new Scene(rootB);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneB);
		stage.show();
	}

	@FXML
	void addNewReceivedServies(ActionEvent event) throws ClassNotFoundException, SQLException {

		receivedServies rc = new receivedServies(Integer.valueOf(visitid.getText()),
				Integer.valueOf(tyoeserviesid.getText()), detels.getText());

		connectDB();
//		ExecuteStatement("insert into received_servies (visit_id, type_servies_id ,detels) value (" + "'"
//				+ rc.getVisitId() + "','" + "'" + rc.getTypeServiesId() + "','" + rc.getDetels() + "')");

		
		  ExecuteStatement("insert into received_servies( visit_id, type_servies_id ,detels) value ("
					+ "'" + rc.getVisitId() + "','" + rc.getTypeServiesId() + "','" + rc.getDetels() + "')");
	        
		
		
		TvReceivedServies.getItems().add(rc);

		visitid.clear();
		tyoeserviesid.clear();
		detels.clear();

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
	void deleteSelectedReceivedServies() throws ClassNotFoundException, SQLException {
		try {
			ObservableList<receivedServies> selectedRows, allDay;
			allDay = TvReceivedServies.getItems();
			selectedRows = TvReceivedServies.getSelectionModel().getSelectedItems();

			connectDB();
			for (receivedServies e : selectedRows) {
				ExecuteStatement("delete from  received_servies where id =" + e.getId() + ";");
				allDay.remove(e);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changeVisitIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		receivedServies per = TvReceivedServies.getSelectionModel().getSelectedItem();
		per.setVisitId(Integer.parseInt(e.getNewValue().toString()));
		Integer a = per.getVisitId();

		connectDB();
		ExecuteStatement("update  received_servies set visit_id = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void changeTypeServiesIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		receivedServies per = TvReceivedServies.getSelectionModel().getSelectedItem();
		per.setTypeServiesId(Integer.parseInt(e.getNewValue().toString()));
		Integer a = per.getTypeServiesId();

		connectDB();
		ExecuteStatement("update  received_servies set type_servies_id = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void changeDetelsCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {

		receivedServies per = TvReceivedServies.getSelectionModel().getSelectedItem();
		per.setDetels(e.getNewValue().toString());
		String a = per.getDetels();

		connectDB();
		ExecuteStatement("update  received_servies set detels = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

}
