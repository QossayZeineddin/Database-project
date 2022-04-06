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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class activeMemberController implements Initializable {

	private ArrayList<activeMember> data;
	private ObservableList<activeMember> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML	private TableView<activeMember> TvActiveMember;
	@FXML	private TableColumn<activeMember, Integer> id;
	@FXML	private TableColumn<activeMember, LocalDate> fromdate;
	@FXML	private TableColumn<activeMember, LocalDate> todate;
	@FXML	private TableColumn<activeMember, Integer> categorieid;
	@FXML	private TableColumn<activeMember, Integer> persoinid;
	
	@FXML	    private Button name;
  	
  	@FXML		private TextField nameQ;
	
	@FXML	private DatePicker fromDate;
	@FXML	private DatePicker toDate;
	@FXML	private TextField categorieId;
	@FXML	private TextField persoinId;
	
	@FXML	private RadioButton R2;
	@FXML	private RadioButton R1;
    @FXML	private RadioButton R3;
    

    @FXML    private Button select;
    @FXML    private Button all;
    @FXML    private TextField dateQ;

    String SQL1 = "select * from active_member order by id";

    String SQL2 ,SQL3,input , name1 ;
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ToggleGroup g = new ToggleGroup();
		R1.setToggleGroup(g);
		R2.setToggleGroup(g);
		R3.setToggleGroup(g);

		data = new ArrayList<>();

		try {
			// TODO
			if (select.isArmed()) {

				if (dateQ.getText() == null || dateQ.getText().trim().isEmpty()) {
					getData(SQL1);
				} else {
					if (R3.isSelected() || R2.isSelected() || R1.isSelected()) {
						if (R3.isSelected()) {
							getData(SQL2);
						} else if (R2.isSelected() || R1.isSelected()) {
							getData2(input);

						}
					}
				}
			} else if (name.isArmed()) {
				getData(SQL3);

			} else
				getData(SQL1);

			dataList = FXCollections.observableArrayList(data);
			R1.setSelected(false);
			R2.setSelected(false);
			R3.setSelected(false);
		} catch (SQLException ex) {
			Logger.getLogger(activeMemberController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(activeMemberController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		id.setCellValueFactory(new PropertyValueFactory<activeMember, Integer>("id"));
		categorieid.setCellValueFactory(new PropertyValueFactory<activeMember, Integer>("categorieId"));
		persoinid.setCellValueFactory(new PropertyValueFactory<activeMember, Integer>("persoinId"));
		fromdate.setCellValueFactory(new PropertyValueFactory<activeMember, LocalDate>("fromDate"));
		todate.setCellValueFactory(new PropertyValueFactory<activeMember, LocalDate>("toDate"));
			

		TvActiveMember.setItems(dataList);
		TvActiveMember.setEditable(true);
		
		id.setCellFactory(TextFieldTableCell.<activeMember, Integer>forTableColumn(new IntegerStringConverter()));
		categorieid.setCellFactory(TextFieldTableCell.<activeMember, Integer>forTableColumn(new IntegerStringConverter()));
		persoinid.setCellFactory(TextFieldTableCell.<activeMember, Integer>forTableColumn(new IntegerStringConverter()));
		fromdate.setCellFactory(TextFieldTableCell.<activeMember, LocalDate>forTableColumn(new LocalDateStringConverter()));
		todate.setCellFactory(TextFieldTableCell.<activeMember, LocalDate>forTableColumn(new LocalDateStringConverter()));
		TvActiveMember.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		

	}

	private void getData(String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		connectDB();
		System.out.println("Connection established");

		Statement stmt1 = con.createStatement();
		ResultSet rs1 = stmt1.executeQuery(SQL);

		data.clear();
		int i = 0;
		while (rs1.next()) {
			data.add(new activeMember(Integer.parseInt(rs1.getString(1)), rs1.getDate(2).toLocalDate(),
					rs1.getDate(3).toLocalDate(), Integer.parseInt(rs1.getString(4)), Integer.parseInt(rs1.getString(5))));
			System.out.print("*********************************\n");
			System.out.print(data.get(i).toString());
			i++;
			System.out.print("*********************************\n");
		}
		
		//rs.close();
		//stmt.close();
		con.close();

	}

	private void getData2(String input) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String[] arr;
		arr = input.split("-");
		String yy = arr[0];
		String mm = arr[1];
		int year = Integer.parseInt(yy);
		int month = Integer.parseInt(mm);

		connectDB();
		System.out.println("Connection established");

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL1);

		data.clear();
		int i = 0;
		while (rs.next()) {
			if (R2.isSelected()) {

				String s = rs.getDate(3).toLocalDate().toString();
				String[] arr1;
				arr1 = s.split("-");
				String yy1 = arr1[0];
				String mm1 = arr1[1];
				int year1 = Integer.parseInt(yy1);
				int month1 = Integer.parseInt(mm1);


				Boolean q1 = year1 < year;
				Boolean q2 = year1 <= year && month1 <= month;
				
				if (q1 || q2) {

					data.add(new activeMember(Integer.parseInt(rs.getString(1)), rs.getDate(2).toLocalDate(),
							rs.getDate(3).toLocalDate(), Integer.parseInt(rs.getString(4)),
							Integer.parseInt(rs.getString(5))));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
					System.out.print("*********************************\n");
					i++;
				
				}

			} else if (R1.isSelected()) {

				String s = rs.getDate(3).toLocalDate().toString();
				String[] arr1;
				arr1 = s.split("-");
				String yy1 = arr1[0];
				String mm1 = arr1[1];
				int year1 = Integer.parseInt(yy1);
				int month1 = Integer.parseInt(mm1);

				if ((year1 >= year && month1 >= month) || (year1 > year)) {

					data.add(new activeMember(Integer.parseInt(rs.getString(1)), rs.getDate(2).toLocalDate(),
							rs.getDate(3).toLocalDate(), Integer.parseInt(rs.getString(4)),
							Integer.parseInt(rs.getString(5))));
					System.out.print("*********************************\n");
					// System.out.print(data.get(i).toString());
					i++;
					System.out.print("*********************************\n");
				}
			}

		}

		// rs.close();
		// stmt.close();
		con.close();

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
	public void changeFromDateCellEvent(TableColumn.CellEditEvent t) {
		((activeMember) t.getTableView().getItems().get(t.getTablePosition().getRow()))
				.setFromDate((LocalDate) t.getNewValue());

		try {

			connectDB();
			ExecuteStatement("update  active_member set from_date = '" + t.getNewValue() + "' where id = "
					+ ((activeMember) t.getRowValue()).getId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changeToDateCellEvent(TableColumn.CellEditEvent t) {
		((activeMember) t.getTableView().getItems().get(t.getTablePosition().getRow()))
				.setToDate((LocalDate) t.getNewValue());

		try {

			connectDB();
			ExecuteStatement("update  active_member set to_date = '" + t.getNewValue() + "' where id = "
					+ ((activeMember) t.getRowValue()).getId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changePersoinIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		
		
		activeMember per = TvActiveMember.getSelectionModel().getSelectedItem();
		per.setPersoinId(Integer.parseInt(e.getNewValue().toString()));
		int a = per.getPersoinId();
		int id = per.getId();
		connectDB();
		ExecuteStatement("update  active_member set persoin_id = '" + a + "' where id = " + id + ";");
		con.close();
		
		
	}

	@FXML
	public void changecategorieIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {

		activeMember per = TvActiveMember.getSelectionModel().getSelectedItem();
		per.setCategorieId(Integer.parseInt(e.getNewValue().toString()));
		int a = per.getCategorieId();
		int id = per.getId();
		connectDB();
		ExecuteStatement("update  active_member set categorie_id = '" + a + "' where id = " + id + ";");
		con.close();
	}

	@FXML
	void addNewActiveMember(ActionEvent event) throws ClassNotFoundException, SQLException {

		activeMember rc = new activeMember(
				// Integer.valueOf(addid.getText()),

				fromDate.getValue(),
				toDate.getValue(),
				Integer.valueOf(categorieId.getText()),
				Integer.valueOf(persoinId.getText()));

		connectDB();
		ExecuteStatement(
				"insert into active_member(from_date,to_date,categorie_id,persoin_id) value (" + "'" + rc.getFromDate()
						+ "','" + rc.getToDate() + "','" + rc.getCategorieId() + "', '" + rc.getPersoinId() + "')");

		TvActiveMember.getItems().add(rc);
		
		categorieId.clear();
		persoinId.clear();
		
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
	void deleteSelectedActiveMember() throws ClassNotFoundException, SQLException {
		try {
			ObservableList<activeMember> selectedRows, allPerson;
			allPerson = TvActiveMember.getItems();
			selectedRows = TvActiveMember.getSelectionModel().getSelectedItems();

			connectDB();
			for (activeMember e : selectedRows) {
				ExecuteStatement("delete from  active_member where id=" + e.getId() + ";");
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
	private void selectQ(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		input = dateQ.getText();

		if (R3.isSelected()) {
			SQL2 = "select * from active_member a\n" + "where a.to_date like '" + input + "-%';";

		}
		

		initialize(null, null);
		dateQ.clear();
	}
	@FXML
	private void allQ(ActionEvent event) throws IOException {
		
		nameQ.clear();
		dateQ.clear();
		initialize(null, null);
	
	}

	
	@FXML
	private void selectName(ActionEvent event) throws IOException {
		 name1 = nameQ.getText() ;
		SQL3 = "select distinct a.id , a.from_date , a.to_date , a.categorie_id , a.persoin_id from person p , active_member a \n"
				+ "where p.id = a.persoin_id and\n"
				+ "p.first_name like '%"+name1+"%' or p.last_name like '%"+name1+"%';";
		nameQ.clear();		
		initialize(null , null);
		
	}
	
}
