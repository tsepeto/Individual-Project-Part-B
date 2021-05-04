/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.views.editviews;

import console.utils.Input;
import console.utils.Style;
import console.views.menuviews.ObjectMenu;
import console.views.menuviews.OtherViews;
import sCOOL.entity.dao.GenericDao;
import sCOOL.entity.Trainer;

/**
 *
 * @author tsepe
 * EditTrainer is the View that the program calls every time the user
 * chooses to edit a Trainer.
 */
public class EditTrainer {
    
//    static DB data = DB.getInstance();                              //This is the program's database.
    
    
    /**
     * The method shows the view and starts the trainer's edit procedure 
     */
    public static void show(){
        if(Trainer.getAllTrainers().size()>0){
            Style.addLines(1);
            boolean createMore = true;
            while(createMore == true){    
                chooseEditor();                                                 //Lets user to choose what to edit.
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("DO YOU WANT TO EDIT ANOTHER TRAINER?"));//asks the user, if he wants to edit another TRAINER.
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                boolean more = Input.giveYesOrNo();

                if( more == true){
                    Style.addLines(1);
                    System.out.println(Style.green("LET'S EDIT THE NEXT TRAINER!"));
                    Style.addLines(1);}
                else{
                    System.out.println(Style.green("THE TRAINER'S EDITING PROGRESS IS OVER!"));
                    createMore = false;
                }    
            }
        }
        else{
            System.out.println(Style.red("THERE ARE NO TRAINERS TO EDIT. PLEASE ADD TRAINERS FIRST IN DATABASE!!!"));
        }
    }
    
    
    /**
     * Lets the user to choose what Trainer's field want to edit, and calls the 
     * right field editor.
     */
    private static void chooseEditor(){
        
      
        
        Trainer chosenTrainer = OtherViews.trainerChoose.chooseOne(Trainer.getAllTrainers()); // Lets the user to choose one trainer
        
        Style.addLines(1);
        boolean stayInThisTrainerEditor = true;                                 //while stayInThisStudentEditor is true, the program runs the while loop to let the user
                                                                                //choose what to edit. when user gives the 6th option, chooseEditor() ends and returns to show() method.
        while(stayInThisTrainerEditor){
            Style.addLines(1);
            
    //prints trainer's details. Every time user edits something and comes back, the program prints the new details.
            System.out.println(Style.yellow(Style.createFormat(new int[]{20,20,30},
                                new String[]{"FIRST NAME", "LAST NAME", "SUBJECT"})));
            
            Style.printFormatted(new int[]{20, 20, 30},
                                new String[]{ chosenTrainer.getFirstName(), chosenTrainer.getLastName(), chosenTrainer.getSubject()});
            
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO CHANGE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - EDIT TRAINER'S FIRST NAME");
            System.out.println(Style.yellow("2")+" - EDIT TRAINER'S LAST NAME");
            System.out.println(Style.yellow("3")+" - EDIT TRAINER'S SUBJECT");
            System.out.println(Style.red("4 - DELETE TRAINER"));
            System.out.println(Style.green("5 - CANCEL"));
            Style.addUnderline();
            int correctInput = Input.giveOnlyInteger(1,6);                          //Asks the user to choose an integer among the options.
            switch(correctInput){
            
                case 1:
                    firstNameEditor(chosenTrainer);                                 //After the user's choice the method calls the right method.
                    Style.addLines(2);
                    break;
                    
                case 2:
                    lastNameEditor(chosenTrainer);
                    Style.addLines(2);
                    break;
                    
                case 3:
                    subjectEditor(chosenTrainer);
                    break;
                    
                case 4:
                    deleteTrainer(chosenTrainer);
                    break;
                    
                case 5:
                    stayInThisTrainerEditor = false;                            //Program stops the while loop and asks the user
                    break;                                                      //if wants to edit another Trainer inside the show() method
                   
                 
                default:
                    System.out.println("A problem has occurred in EditStudent file, Line 104");;
            }
        }
    }
    
    
    /**
     * Takes a student and lets User to changes it's firstName
     * @param student 
     */
    private static void firstNameEditor(Trainer trainer){
        
        String newFirstName;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT TRAINER'S FIRST NAME: "), trainer.getFirstName()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW FIRST NAME: "));
        newFirstName =Input.giveOnlyString();                                   //User gives the new First Name
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the First Name
        Style.addLines(2);
        
        if(confirmedChanges == true){
            trainer.setFirstName(newFirstName);
            trainer.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    /**
     * Takes a student and lets User to changes it's lastName
     * @param student 
     */
    private static void lastNameEditor(Trainer trainer){
        
        String newLastName;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT TRAINER'S LAST NAME: "), trainer.getLastName()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW LAST NAME: "));
        newLastName =Input.giveOnlyString();                                    //User gives the new Last Name
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Last Name
        Style.addLines(2);
        
        if(confirmedChanges == true){
            trainer.setLastName(newLastName);
            trainer.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    
     /**
     * Takes a student and lets User to changes it's lastName
     * @param student 
     */
    private static void subjectEditor(Trainer trainer){
        
        String newSubject;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT TRAINER'S SUBJECT: "), trainer.getSubject()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW SUBJECT: "));
        newSubject =Input.giveOnlyString();                                     //User gives the new subject
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the subject
        Style.addLines(2);
        
        if(confirmedChanges == true){
            trainer.setSubject(newSubject);
            trainer.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Tells to database to delete the trainer
     * @param trainer 
     */
    private static void deleteTrainer(Trainer trainer){
        
        boolean result = OtherViews.confirmChanges();                           //User confirms that wants to delete the Trainer
        
        if(result == true){
            Trainer.delete(trainer);
            System.out.println(Style.green("The trainer deleted!"));
            ObjectMenu.showFor(GenericDao.viewFor.EDIT);
        }
        
    }
    
    
}
