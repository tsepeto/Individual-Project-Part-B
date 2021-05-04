
package console.views.editviews;

import console.utils.Input;
import console.utils.Style;
import console.views.menuviews.ObjectMenu;
import console.views.menuviews.OtherViews;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.StudentGrade;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * EditStudent is the View that the program calls every time the user
 * chooses to edit a student.
 */
public class EditStudent {
    
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    /**
     * The method shows the view and starts the student's edit procedure 
     */
    public static void show(){
        if(Student.getAllStudents().size()>0){
            Style.addLines(1);
            boolean createMore = true;
            while(createMore == true){    
                chooseEditor();                                                 //Lets user to choose what to edit.
                Style.addLines(1);
                Style.addUnderline();
                System.out.println(Style.yellow("DO YOU WANT TO EDIT ANOTHER STUDENT?"));//asks the user, if he wants to edit another Student.
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                boolean more = Input.giveYesOrNo();

                if( more == true){
                    Style.addLines(1);
                    System.out.println(Style.green("LET'S EDIT THE NEXT STUDENT!"));
                    Style.addLines(1);}
                else{
                    System.out.println(Style.green("THE STUDENT'S EDITING PROGRESS IS OVER!"));
                    createMore = false;
                }    
            }
        }
        else{
            System.out.println(Style.red("THERE ARE NO STUDENTS TO EDIT. PLEASE ADD STUDENTS FIRST IN DATABASE!!!"));
        }
    }
    
    
    /**
     * Lets the user to choose what Student's field want to edit, and calls the 
     * right field editor.
     */
    private static void chooseEditor(){
        
      
        
        Student chosenStudent = OtherViews.studentChoose.chooseOne(Student.getAllStudents()); // Lets the user to choose one student
        
        
        boolean stayInThisStudentEditor = true;                                 //while stayInThisStudentEditor is true, the program runs the while loop to let the user
                                                                                //choose what to edit. when user gives the 6th option, chooseEditor() ends and returns to show() method.
        while(stayInThisStudentEditor){
            
            //prints student's details. Every time user edits something and comes back, the program prints the new details.
            System.out.println(Style.yellow(Style.createFormat(new int[]{15,20,15,3,12},
                                new String[]{"FIRST NAME", "LAST NAME", "DATE OF BIRTH", "", "TUITION FEES"})));
            
            Style.printFormatted(new int[]{15, 20, 15, 3, 12},
                                new String[]{ chosenStudent.getFirstName(), chosenStudent.getLastName(), chosenStudent.getDateOfBirth().format(DateTimeFormatter.ofPattern(data.daTiFormat)),
                                             "-", Double.toString(chosenStudent.getTuitionFees())});
            
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO CHANGE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - RATE STUDENT'S ASSIGNMENT");
            System.out.println(Style.yellow("2")+" - EDIT STUDENT'S FIRST NAME");
            System.out.println(Style.yellow("3")+" - EDIT STUDENT'S LAST NAME");
            System.out.println(Style.yellow("4")+" - EDIT STUDENT'S DATE OF BIRTH");
            System.out.println(Style.yellow("5")+" - TUITION FEES");
            System.out.println(Style.yellow("6")+" - EDIT STUDENT'S ASSIGNMENT");
            System.out.println(Style.red("7 - DELETE STUDENT"));
            System.out.println(Style.green("8 - GO BACK"));
            Style.addUnderline();
            int correctInput = Input.giveOnlyInteger(1,8);                          //Asks the user to choose an integer among the options.
            switch(correctInput){
            
                case 1:                                                         //After the user's choice the method calls the right method.
                    rateStudentGrade(chosenStudent);                                
                    Style.addLines(2);
                    break;
            
                case 2:
                    firstNameEditor(chosenStudent);                             
                    Style.addLines(2);
                    break;
                    
                case 3:
                    lastNameEditor(chosenStudent);
                    Style.addLines(2);
                    break;
                    
                case 4:
                    dateOfBirthEditor(chosenStudent);
                    break;
                    
                case 5:
                    tuitionFeesEditor(chosenStudent);
                    break;
                    
                case 6:
                    editStudentGrade(chosenStudent);
                    break;
                    
                case 7:
                    deleteStudent(chosenStudent);
                    break;
                    
                case 8:
                    stayInThisStudentEditor = false;                            //Program stops the while loop and asks the user
                    break;                                                      //if wants to edit another Student inside the show() method
                    
                default:
                    System.out.println("A problem has occurred in EditStudent file, Line 104");;
            }
        }
    }
    
   
    
    
    /**
     * Lets the User to  choose a student's course and finds the unrated student's
     * assignments.
     * Then lets the user choose the assignment that want to rate, and saves it
     * in database.
     * @param student 
     */
    private static void rateStudentGrade(Student student){
        
        if(student.getCourses().size()> 0){                      //Checks if the Student is registered to a course
            System.out.println(Style.yellow("PLEASE CHOOSE THE STUDENT'S COURSE FIRST"));
            Course course = OtherViews.courseChoose.chooseOne(student.getCourses());
            if(course.getAssignments().size()>0){
                ArrayList<Assignment> studentUnratedAssignments = student.getUnratedAssignmentsFromCourse(course); // Takes all unrated student assignments from a course.
                if(studentUnratedAssignments.size()>0){
                    System.out.println(Style.yellow("CHOOSE AN ASSIGNMENT:"));
                    Assignment assignment = OtherViews.assignmentChoose.chooseOne(studentUnratedAssignments);
                    Style.addLines(1);
                    System.out.println(Style.yellow("PLEASE GIVE THE ORAL MARK: "));
                    System.out.println("( 1 - "+ assignment.getOralMark() +" )");
                    int oralMark = Input.giveOnlyInteger(1, assignment.getOralMark());
                    Style.addLines(1);
                    System.out.println(Style.yellow("PLEASE GIVE THE TOTAL MARK:"));
                    System.out.println("( 1 - "+ assignment.getTotalMark() +" )");
                    int totalMark = Input.giveOnlyInteger(1, assignment.getTotalMark());
                    Style.addLines(1);
                    
                    System.out.println(Style.yellow("ARE YOU SURE THAT YOU WANT TO SAVE THE CHANGES?"));
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean sure = Input.giveYesOrNo();
                    
                    if(sure == true){
                        StudentGrade grade = new StudentGrade(student,assignment,course,oralMark,totalMark);
                        StudentGrade.saveStudentGrade(grade);
                        System.out.println(Style.green("YOU RATED THE STUDENT!!!"));
                    }
                    else{
                        System.out.println(Style.red("YOU CANCEL THE PROCEDURE!!!"));
                    }
                    
                }
                else{
                    System.out.println(Style.red("THERE ARE NO UNRATED ASSIGNMENTS FOR THIS STUDENT IN THAT COURSE!!!"));
                    System.out.println(Style.red("IF YOU WANT TO EDIT A MARK PLEASE GO TO EDIT MENU!!!"));
                    Input.pressToGoBack();
                }
            }
            else{
                System.out.println(Style.red("THERE ARE NO ASSIGNMENTS FOR THIS COURSE!!!"));
                Input.pressToGoBack();
            }
        }
        else{
            System.out.println(Style.red("THE STUDENT IS NOT IN ANY COURSE. HE HAS TO REGISTER TO A COURSE FIRST!!!"));
        }
    }
    
    
    
    public static void editStudentGrade(Student student){
    
        if(student.getCourses().size()> 0){                      //Checks if the Student is registered to a course 
            System.out.println(Style.yellow("PLEASE CHOOSE THE STUDENT'S COURSE FIRST"));
            Course course = OtherViews.courseChoose.chooseOne(student.getCourses()); //User Chooses a course
            if(course.getAssignments().size()>0){                 //Checks if there are assignments in that course
                ArrayList<Assignment> studentRatedAssignments = student.getRatedAssignmentsFromCourse(course); // Takes all rated student assignments from a course.
                if(studentRatedAssignments.size()>0){                           //Checks if there are Student's rated Assignments
                    System.out.println(Style.yellow("CHOOSE AN ASSIGNMENT:"));
                    Assignment assignment = OtherViews.assignmentChoose.chooseOne(studentRatedAssignments); //User choose an assignment for edit
                    StudentGrade studentGrade = StudentGrade.getStudentGrade(student, course, assignment); //Data gives to the user the rated assignment
                    //prints the current assignment marks.
                    System.out.println(Style.createFormat(new int[] {60,10}, new String[] {Style.yellow("STUDENT'S CURENT ORAL MARK : "), studentGrade.getOralMark()+"/"+assignment.getOralMark()}));
                    System.out.println(Style.createFormat(new int[] {60,10}, new String[] {Style.yellow("STUDENT'S CURENT TOTAL MARK : "),studentGrade.getTotalMark()+"/"+assignment.getTotalMark()}));
                    Style.addLines(1);
                    System.out.println(Style.yellow("PLEASE GIVE THE NEW ORAL MARK: "));
                    System.out.println("( 1 - "+ assignment.getOralMark() +" )"); // Gives the new oral mark
                    int oralMark = Input.giveOnlyInteger(1, assignment.getOralMark());
                    Style.addLines(1);
                    System.out.println(Style.yellow("PLEASE GIVE THE TOTAL MARK:"));
                    System.out.println("( 1 - "+ assignment.getTotalMark() +" )");//Gives the new total mark
                    int totalMark = Input.giveOnlyInteger(1, assignment.getTotalMark());
                    Style.addLines(1);
                    
                    System.out.println(Style.yellow("ARE YOU SURE THAT YOU WANT TO SAVE THE CHANGES?"));
                    System.out.println(Style.green("1 - YES"));
                    System.out.println(Style.red("2 - NO"));
                    boolean sure = Input.giveYesOrNo();
                    
                    if(sure == true){                                           //If user wants to save the changes
                        studentGrade.setOralMark(oralMark);                     //The program changes the marks.
                        studentGrade.setTotalMark(totalMark);
                        studentGrade.update();
                        System.out.println(Style.green("YOU EDITED STUDENT'S RATED SUCCESSFUL!!!"));
                    }
                    else{
                        System.out.println(Style.red("YOU CANCEL THE PROCEDURE!!!"));
                    }
                }
                else{
                    System.out.println(Style.red("THERE ARE NO RATED ASSIGNMENTS FOR THIS STUDENT IN THAT COURSE!!!"));
                    System.out.println(Style.red("IF YOU WANT TO RATE AN ASSIGNMENT PLEASE CHOOSE 1 TO MENU!!!"));
                    Input.pressToGoBack();
                }
            }
            else{
                System.out.println(Style.red("THERE ARE NO ASSIGNMENTS FOR THIS COURSE!!!"));
                Input.pressToGoBack();
            }
        }
        else{
            System.out.println(Style.red("THE STUDENT IS NOT IN ANY COURSE. HE HAS TO REGISTER TO A COURSE FIRST!!!"));
        }
    }
    
    
    
    
    
    /**
     * Takes a student and lets User to changes it's firstName
     * @param student 
     */
    private static void firstNameEditor(Student student){
        
        String newFirstName;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT STUDENT'S FIRST NAME: "), student.getFirstName()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW FIRST NAME: "));
        newFirstName =Input.giveOnlyString();                                   //User gives the new First Name
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the First Name
        Style.addLines(2);
        
        if(confirmedChanges == true){
            student.setFirstName(newFirstName);
            student.update();
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
    private static void lastNameEditor(Student student){
        
        String newLastName;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT STUDENT'S LAST NAME: "), student.getLastName()});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW LAST NAME: "));
        newLastName =Input.giveOnlyString();                                    //User gives the new Last Name
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Last Name
        Style.addLines(2);
        
        if(confirmedChanges == true){
            student.setLastName(newLastName);
            student.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes a student and lets User to changes it's dateOfBirth
     * @param student 
     */
    private static void dateOfBirthEditor(Student student){
        
        LocalDate newDateOfBirth;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT STUDENT'S DATE OF BIRTH: "), student.getDateOfBirth().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW DATE OF BIRTH: "));
        newDateOfBirth =Input.giveADate();                                      //User gives the new Date of birth
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Date of birth
        Style.addLines(2);
        
        if(confirmedChanges == true){
            student.setDateOfBirth(newDateOfBirth);
            student.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Takes a student and lets User to changes it's tuitionFees
     * @param student 
     */
    private static void tuitionFeesEditor(Student student){
        
        double newTuitionFees;
        boolean confirmedChanges;
        
        Style.addLines(1);
        
        Style.printFormatted(new int[]{45,20}, new String[]{Style.yellow("CURRENT STUDENT'S TUITION FEES: "), Double.toString(student.getTuitionFees())});
        Style.addUnderline();
        Style.addLines(1);
        System.out.println(Style.yellow("WRITE THE NEW TUITION FEES: "));
        newTuitionFees =Input.giveOnlyDouble();                                 //User gives the new Tuition Fees
        confirmedChanges = OtherViews.confirmChanges();                         //User confirms that wants to change the Tuition Fees
        Style.addLines(2);
        
        if(confirmedChanges == true){
            student.setTuitionFees(newTuitionFees);
            student.update();
            System.out.println(Style.green("CHANGES SAVED"));
        }
        else{
            System.out.println(Style.red("YOU CANCELED THE EDITING PROCEDURE"));
        }
    }
    
    
    
    /**
     * Tells to database to delete the student
     * @param student 
     */
    private static void deleteStudent(Student student){
        
        boolean result = OtherViews.confirmChanges();                           //User confirms that wants to delete the Student
        
        if(result == true){
            Student.delete(student);
            System.out.println(Style.green("The student deleted!"));
            ObjectMenu.showFor(GenericDao.viewFor.EDIT);
        }
        
    }
    
}
