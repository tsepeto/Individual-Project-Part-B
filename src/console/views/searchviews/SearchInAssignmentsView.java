
package console.views.searchviews;

import console.utils.Style;
import console.utils.Input;
import console.views.menuviews.ObjectMenu;
import console.views.menuviews.OtherViews;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * 
 * In that View the user chooses what we want to search in assignments
 */
public class SearchInAssignmentsView {
                              
    static GenericDao data = GenericDao.getInstance();                              //This is the program's database.
    
    /**
     * Shows the View and ask the user to choose what to ask for a assignment
     */
    public static void show(){
        while(true){
            Style.addLines(1);
            System.out.println(Style.yellow("WHAT DO YOU WANT TO SEE?"));
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" - SHOW ALL THE ASSIGNMENTS");
            System.out.println(Style.yellow("2")+" - SHOW A ASSIGNMENT'S DETAILS");
            System.out.println(Style.yellow("3")+" - BACK ");
            Style.addUnderline();
            Style.addLines(1);
            int input = Input.giveOnlyInteger(1, 3);                            

            switch(input){
                case 1:
                    showAssignments();
                    break;

                case 2:
                    showAssignmentDetails();
                    break;

                case 3:
                    ObjectMenu.showFor(GenericDao.viewFor.SEARCH);
                    break;

            }
        }
    }
     
    
    
    /**
     * Prints all the assignments we have in database
     * @param assignments list
     */
    private static void printAssignments(ArrayList<Assignment> assignments){
        Style.addLines(1);
        System.out.println(Style.yellow("ASSIGNMENTS"));
        Style.addUnderline();
        int counter = 1;
        
        System.out.println(Style.yellow(Style.createFormat(new int[]{3,3,40,47,15},new String[]{"","","TITLE", "DESCRIPTION", "SUBDATE"})));
        for(Assignment assignment:assignments){
            Style.printFormatted( new int[]{6,40,50,15},
                    new String[]{Style.yellow(counter+ "-"),assignment.getTitle(),assignment.getDescription(),
                        assignment.getSubDateTime().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
            counter+=1;
        }
        Style.addUnderline();
        Style.addLines(1);
    }
    
    
    
    /**
     * The method prints Assignments and waits the user to give something to go
     * to the previous view
     */
    private static void showAssignments(){
        if(Assignment.getAllAssignments().size()>0){
            printAssignments(Assignment.getAllAssignments());
            Input.pressToGoBack();                                              //Waits the user to give something to continue
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO ASSIGNMENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }
    }
    
    
    /**
     * The method prints the details of the chosen assignment
     */
    private static void showAssignmentDetails(){
         if(Assignment.getAllAssignments().size()>0){
            Assignment result = OtherViews.assignmentChoose.chooseOne(Assignment.getAllAssignments());//The user chooses assignment
            ArrayList<Course> courses = result.getCourses();  //Gets the assignment's courses

            Style.addLines(1);
            Style.addUnderline();

            //Prints all the formatted details
            Style.printFormatted(new int[]{40, 50}, new String[]{Style.yellow("TITLE: "), result.getTitle()});
            Style.printFormatted(new int[]{40, 50}, new String[]{Style.yellow("DESCRIPTION: "), result.getDescription()});
            Style.printFormatted(new int[]{40, 50}, new String[]{Style.yellow("ASSIGNMENT'S DEADLINE: "), result.getSubDateTime().format(DateTimeFormatter.ofPattern(data.daTiFormat))});
            Style.printFormatted(new int[]{40, 50}, new String[]{Style.yellow("ORAL MARK: "), ""+result.getOralMark()});
            Style.printFormatted(new int[]{40, 50}, new String[]{Style.yellow("TOTAL MARK: "),""+ result.getTotalMark()});

            Style.addUnderline();
            Style.printFormatted(new int[]{26, 50}, new String[]{Style.yellow("COURSES: "), ""+courses.size()});


            for(Course course:courses){
                 System.out.println(course);
            }
            Style.addUnderline();
            Style.addLines(1);
            Input.pressToGoBack();
        }
        else{
            Style.addLines(1);
            System.out.println(Style.red("THERE ARE NO ASSIGNMENTS IN DATABASE!!!"));
            System.out.println(Style.red("PLEASE CREATE SOME AND TRY TO SEARCH LATER!!!"));
        }  
    }
    
}
