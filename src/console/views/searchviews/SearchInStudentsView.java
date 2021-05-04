
package console.views.searchviews;

import console.utils.Style;
import console.utils.Input;
import console.views.menuviews.ObjectMenu;
import console.views.menuviews.OtherViews;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.StudentGrade;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * 
 * In that View the user chooses what we want to search in students
 */
public class SearchInStudentsView {
    
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    /**
     * Shows the View and ask the user to choose what to ask for a student
     */
    public static void show(){
        while(true){
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO SEE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - SHOW ALL THE STUDENTS");
            System.out.println(Style.yellow("2")+" - SHOW A STUDENT'S DETAILS");
            System.out.println(Style.yellow("3")+" - WHICH STUDENTS ARE ENROLL IN MORE THAN ONE COURSE");
            System.out.println(Style.yellow("4")+" - SHOW THE STUDENTS WHO HAVE TO DELIVER THEIR ASSIGNMENT");
            System.out.println(Style.yellow("5")+" - BACK ");
            Style.addUnderline();
            Style.addLines(1);
            int input = Input.giveOnlyInteger(1, 5);

            switch(input){
                case 1:
                    showStudents();
                    break;

                case 2:
                    showStudentDetails();
                    break;

                case 3:
                    showStudentsEnrollMoteThan2();
                    break;

                case 4:
                    showStudentsToDeliverAssignment();
                    break;

                case 5:
                    ObjectMenu.showFor(GenericDao.viewFor.SEARCH);
                    break;
            }
        }
    }
    
    
    
    /**
     * Prints all the students we have in database
     * @param students list
     */
    private static void printStudents(ArrayList<Student> students){
        Style.addLines(1);
        System.out.println(Style.yellow("STUDENTS"));
        Style.addUnderline();
        int counter = 1;
        System.out.println(Style.yellow(Style.createFormat(new int[]{3,3,15,20,15,3,12},new String[]{"","", "FIRST NAME", "LAST NAME", "DATE OF BIRTH", "", "TUITION FEES"})));
        for(Student student:students){
            Style.printFormatted(new int[]{3,3, 15, 20, 15, 3, 12},new String[]{Style.yellow(counter+""), Style.yellow("  - "), student.getFirstName(), student.getLastName(), student.getDateOfBirth().format(DateTimeFormatter.ofPattern(data.daTiFormat)),
                                                                                "-", Double.toString(student.getTuitionFees())});

            counter+=1;
        }
        Style.addUnderline();
        Style.addLines(1);
    }
    
    
    /**
     * The method calls the printStudents method, and waits user to write something
     * to continue the program
     */
    private static void showStudents(){
        if(Student.getAllStudents().size()>0){
            printStudents(Student.getAllStudents());                                  //prints all the students
            Input.pressToGoBack();
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO STUDENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }
    }
    
    
    
    /**
     * The method prints the details of the chosen Student
     */
    private static void showStudentDetails(){
        
        if(Student.getAllStudents().size()>0){
            Student student = OtherViews.studentChoose.chooseOne(Student.getAllStudents()); //The user chooses the student he wants
            ArrayList<Course> courses = student.getCourses();                   //The program finds the student's courses
            ArrayList<Assignment> assignments;

            Style.addLines(1);
            System.out.println(Style.yellow("STUDENTS DETAILS:"));
            Style.addUnderline();
            Style.printFormatted(new int[]{40, 20}, new String[]{Style.yellow("FIRST NAME: "), student.getFirstName()});
            Style.printFormatted(new int[]{40, 20}, new String[]{Style.yellow("LAST NAME: "), student.getLastName()});
            Style.printFormatted(new int[]{40, 20}, new String[]{Style.yellow("DATE OF BIRTH: "), student.getDateOfBirth().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
            Style.printFormatted(new int[]{40, 20}, new String[]{Style.yellow("TUITION FEES: "), Double.toString(student.getTuitionFees())});


            Style.addUnderline();
            System.out.println(Style.yellow("COURSES : ") + courses.size());
            Style.addUnderline();
            for(Course course:courses){
                System.out.println(Style.yellow(" - ") + course);
            }
            Style.addUnderline();
            System.out.println(Style.yellow("STUDENT ASSIGNMENTS : ") );
            Style.addUnderline();
            for(Course course:courses){ 
                assignments = course.getAssignments();                          //finds the course's assignments,for each course and prints them
                for(Assignment assignment:assignments){
                    if(StudentGrade.exists(student, course, assignment)){
                        StudentGrade sT = StudentGrade.getStudentGrade(student, course, assignment);
                        Style.printFormatted(new int[]{ 5, 20, 5, 20,35,35}, new String[]{Style.yellow(" - "), assignment.getTitle(), " for ", course.getStream(),Style.yellow("O.M. : ")+sT.getOralMark()+"/"+assignment.getOralMark(),Style.yellow("T.M. : ")+sT.getTotalMark()+"/"+assignment.getTotalMark()});
                    }
                    else{
                    Style.printFormatted(new int[]{ 5, 20, 5, 20,50}, new String[]{Style.yellow(" - "), assignment.getTitle(), " for ", course.getStream(),Style.red("THIS ASSIGNMENT IS NOT MARKED YET!!!")});
                    }
                }
            }
            Style.addUnderline();
            Style.addLines(1);
            Input.pressToGoBack();
        }
         else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO STUDENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }
    }
    
    
    /**
     * The method prints all the students who have enrolled
     * in more than, 2 courses
     */
    private static void showStudentsEnrollMoteThan2(){   
        if(Student.getStudentsWhenLessonsMoreThan(1).size()>0){
            ArrayList<Student> students = Student.getStudentsWhenLessonsMoreThan(1); //finds all the students who have enrolled in more tha, 2 courses
            printStudents(students);
            Style.addLines(1);
            Input.pressToGoBack();
        }
     else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO STUDENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }
    }
    
    
    
    /**
     * Asks a date and return all the students who have to deliver their assignments
     * in that date's week.
     */
    private static void showStudentsToDeliverAssignment(){
        if(Student.getAllStudents().size()>0){                                        //Checks if there are Students
            if(Assignment.getAllAssignments().size()>0){                                 //Checks if there are Assignments
                Style.addLines(1);
                System.out.println(Style.yellow("PLEASE GIVE A DATE TO SHOW YOU THE STUDENTS WHO HAVE TO DELIVER THEIR ASSIGNMENTS AT THAT WEEK"));
                LocalDate date = Input.giveADate();
                ArrayList<Assignment> assignmentsForDeliver = Assignment.getAllAssignmentsInWeek(date.get(WeekFields.ISO.weekOfYear()));
                Style.addLines(1);
                
                if(assignmentsForDeliver.size()>0){                             //Checks if there are assignments in that week
                    for(Assignment assignment : assignmentsForDeliver){
                        System.out.println();
                        Style.addUnderline();
                        System.out.println(Style.yellow("FOR ASSIGNMENT : "));
                        System.out.println(assignment);
                        Style.addUnderline();
                        if(assignment.getCourses().size()>0){ //Checks if there are courses in that assignment
                            for (Course course : assignment.getCourses()){
                                System.out.println(Style.yellow("FOR COURSE : ")+course);
                                System.out.println(Style.yellow("THE FOLLOWING STUDENTS HAVE TO DELIVER THEIR ASSIGNMENTS TILL: ")+assignment.getSubDateTime().format(DateTimeFormatter.ofPattern(data.daTiFormat)));
                                Style.addUnderline();
                                if(course.getStudents().size()>0){//checks if there are students in that course.
                                    int counter = 1;
                                    for(Student student: course.getStudents()){
                                        
                                        if(StudentGrade.exists(student, course, assignment)){
                                            StudentGrade studentGrade = StudentGrade.getStudentGrade(student,course,assignment);
                                            System.out.println(Style.createFormat(new int[] {5,15,20,56,40,40}, 
                                                    new String[]{Style.yellow(counter+" - "),student.getFirstName(),student.getLastName(), Style.green("THE STUDENT HAS DELIVERED THE ASSIGNMENT!"),
                                                    Style.yellow("O.M. : ")+studentGrade.getOralMark()+Style.yellow("/"+studentGrade.getAssignment().getOralMark()),Style.yellow("T.M. : ")+studentGrade.getTotalMark()+Style.yellow("/"+studentGrade.getAssignment().getTotalMark())}));
                                            counter++;
                                        }
                                        else{
                                            System.out.println(Style.createFormat(new int[] {5,15,20,60,29,10}, new String[]{Style.yellow(counter+" - "),student.getFirstName(),student.getLastName(), Style.red("THE STUDENT HAS TO DELIVER THE ASSIGNMENT!"),Style.red("---"),Style.red("---")}));
                                            counter++;
                                        }
                                    }
                                    Style.addLines(1);
                                }
                                else{
                                    System.out.println(Style.red("THERE ARE NO STUDENTS IN THAT COURSE!!!"));
                                    System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
                                }
                            }
                        }
                        else{
                            System.out.println(Style.red("THERE ARE NO COURSES IN THAT ASSIGNMENT!!!"));
                            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
                        }
                    }
                    Input.pressToGoBack();
                }
                else{
                    System.out.println(Style.red("THERE ARE NO ASSIGNMENTS IN THAT WEEK!!!"));
                }
            }else{
                 Style.addLines(1);
                System.out.println(Style.red("THERE ARE NO ASSIGNMENTS IN DATABASE!!!"));
                System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));       
            }
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO STUDENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }    
    }
}
