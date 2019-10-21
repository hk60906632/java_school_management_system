package loginapp;

import java.sql.*;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try {
            this.connection = dbConnection.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }
    

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass, String opt)throws Exception{

        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";

        try{
            System.out.println("called isLogin ");
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, opt);
            rs = pr.executeQuery();
            boolean test1;

            if (rs.next()) {
                System.out.println("true");
                return true;
            }
            System.out.println("false");
            return false;
        }
        catch (SQLException ex) {
            System.out.println("catch block");
            return false;
        }
        finally {
            pr.close();
            rs.close();
        }
    }
}
