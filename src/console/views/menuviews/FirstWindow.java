
package console.views.menuviews;


import console.utils.Style;
import console.utils.Input;
import console.views.editviews.EditServerMenu;
import sCOOL.entity.dao.GenericDao;
//import database.classes.TestDataCreator;

/**
 *
 * @author tsepe
 * 
 * Is the first view that shows up in program.
 * Here the user chooses if he want to use the program in testing mode with random
 * data, or to use it with empty database.
 */
public class FirstWindow {
    
    
         
    /**
    *  The show() method welcomes the user and calls userChoice() method to let 
    * the user choose the next step
    */
    public static void showMenu(){
        GenericDao data = GenericDao.getInstance();                             //The program creates the singleton class DataBase.
        boolean serverConnection =data.checkServerConnection();                 //Checks the connection with the database.
        if(serverConnection == true){
            if(data.checkDbConnection()){
                if(data.checkTables().equals("EMPTY DB")){
                    EditServerMenu.show();
                }
                else if(data.checkTables().equals("WRONG DATABASE")){
                    EditServerMenu.show();
                }
                else if(data.checkTables().equals("OK")){
                }
            }
            else{
                EditServerMenu.show();
            }
        }
        else{
            EditServerMenu.show();
        }
        
        Style.addLines(1);
        System.out.println(Style.green("WELCOME TO sCOOL!!!"));
        System.out.println(Style.green("sCOOL IS THE APPLICATION THAT WILL MAKE THE SCHOOL MANAGMENT, BE A FUN"));
       
        Style.addLines(1);
        userChoice();                                                           //calls the choice procedure.
        
    }
    
    
    
    /**
     * The method let the user choose about the next move.<p>
     * Let him choose among:<p>
     * -Testing  mode with random data<p>
     * -Use the program with empty database<p>
     * -Exit the program<p>
     */
    private static void userChoice(){
        int correctInput;
        while(true){
       
            System.out.println(Style.yellow("PLEASE WRITE THE RIGHT NUMBER"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - CHANGE SERVER'S PROPERTIES");
            System.out.println(Style.yellow("2")+" - LET'S USE IT FOR MY SCHOOL");
            System.out.println(Style.yellow("3")+" - EXIT");
            Style.addUnderline();
            correctInput = Input.giveOnlyInteger(1,3);                          //Asks the user to choose an integer among the options.
            switch(correctInput){
            
                case 1:                                                         //After the user's choice the method calls the right View.

                    Style.addLines(2);
                    EditServerMenu.show();
                    Style.addLines(2);
                    break;
                    
                case 2:
                    MainMenu.showMenu();
                    Style.addLines(2);
                    break;
                    
                case 3:
                    OtherViews.closeProgram();
                    break;
                    
                default:
                    System.out.println(Style.red("A problem has occurred in FirstWindow file, Line 78"));
            }
            
        
        }
    
    }
    

    
}
