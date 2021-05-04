
package console.views.menuviews;

import console.utils.Style;
import console.utils.Input;
import java.util.ArrayList;

/**
 * ChooseObjectFromList is a generic class, that allows us to choose one or many
 * objects from a list.<p>
 * 
 * @author tsepe
 */
public class ChooseObjectFromList< T > {
     
    /**
     * Helps us to choose some objects from a list.<p> 
     * There is a process that checks if the user requests an option through a 
     * specific range of options (as much as the size of the list we declare). 
     * @param list with the objects from where we want to choose our objects. 
     * @return an ArrayList with our choices.
     */
    public  ArrayList<T> chooseMany(ArrayList<T> list){
        ArrayList<T> selectedObjects = new ArrayList();
        ArrayList<T> savedObjects; 
        savedObjects = new ArrayList<T>(list);                                  //We copy the given list to another new temporary list
                                                                                //because everytime the user selects an object, that object will be removed
        boolean moreObjects = true;                                             //from the list so that the user can not select it again.
        int choose;
        while(moreObjects == true){
            Style.addLines(1);
            if(savedObjects.size() > 1){
                Style.addLines(1);
                System.out.println(Style.yellow("PLEASE CHOOSE ONE OF THE FOLLOWING:"));
                Style.addUnderline();
                for(int i=0; i < savedObjects.size(); i++){                     //Here the method print the options
                    System.out.println(Style.yellow(i+1 +" ") + savedObjects.get(i));
                }
                Style.addLines(1);
                choose = Input.giveOnlyInteger(1,savedObjects.size());          //The user gives the choice as an integer
                selectedObjects.add(savedObjects.get(choose-1));                //the method saves his choice
                savedObjects.remove(choose-1);                                  //and removes the selected object from the option list
                Style.addUnderline();
                System.out.println(Style.yellow("DO YOU WANT TO CHOOSE MORE?"));              //after tha it asks the user if he want to choose another one object
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                Style.addLines(1);
                moreObjects = Input.giveYesOrNo();}
            else if(savedObjects.size() == 1){                                  //if only one object left, method asks the user
                System.out.println(Style.yellow("ONLY  ")+savedObjects.get(0) + Style.yellow("  LEFT."));   //if he want to add the object to his option list
                System.out.println(Style.yellow("DO YOU WANT TO CHOOSE IT?"));
                System.out.println(Style.yellow("1")+" - YES");
                System.out.println(Style.yellow("2")+" - NO");
                Style.addUnderline();
                Style.addLines(1);
                moreObjects = Input.giveYesOrNo();
                selectedObjects.add(savedObjects.get(0));
                savedObjects.remove(0);
            }
            else{ moreObjects = false;}
            
        }
        return selectedObjects;
    }
    
    
    
    /**
     * Helps us to choose one object from a list.<p> 
     * There is a process that checks if the user requests an option through a 
     * specific range of options (as much as the size of the list we declare). 
     * @param list with the objects from where we want to choose our object.
     * @return the chosen object.
     */
    public T chooseOne(ArrayList<T> list){
        ArrayList<T> savedObjects; 
        savedObjects = new ArrayList<T>(list);
        T result;                                                               //Here the method declares the variable, where we
        int choose;                                                             //will save the choosen object by the user.
        System.out.println(Style.yellow("PLEASE CHOOSE ONE OF THE FOLLOWING:"));
        Style.addUnderline();
        for(int i=0; i < savedObjects.size(); i++){
            System.out.println(Style.yellow(i+1 +" " )+ savedObjects.get(i));
        }
        Style.addUnderline();
        Style.addLines(1);
        choose = Input.giveOnlyInteger(1,savedObjects.size());
        result = savedObjects.get(choose-1);
        savedObjects.remove(choose-1);
        
        return result;
    }
    
    
}
