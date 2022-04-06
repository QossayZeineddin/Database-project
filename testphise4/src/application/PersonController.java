package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;







public class PersonController implements Initializable {
	
	String SQL2 , SQL4;
	String SQL = "select * from person";
	private ArrayList<Person> data;
	private ObservableList<Person> dataList;
	

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML	private TableView<Person> TvPerson;
	@FXML	private TableColumn<Person, Integer> id;
	@FXML	private TableColumn<Person, String> firstName;
	@FXML	private TableColumn<Person, String> lastName;
	@FXML	private TableColumn<Person, LocalDate> birthdate;
	@FXML	private TableColumn<Person, String> address;
	@FXML	private TableColumn<Person, String> diseases;
	@FXML	private TableColumn<Person, String> phoneNumber;
		
	  	@FXML	    private Button selectQ;
	  	@FXML	    private Button selectQAge;
	  	@FXML	    private Button all;
	  	
		@FXML		private TextField FirstName;
		@FXML	    private TextField PhoneNumber;
	    @FXML	    private TextField LastName;
	    @FXML	    private DatePicker birthday;
	    @FXML	    private TextField Address;
	    @FXML	    private TextField Diesases;
		@FXML		private TextField addressQ;
		@FXML		private TextField age;
		

	    @FXML	    private RadioButton r2;
	    @FXML	    private RadioButton r3;
	    @FXML	    private RadioButton r1;
	    @FXML	    private RadioButton R2;
	    @FXML	    private RadioButton R3;
	    @FXML	    private RadioButton R1;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		
		data = new ArrayList<>();
		ToggleGroup g = new ToggleGroup();
		r1.setToggleGroup(g);
		r2.setToggleGroup(g);
		r3.setToggleGroup(g);
		R3.setToggleGroup(g);
		R2.setToggleGroup(g);
		R1.setToggleGroup(g);
		
		try {
			// TODO

			if (selectQ.isArmed()) {
				System.out.println(SQL2);
				getData2(SQL2);
				r1.setSelected(false);
				r2.setSelected(false);
				r3.setSelected(false);
				R1.setSelected(false);
				R2.setSelected(false);
				R3.setSelected(false);

			} else if (selectQAge.isArmed()) {
				r1.setSelected(false);
				r2.setSelected(false);
				r3.setSelected(false);
				if (age.getText() == null || age.getText().trim().isEmpty()) {
					
					getData2(SQL);
				}else {
					if(R1.isSelected() || R2.isSelected() || R3.isSelected()) {
				
				getData3(Integer.parseInt(age.getText()));
				R1.setSelected(false);
				R2.setSelected(false);
				R3.setSelected(false);
				} else getData2(SQL);
				
				}

			} else if (r1.isSelected()) {
				getData2(SQL);
				r2.setSelected(false);
				r3.setSelected(false);
			} else if (r2.isSelected()) {
				getData2(SQL4);
				r1.setSelected(false);
				r3.setSelected(false);
			

			} else if (r3.isSelected()) {
				getData2(SQL4);
				r2.setSelected(false);
				r1.setSelected(false);
			} else {
				getData2(SQL);
			}

			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
		}

		id.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
		firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		birthdate.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthday"));
		address.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
		diseases.setCellValueFactory(new PropertyValueFactory<Person, String>("diseases"));
		phoneNumber.setCellValueFactory(new PropertyValueFactory<Person, String>("PhoneNumber"));

		TvPerson.setItems(dataList);
		TvPerson.setEditable(true);

		id.setCellFactory(TextFieldTableCell.<Person, Integer>forTableColumn(new IntegerStringConverter()));
		firstName.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		lastName.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		birthdate.setCellFactory(TextFieldTableCell.<Person, LocalDate>forTableColumn(new LocalDateStringConverter()));
		address.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		diseases.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		phoneNumber.setCellFactory(TextFieldTableCell.<Person>forTableColumn());

		TvPerson.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	
	private void getData2(String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		
		
		connectDB();
		System.out.println("Connection established");
		Statement stmt11 = con.createStatement();
		ResultSet rs1 = stmt11.executeQuery(SQL);

		data.clear();
		int i=0;
		while (rs1.next()) {
			data.add(new Person(Integer.parseInt(rs1.getString(1)), rs1.getString(2), rs1.getString(3),
					rs1.getDate(4).toLocalDate(), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
			System.out.print("*********************************\n");
			System.out.print(data.get(i).toString());
			i++;
			System.out.print("*********************************\n");
		}
		
		//rs.close();
		//stmt1.close();
		

		con.close();
		System.out.println("Connection closed" + data.size());

	}
	
	private void getData3(int input) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		SQL = "select * from person";

		connectDB();
		System.out.println("Connection established");
		Statement stmt1 = con.createStatement();
		ResultSet rs = stmt1.executeQuery(SQL);

		data.clear();
		int i = 0;
		while (rs.next()) {
			LocalDate age = rs.getDate(4).toLocalDate();
			Period period = Period.between(age, LocalDate.now());
			int x = period.getYears();

			if (R1.isSelected()) {
				if (x == input) {
					data.add(new Person(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getString(7)));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
					i++;
					System.out.print("*********************************\n");
				}
			} else if (R2.isSelected()) {

				if (x > input) {
					data.add(new Person(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getString(7)));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
					i++;
					System.out.print("*********************************\n");
				}
			} else if (R3.isSelected()) {

				if (x < input) {
					data.add(new Person(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getString(7)));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
					i++;
					System.out.print("*********************************\n");
				}

			}

		}
		
		//rs.close();
		//stmt1.close();
		

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
			
				 
			}
			catch(SQLException s) {
				s.printStackTrace();
				System.out.println("SQL statement is not executed!");
				  
			}	
		}
	 
	 
	 
	 @FXML
	public void changeFirstNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{

		Person per =  TvPerson.getSelectionModel().getSelectedItem();
        per.setFirstName(e.getNewValue().toString());
        String a =per.getFirstName();
       int id = per.getId();
        connectDB();
        ExecuteStatement("update  person set first_name = '"+a + "' where id = "+id+";");
        con.close();
	}
	@FXML
	public void changelastNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{

		Person per =  TvPerson.getSelectionModel().getSelectedItem();
        per.setLastName(e.getNewValue().toString());
        String a =per.getLastName();
        
        connectDB();
        ExecuteStatement("update  person set last_name = '" +a+"' where id = " + per.getId() + ";");
        con.close();
	}

	@FXML
	public void changeAddressCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{

		Person per =  TvPerson.getSelectionModel().getSelectedItem();
        per.setAddress(e.getNewValue().toString());
        String a =per.getAddress();
        
        connectDB();
        ExecuteStatement("update  person set address = '" +a+"' where id = " + per.getId() + ";");
        con.close();
	}

	@FXML
	public void changeDiseasesCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		 Person per =  TvPerson.getSelectionModel().getSelectedItem();
        per.setDiseases(e.getNewValue().toString());
        String a =per.getDiseases();
        
        connectDB();
        ExecuteStatement("update  person set diseases = '" +a+"' where id = " + per.getId() + ";");
        con.close();

    }

	@FXML
	public void changePhoneNumberCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		Person per =  TvPerson.getSelectionModel().getSelectedItem();
        per.setPhoneNumber(e.getNewValue().toString());
        String a =per.getPhoneNumber();
        
        connectDB();
        ExecuteStatement("update  person set phone_number = '" +a+"' where id = " + per.getId() + ";");
        con.close();
	}
//	@FXML
//	public void changeBirthdateCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
//		Person per =  TvPerson.getSelectionModel().getSelectedItem();
//        e.getTablePosition().getRow().setBirthday(e.getNewValue();
//        String a =per.getPhoneNumber();
//        
//        
//        connectDB();
//        ExecuteStatement("update  person set birthday = " +a+" where id = " + per.getId() + ";");
//        con.close();
//	}
	@FXML
public void changeBirthdateCellEvent(TableColumn.CellEditEvent t) {
	((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBirthday((LocalDate) t.getNewValue());
	
		
			
	try {
			
			connectDB();
			ExecuteStatement("update  person set birthday = '"+t.getNewValue() + "' where id = "+((Person) t.getRowValue()).getId()+";");
			con.close();
			System.out.println("Connection closed");
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	@FXML
	void addNewPerson (ActionEvent event) throws ClassNotFoundException, SQLException {
		
		
		
		Person rc = new Person(
               // Integer.valueOf(addid.getText()),
                FirstName.getText(),
                LastName.getText(),
                birthday.getValue(),
                Address.getText(),
                Diesases.getText(),
                PhoneNumber.getText());
		
		 
	        connectDB();
	        ExecuteStatement("insert into person(first_name,last_name,birthday,address,diseases,phone_number) value ("
					+ "'" + rc.getFirstName() + "','" + rc.getLastName() + "','" + rc.getBirthday() + "', '"
					+ rc.getAddress() + "' , '" + rc.getDiseases() + "', '" + rc.getPhoneNumber() + "')");
	        
	        TvPerson.getItems().add(rc);

	        FirstName.clear();
	        PhoneNumber.clear();
	        LastName.clear();
	        Address.clear();
	        Diesases.clear();
	        
			
			try {
				
				
				getData2(SQL);

				// convert data from arraylist to observable arraylist
				dataList = FXCollections.observableArrayList(data);

				
				// really bad method
				initialize( null, null) ;
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}	
			
			
		
	
	
	
	
	
	}


	@FXML
	void deleteSelectedPerson() throws ClassNotFoundException, SQLException{
		try {
		ObservableList<Person> selectedRows, allPerson;
		allPerson = TvPerson.getItems();
        selectedRows = TvPerson.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(Person e : selectedRows){
        	ExecuteStatement("delete from  person where id="+e.getId() + ";");
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
	private void SelectQu(ActionEvent event) throws IOException {
		
		
		
		
		
		String qury = addressQ.getText().toLowerCase();
		System.out.println("***********" + qury + "**************");
		 SQL2 = "select * from person p\n" + "where\n" + "address like '%" + qury + "%' ;";
		
		 initialize(null , null);
		addressQ.clear();
		age.clear();
		
	}
	@FXML
	private void SelectQuAge(ActionEvent event) throws IOException {
			
		 initialize(null , null);
		 age.clear();
		 addressQ.clear();
		 R1.setSelected(false);
			R2.setSelected(false);
			R3.setSelected(false);
		 
			
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
	private void R1(ActionEvent event) throws IOException {
		initialize(null , null);
		
	}
	
	
	@FXML
	private void R2(ActionEvent event) throws IOException {
		SQL4 = "select distinct * from person p \n"
				+ "where p.id  in (select   a.persoin_id from active_member a);";
				initialize(null , null);
		
	}
	@FXML
	private void R3(ActionEvent event) throws IOException {
		SQL4 = "select distinct * from person p \n"
				+ "where p.id not in (select   a.persoin_id from active_member a);";
				initialize(null , null);
		
	}
	
	
	
	

}
