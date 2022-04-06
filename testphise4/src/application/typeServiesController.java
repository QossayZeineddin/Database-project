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
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;




public class typeServiesController implements Initializable {

	private ArrayList<typeServies> data;
	private ObservableList<typeServies> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML
	private TableView<typeServies> ser;
	@FXML
	private TableColumn<typeServies, Integer> id;
	@FXML
	private TableColumn<typeServies, Double> typeServiesCost;
	@FXML
	private TableColumn<typeServies, String> tsName;

	@FXML	private TextField TypeServiesCost;
	@FXML	private TextField TsName;
	 
	 

	    @FXML	    private TextField more;
	    @FXML	    private TextField less;
	    @FXML	    private Button select2;
	    @FXML		private Button select1;
	    @FXML	    private Button allM;


	
	
	String SQL1 = "select * from type_servies order by id";
	String SQL2,SQL3;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		data = new ArrayList<>();

		try {
			// TODO

			if (select1.isArmed()) {
				getData(SQL2);

			} else if (select2.isArmed()) {
				getData(SQL3);

			} else if (allM.isArmed()) {
				getData(SQL1);

			} else {
				getData(SQL1);
			}
			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(typeServiesController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(typeServiesController.class.getName()).log(Level.SEVERE, null, ex);
		}

		id.setCellValueFactory(new PropertyValueFactory<typeServies, Integer>("id"));
		typeServiesCost.setCellValueFactory(new PropertyValueFactory<typeServies, Double>("typeServiesCost"));
		tsName.setCellValueFactory(new PropertyValueFactory<typeServies, String>("tsName"));

		ser.setItems(dataList);
		ser.setEditable(true);

		id.setCellFactory(TextFieldTableCell.<typeServies, Integer>forTableColumn(new IntegerStringConverter()));
		typeServiesCost.setCellFactory(TextFieldTableCell.<typeServies, Double>forTableColumn(new DoubleStringConverter()));
		tsName.setCellFactory(TextFieldTableCell.<typeServies>forTableColumn());

		ser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	private void getData(String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		connectDB();
		System.out.println("Connection established");

		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		data.clear();
		int i = 0;
		while (rs.next()) {
			data.add(new typeServies(Integer.parseInt(rs.getString(1)), Double.parseDouble(rs.getString(2)),
					rs.getString(3)));

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
	void addNewPerson(ActionEvent event) throws ClassNotFoundException, SQLException {

		typeServies rc = new typeServies(Double.valueOf(TypeServiesCost.getText()), TsName.getText());

		connectDB();
		ExecuteStatement("insert into type_servies (type_servies_cost,ts_name) value (" + "'"
				+ rc.getTypeServiesCost() + "','" + rc.getTsName() + "')");

		ser.getItems().add(rc);
		TypeServiesCost.clear();
		TsName.clear();

		try {

			getData(SQL1);

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
	void deleteSelectedPerson() throws ClassNotFoundException, SQLException {

		try {
			ObservableList<typeServies> selectedRows, allPerson;
			allPerson = ser.getItems();
			selectedRows = ser.getSelectionModel().getSelectedItems();

			connectDB();
			for (typeServies e : selectedRows) {
				ExecuteStatement("delete from  type_servies where id=" + e.getId() + ";");
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
	public void changeTypeServiesCostCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		typeServies per =  ser.getSelectionModel().getSelectedItem();
	        per.setTypeServiesCost( Double.parseDouble( e.getNewValue().toString()));
	        Double a =per.getTypeServiesCost();
        
        connectDB();
        ExecuteStatement("update  type_servies set type_servies_cost = '" +a+"' where id = " + per.getId() + ";");
        con.close();
	}
	
	@FXML
	public void changeTsNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		typeServies per =  ser.getSelectionModel().getSelectedItem();
        per.setTsName(e.getNewValue().toString());
        String a =per.getTsName();
        
        connectDB();
        ExecuteStatement("update  type_servies set ts_name = '" +a+"' where id = " + per.getId() + ";");
        con.close();

    }
	
	@FXML
	public void selectQ1(ActionEvent event)  throws IOException {
		
		if (more.getText() == null || more.getText().trim().isEmpty()) {
			SQL2 = SQL1;
		}else {
		
		Double costIN = Double.parseDouble( more.getText());
		SQL2 = "select * from type_servies t\n"
				+ "where t.type_servies_cost >= "+costIN+";";
		System.out.println("\n\n$$$$$$$$$$$$$$$$$$$$$$$$$"+costIN+"\n\n$$$$$$$$$$$$$$$$$$$$$$$$$");
		}
		less.clear();
		more.clear();
		initialize(null, null);
		

    }
	@FXML
	public void selectQ2(ActionEvent event)  throws IOException {
		
		
		if (less.getText() == null || less.getText().trim().isEmpty()) {
			SQL3 = SQL1;
		}else {
		
		Double costIN = Double.parseDouble( less.getText());
		SQL3 = "select * from type_servies t\n"
				+ "where t.type_servies_cost <= "+costIN+";";
		}
		more.clear();
		less.clear();
		initialize(null, null);
		

    }
	
	@FXML
	public void all(ActionEvent event)  throws IOException {
		less.clear();
		more.clear();
		initialize(null, null);
		

    }
	
	
	
}
