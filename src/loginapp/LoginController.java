package loginapp;

import admin_package.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import student_package.StudentController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    public void initialize (URL url, ResourceBundle rb){
        if(this.loginModel.isDatabaseConnected()){
            this.dbstatus.setText("Connected to Database");
        }else{
            this.dbstatus.setText("Not Connected to Database");
        }

        this.combobox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event) {
        try{
            //this.loginModel.isLogin("adm01", "1234567", "Admin");
            if(this.loginModel.isLogin(this.username.getText(), this.password.getText(), ((option) this.combobox.getValue()).toString())){
                System.out.println("in if statement");
                //close the older stage, the login window
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();

                switch (((option)this.combobox.getValue()).toString()){
                    case "Admin":
                        adminLogin();
                        break;

                    case "Student":
                        studentLogin();
                        break;
                }
            }else{
                System.out.println("in else statement");
                this.loginStatus.setText("Wrong Creditals");
            }
        }catch(Exception loaclException){
            loaclException.printStackTrace();
        }
    }

    public void studentLogin(){
        try {
            Stage studentStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/student_package/studentFXML.fxml"));
            StudentController studentcontroller = (StudentController)loader.getController();
            Scene scene = new Scene(root);
            studentStage.setScene(scene);
            studentStage.setTitle("Student Dashboard");
            studentStage.setResizable(false);
            studentStage.show();

        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    public void adminLogin(){
        try{
            System.out.println("called adminLogin");
            Stage adminStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            System.out.println("before show");
            Pane adminroot = (Pane)loader.load(getClass().getResource("/admin_package/Admin.fxml"));
            AdminController admincontroller = (AdminController)loader.getController();
            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();


        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
