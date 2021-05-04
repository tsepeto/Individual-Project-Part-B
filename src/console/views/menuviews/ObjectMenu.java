
package console.views.menuviews;

import console.views.searchviews.SearchInTrainersView;
import console.views.searchviews.SearchInStudentsView;
import console.views.searchviews.SearchInCoursesView;
import console.views.searchviews.SearchInAssignmentsView;
import console.views.editviews.EditTrainer;
import console.views.editviews.EditStudent;
import console.views.editviews.EditCourse;
import console.views.editviews.EditAssignment;
import console.views.createviews.CreateTrainer;
import console.views.createviews.CreateStudent;
import console.views.createviews.CreateCourse;
import console.views.createviews.CreateAssignment;
import console.utils.Style;
import console.utils.Input;
import sCOOL.entity.dao.GenericDao;

/**
 *
 * @author tsepe
 * 
 * That View takes an argument from MainMenu View and decides what will be the 
 * next view among Create, Edit, or Search an object. The user chooses the object's
 * type.
 */
public class ObjectMenu {
    
    public static void showFor(GenericDao.viewFor view){
        
        while(true){                                                            //Runs the loop until the user choose to go back
            int result;
            Style.addLines(1);
            if (view == GenericDao.viewFor.CREATE){ System.out.println(Style.yellow("     CREATE"));}// Choose what header to print among create,edit,or search.
            else if(view == GenericDao.viewFor.EDIT){System.out.println(Style.yellow("     EDIT"));} 
            else if(view == GenericDao.viewFor.SEARCH){System.out.println(Style.yellow("     SEARCH"));}
            else{ System.out.println(Style.red("An unexpected error has occurred. ObjectMenu file, Line 23"));}
            Style.addUnderline();
            System.out.println(Style.yellow("1")+" -  STUDENT");
            System.out.println(Style.yellow("2")+" -  TRAINER");
            System.out.println(Style.yellow("3")+" -  COURSE");
            System.out.println(Style.yellow("4")+" -  ASSIGNMENT");
            System.out.println(Style.yellow("5")+" -  GO BACK");
            Style.addUnderline();
            Style.addLines(1);

            result = Input.giveOnlyInteger(1,5);                                //Takes an Integer from the user.Minimum number 1 and maximum 5

            switch(result){
                case 1:
                    if (view == GenericDao.viewFor.CREATE){CreateStudent.show();}                             //Create Student procedure
                    else if(view == GenericDao.viewFor.EDIT){EditStudent.show();}             //Edit Student procedure
                    else if(view == GenericDao.viewFor.SEARCH){SearchInStudentsView.show();}                  //Search in database for Students procedure
                    else{ System.out.println("An unexpected error has occurred. ObjectMenu file, Line 40");}
                    break;

                case 2:
                    if (view == GenericDao.viewFor.CREATE){CreateTrainer.show();}                             //Create Trainer procedure
                    else if(view == GenericDao.viewFor.EDIT){{EditTrainer.show();}}             //Edit Trainer procedure
                    else if(view == GenericDao.viewFor.SEARCH){SearchInTrainersView.show();}                  //Search in database for Trainers procedure
                    else{ System.out.println(Style.red("An unexpected error has occurred. ObjectMenu file, Line 47"));}
                    break;

                case 3:
                    if (view == GenericDao.viewFor.CREATE){CreateCourse.show();}                              //Create Course procedure
                    else if(view == GenericDao.viewFor.EDIT){EditCourse.show();}             //Edit Course procedure
                    else if(view == GenericDao.viewFor.SEARCH){SearchInCoursesView.show();}                   //Search in database for Courses procedure
                    else{ System.out.println(Style.red("An unexpected error has occurred. ObjectMenu file, Line 54"));}
                    break;

                case 4:
                    if (view == GenericDao.viewFor.CREATE){CreateAssignment.show();}                          //Create Assignment procedure
                    else if(view == GenericDao.viewFor.EDIT){EditAssignment.show();}             //Edit Assignment procedure
                    else if(view == GenericDao.viewFor.SEARCH){SearchInAssignmentsView.show();}               //Search in database for Assignments procedure
                    else{ System.out.println(Style.red("An unexpected error has occurred. ObjectMenu file, Line 61"));}
                    break;

                case 5:
                    MainMenu.showMenu();                                                                          //Stops the loop and go to previous View
                    break;

            }
        }
    }
    
   
}
