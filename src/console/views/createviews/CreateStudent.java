
package console.views.createviews;


import console.utils.Style;
import console.utils.Input;
import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import sCOOL.entity.Student;




/**
 *
 * @author tsepe
 * CreateStudent is a View that the program calls it every time the user
 * chooses to create a new student.
 */
public class CreateStudent {
    

    
    /**
     * The method shows the view and starts the student's create procedure 
     */
    public static void show(){
        Style.addLines(1);
        boolean createMore = true;
        while(createMore == true){    
            studentCreator();                                                   //starts the student's create procedure.
            Style.addLines(1);
            Style.addUnderline();
            System.out.println(Style.yellow("DO YOU WANT TO CREATE ANOTHER STUDENT?"));       //asks the user, if he want to create another student.
            System.out.println(Style.yellow("1")+" - YES");
            System.out.println(Style.yellow("2")+" - NO");
            Style.addUnderline();
            boolean more = Input.giveYesOrNo();
            
            if( more == true){
                Style.addLines(1);
                System.out.println(Style.green("LET'S CREATE THE NEXT STUDENT!"));
                Style.addLines(1);}
            else{
                System.out.println(Style.green("THE STUDENT'S CREATION PROGRESS IS OVER!"));
                createMore = false;
            }    
        }
    }
    
    
    
    /**
     * The method studentCreator() is the user creation procedure
     */
    private static void studentCreator(){
        String firstName;
        String lastName;
        LocalDate dateOfBirth; 
        double tuitionFees;
        Student student;
        boolean studentSaved = false;
        
        while(studentSaved == false){
            System.out.println(Style.yellow("GIVE THE STUDENT'S FIRST NAME:"));
            firstName = Input.giveOnlyString();
            System.out.println(Style.yellow("GIVE THE STUDENT'S LAST NAME:"));
            lastName = Input.giveOnlyString();
            System.out.println(Style.yellow("GIVE THE STUDENT'S BIRTH DATE:"));
            dateOfBirth = Input.giveADate();
            System.out.println(Style.yellow("GIVE THE STUDENT'S TUITION FEES:"));
            tuitionFees = Input.giveOnlyDouble();
            
            student = new Student(firstName,lastName,dateOfBirth,tuitionFees);  // Creates a temporary student.
            if (!Student.exists(student)){                                      // Checks if the student allready exists,
                Student.saveStudent(student);                                   // if not saves it in the database.
                studentSaved = true;                                            // Here it stops the while loop.
            }
            else{
                Style.addLines(1);                                              // If student allready exists, the method starts again the student's creation procedure.
                System.out.println(Style.red("THIS STUDENT ALLREADY EXISTS,PLEASE CREATE ANOTHER STUDENT."));
                break;}
        
        }
       
    }
}
