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

import application.Person;
import application.PersonController;
import application.Visit;
import application.typeServies;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.DoubleStringConverter;

public class VisitController implements Initializable {

	private ArrayList<Visit> data;
	private ObservableList<Visit> dataList;
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "q123";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "insurance_company";
	private Connection con;

	@FXML	private TableView<Visit> TvVisit;
	@FXML	private TableColumn<Visit, Integer> id;
	@FXML	private TableColumn<Visit, Double> CompanyPayment;
	@FXML	private TableColumn<Visit, LocalDate> DateVisit;
	@FXML	private TableColumn<Visit, Integer> MemberPayment;
	@FXML	private TableColumn<Visit, Integer> MedicalEntityId;
	@FXML	private TableColumn<Visit, Integer> MemberId;
	@FXML	private TextField companypayment;
	@FXML	private DatePicker datevisit;
	@FXML	private TextField memberpayment;
	@FXML	private TextField medicalentityid;
	@FXML	private TextField memberid;
	@FXML	private TextField visitQ;
	@FXML	private Button all;
	@FXML	private Button select1;
	@FXML	private RadioButton r1;
	@FXML	private RadioButton r2;
	@FXML	private RadioButton r3;
	@FXML	private RadioButton R2;
	@FXML	private RadioButton R1;
	@FXML	private RadioButton R3;
	@FXML	private Button select;
	@FXML	private Button name;
	@FXML	private Button all1;
	@FXML	private TextField dateQ;
	@FXML	private TextField nameQ;

	Double costIN = 0.0;

	String SQL1 = "select * from visit order by id;";
	String SQL2, input;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ToggleGroup group = new ToggleGroup();
		r1.setToggleGroup(group);
		r2.setToggleGroup(group);
		r3.setToggleGroup(group);
		data = new ArrayList<>();

		
		R1.setToggleGroup(group);
		R2.setToggleGroup(group);
		R3.setToggleGroup(group);
		try {
			// TODO

			if (select1.isArmed()) {

				getData(SQL2);

			} else if (all.isArmed()) {
				getData(SQL1);

			} else if (name.isArmed()) {
				getData(SQL2);

			} else if (select.isArmed()) {

				if (dateQ.getText() == null || dateQ.getText().trim().isEmpty()) {
					getData(SQL1);
				} else {

					if (R3.isSelected() || R2.isSelected() || R1.isSelected()) {

						if (R3.isSelected()) {
							getData(SQL2);
							
						} else if (R2.isSelected() || R1.isSelected()) {
							getData2(input);
						}

					} else
						getData(SQL1);
				}
			} else
				getData(SQL1);

			dataList = FXCollections.observableArrayList(data);
			R1.setSelected(false);
			R2.setSelected(false);
			R3.setSelected(false);

			dataList = FXCollections.observableArrayList(data);

		} catch (SQLException ex) {
			Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(VisitController.class.getName()).log(Level.SEVERE, null, ex);
		}

		id.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("id"));
		CompanyPayment.setCellValueFactory(new PropertyValueFactory<Visit, Double>("companyPayment"));
		MemberPayment.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("memberPayment"));
		MedicalEntityId.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("medicalEntityId"));
		MemberId.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("memberId"));
		DateVisit.setCellValueFactory(new PropertyValueFactory<Visit, LocalDate>("dateVisit"));

		TvVisit.setItems(dataList);
		TvVisit.setEditable(true);

		id.setCellFactory(TextFieldTableCell.<Visit, Integer>forTableColumn(new IntegerStringConverter()));
		MemberId.setCellFactory(TextFieldTableCell.<Visit, Integer>forTableColumn(new IntegerStringConverter()));
		MedicalEntityId.setCellFactory(TextFieldTableCell.<Visit, Integer>forTableColumn(new IntegerStringConverter()));
		MemberPayment.setCellFactory(TextFieldTableCell.<Visit, Integer>forTableColumn(new IntegerStringConverter()));
		CompanyPayment.setCellFactory(TextFieldTableCell.<Visit, Double>forTableColumn(new DoubleStringConverter()));
		DateVisit.setCellFactory(TextFieldTableCell.<Visit, LocalDate>forTableColumn(new LocalDateStringConverter()));

		TvVisit.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
			if (rs.getString(1) != null) {
				data.add(new Visit(Integer.parseInt(rs.getString(1)), Double.parseDouble(rs.getString(2)),
						rs.getDate(3).toLocalDate(), Integer.parseInt(rs.getString(4)),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
				System.out.print("*********************************\n");
				System.out.print(data.get(i).toString());
				i++;
				System.out.print("*********************************\n");
			}
		}
		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed" + data.size());

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
				Boolean q2 = year1 <= year && month1 < month;

				if (q1 || q2) {

					data.add(new Visit(Integer.parseInt(rs.getString(1)), Double.parseDouble(rs.getString(2)),
							rs.getDate(3).toLocalDate(), Integer.parseInt(rs.getString(4)),
							Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
					i++;
					System.out.print("*********************************\n");
				}

			} else if (R1.isSelected()) {

				String s = rs.getDate(3).toLocalDate().toString();
				String[] arr1;
				arr1 = s.split("-");
				String yy1 = arr1[0];
				String mm1 = arr1[1];
				int year1 = Integer.parseInt(yy1);
				int month1 = Integer.parseInt(mm1);
				Boolean q1 = year1 > year;
				Boolean q2 = year1 >= year && month1 > month;
				if (q1 || q2) {
					data.add(new Visit(Integer.parseInt(rs.getString(1)), Double.parseDouble(rs.getString(2)),
							rs.getDate(3).toLocalDate(), Integer.parseInt(rs.getString(4)),
							Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
					System.out.print("*********************************\n");
					System.out.print(data.get(i).toString());
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
	void deleteSelectedVisit() throws ClassNotFoundException, SQLException {
		try {
			ObservableList<Visit> selectedRows, allPerson;
			allPerson = TvVisit.getItems();
			selectedRows = TvVisit.getSelectionModel().getSelectedItems();

			connectDB();
			for (Visit e : selectedRows) {
				ExecuteStatement("delete from  visit where id=" + e.getId() + ";");
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
	void addNewVisit(ActionEvent event) throws ClassNotFoundException, SQLException {

		Visit rc = new Visit(Double.valueOf(companypayment.getText()), datevisit.getValue(),
				Integer.valueOf(memberpayment.getText()), Integer.valueOf(medicalentityid.getText()),
				Integer.valueOf(memberid.getText()));

		connectDB();
		ExecuteStatement(
				"insert into visit(company_payment,date_visit,member_payment,medical_entity_id,member_id) value (" + "'"
						+ rc.getCompanyPayment() + "','" + rc.getDateVisit() + "','" + rc.getMemberPayment() + "', '"
						+ rc.getMedicalEntityId() + "' , '" + rc.getMemberId() + "')");

		TvVisit.getItems().add(rc);

		companypayment.clear();
		memberpayment.clear();
		medicalentityid.clear();
		memberid.clear();

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
	public void changeDateVisitCellEvent(TableColumn.CellEditEvent t) {
		((Visit) t.getTableView().getItems().get(t.getTablePosition().getRow()))
				.setDateVisit((LocalDate) t.getNewValue());

		try {

			connectDB();
			ExecuteStatement("update  visit set date_visit = '" + t.getNewValue() + "' where id = "
					+ ((Visit) t.getRowValue()).getId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changeCompanyPaymentCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		Visit per = TvVisit.getSelectionModel().getSelectedItem();
		per.setCompanyPayment(Double.parseDouble(e.getNewValue().toString()));
		Double a = per.getCompanyPayment();

		connectDB();
		ExecuteStatement("update  visit set company_payment = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void changeMemberPaymentCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		Visit per = TvVisit.getSelectionModel().getSelectedItem();
		per.setMemberPayment(Integer.parseInt(e.getNewValue().toString()));
		Integer a = per.getMemberPayment();

		connectDB();
		ExecuteStatement("update  visit set member_payment = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void changemedicalentityidCellEvent(TableColumn.CellEditEvent e)
			throws ClassNotFoundException, SQLException {
		Visit per = TvVisit.getSelectionModel().getSelectedItem();
		per.setMedicalEntityId(Integer.parseInt(e.getNewValue().toString()));
		Integer a = per.getMedicalEntityId();

		connectDB();
		ExecuteStatement("update  visit set medical_entity_id = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void changememberidCellEvent(TableColumn.CellEditEvent e) throws ClassNotFoundException, SQLException {
		Visit per = TvVisit.getSelectionModel().getSelectedItem();
		per.setMemberId(Integer.parseInt(e.getNewValue().toString()));
		Integer a = per.getMemberId();

		connectDB();
		ExecuteStatement("update  visit set member_id = '" + a + "' where id = " + per.getId() + ";");
		con.close();
	}

	@FXML
	public void selectQ1(ActionEvent event) throws IOException {

		if (visitQ.getText() == null || visitQ.getText().trim().isEmpty()) {

			SQL2 = SQL1;

		} else {
			costIN = Double.parseDouble(visitQ.getText());

			if (r1.isSelected()) {

				SQL2 = "select * from visit v where v.company_payment  > " + costIN + ";";
				r1.setSelected(false);

			} else if (r2.isSelected()) {
				SQL2 = "select * from visit v where v.company_payment  < " + costIN + ";";
				r2.setSelected(false);
			} else if (r3.isSelected()) {
				SQL2 = "select * from visit v where v.company_payment  = " + costIN + ";";
				r3.setSelected(false);
			}

		}
		

		
		initialize(null, null);

		nameQ.clear();
		dateQ.clear();
		visitQ.clear();
		r1.setSelected(false);
		r2.setSelected(false);
		r3.setSelected(false);
	}

	@FXML
	public void allV(ActionEvent event) throws IOException {

		initialize(null, null);

		nameQ.clear();
		dateQ.clear();
		visitQ.clear();

	}

	@FXML
	private void selectQ(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

		

		input = dateQ.getText();
		if (R3.isSelected()) {
			SQL2 = "select * from visit a\n" + "where a.date_visit like '" + input + "-%';";
		

		}
		

		initialize(null, null);

		nameQ.clear();
		dateQ.clear();
		visitQ.clear();
		r1.setSelected(false);
		r2.setSelected(false);
		r3.setSelected(false);
	}

	@FXML
	private void getName(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		String name = nameQ.getText();

		SQL2 = "select distinct v.id,v.company_payment,v.date_visit,v.member_payment,v.medical_entity_id,v.member_id from visit v , active_member a  , person p\n"
				+ " where p.id = a.persoin_id and\n" + " a.id = v.member_id and\n" + " p.first_name like '%" + name
				+ "%' or p.last_name like '%" + name + "%';";


		initialize(null, null);

		nameQ.clear();
		dateQ.clear();
		visitQ.clear();
	}

}