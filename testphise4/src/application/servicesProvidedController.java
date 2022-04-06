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

public class servicesProvidedController implements Initializable {
	

	private ArrayList<servicesProvided> data;
	private ObservableList<servicesProvided> dataList;
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;
	
	    @FXML	    private TableView<servicesProvided> TvServicesProvided;
	    @FXML	    private TableColumn<servicesProvided, Integer> id;
	    @FXML	    private TableColumn<servicesProvided, Integer> MedicalEntityId;
	    @FXML	    private TableColumn<servicesProvided, Integer> TypeServiesId;

	    @FXML	    private TextField medicalentityid;
	    @FXML	    private TextField typeserviesid;


	    
	    @Override
		public void initialize(URL url, ResourceBundle rb) {

			data = new ArrayList<>();

			try {
				// TODO
				getData();
				dataList = FXCollections.observableArrayList(data);

			} catch (SQLException ex) {
				Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
			}

			id.setCellValueFactory(new PropertyValueFactory<servicesProvided, Integer>("id"));
			MedicalEntityId.setCellValueFactory(new PropertyValueFactory<servicesProvided, Integer>("medicalEntityId"));
			TypeServiesId.setCellValueFactory(new PropertyValueFactory<servicesProvided, Integer>("typeServiesId"));
			

			TvServicesProvided.setItems(dataList);
			TvServicesProvided.setEditable(true);

			id.setCellFactory(TextFieldTableCell.<servicesProvided, Integer>forTableColumn(new IntegerStringConverter()));
			MedicalEntityId.setCellFactory(TextFieldTableCell.<servicesProvided, Integer>forTableColumn(new IntegerStringConverter()));
			TypeServiesId.setCellFactory(TextFieldTableCell.<servicesProvided, Integer>forTableColumn(new IntegerStringConverter()));
			
			TvServicesProvided.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		}
	    
	    private void getData() throws SQLException, ClassNotFoundException {
			// TODO Auto-generated method stub

			String SQL;

			connectDB();
			System.out.println("Connection established");

			SQL = "select * from services_provided order by id";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			data.clear();
			int i=0;
			while (rs.next()) {
				data.add(new servicesProvided(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)),Integer.parseInt(rs.getString(3))));
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
			void deleteSelectedservicesProvided() throws ClassNotFoundException, SQLException{
				try {
				ObservableList<servicesProvided> selectedRows, allPerson;
				allPerson = TvServicesProvided.getItems();
		        selectedRows = TvServicesProvided.getSelectionModel().getSelectedItems();
		        
		        connectDB();
		        for(servicesProvided e : selectedRows){
		        	ExecuteStatement("delete from  services_provided where id="+e.getId() + ";");
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
			void addNewservicesProvided(ActionEvent event) throws ClassNotFoundException, SQLException {
				
				
				
				servicesProvided rc = new servicesProvided(
		                
		                Integer.valueOf(medicalentityid.getText()),
		                Integer.valueOf(typeserviesid.getText()));
		
					
			        connectDB();
			        ExecuteStatement("insert into services_provided (medical_entity_id,type_servies_id) value ("
							+ "'" + rc.getMedicalEntityId() + "','" + rc.getTypeServiesId() + "')");
			        
			        TvServicesProvided.getItems().add(rc);

			      
			        medicalentityid.clear();
			        typeserviesid.clear();		        
					
					try {

						getData();

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
			public void changeMedicalEntityIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
				servicesProvided per =  TvServicesProvided.getSelectionModel().getSelectedItem();
				per.setMedicalEntityId( Integer.parseInt( e.getNewValue().toString()));
		        Integer a =per.getMedicalEntityId();
		        
		        connectDB();
		        ExecuteStatement("update  services_provided set medical_entity_id = '" +a+"' where id = " + per.getId() + ";");
		        con.close();
			}
			
			
			@FXML
			public void changeTypeServiesIdCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
				servicesProvided per =  TvServicesProvided.getSelectionModel().getSelectedItem();
				per.setTypeServiesId( Integer.parseInt( e.getNewValue().toString()));
		        Integer a =per.getTypeServiesId();
		        
		        connectDB();
		        ExecuteStatement("update  services_provided set type_servies_id = '" +a+"' where id = " + per.getId() + ";");
		        con.close();
			}
			
	    
}
