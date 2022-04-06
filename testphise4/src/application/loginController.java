package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Sample Skeleton for 'Sample.fxml' Controller Class
 */


public class loginController  implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="usernameTextField"
    private TextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTextField"
    private PasswordField passwordTextField; // Value injected by FXMLLoader
    
    @FXML
    private Label message;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private void handleMenuAction(ActionEvent event) throws IOException {
        if(checkNamePassword()){
        Parent rootVE = FXMLLoader.load(getClass().getResource("tablesMenu.fxml"));
        Scene sceneVE = new Scene(rootVE);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(sceneVE);
        stage.show();
        }else
             System.out.println("Can't join to the database");
        //message.setText("Password Or Username Is Incorrect..");
         }
    
    public boolean checkNamePassword(){
        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        if(password.equals("root") && username.equals("root"))
            return true;
        
        return false;
        
    }
    
    @FXML
    private void handleExitAction(ActionEvent event) throws IOException {
        System.exit(0);
    }
    
}
