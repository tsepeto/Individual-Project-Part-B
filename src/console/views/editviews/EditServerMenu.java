/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.views.editviews;

import console.utils.Input;
import console.utils.Style;
import console.views.menuviews.FirstWindow;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 */
public class EditServerMenu {
    
    private static  GenericDao data = GenericDao.getInstance();
    
    public static void show(){
        
        
        Style.addLines(1);
        checkConnection();
        int correctInput;
        while(true){
            
            System.out.println(Style.red("YOU HAVE TO BE CAREFUL WHEN YOU CHANGING THE PROPERTIES!!!")); 
            System.out.println(Style.red("IF YOU USE THE DEFAULT SERVER AND YOU DIDN'T REMEMBER WHAT HAVE YOU DONE")); 
            System.out.println(Style.red("JUST PRESS 6, TO RESTORE THE DEFAULT SETTINGS!!!")); 
            Style.addLines(1);
            System.out.println(Style.yellow("SERVER MENU"));
            Style.addUnderline();
            System.out.println(Style.red("1")+" - EDIT SERVER DOMAIN");
            System.out.println(Style.red("2")+" - EDIT SERVER PORT");
            System.out.println(Style.red("3")+" - EDIT DATABASE NAME");
            System.out.println(Style.red("4")+" - EDIT USERNAME");
            System.out.println(Style.red("5")+" - EDIT PASSWORD");
            System.out.println(Style.green("6 - RESTORE TO DEFAULT SETTINGS"));
            System.out.println(Style.yellow("7")+" - GO BACK");
            
            Style.addUnderline();
            correctInput = Input.giveOnlyInteger(1,7);                          //Asks the user to choose an integer among the options.
            switch(correctInput){
            
                case 1:                                                         //After the user's choice the method calls the right View.
                    Style.addLines(2);
                    editDomain();
                    checkConnection();
                    break;
                    
                case 2:
                    Style.addLines(2);
                    editPort();
                    checkConnection();
                    break;
                    
                case 3:
                    Style.addLines(2);
                    editDatabase();
                    checkConnection();
                    break;
                    
                case 4:
                    Style.addLines(2);
                    editUsername();
                    checkConnection();
                    break;
                    
                case 5:
                    Style.addLines(2);
                    editPassword();
                    checkConnection();
                    break;
                    
                    
                case 6:
                    Style.addLines(2);
                    data.setDefaultProperties();
                    checkConnection();
                    break;
                    
                case 7:
                    Style.addLines(2);
                    FirstWindow.showMenu();
                    break;
                    
                default:
                    System.out.println(Style.red("A problem has occurred in Server Menu file, Line 51"));
            }
            
        }
    }
    
    private static void checkConnection(){
        boolean serverConnection =data.checkServerConnection();                 //Checks the connection with the database.
        if(serverConnection == true){
            System.out.println(Style.green("THE PROGRAM IS CONNECTED TO THE SERVER!!!!"));
            if(data.checkDbConnection()){
                System.out.println(Style.green("THE PROGRAM IS CONNECTED TO THE DATABASE!!!!"));
                if(data.checkTables().equals("EMPTY DB")){
                    System.out.println(Style.red("THE DATABASE IS EMPTY!!!"));
                    System.out.println(Style.red("PLEASE CHOOSE THE RIGHT DATABASE!!!"));
                }
                else if(data.checkTables().equals("WRONG DATABASE")){
                    System.out.println(Style.red("THE DATABASE '" + data.getDataBase()+ "' IS NOT CORRECT FOR THE PROGRAM!!!"));
                    System.out.println(Style.red("PLEASE CHANGE THE DATABASE NAME, AND TRY TO CONNECT AGAIN!!!"));
                    
                }
                else if(data.checkTables().equals("OK")){
                    System.out.println(Style.green("THE DATABASE IS READY FOR USE!!!!"));
                }
            }
            else{
                System.out.println(Style.red("THE PROGRAM IS NOT CONNECTED TO THE DATABASE!!!"));
            }
        }
        else{
            System.out.println(Style.green("THE PROGRAM IS NOT CONNECTED TO THE SERVER!!!!"));
        }
        Style.addLines(1);
    }
    
    private static void editDomain(){
    
        Style.addLines(2);
        System.out.println("OLD SERVER'S DOMAIN : "+ data.getServer());
        System.out.println(Style.yellow("PLEASE WRITE THE NEW SERVER'S DOMAIN"));
        String server = Input.input.nextLine();
        Style.addLines(1);
        System.out.println(Style.red("DO YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.red("1 - YES"));
        System.out.println(Style.green("2 - NO"));
        boolean des = Input.giveYesOrNo();
        if(des == true){
            data.setServer(server);
            data.setProperties();
        }
        Style.addLines(2); 
    }
    
    private static void editPort(){
    
        Style.addLines(2);
        System.out.println("OLD SERVER'S PORT : "+ data.getPort());
        System.out.println(Style.yellow("PLEASE WRITE THE NEW SERVER'S PORT"));
        int port = Input.giveOnlyInteger();
        Style.addLines(1);
        System.out.println(Style.red("DO YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.red("1 - YES"));
        System.out.println(Style.green("2 - NO"));
        boolean des = Input.giveYesOrNo();
        if(des == true){
            data.setPort(String.valueOf(port));
            data.setProperties();
        }
        Style.addLines(2); 
    }
    
    
    private static void editDatabase(){
    
        Style.addLines(2);
        System.out.println("OLD SERVER'S DATABASE NAME : "+ data.getDataBase());
        System.out.println(Style.yellow("PLEASE WRITE THE NEW SERVER'S DATABASE NAME"));
        String dataBase = Input.input.next();
        Style.addLines(1);
        System.out.println(Style.red("DO YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.red("1 - YES"));
        System.out.println(Style.green("2 - NO"));
        boolean des = Input.giveYesOrNo();
        if(des == true){
            data.setDataBase(dataBase);
            data.setProperties();
        }
        Style.addLines(2); 
    }
    
    private static void editUsername(){
    
        Style.addLines(2);
        System.out.println("OLD SERVER'S Username : "+ data.getUsername());
        System.out.println(Style.yellow("PLEASE WRITE THE NEW SERVER'S USERNAME"));
        String username = Input.input.next();
        Style.addLines(1);
        System.out.println(Style.red("DO YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.red("1 - YES"));
        System.out.println(Style.green("2 - NO"));
        boolean des = Input.giveYesOrNo();
        if(des == true){
            data.setUsername(username);
            data.setProperties();
        }
        Style.addLines(2); 
    }
    
    
    private static void editPassword(){
    
        Style.addLines(2);
        System.out.println(Style.yellow("PLEASE WRITE THE NEW SERVER'S PASSWORD"));
        String password = Input.input.next();
        Style.addLines(1);
        System.out.println(Style.red("DO YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.red("1 - YES"));
        System.out.println(Style.green("2 - NO"));
        boolean des = Input.giveYesOrNo();
        if(des == true){
            data.setPassword(password);
            data.setProperties();
        }
        Style.addLines(2); 
    }
    
}
