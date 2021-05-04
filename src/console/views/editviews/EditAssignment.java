/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.views.editviews;

import console.utils.Input;
import console.utils.Style;
import console.views.menuviews.OtherViews;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 */
public class EditAssignment {
    
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    
    /**
     * The method shows the view and starts the assignment's edit procedure 
     */
    public static void show(){
        Style.addLines(1);
        if(Assignment.getAllAssignments().size()>0){
            boolean createMore = true;
            while(createMore == true){    
                chooseEditor();                                                     //Lets user to choose what to edit.
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("DO YOU WANT TO EDIT ANOTHER ASSIGNMENT?"));//asks the user, if he wants to edit another assignment.
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                boolean more = Input.giveYesOrNo();

                if( more == true){
                    Style.addLines(1);
                    System.out.println(Style.green("LET'S EDIT THE NEXT ASSIGNMENT!"));
                    Style.addLines(1);}
                else{
                    System.out.println(Style.green("THE ASSIGNMENT'S EDITING PROGRESS IS OVER!"));
                    createMore = false;
                }    
            }
        
        }
        else{
            System.out.println(Style.red("THERE ARE NO ASSIGNMENTS TO EDIT. PLEASE ADD ASSIGNMENTS FIRST IN DATABASE!!!"));
            
        }
    }
    
    
    
    /**
     * Lets the user to choose what assignment's field want to edit, and calls the 
     * right field editor.
     */
    private static void chooseEditor(){
        
        Assignment chosenAssignment = OtherViews.assignmentChoose.chooseOne(Assignment.getAllAssignments()); // Lets the user to choose one assignment
        Style.addLines(1);
        boolean stayInThisAssignmentEditor = true;                                 //while stayInThisAssignmentEditor is true, the program runs the while loop to let the user
                                                                                //choose what to edit. when user gives the 68th option, chooseEditor() ends and returns to show() method.
        while(stayInThisAssignmentEditor){
            Style.addLines(1);

    //prints course's details. Every time user edits something and comes back, the program prints the new details.
            System.out.println(Style.yellow(Style.createFormat(new int[]{30, 55, 15, 15, 15},
                                new String[]{"TITLE", "DESCRIPTION", "DEADLINE","ORAL MARK","TOTAL MARK"})));

            Style.printFormatted(new int[]{30, 55, 15, 15, 15},
                                new String[]{ chosenAssignment.getTitle(), chosenAssignment.getDescription(), chosenAssignment.getSubDateTime().format(DateTimeFormatter.ofPattern(data.daTiFormat)),
                                            ""+chosenAssignment.getOralMark(), ""+chosenAssignment.getTotalMark()});

            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO CHANGE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - EDIT ASSIGNMENT'S TITLE");
            System.out.println(Style.yellow("2")+" - EDIT ASSIGNMENT'S DESCRIPTION");
            System.out.println(Style.yellow("3")+" - EDIT ASSIGNMENT'S DEADLINE");
            System.out.println(Style.yellow("4")+" - EDIT ASSIGNMENT'S ORAL MARK");
            System.out.println(Style.yellow("5")+" - EDIT ASSIGNMENT'S TOTAL MARK");
            System.out.println(Style.yellow("6")+" - ADD COURSE IN ASSIGNMENT");
            System.out.println(Style.yellow("7")+" - REMOVE COURSE FROM ASSIGNMENT");
            System.out.println(Style.red("8 - DELETE ASSIGNMENT"));
            System.out.println(Style.green("9 - GO BACK"));
            Style.addUnderline();
            int correctInput = Input.giveOnlyInteger(1,9);                      //Asks the user to choose an integer among the options.
            switch(correctInput){
                case 1:
                    titleEditor(chosenAssignment);                              //After the user's choice the method calls the right method.
                    Style.addLines(2);
                    break;
                case 2:
                    descriptionEditor(chosenAssignment);
                    Style.addLines(2);
                    break;
                case 3:
                    subDateTimeEditor(chosenAssignment);
                    break;
                case 4:
                    oralMarkEditor(chosenAssignment);
                    break;
                case 5:
                    totalMarkEditor(chosenAssignment);
                    break;
                case 6:
                    addCourse(chosenAssignment);
                    break;
                case 7:
                    removeCourse(chosenAssignment);
                    break;
                case 8:
                    boolean deleted = deleteAssignment(chosenAssignment);       //Start the deletion procedure. If the method deletes the assignment
                    if(deleted){stayInThisAssignmentEditor = false;}            //returns true, and leaves the course editor.
                    break;
                case 9:
                    stayInThisAssignmentEditor = false;                         //Program stops the while loop if user press ("9")
                    break;                                                      //if wants to edit another Course inside the show() method


                default:
                    System.out.println("A problem has occurred in EditAssignment file, Line 119");
                }    
            }

    }
    
    
    
    /**
     * Takes an assignment and lets the User to change it's title
     * @param assignment 
     */
    private static void titleEditor(Assignment assignment){
        
        String newTitle;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,30}, new String[]{Style.yellow("CURRENT ASSIGNMENT'S TITLE: "), assignment.getTitle()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW TITLE: "));
        newTitle =Input.input.nextLine();                                       //User gives the new Title
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Title
        Style.addLines(2);
        
        if(confirmedChanges == true){
            assignment.setTitle(newTitle);
            assignment.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    /**
     * Takes an assignment and lets the User to change it's stream
     * @param assignment 
     */
    private static void descriptionEditor(Assignment assignment){
        
        String newDescription;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,55}, new String[]{Style.yellow("CURRENT ASSIGNMENT'S DESCRIPTION: "), assignment.getDescription()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW DESCRIPTION: "));
        newDescription =Input.input.nextLine();                                     //User gives the new Description
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the description
        Style.addLines(2);
        
        if(confirmedChanges == true){
            assignment.setDescription(newDescription);
            assignment.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    
    /**
     * Takes an assignment and lets the User to change it's deadline
     * @param assignment 
     */
    private static void subDateTimeEditor(Assignment assignment){
        
        LocalDate newsSubDateTime;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,15}, new String[]{Style.yellow("CURRENT ASSIGNMENT'S DEADLINE: "), assignment.getSubDateTime().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW DEADLINE: "));
        newsSubDateTime =Input.giveADate();                                     //User gives the new deadline
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the deadline
        Style.addLines(2);
        
        if(confirmedChanges == true){
            assignment.setSubDateTime(newsSubDateTime);
            assignment.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes an assignment and lets the User to change it's oral mark
     * @param assignment 
     */
    private static void oralMarkEditor(Assignment assignment){
        
        int newsOralMark;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,5}, new String[]{Style.yellow("CURRENT ASSIGNMENT'S ORAL MARK: "), ""+assignment.getOralMark()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW ORAL MARK: "));
        newsOralMark =Input.giveOnlyInteger(0, 100);                            //User gives the new Oral Mark
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Oral Mark
        Style.addLines(2);
        
        if(confirmedChanges == true){
            assignment.setOralMark(newsOralMark);
            assignment.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    
    /**
     * Takes an assignment and lets the User to change it's total mark
     * @param assignment 
     */
    private static void totalMarkEditor(Assignment assignment){
        
        int newsTotalMark;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,5}, new String[]{Style.yellow("CURRENT ASSIGNMENT'S TOTAL MARK: "), ""+assignment.getTotalMark()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW TOTAL MARK: "));
        newsTotalMark =Input.giveOnlyInteger(0, 100);                           //User gives the new Total Mark
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Total Mark
        Style.addLines(2);
        
        if(confirmedChanges == true){
            assignment.setTotalMark(newsTotalMark);
            assignment.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes an assignment and lets the User to add a course
     * @param course 
     */
    private static void addCourse(Assignment assignment){
    
        
        ArrayList<Course> coursesAreNotInAssignment = assignment.getUnregisteredCourses();//the database returs all courses that are not associated with that assignment
        
        if(coursesAreNotInAssignment.size()>0){                                 //checks if there are more courses to add in assignment.
            boolean keepAsk = true;
            while(keepAsk == true){
                Style.addLines(1);
                System.out.println(Style.yellow("LET'S ADD THE COURSE"));
                Course course = OtherViews.courseChoose.chooseOne(coursesAreNotInAssignment);
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    assignment.addCourse(course);                               //adds the course in the assignment
                    coursesAreNotInAssignment.remove(course);                   //removes the current course from coursesAreNotInAssignment list
                    System.out.println(Style.green("COURSE IS NOW IN ASSIGNMENT"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }

                Style.addLines(1);
                if(coursesAreNotInAssignment.size()>0){                         //checks if there are more courses to add in assignment.
                    System.out.println(Style.yellow("DO YOU WANT TO ADD ANOTHER COURSE?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        System.out.println(Style.green("LET'S ADD ANOTHER COURSE"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
                else{                                                           //If there are no more courses then stops the while.
                    System.out.println(Style.red("THERE ARE NO OTHER COURSES IN DATABASE THAT YOU CAN ADD IN THE ASSIGNMENT."));
                    keepAsk=false;
                }

            }
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO OTHER COURSES IN DATABASE THAT YOU CAN ADD IN THE ASSIGNMENT."));
        }
    }
    
    
    
    /**
     * Takes an assignment and lets the User to remove a course
     * @param assignment 
     */
    private static void removeCourse(Assignment assignment){
    
        
        boolean keepAsk = true;
        while(keepAsk == true){
            if(0<assignment.getCourses().size()){                               //Checks if assignment's course list is no empty
                
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("LET'S REMOVE THE COURSE"));
                Course course = OtherViews.courseChoose.chooseOne(assignment.getCourses());
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    assignment.removeCourse(course);                            //removes the course from the assignment
                    System.out.println(Style.green("COURSE IS NOW OUT OF ASSIGNMENT"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }
                Style.addLines(1);
                if(assignment.getCourses().size()>0){
                    System.out.println(Style.yellow("DO YOU WANT TO REMOVE ANOTHER COURSE?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        Style.addLines(1);
                        System.out.println(Style.green("LET'S REMOVE ANOTHER COURSE"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
            }
            else{
                Style.addLines(1);                                              //If assignment's course list is empty then breaks the wile loop.
                System.out.println(Style.red("THE ASSIGNMENT HAS NO OTHER COURSE TO REMOVE"));
                keepAsk=false;
            }
        }
    }
    
    
    
    /**
     * Deletes the given Assignment from the database.If the assignment will be removed then 
     * the method returns true. Else false.
     * @param assignment 
     */
    private static boolean deleteAssignment(Assignment assignment){
        System.out.println(Style.yellow("ARE YOU SURE THAT YOU WANT TO DELETE THIS ASSIGNMENT?"));
        Style.addUnderline();
        System.out.println(Style.green("1 - YES"));
        System.out.println(Style.red("2 - NO"));
        boolean answer = Input.giveYesOrNo();

        if(answer == true){
            Assignment.delete(assignment);
            return true;
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE DELETION"));
        }
        return false;
    }
    
    
}
