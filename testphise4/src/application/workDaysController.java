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
import javafx.util.converter.IntegerStringConverter;



public class workDaysController implements Initializable {

	private ArrayList<workDays> data;
	private ObservableList<workDays> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;
	
    @FXML private TableView<workDays> TvWorkDays;
    @FXML private TableColumn<workDays, Integer> id;
    @FXML private TableColumn<workDays, String> dayName;
    @FXML private TableColumn<workDays, Integer> medicalEntityId;
    
    @FXML private TextField dayname;
    @FXML private TextField name;
    @FXML private TextField medicalentityid;
    @FXML private Button select;
    @FXML private Button all;
	
    
    
    String SQL1 = "select * from work_days " , SQL2;
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		data = new ArrayList<>();

		try {
			
			if (select.isArmed()) {
				getData(SQL2);
				
			}else if (all.isArmed()) {
					getData(SQL1);
					
			} else {
		
			getData(SQL1);
			}
			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
		}

	id.setCellValueFactory(new PropertyValueFactory<workDays, Integer>("id"));
	medicalEntityId.setCellValueFactory(new PropertyValueFactory<workDays, Integer>("medicalEntityId"));
	dayName.setCellValueFactory(new PropertyValueFactory<workDays, String>("dayName"));
	
	TvWorkDays.setItems(dataList);
	TvWorkDays.setEditable(true);

	id.setCellFactory(TextFieldTableCell.<workDays, Integer>forTableColumn(new IntegerStringConverter()));
	medicalEntityId.setCellFactory(TextFieldTableCell.<workDays, Integer>forTableColumn(new IntegerStringConverter()));
	dayName.setCellFactory(TextFieldTableCell.<workDays>forTableColumn());


	TvWorkDays.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
}
	
	private void getData(String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		

		connectDB();
		System.out.println("Connection established");

		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.print(rs);

		data.clear();
		int i=0;
		while (rs.next()) {
			data.add(new workDays(Integer.parseInt(rs.getString(1)),rs.getString(2), Integer.parseInt(rs.getString(3))));
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
			
				 
			}
			catch(SQLException s) {
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
		void addNewDay(ActionEvent event) throws ClassNotFoundException, SQLException {
			
			
		 workDays rc = new workDays(
				 dayname.getText(),
				 Integer.valueOf(medicalentityid.getText())
				 
				 );
			
			 
		        connectDB();
		        ExecuteStatement("insert into work_days(day_name , medical_entity_id) value ("
						+ "'" + rc.getDayName() + "','" + rc.getMedicalEntityId() + "')");
		        
		        TvWorkDays.getItems().add(rc);

		        dayname.clear();
		        medicalentityid.clear();		        
				
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
				void deleteSelectedDay() throws ClassNotFoundException, SQLException{
					try {
					ObservableList<workDays> selectedRows, allDay;
					allDay = TvWorkDays.getItems();
			        selectedRows = TvWorkDays.getSelectionModel().getSelectedItems();
			        
			        connectDB();
			        for(workDays e : selectedRows){
			        	ExecuteStatement("delete from  work_days where id ="+e.getId() + ";");
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
				public void changeMedicalEntityIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
					workDays per =  TvWorkDays.getSelectionModel().getSelectedItem();
					per.setMedicalEntityId( Integer.parseInt( e.getNewValue().toString()));
			        Integer a =per.getMedicalEntityId();
			        
			        connectDB();
			        ExecuteStatement("update  work_days set medical_entity_id = '" +a+"' where id = " + per.getId() + ";");
			        con.close();
				}
				
				@FXML
				public void changeDayNameCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{

					workDays per =  TvWorkDays.getSelectionModel().getSelectedItem();
			        per.setDayName(e.getNewValue().toString());
			        String a =per.getDayName();
			        
			        connectDB();
			        ExecuteStatement("update  work_days set day_name = '" +a+"' where id = " + per.getId() + ";");
			        con.close();
				}
				
				
				
				@FXML
				private void selectQ(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
					
					
					if (name.getText() == null || name.getText().trim().isEmpty()) {
						SQL2 = SQL1;
					}else {
					String name1 = name.getText();

					SQL2 = "select  distinct w.id, w.day_name , w.medical_entity_id from work_days w , medical_entity m\n"
							+ " where  w.medical_entity_id = m.id and\n"
							+ " m.m_name like'%"+name1+"%'; ";
					}

					name.clear();

					initialize(null, null);
				}
				
				@FXML
				public void allV(ActionEvent event) throws IOException {

					initialize(null, null);

				}

				
}