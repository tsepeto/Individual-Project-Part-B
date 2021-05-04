/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;


import console.utils.Style;
import console.views.editviews.EditServerMenu;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

/**
 *
 * @author tsepe
 */
public class GenericDao {
    public static enum viewFor{CREATE, EDIT, SEARCH;}                           // We use them to call the Views of objectMenu, so that they know for what purpose we want them
    public static final String daTiFormat = "dd/MM/yyyy";                       //The date time format
    protected static Connection conn;
    protected static Statement statement;
    protected static ResultSet result;
    protected static PreparedStatement ps;
    
    private String server;
    private String port; 
    private String dataBase;
    private String username;
    private String password;
    
    
    public static GenericDao instance;
    private final String GETNUMBEROFTABLES ="SELECT COUNT(DISTINCT `table_name`) AS TotalNumberOfTables FROM `information_schema`.`columns` WHERE `table_schema` = ?";
    
    
    public GenericDao(){
        getProperties();
        
    }
    /**
     * Returns our singleton database
     * @return the database's instance
     */
    public static GenericDao getInstance(){
        if(instance == null){
            instance = new GenericDao();
        }
        return instance;
    }
    
    
    /**
     * Sets the default properties to the properties file.
     */
    public void setDefaultProperties(){
        Properties scoolProperties = new Properties();
        scoolProperties.setProperty("server", "localhost");
        scoolProperties.setProperty("port", "3306");
        scoolProperties.setProperty("dataBase", "scool");
        scoolProperties.setProperty("username", "root");
        scoolProperties.setProperty("password", "");
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Writer PropWriter = Files.newBufferedWriter(PropertyFile);
            scoolProperties.store(PropWriter, "sCOOL Properties");
            PropWriter.close();
        }
        catch(IOException ex){
            System.out.println("IO Exception :" +
            ex.getMessage());
        }
        getProperties();
    }
    
    
    /**
     * Sets database properties to properties file
     */
    public void setProperties(){
        Properties scoolProperties = new Properties();
        scoolProperties.setProperty("server", this.server);
        scoolProperties.setProperty("port", this.port);
        scoolProperties.setProperty("dataBase", this.dataBase);
        scoolProperties.setProperty("username", this.username);
        scoolProperties.setProperty("password", this.password);
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Writer PropWriter = Files.newBufferedWriter(PropertyFile);
            scoolProperties.store(PropWriter, "sCOOL Properties");
            PropWriter.close();
        }
        catch(IOException ex){
            System.out.println("IO Exception :" +
            ex.getMessage());
        }
    }
    
    
    
    public void getProperties(){
        Path PropertyFile = Paths.get("scool.Properties");
        try{
            Reader PropReader = Files.newBufferedReader(PropertyFile);
            Properties scoolProperties = new Properties();
            scoolProperties.load(PropReader);
            this.server = scoolProperties.getProperty("server");
            this.port = scoolProperties.getProperty("port");
            this.dataBase = scoolProperties.getProperty("dataBase");
            this.username = scoolProperties.getProperty("username");
            this.password = scoolProperties.getProperty("password");
            PropReader.close();
        }
        catch(IOException Ex)                                                   //If the file with the properties doesn't exist,
        {
            setDefaultProperties();                                             //create new one with default options.
        }
    }
    
    /**
     * Does the connection to the database.
     */
    public Connection getConnection() {
        Connection connection = null;
        try{
            
             connection = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dataBase,username,password);
             
        }
        catch(SQLException e){
            System.out.println(Style.red("THE PROGRAM CAN'T CONNECT TO THE DATABASE. CHECK IF THE SERVER IS ONLINE AND CHECK YOUR SETTINGS!!!"));
            Style.addLines(1);
            System.out.println("WE ARE RELOCATED TO SERVER SETTINGS");
            EditServerMenu.show();
        }
        return connection;
    }
    
    public String checkTables(){
        String resultTables ="";
        int numberOfTables = 0;
        result = makePreparedStatement(GETNUMBEROFTABLES ,new Object[]{(Object)dataBase});
        try {
            result.next();
            numberOfTables = result.getInt(1);
        } catch (SQLException ex) {
            System.out.println(Style.red("Somthing went wrong in GenericDao.checkTables()"));
        }
        if(numberOfTables == 0){
            resultTables = "EMPTY DB";
        }
        else if(numberOfTables == 8){
            resultTables = "OK";
        }
        else if(numberOfTables !=8){
            resultTables = "WRONG DATABASE";
        }
        return resultTables;
    }
    
    
    public boolean checkDbConnection(){
        Connection conn;
        boolean result=false;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dataBase,username,password);
            result = true;
            conn.close();
        }
        catch(SQLException e){
            result = false;
        }
        return result;
    }
    
    public boolean checkServerConnection(){
        Connection conn;
        boolean result = false;
        try{
            
             conn = DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/?user="+username+"&password="+password);
             result = true;
             conn.close();
             
        }
        catch(SQLException e){
            result=false;
            
        }
        return result;
    }
    
    protected void closeConnections(ResultSet rs, Statement stmt, Connection conn) {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SOMETHING WENT WRONG WITH CLOSING CONNECTION");
        }
    }

    protected void closeConnections(PreparedStatement pstm, Connection conn) {
        try {
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SOMETHING WENT WRONG WITH CLOSING CONNECTION");
        }
    }
    
    public ResultSet makeStatement(String str){
        conn = getConnection();
        
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(str);
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("SOMETHING WENT WRONG WITH makeStatement");
        }
        
//        
        return result;
    }
    
    public ResultSet makePreparedStatement(String str,Object[] objArray){
        conn = getConnection();
        try {
            ps = conn.prepareStatement(str);
            if(objArray.length>0){
                for (int i = 0; i < objArray.length; i++) {
                    if(Integer.class.isInstance(objArray[i])){
                        ps.setInt(i+1,(int)objArray[i]);
                    }
                    else if(objArray[i] instanceof String){
                        ps.setString(i+1, (String)objArray[i]);
                    }
                    else if(Double.class.isInstance(objArray[i])){
                        ps.setDouble(i+1, (double)objArray[i]);
                    }
                    else if(objArray[i] instanceof LocalDate){
                        LocalDate x = (LocalDate) objArray[i];
                        ps.setDate(i+1, Date.valueOf(x));
                    }
                }
            }
            result = ps.executeQuery();
        } catch (SQLException ex) {
            
            System.out.println("SOMETHING GET WRONG WITH makeStatement IN GenericDao");
        }
          
        
        return result;
    }
    
    
    public void makeUpdateStatement(String str,Object[] objArray){
        conn = getConnection();
        try {
            ps = conn.prepareStatement(str);
            if(objArray.length>0){
                for (int i = 0; i < objArray.length; i++) {
                    if(Integer.class.isInstance(objArray[i])){
                        ps.setInt(i+1,(int)objArray[i]);
                    }
                    else if(objArray[i] instanceof String){
                        ps.setString(i+1, (String)objArray[i]);
                    }
                    else if(Double.class.isInstance(objArray[i])){
                        ps.setDouble(i+1, (double)objArray[i]);
                    }
                    else if(objArray[i] instanceof LocalDate){
                        LocalDate x = (LocalDate) objArray[i];
                        ps.setDate(i+1, Date.valueOf(x));
                    }
                }
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("SOMETHING GET WRONG WITH makeStatement IN GenericDao");
        }
        
    }
    
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
