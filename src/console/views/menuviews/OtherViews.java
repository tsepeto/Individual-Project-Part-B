/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.views.menuviews;

import console.utils.Input;
import console.utils.Style;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.Trainer;

/**
 *
 * @author tsepe
 * 
 * Here are some helpful Views
 */
public class OtherViews {
    
    // We create a View that gives us one object from object's list
    public static ChooseObjectFromList<Student>     studentChoose = new ChooseObjectFromList();     // We use this View to choose one or more Students from a list.
    public static ChooseObjectFromList<Course>      courseChoose = new ChooseObjectFromList();      // We use this View to choose one or more Courses from a list.
    public static ChooseObjectFromList<Trainer>     trainerChoose = new ChooseObjectFromList();     // We use this View to choose one or more Trainers from a list.
    public static ChooseObjectFromList<Assignment>  assignmentChoose = new ChooseObjectFromList();  // We use this View to choose one or more Assignments from a list.
    
    
    
    
    /**
     * A View that confirms the User Changes
     * @return boolean
     */
    public static boolean confirmChanges(){
        boolean result;
        
        Style.addLines(2);
        System.out.println(Style.yellow("ARE YOU SURE THAT YOU WANT TO SAVE THE CHANGES?"));
        System.out.println(Style.green(" 1 - YES"));
        System.out.println(Style.red(" 2 - NO"));
        result = Input.giveYesOrNo();
        
        return result;
    }
    
    
    
    /**
     * We call closeProgram!() to close the program.<p>
     * The method thanks the user, prints the logo and closes the program.
     * The closeProgram1 method is an alternative method to close. It prints
     * another ASCII ART LOGO. That's the only difference from closeProgram().
     */
    public static void closeProgram1(){
        Style.addLines(2);
        System.out.println(Style.green("THANK YOU FOR USING sCOOL"));
        System.out.println(Style.green("THE PROGRAM THAT FREES YOUR HANDS IN THE MANAGEMENT OF YOUR SCHOOL"));
        System.out.println(Style.blue("     _____ _____  _____ _     "));
        System.out.println(Style.blue("    /  __ \\  _  ||  _  | |    "));
        System.out.println(Style.blue(" ___| /  \\/ | | || | | | |    "));
        System.out.println(Style.blue("/ __| |   | | | || | | | |    "));
        System.out.println(Style.blue("\\__ \\ \\__/\\ \\_/ /\\ \\_/ / |____"));
        System.out.println(Style.blue("|___/\\____/\\___/  \\___/\\_____/"));
        System.out.println(Style.blue("                              "));
        System.out.println(Style.blue("                              "));
        System.exit(0);
    }
    
    
    
    
    /**
     * We call closeProgram() to close the program.<p>
     * The method thanks the user, prints the logo and closes the program.
     */
    public static void closeProgram(){
        Style.addLines(2);
        System.out.println(Style.blue("THANK YOU FOR USING")+Style.yellow(" sCOOL"));
        System.out.println(Style.green("THE PROGRAM THAT FREES YOUR HANDS IN THE MANAGEMENT OF YOUR SCHOOL"));
        
        System.out.println(Style.blue("_____________________")+Style.yellow("/\\\\\\\\\\\\\\\\\\")+Style.blue("_______")+Style.yellow("/\\\\\\\\\\")+Style.blue("____________")+Style.yellow("/\\\\\\\\\\")+Style.blue("_______")+Style.yellow("/\\\\\\")+Style.blue("_____________        "));
        System.out.println(Style.blue(" __________________")+Style.yellow("/\\\\\\////////")+Style.blue("______")+Style.yellow("/\\\\\\///\\\\\\")+Style.blue("________")+Style.yellow("/\\\\\\///\\\\\\")+Style.blue("____")+Style.yellow("\\/\\\\\\")+Style.blue("_____________       "));
        System.out.println(Style.blue("  ________________")+Style.yellow("/\\\\\\/")+Style.blue("_____________")+Style.yellow("/\\\\\\/")+Style.blue("__")+Style.yellow("\\///\\\\\\")+Style.blue("____")+Style.yellow("/\\\\\\/")+Style.blue("__")+Style.yellow("\\///\\\\\\")+Style.blue("__")+Style.yellow("\\/\\\\\\")+Style.blue("_____________      "));
        System.out.println(Style.blue("   __")+Style.yellow("/\\\\\\\\\\\\\\\\\\\\")+Style.blue("__")+Style.yellow("/\\\\\\")+Style.blue("______________")+Style.yellow("/\\\\\\")+Style.blue("______")+Style.yellow("\\//\\\\\\")+Style.blue("__")+Style.yellow("/\\\\\\")+Style.blue("______")+Style.yellow("\\//\\\\\\")+Style.blue("_")+Style.yellow("\\/\\\\\\")+Style.blue("_____________     "));
        System.out.println(Style.blue("    _")+Style.yellow("\\/\\\\\\//////")+Style.blue("__")+Style.yellow("\\/\\\\\\")+Style.blue("_____________")+Style.yellow("\\/\\\\\\")+Style.blue("_______")+Style.yellow("\\/\\\\\\")+Style.blue("_")+Style.yellow("\\/\\\\\\")+Style.blue("_______")+Style.yellow("\\/\\\\\\")+Style.blue("_")+Style.yellow("\\/\\\\\\")+Style.blue("_____________    "));
        System.out.println(Style.blue("     _")+Style.yellow("\\/\\\\\\\\\\\\\\\\\\\\")+Style.blue("_")+Style.yellow("\\//\\\\\\")+Style.blue("____________")+Style.yellow("\\//\\\\\\")+Style.blue("______")+Style.yellow("/\\\\\\")+Style.blue("__")+Style.yellow("\\//\\\\\\")+Style.blue("______")+Style.yellow("/\\\\\\")+Style.blue("__")+Style.yellow("\\/\\\\\\")+Style.blue("_____________   "));
        System.out.println(Style.blue("      _")+Style.yellow("\\////////\\\\\\")+Style.blue("__")+Style.yellow("\\///\\\\\\")+Style.blue("___________")+Style.yellow("\\///\\\\\\")+Style.blue("__")+Style.yellow("/\\\\\\")+Style.blue("_____")+Style.yellow("\\///\\\\\\")+Style.blue("__")+Style.yellow("/\\\\\\")+Style.blue("____")+Style.yellow("\\/\\\\\\")+Style.blue("_____________  "));
        System.out.println(Style.blue("       __")+Style.yellow("/\\\\\\\\\\\\\\\\\\\\")+Style.blue("____")+Style.yellow("\\////\\\\\\\\\\\\\\\\\\")+Style.blue("____")+Style.yellow("\\///\\\\\\\\\\/")+Style.blue("________")+Style.yellow("\\///\\\\\\\\\\/")+Style.blue("_____")+Style.yellow("\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\")+Style.blue("_ "));
        System.out.println(Style.blue("        _")+Style.yellow("\\//////////")+Style.blue("________")+Style.yellow("\\/////////")+Style.blue("_______")+Style.yellow("\\/////")+Style.blue("____________")+Style.yellow("\\/////")+Style.blue("_______")+Style.yellow("\\///////////////")+Style.blue("__"));
        System.exit(0);
    }
    
}
