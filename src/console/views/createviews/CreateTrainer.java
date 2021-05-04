
package console.views.createviews;

import console.utils.Style;
import console.utils.Input;
import sCOOL.entity.Trainer;

/**
 *
 * @author tsepe
 * CreateTrainer is a View that the program calls it every time the user
 * chooses to create a new trainer.
 */
public class CreateTrainer {
    

    
    /**
     * The method shows the view and starts the trainer's create procedure 
     */
     public static void show(){
        Style.addLines(1);
        boolean createMore = true;
        while(createMore == true){    
            trainerCreator();                                                   //starts the trainer's creation procedure.
            Style.addLines(1);
            Style.addUnderline();
            System.out.println(Style.yellow("DO YOU WANT TO CREATE ANOTHER TRAINER?"));//asks the user, if he want to create another trainer.
            System.out.println(Style.yellow("1")+" - YES");
            System.out.println(Style.yellow("2")+" - NO");
            Style.addUnderline();
            boolean more = Input.giveYesOrNo();
            
            if( more == true){
                Style.addLines(1);
                System.out.println(Style.green("LET'S CREATE THE NEXT TRAINER!"));
                Style.addLines(1);}
            else{
                System.out.println(Style.green("THE TRAINER'S CREATION PROGRESS IS OVER!"));
                createMore = false;
            }    
        }
    }
    
     
     
    /**
     * The method trainerCreator() is the trainer creation procedure
     */
    private static void trainerCreator(){
        String firstName;
        String lastName;
        String subject;
        Trainer trainer;
        boolean trainerSaved = false;
        
        while(trainerSaved == false){
            System.out.println(Style.yellow("GIVE THE TRAINER'S FIRST NAME:"));
            firstName = Input.giveOnlyString();
            System.out.println(Style.yellow("GIVE THE STUDENT'S LAST NAME:"));
            lastName = Input.giveOnlyString();
            System.out.println(Style.yellow("GIVE THE TRAINERS'S SUBJECT:"));
            subject = Input.input.next(); 
            
            
            trainer = new Trainer(firstName,lastName,subject);                  // Creates a temporary trainer.
            if (!Trainer.exists(trainer)){                                      // Checks if the trainer allready exists,
                Trainer.saveTrainer(trainer);                                   // if not saves it in the database.
                trainerSaved = true;                                            // Here it stops the while loop.
            }
            else{
                Style.addLines(1);                                              // If trainer allready exists, the method starts again the trainer's creation procedure.
                System.out.println(Style.red("THIS TRAINER ALLREADY EXISTS,PLEASE CREATE ANOTHER TRAINER."));
                break;}
        }
       
    }
}
