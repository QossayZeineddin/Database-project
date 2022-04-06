package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.util.converter.IntegerStringConverter;


public class medicalEntityController implements Initializable {

	private ArrayList<medicalEntity> data;
	private ObservableList<medicalEntity> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;
	
	

    @FXML    private TableView<medicalEntity> TvmedicalEntity;
    @FXML    private TableColumn<medicalEntity,Integer> id;
    @FXML    private TableColumn<medicalEntity,String > address;
    @FXML    private TableColumn<medicalEntity,String > mname;
    @FXML    private TableColumn<medicalEntity,String > phonenumber;
    @FXML    private TableColumn<medicalEntity,Integer > medicalentitytypeId;

    
    @FXML    private TextField Adress;
    @FXML    private TextField MName;
    @FXML    private TextField PhoneNumber;
    @FXML    private TextField MedicalEntityTypeId;
    
    @FXML    private Button select;
    @FXML    private Button selectN;
    @FXML    private Button all;
    @FXML    private TextField adrs;
    @FXML    private TextField mn;
    
	
	String SQL1 =  "select * from medical_entity order by id";
	String SQL2, SQL3;
   
    public void initialize(URL url, ResourceBundle rb) {

		data = new ArrayList<>();

		try {
		
			if(select.isArmed()) {
										
				getData(SQL2);	
				
			}else if (all.isArmed()) {
			
				getData(SQL1);
				
			}else if (selectN.isArmed()) {
			
				getData(SQL3);	
			} else {
				
				getData(SQL1);
			}
				
			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(medicalEntityController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(medicalEntityController.class.getName()).log(Level.SEVERE, null, ex);
		}

		id.setCellValueFactory(new PropertyValueFactory<medicalEntity, Integer>("ID"));
		address.setCellValueFactory(new PropertyValueFactory<medicalEntity, String>("address"));
		mname.setCellValueFactory(new PropertyValueFactory<medicalEntity, String>("theName"));
		
		phonenumber.setCellValueFactory(new PropertyValueFactory<medicalEntity, String>("phoneNumber"));
		medicalentitytypeId.setCellValueFactory(new PropertyValueFactory<medicalEntity, Integer>("medicalEntityTypeId"));
		
		TvmedicalEntity.setItems(dataList);
		TvmedicalEntity.setEditable(true);

		id.setCellFactory(TextFieldTableCell.<medicalEntity, Integer>forTableColumn(new IntegerStringConverter()));
		address.setCellFactory(TextFieldTableCell.<medicalEntity>forTableColumn());
		mname.setCellFactory(TextFieldTableCell.<medicalEntity>forTableColumn());
		phonenumber.setCellFactory(TextFieldTableCell.<medicalEntity>forTableColumn());
		medicalentitytypeId.setCellFactory(TextFieldTableCell.<medicalEntity,Integer>forTableColumn(new IntegerStringConverter()));
		
		TvmedicalEntity.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}
	
	
    private void getData(String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		
    	
		connectDB();
		System.out.println("Connection established");

		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		data.clear();
		int i=0;
		while (rs.next()) {
			data.add(new medicalEntity(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
					 rs.getString(4),Integer.parseInt( rs.getString(5))));
			System.out.print("*********************************\n");
			System.out.print(data.get(i).toString());
			i++;
			System.out.print("*********************************\n");
		}
		//rs.close();
		//stmt.close();

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
	public void changeaddressCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		medicalEntity per =  TvmedicalEntity.getSelectionModel().getSelectedItem();
        per.setAddress(e.getNewValue().toString());
        String a =per.getAddress();
        connectDB();
        ExecuteStatement("update medical_entity set address = '" +a+"' where id = " + per.getID() + ";");
        con.close();
	}
	
	@FXML
	public void changemNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		medicalEntity per =  TvmedicalEntity.getSelectionModel().getSelectedItem();
        per.setTheName(e.getNewValue().toString());
        String a =per.getTheName();
        connectDB();
        ExecuteStatement("update medical_entity set m_name = '" +a+"' where id = " + per.getID() + ";");
        con.close();
	}
	

	
	
	@FXML
	public void changePhoneNumberCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
		medicalEntity per =  TvmedicalEntity.getSelectionModel().getSelectedItem();
        per.setPhoneNumber(e.getNewValue().toString());
        String a =per.getPhoneNumber();
        connectDB();
        ExecuteStatement("update medical_entity set phone_number = '" +a+"' where id = " + per.getID() + ";");
        con.close();
	}
	
	
	@FXML
	public void changeMedicalEntityTypeIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		medicalEntity emWorkRo = TvmedicalEntity.getSelectionModel().getSelectedItem();
		connectDB();
		ExecuteStatement("UPDATE medical_entity set medical_entity_type_id = " + Integer.parseInt(e.getNewValue().toString())
				+ " WHERE EmWorkRo = " + emWorkRo.getID() + ";");
		con.close();
		emWorkRo.setMedicalEntityTypeId(Integer.parseInt(e.getNewValue().toString()));
	}
	
	
	
	
	
	
	@FXML
	void addNewmedicalEntity(ActionEvent event) throws ClassNotFoundException, SQLException {
		

		medicalEntity rc = new medicalEntity(
		           // Integer.valueOf(addid.getText()),
			
					
					Adress.getText(),
					MName.getText(),
					PhoneNumber.getText(),
					Integer.parseInt(MedicalEntityTypeId.getText()));
		            
			
			 
		        connectDB();
		        ExecuteStatement("insert into medical_entity(address,m_name,phone_number,medical_entity_type_id) value (" + "'" + rc.getAddress()
				+ "','" + rc.getTheName() + "','" + rc.getPhoneNumber() + "', '" + rc.getMedicalEntityTypeId() + "')");
		        
		        TvmedicalEntity.getItems().add(rc);

		        Adress.clear();
		        MName.clear();
		        PhoneNumber.clear();
		        MedicalEntityTypeId.clear();
				try {

					getData(SQL1);

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
	void deleteSelectedmedicalEntity() throws ClassNotFoundException, SQLException{
		try {
		ObservableList<medicalEntity> selectedRows, allmedicalEntity;
		allmedicalEntity = TvmedicalEntity.getItems();
        selectedRows = TvmedicalEntity.getSelectionModel().getSelectedItems();
        
        connectDB();
        for(medicalEntity e : selectedRows){
        	ExecuteStatement("delete from  medical_entity where id="+e.getID() + ";");
        	allmedicalEntity.remove(e);
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
	private void select(ActionEvent event) throws IOException {
		String input = adrs.getText();
		SQL2 = " select * from medical_entity m\n"
				+ "where\n"
				+ "m.address like '%"+input+"%'; ";
				initialize(null , null);
				adrs.clear();
		
		
	}
	@FXML
	private void allM(ActionEvent event) throws IOException {
		initialize(null , null);
		
	}

	@FXML
	private void selectNm(ActionEvent event) throws IOException {
		String input = mn.getText();
		SQL3 = "select m.id , m.address ,m.m_name ,m.phone_number , m.medical_entity_type_id from medical_entity m ,  medical_entity_type t\n"
				+ "	where\n"
				+ "	m.medical_entity_type_id = t.id and\n"
				+ "	t.type_name like '%"+input+"%';";
				initialize(null , null);
				mn.clear();
		
		
	}
	
	
	
}       
                
                
                
