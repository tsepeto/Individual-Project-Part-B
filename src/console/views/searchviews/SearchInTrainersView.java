/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.views.searchviews;


import console.utils.Style;
import console.utils.Input;
import console.views.menuviews.ObjectMenu;
import console.views.menuviews.OtherViews;
import java.util.ArrayList;
import sCOOL.entity.Course;
import sCOOL.entity.Trainer;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * 
 * In that View the user chooses what we want to search in Trainers
 */
public class SearchInTrainersView {
    
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    /**
     * Shows the View and ask the user to choose what to ask for the trainers
     */
    public static void show(){
        while(true){
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO SEE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - SHOW ALL THE TRAINERS");
            System.out.println(Style.yellow("2")+" - SHOW A TRAINER'S DETAILS");
            System.out.println(Style.yellow("3")+" - BACK ");
            Style.addUnderline();
            Style.addLines(1);

            int input = Input.giveOnlyInteger(1, 3);

            switch(input){
                case 1:
                    showTrainers();
                    break;

                case 2:
                    showTrainerDetails();
                    break;

                case 3:
                    ObjectMenu.showFor(GenericDao.viewFor.SEARCH);
                    break;

            }
        }
    }
     
    
    /**
     * Prints all the trainers we have in database
     * @param trainers list
     */
    private static void printTrainers(ArrayList<Trainer> trainers){
        Style.addLines(1);
        System.out.println(Style.yellow("TRAINERS"));
        Style.addUnderline();
        int counter = 1;
        
        for(Trainer trainer:trainers){
            System.out.println(Style.yellow(counter + " - ") + trainer);
            counter+=1;
        }
        Style.addUnderline();
        Style.addLines(1);
    }
    
      
    /**
    * The method prints trainers and waits the user to give something to go
    * to the previous view
    */
    private static void showTrainers(){
        if(Trainer.getAllTrainers().size()>0){
            printTrainers(Trainer.getAllTrainers());
            Input.pressToGoBack();
        }
        else{
            System.out.println(Style.red("THERE ARE NO TRAINERS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }
    }
    
    
    /**
    * The method prints the details of the chosen Train
    */
    private static void showTrainerDetails(){
        if(Trainer.getAllTrainers().size()>0){
            Trainer trainer = OtherViews.trainerChoose.chooseOne(Trainer.getAllTrainers()); //The user chooses Trainer
            ArrayList<Course> courses = trainer.getCourses();                   //the program finds the trainer's courses


            Style.addLines(1);
            System.out.println(Style.yellow("TRAINER DETAILS"));
            Style.addUnderline();
            Style.printFormatted(new int[]{40, 30}, new String[]{Style.yellow("FIRST NAME: "), trainer.getFirstName()});
            Style.printFormatted(new int[]{40, 30}, new String[]{Style.yellow("LAST NAME: "), trainer.getLastName()});
            Style.printFormatted(new int[]{40, 30}, new String[]{Style.yellow("SUBJECT: "), trainer.getSubject()});

            Style.addUnderline();
            System.out.println(Style.yellow("COURSES :") + courses.size());
            for(Course course:courses){
                Style.printFormatted(new int[]{5, 30, 20, 20}, new String[]{Style.yellow(" - "), course.getTitle(), course.getStream(), course.getType()});
            }

            Style.addUnderline();
            Style.addLines(1);
            Input.pressToGoBack();
        }
        else{
            System.out.println(Style.red("THERE ARE NO TRAINERS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }    
    }
}
