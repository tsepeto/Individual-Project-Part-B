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
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.Trainer;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 */
public class EditCourse {
    
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    /**
     * The method shows the view and starts the course's edit procedure 
     */
    public static void show(){
        if(Course.getAllCourses().size()>0){
            Style.addLines(1);
            boolean createMore = true;
            while(createMore == true){    
                chooseEditor();                                                     //Lets user to choose what to edit.
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("DO YOU WANT TO EDIT ANOTHER COURSE?"));//asks the user, if he wants to edit another course.
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                boolean more = Input.giveYesOrNo();

                if( more == true){
                    Style.addLines(1);
                    System.out.println(Style.green("LET'S EDIT THE NEXT COURSE!"));
                    Style.addLines(1);}
                else{
                    System.out.println(Style.green("THE COURSE'S EDITING PROGRESS IS OVER!"));
                    createMore = false;
                }    
            }
        }
        else{
            System.out.println(Style.red("THERE ARE NO COURSES TO EDIT. PLEASE ADD COURSES FIRST IN DATABASE!!!"));
        }
    }
    
    
    /**
     * Lets the user to choose what Course's field want to edit, and calls the 
     * right field editor.
     */
    private static void chooseEditor(){
        
      
        
        Course chosenCourse = OtherViews.courseChoose.chooseOne(Course.getAllCourses());// Lets the user to choose one course
        
        Style.addLines(1);
        boolean stayInThisCOurseEditor = true;                                  //while stayInThisCourseEditor is true, the program runs the while loop to let the user
                                                                                //choose what to edit. when user gives the 11th option, chooseEditor() ends and returns to show() method.
        while(stayInThisCOurseEditor){
            Style.addLines(1);
            
    //prints course's details. Every time user edits something and comes back, the program prints the new details.
            System.out.println(Style.yellow(Style.createFormat(new int[]{30, 20, 20, 12, 3, 12},
                                new String[]{"TITLE", "STREAM", "TYPE","START DATE","","END DATE"})));
            
            Style.printFormatted(new int[]{30, 20, 20, 12, 3, 12},
                                new String[]{ chosenCourse.getTitle(), chosenCourse.getStream(), chosenCourse.getType(),
                                    chosenCourse.getStart_date().format(DateTimeFormatter.ofPattern(data.daTiFormat)), "-", chosenCourse.getEnd_date().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
            
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO CHANGE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - EDIT COURSE'S TITLE");
            System.out.println(Style.yellow("2")+" - EDIT COURSE'S STREAM");
            System.out.println(Style.yellow("3")+" - EDIT COURSE'S TYPE");
            System.out.println(Style.yellow("4")+" - EDIT COURSE'S START DATE");
            System.out.println(Style.yellow("5")+" - EDIT COURSE'S END DATE");
            System.out.println(Style.yellow("6")+" - ADD STUDENT IN COURSE");
            System.out.println(Style.yellow("7")+" - REMOVE STUDENT FROM COURSE");
            System.out.println(Style.yellow("8")+" - ADD TRAINER IN COURSE");
            System.out.println(Style.yellow("9")+" - REMOVE TRAINER FROM COURSE");
            System.out.println(Style.red("10 - DELETE COURSE"));
            System.out.println(Style.green("11 - GO BACK"));
            Style.addUnderline();
            int correctInput = Input.giveOnlyInteger(1,11);                          //Asks the user to choose an integer among the options.
            switch(correctInput){
                case 1:
                    titleEditor(chosenCourse);                                 //After the user's choice the method calls the right method.
                    Style.addLines(2);
                    break;
                case 2:
                    streamEditor(chosenCourse);
                    Style.addLines(2);
                    break;
                case 3:
                    typeEditor(chosenCourse);
                    break;
                case 4:
                    startDateEditor(chosenCourse);
                    break;
                case 5:
                    endDateEditor(chosenCourse);
                    break;
                case 6:
                    addStudent(chosenCourse);
                    break;
                case 7:
                    removeStudent(chosenCourse);
                    break;
                case 8:
                    addTrainer(chosenCourse);
                    break;
                case 9:
                    removeTrainer(chosenCourse);
                    break;
                case 10:
                    boolean deleted = deleteCourse(chosenCourse);               //Start the deletion procedure. If the method deletes the course
                    if(deleted){stayInThisCOurseEditor = false;}                //returns true, and leaves the course editor.
                    break;
                case 11:
                    stayInThisCOurseEditor = false;                             //Program stops the while loop and asks the user System.out.println("9");
                    break;                                                      //if wants to edit another Course inside the show() method
                   
                 
                default:
                    System.out.println("A problem has occurred in EditStudent file, Line 104");;
            }
        }
    }
    
    
    
    /**
     * Takes a course and lets the User to change it's title
     * @param course 
     */
    private static void titleEditor(Course course){
        
        String newTitle;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT COURSE'S TITLE: "), course.getTitle()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW TITLE: "));
        newTitle =Input.input.nextLine();                                           //User gives the new Title
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Title
        Style.addLines(2);
        
        if(confirmedChanges == true){
            course.setTitle(newTitle);
            course.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes a course and lets the User to change it's stream
     * @param course 
     */
    private static void streamEditor(Course course){
        
        String newStream;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,40}, new String[]{Style.yellow("CURRENT COURSE'S STREAM: "), course.getStream()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW STREAM: "));
        newStream =Input.input.nextLine();                                          //User gives the new Stream
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Stream
        Style.addLines(2);
        
        if(confirmedChanges == true){
            course.setStream(newStream);
            course.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    
    /**
     * Takes a course and lets the User to change it's type
     * @param course 
     */
    private static void typeEditor(Course course){
        
        String newType;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,40}, new String[]{Style.yellow("CURRENT COURSE'S TYPE: "), course.getType()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW TYPE: "));
        newType =Input.input.nextLine();                                            //User gives the new Type
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Type
        Style.addLines(2);
        
        if(confirmedChanges == true){
            course.setType(newType);
            course.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes a course and lets the User to change it's Start Date
     * @param course 
     */
    private static void startDateEditor(Course course){
        
        LocalDate newStartDate;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,15}, new String[]{Style.yellow("CURRENT COURSE'S START DATE: "), course.getStart_date().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW START DATE: "));
        newStartDate =Input.giveADate();                                        //User gives the new Start Date
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Start Date
        Style.addLines(2);
        
        if(confirmedChanges == true){
            course.setStart_date(newStartDate);
            course.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    /**
     * Takes a course and lets the User to change it's End Date
     * @param course 
     */
    private static void endDateEditor(Course course){
        
        LocalDate newEndDate;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,15}, new String[]{Style.yellow("CURRENT COURSE'S END DATE: "), course.getEnd_date().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW END DATE: "));
        newEndDate =Input.giveADate();                                          //User gives the new End Date
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the End Date
        Style.addLines(2);
        
        if(confirmedChanges == true){
            course.setEnd_date(newEndDate);
            course.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes a course and lets the User to add a student
     * @param course 
     */
    private static void addStudent(Course course){
    
        ArrayList<Student> studentsAreNotInCourse = course.getUnregisteredStudents(); //the database returs all students who are not associated with that course
        
        if(studentsAreNotInCourse.size()>0){                                    //checks if there are more students to add in course.
            boolean keepAsk = true;
            while(keepAsk == true){
                Style.addLines(1);
                System.out.println(Style.yellow("LET'S ADD THE STUDENT"));
                Student student = OtherViews.studentChoose.chooseOne(studentsAreNotInCourse);
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    course.addStudent(student);                   //adds the student in the course
                    studentsAreNotInCourse.remove(student);                     //removes the current student from studentsAreNotInCourse list
                    System.out.println(Style.green("STUDENT IS NOW IN COURSE"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }

                Style.addLines(1);
                if(studentsAreNotInCourse.size()>0){                            //checks if there are more students to add in course.
                    System.out.println(Style.yellow("DO YOU WANT TO ADD ANOTHER STUDENT?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        System.out.println(Style.green("LET'S ADD ANOTHER STUDENT"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
                else{                                                           //If there are no more students then stops the while.
                    System.out.println(Style.red("THERE ARE NO OTHER STUDENTS IN DATABASE THAT YOU CAN ADD IN THE COURSE."));
                    keepAsk=false;
                }

            }
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO OTHER STUDENTS IN DATABASE THAT YOU CAN ADD IN THE COURSE."));
        }
    }
    
    
    
    /**
     * Takes a course and lets the User to remove a student
     * @param course 
     */
    private static void removeStudent(Course course){
    
        
        boolean keepAsk = true;
        while(keepAsk == true){
            if(0<course.getStudents().size()){                    //Checks if course's student list is no empty
                
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("LET'S REMOVE THE STUDENT"));
                Student student = OtherViews.studentChoose.chooseOne(course.getStudents());
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    course.removeStudent(student);                              //removes the student from the course
                    System.out.println(Style.green("STUDENT IS NOW OUT OF COURSE"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }
                Style.addLines(1);
                if(course.getStudents().size()>0){
                    System.out.println(Style.yellow("DO YOU WANT TO REMOVE ANOTHER STUDENT?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        Style.addLines(1);
                        System.out.println(Style.green("LET'S REMOVE ANOTHER STUDENT"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
            }
            else{
                Style.addLines(1);
                System.out.println(Style.red("THE COURSE HAS NO OTHER STUDENT TO REMOVE")); //If course's studen list is empty then breaks the wile loop.
                keepAsk=false;
            }
        }
    }
    
    
    
     /**
     * Takes a course and lets the User to add a trainer
     * @param course 
     */
    private static void addTrainer(Course course){
    
        ArrayList<Trainer> trainersAreNotInCourse = course.getUnregisteredTrainers();//the database returs all trainers who are not associated with that course
        
        if(trainersAreNotInCourse.size()>0){                                    //checks if there are more trainers to add in course.
            boolean keepAsk = true;
            while(keepAsk == true){
                Style.addLines(1);
                System.out.println(Style.yellow("LET'S ADD THE TRAINER"));
                Trainer trainer = OtherViews.trainerChoose.chooseOne(trainersAreNotInCourse);
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    course.addTrainer(trainer);                                 //adds the trainer in the course
                    trainersAreNotInCourse.remove(trainer);                     //removes the current trainer from trainersAreNotInCourse list
                    System.out.println(Style.green("TRAINER IS NOW IN COURSE"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }

                Style.addLines(1);
                if(trainersAreNotInCourse.size()>0){                            //checks if there are more trainers to add in course.
                    System.out.println(Style.yellow("DO YOU WANT TO ADD ANOTHER TRAINER?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        System.out.println(Style.green("LET'S ADD ANOTHER TRAINER"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
                else{                                                           //If there are no more trainers then stops the while.
                    System.out.println(Style.red("THERE ARE NO OTHER TRAINERS IN DATABASE THAT YOU CAN ADD IN THE COURSE."));
                    keepAsk=false;
                }

            }
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO OTHER TRAINERS IN DATABASE THAT YOU CAN ADD IN THE COURSE."));
        }
    }
    
    
    
    
    
     /**
     * Takes a course and lets the User to remove a trainer
     * @param course 
     */
    private static void removeTrainer(Course course){
    
        
        boolean keepAsk = true;
        while(keepAsk == true){
            if(0<course.getTrainers().size()){                                  //Checks if course's trainer list is no empty
                
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("LET'S REMOVE THE TRAINER"));
                Trainer trainer = OtherViews.trainerChoose.chooseOne(course.getTrainers());
                boolean confirmedChanges = OtherViews.confirmChanges();         //User confirms that wants to save the changes
                if(confirmedChanges == true){
                    course.removeTrainer(trainer);                              //adds the trainer in the course
                    System.out.println(Style.green("TRAINER IS NOW OUT OF COURSE"));
                }
                else{
                    Style.addLines(1);
                    System.out.println(Style.red("YOU CANCELED THE PROCEDURE"));
                }
                Style.addLines(1);
                if(course.getTrainers().size()>0){
                    System.out.println(Style.yellow("DO YOU WANT TO REMOVE ANOTHER TRAINER?"));
                    Style.addUnderline();
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean answer = Input.giveYesOrNo();

                    if(answer == true){
                        Style.addLines(1);
                        System.out.println(Style.green("LET'S REMOVE ANOTHER TRAINER"));
                    }
                    else{
                        keepAsk=false;
                    }

                }
            }
            else{
                Style.addLines(1);
                System.out.println(Style.red("THE COURSE HAS NO OTHER TRAINER TO REMOVE")); //If course's trainer list is empty then breaks the wile loop.
                keepAsk=false;
            }
        }
    }
    
    
   
    
    /**
     * Deletes the given course from the database.If the curse will be removed then 
     * the method returns true. Else false.
     * @param course 
     */
    private static boolean deleteCourse(Course course){
        System.out.println(Style.yellow("ARE YOU SURE THAT YOU WANT TO DELETE THIS COURSE?"));
        Style.addUnderline();
        System.out.println(Style.green("1 - YES"));
        System.out.println(Style.red("2 - NO"));
        boolean answer = Input.giveYesOrNo();

        if(answer == true){
            Course.delete(course);
            return true;
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE DELETION"));
        }
        return false;
    }
    
    
}
