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


public class categoriesController implements Initializable {

	private ArrayList<categories> data;
	private ObservableList<categories> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML	private TableView<categories> TvCategories;
	@FXML	private TableColumn<categories, Integer> id;
	@FXML	private TableColumn<categories, Integer> subscrubePeriod;
	@FXML	private TableColumn<categories, Integer> coverageRatio;
	@FXML	private TableColumn<categories, Integer> catrgouriyCost;

	@FXML	private TextField SubscrubePeriod;
	@FXML	private TextField CoverageRatio;
	@FXML	private TextField CatrgouriyCost;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		data = new ArrayList<>();

		try {
			// TODO
			getData();
			dataList = FXCollections.observableArrayList(data);
			

		} catch (SQLException ex) {
			Logger.getLogger(activeMemberController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(activeMemberController.class.getName()).log(Level.SEVERE, null, ex);
		}

		
		id.setCellValueFactory(new PropertyValueFactory<categories, Integer>("id"));
		subscrubePeriod.setCellValueFactory(new PropertyValueFactory<categories, Integer>("subscrubePeriod"));			
		coverageRatio.setCellValueFactory(new PropertyValueFactory<categories, Integer>("coverageRatio"));			
		catrgouriyCost.setCellValueFactory(new PropertyValueFactory<categories, Integer>("catrgouriyCost"));			

		TvCategories.setItems(dataList);
		TvCategories.setEditable(true);
		
		id.setCellFactory(TextFieldTableCell.<categories, Integer>forTableColumn(new IntegerStringConverter()));
		subscrubePeriod.setCellFactory(TextFieldTableCell.<categories, Integer>forTableColumn(new IntegerStringConverter()));
		coverageRatio.setCellFactory(TextFieldTableCell.<categories, Integer>forTableColumn(new IntegerStringConverter()));
		catrgouriyCost.setCellFactory(TextFieldTableCell.<categories, Integer>forTableColumn(new IntegerStringConverter()));
		
		
		TvCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}
	
	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "select * from categories order by id";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		data.clear();
		int i=0;
		while (rs.next()) {
			data.add(new categories(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
					Integer.parseInt(rs.getString(3)),Integer.parseInt(rs.getString(4))));
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
		void addNewCatrgouriy(ActionEvent event) throws ClassNotFoundException, SQLException {
			
			
		 categories rc = new categories(
	             Integer.valueOf(SubscrubePeriod.getText()),
				 Integer.valueOf(CoverageRatio.getText()),
				 Integer.valueOf(CatrgouriyCost.getText())				 );
			
			 
		        connectDB();
		     	ExecuteStatement("insert into categories(subscrube_period,coverage_ratio,catrgouriy_cost) value ("
		    					+ "'" + rc.getSubscrubePeriod() + "','" + rc.getCoverageRatio() + "','" + rc.getCatrgouriyCost() + "')");
		    	        
		     	TvCategories.getItems().add(rc);

		     	SubscrubePeriod.clear();
		     	CoverageRatio.clear();
		     	CatrgouriyCost.clear();
				
				try {

					getData();

					//convert data from arraylist to observable arraylist
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
		void deleteSelectedCatrgouriy() throws ClassNotFoundException, SQLException{
			try {
			ObservableList<categories> selectedRows, allDay;
			allDay = TvCategories.getItems();
	        selectedRows = TvCategories.getSelectionModel().getSelectedItems();
	        
	        connectDB();
	        for(categories e : selectedRows){
	        	ExecuteStatement("delete from  categories where id="+e.getId() + ";");
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
		public void changeSubscrubePeriodCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
			categories per =  TvCategories.getSelectionModel().getSelectedItem();
			per.setSubscrubePeriod( Integer.parseInt( e.getNewValue().toString()));
	        Integer a =per.getSubscrubePeriod();
	        
	        connectDB();
	        ExecuteStatement("update  categories set subscrube_period = '" +a+"' where id = " + per.getId() + ";");
	        con.close();
		}
	 
	 @FXML
		public void changeCoverageRatioCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
			categories per =  TvCategories.getSelectionModel().getSelectedItem();
			per.setCoverageRatio( Integer.parseInt( e.getNewValue().toString()));
	        Integer a =per.getCoverageRatio();
	        
	        connectDB();
	        ExecuteStatement("update  categories set coverage_ratio = '" +a+"' where id = " + per.getId() + ";");
	        con.close();
		}
	 
	 @FXML
		public void changeCatrgouriyCostCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException{
			categories per =  TvCategories.getSelectionModel().getSelectedItem();
			per.setCatrgouriyCost( Integer.parseInt( e.getNewValue().toString()));
	        Integer a =per.getCatrgouriyCost();
	        
	        connectDB();
	        ExecuteStatement("update  categories set catrgouriy_cost = '" +a+"' where id = " + per.getId() + ";");
	        con.close();
		}
}