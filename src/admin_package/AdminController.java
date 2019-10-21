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

            ResultSet rs = conn.createStatement().executeQuery(sql);
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
    private void addStudent(ActionEvent event){
        String sqlInsert = "INSERT INTO students (family_name, last_name, email, date_of_birth) VALUES (?, ?, ?, ?)";
    }

}
