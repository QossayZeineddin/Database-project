package application;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class tablesMenuController implements Initializable {
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

    @FXML
    private Label texNumPerson;
    @FXML
    private Label texNumActive;
	
    

	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		String st = "select count(*) from person;";
		String st2 = "select count(*) from active_member;";
		try {
			int nim = getData(st);
			int nim2 = getData(st2);
			texNumPerson.setText("Number of person in \nour company is:" + nim);
			texNumActive.setText("Number of active member \nin our company is:" + nim2);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO
	}
		private int getData(String SQL ) throws SQLException, ClassNotFoundException {
			// TODO Auto-generated method stub

			
			 
			connectDB();
			System.out.println("Connection established");
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt1.executeQuery(SQL);

			String s = null;
			int i=0;
			while (rs.next()) {
			 s = rs.getString(1);	
				
			}
			//rs.close();
			//stmt1.close();

			con.close();
			return Integer.parseInt(s);

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
	private void handlePersonAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("Person.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}

	@FXML
	private void handleActiveMemberAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("activeMember.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}

	@FXML
	private void handleCategoriesAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("categories.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}

	@FXML
	private void handletypeServiesAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("typeServies.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}

	@FXML
	private void handlemedicalEntityTypeAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("medicalEntityType.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();
	}
	
	@FXML
	private void handlemedicalEntityAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("medicalEntity.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	@FXML
	private void handlWorkDaysAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("workDays.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	@FXML
	private void handlVisitAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("visit.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	
	@FXML
	private void handlServicesProvidedAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("servicesProvided.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	
	
	
	@FXML
	private void handlReceivedServiesAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("ReceivedServies.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	@FXML
	private void handlFinancialRecordsAction(ActionEvent event) throws IOException {

		Parent rootVE = FXMLLoader.load(getClass().getResource("FinancialRecord.fxml"));
		Scene sceneVE = new Scene(rootVE);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneVE);
		stage.show();

	}
	
	
	
	
	@FXML
    private void handleExitAction(ActionEvent event) throws IOException {
        System.exit(0);
    }
	
	

}