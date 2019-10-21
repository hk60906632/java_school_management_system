package admin_package;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dbUtil.dbConnection;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController {

    @FXML
    private TextField firstname_entry;
    @FXML
    private TextField lastname_entry;
    @FXML
    private TextField email_entry;
    @FXML
    private DatePicker dob_entry;
    @FXML
    private TextField search_entry;
    @FXML
    private TableView<StudentData> studenttable;
    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> dobcolumn;

    private dbConnection dc;
    private ObservableList<StudentData> data;

    private String sql = "SELECT * FROM students";

    public void initialize(URL url, ResourceBundle rb){
        this.dc = new dbConnection();
    }

    //load data to the column
    @FXML
    private void loadStudentData(ActionEvent event) throws SQLException{
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            //more vulnerable to sql injection
            //ResultSet rs = conn.createStatement().executeQuery(sql);

            PreparedStatement pr = conn.prepareStatement(this.sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                this.data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        }catch (SQLException e){
            System.err.println("Error " + e);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("id"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstname"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastname"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("dob"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }

    //insert into student table
    @FXML
    private void addStudent(ActionEvent event) throws SQLException{
        String sqlInsert = "INSERT INTO students (family_name, last_name, email, date_of_birth) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(sqlInsert);

            pr.setString(1, this.firstname_entry.getText());
            pr.setString(2, this.lastname_entry.getText());
            pr.setString(3, this.email_entry.getText());
            //getEditor() will get the value of the property editor
            pr.setString(4, this.dob_entry.getEditor().getText());

            pr.execute();
            conn.close();


        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void refresh_database() throws SQLException{

    }

    @FXML
    private void search(ActionEvent event) throws SQLException{
        String sqlSearch = "SELECT * FROM students WHERE family_name LIKE ? OR last_name LIKE ? OR email LIKE ? OR id LIKE ?";

        try{
            Connection cnn = dbConnection.getConnection();
            PreparedStatement pr = cnn.prepareStatement(sqlSearch);
            if (this.search_entry.getText() != ""){
                System.out.println("in if statement");
                //pr.setString(1, "family_name");
                pr.setString(1, this.search_entry.getText() + "%");
                pr.setString(2, this.search_entry.getText() + "%");
                pr.setString(3, this.search_entry.getText() + "%");
                pr.setString(4, this.search_entry.getText() + "%");
                ResultSet rs = pr.executeQuery();
                this.data = FXCollections.observableArrayList();
//                rs.next();
//                System.out.println(rs.getString(1));
//                System.out.printf(rs.getString(2));

                while (rs.next()){
                    this.data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
                cnn.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("id"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstname"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastname"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("dob"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);

    }

    @FXML
    private void clearFields(ActionEvent event){
        this.firstname_entry.setText("");
        this.lastname_entry.setText("");
        this.email_entry.setText("");
        this.dob_entry.setValue(null);
    }

}
