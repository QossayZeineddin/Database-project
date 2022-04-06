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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MonthlyFinancialRecordController implements Initializable {
	
	double finals= 0 ;

	private ArrayList<MonthlyFinancialRecord> data;
	private ObservableList<MonthlyFinancialRecord> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;
	
	@FXML    private RadioButton r1;
    @FXML    private TableView<MonthlyFinancialRecord> TvRecord;
    @FXML    private TableColumn<MonthlyFinancialRecord, String> years;
    @FXML    private TableColumn<MonthlyFinancialRecord, Double> CompanyPayment;
    @FXML    private TableColumn<MonthlyFinancialRecord, Double> MemberPayment;

    
	String SQL1, SQL2;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String MaxDate = null, MinDate = null;
		data = new ArrayList<>();
		try {
			String max = "select max(v.date_visit) from person p , visit v , active_member a where v.member_id = a.id and a.persoin_id = p.id ;";
			String min = "select min(v.date_visit) from person p , visit v , active_member a where v.member_id = a.id and a.persoin_id = p.id ;";

			MinDate = Mindate(min);
			MaxDate = Maxdate(max);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] arr;

		arr = MinDate.split("-");
		String min = arr[0];
		String minmonthly = arr[1];
		int minInt = Integer.parseInt(min);
		int MinMonthly = Integer.parseInt(minmonthly);

		System.out.println(minInt);

		arr = MaxDate.split("-");
		String max = arr[0];
		String maxmonth = arr[1];
		int maxInt = Integer.parseInt(max);
		int MaxMonth = Integer.parseInt(maxmonth);

		System.out.println(maxInt);
		String s = null;

		try {
			data.clear();
			String q = null;
			for (int i = minInt; i <= maxInt; i++) {

				for (int x = 0; x <= 9; x++) {
					

					q = "select sum(v.company_payment)  , sum(v.member_payment)  from person p , visit v , active_member a , categories c \n"
							+ " where v.member_id = a.id and\n" + " a.persoin_id = p.id and\n"
							+ " a.categorie_id = c.id and\n" + " v.date_visit like '" + i + "-" + "%" + x + "-" + "%';";

					s = ""+i+"-"+x;
					getData(s, q);

				}
			}

			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
		}

		years.setCellValueFactory(new PropertyValueFactory<MonthlyFinancialRecord, String>("monthly"));
		CompanyPayment
				.setCellValueFactory(new PropertyValueFactory<MonthlyFinancialRecord, Double>("totalCompanyPayment"));
		MemberPayment
				.setCellValueFactory(new PropertyValueFactory<MonthlyFinancialRecord, Double>("totalMemberPayment"));

		TvRecord.setItems(dataList);
		TvRecord.setEditable(true);

		years.setCellFactory(TextFieldTableCell.<MonthlyFinancialRecord>forTableColumn());
		CompanyPayment.setCellFactory(
				TextFieldTableCell.<MonthlyFinancialRecord, Double>forTableColumn(new DoubleStringConverter()));
		MemberPayment.setCellFactory(
				TextFieldTableCell.<MonthlyFinancialRecord, Double>forTableColumn(new DoubleStringConverter()));

		TvRecord.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

    String Mindate(String SQL) throws SQLException, ClassNotFoundException {

		connectDB();
		System.out.println("Connection established");

		// SQL = "select max(v.date_visit) from person p , visit v , active_member a\n"
		// + " where v.member_id = a.id and a.persoin_id = p.id ;";
		Statement stmt2 = con.createStatement();
		ResultSet rss = stmt2.executeQuery(SQL);
		rss.next();
		String foundType = rss.getString(1);


		rss.close();
		stmt2.close();
		con.close();

		return foundType;
	}

	String Maxdate(String SQL) throws SQLException, ClassNotFoundException {

		connectDB();
		System.out.println("Connection established");

		// SQL = "select min(v.date_visit) from person p , visit v , active_member a\n"
		// + " where v.member_id = a.id and\n"
		// + " a.persoin_id = p.id ;";
		Statement stmt3 = con.createStatement();
		ResultSet rssss = stmt3.executeQuery(SQL);
		rssss.next();
		String foundType = rssss.getString(1);

		
		rssss.close();
		stmt3.close();
		con.close();

		return foundType;
	}
	private void getData(String numb, String SQL) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		connectDB();
		System.out.println("Connection established");

		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next()) {
			
			if (rs.getString(1)  != null) {
				
			data.add(new MonthlyFinancialRecord(numb, Double.parseDouble(rs.getString(1)),
					Double.parseDouble(rs.getString(2))));

		}}
		// rs.close();
		// stmt.close();

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
	private void R1(ActionEvent event) throws IOException {
		Parent rootB = FXMLLoader.load(getClass().getResource("FinancialRecord.fxml"));
		Scene sceneB = new Scene(rootB);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(sceneB);
		stage.show();
	}
    

}
