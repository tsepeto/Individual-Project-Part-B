
package console.views.menuviews;

import console.utils.Style;
import console.utils.Input;
import console.views.editviews.EditServerMenu;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * 
 * The MainMenu is the first View that shows up if the user chooses to run the
 * program with empty database.
 * Here the user chooses if he want to create, edit or search something.
 */
public class MainMenu {
    
    
    
 /**
 * 
 * The show() method shows the user's choice procedure about the next move. 
 * 
 */
    public static void showMenu(){
        
        int result;
        
        
        do{
            Style.addLines(2);
            System.out.println(Style.yellow("PLEASE ENTER YOUR CHOICE:"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - CREATE IN DATABASE");
            System.out.println(Style.yellow("2")+" - EDIT IN DATABASE");
            System.out.println(Style.yellow("3")+" - SEARCH IN DATABASE");
            System.out.println(Style.yellow("4")+" - EDIT SERVER SETTINGS");
            System.out.println(Style.yellow("5")+" - EXIT THE PROGRAM");
            Style.addUnderline();
            
            Style.addLines(1);
            
            result = Input.giveOnlyInteger(1,5);
            switch(result){
            
            case 1:
                ObjectMenu.showFor(GenericDao.viewFor.CREATE);                    //Calls the next View and tells it that the
                break;                                                          //user wants to create an object.
                
            case 2:
                ObjectMenu.showFor(GenericDao.viewFor.EDIT);                      //Calls the next View and tells it that the
                break;                                                          //user wants to edit an object.
                
            case 3:
                ObjectMenu.showFor(GenericDao.viewFor.SEARCH);                    //Calls the next View and tells it that the
                break;                                                          //user wants to search an object.
            case 4:
                EditServerMenu.show();
                break;
            case 5:
                System.out.println(Style.red("YOU ARE EXITING THE PROGRAM"));   //It exits from the program.
                OtherViews.closeProgram();
                break;
                
            }
        }
        while(true);                                                            // This will be infinitive loop till the end.
    }
    
    
    
}
