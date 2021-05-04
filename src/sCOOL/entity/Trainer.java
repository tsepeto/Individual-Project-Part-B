
package sCOOL.entity;

import console.utils.Style;
import java.sql.SQLException;
import java.util.ArrayList;
import sCOOL.entity.dao.TrainerDao;


/*
 * @author tsepe
 * 
 * Trainer represents the school's teachers.
 */
public class Trainer {
    private int id;
    private String firstName;
    private String lastName;
    private String subject;
    
    static TrainerDao data = new TrainerDao();

    
    
    public Trainer(String firstName,String lastName,String subject){
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;

        }

    public Trainer(int id, String firstName, String lastName, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
    
//  Getter και Setter for the Trainer's firstName   
    public String getFirstName(){
        return this.firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    
    
//  Getter και Setter for the Trainer's lasttName     
    public String getLastName(){
        return this.lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    
    
//  Getter και Setter for the Trainer's subject     
    public String getSubject(){
        return this.subject;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    
    
    
    
    /**
     * Returns an ArrayList with all Courses
     * @return ArrayList with Courses
     */
    public static ArrayList<Trainer> getAllTrainers() {
        
        return data.getAll();
    }
    
    
    
    /**
     * Saves the Trainer into database
     * @param trainer
     */
    public static void saveTrainer(Trainer trainer) {
        data.create(trainer);
    }
    
    /**
     * Checks if the given trainer exists
     * @param trainer
     * @return boolean
     */
    public static boolean exists(Trainer trainer) {
        return data.exists(trainer);
    }
    
    
    /**
     * Updates the trainer in Database. 
     * The trainer's parameters, must be updated first by the program
     */
    public void update() {
        data.update(this);
    }
    
    
    /**
     * Returns all the trainer's courses
     * @return ArrayList with courses
     * @throws SQLException 
     */
    public ArrayList<Course> getCourses() {
        return data.getCoursesForTrainer(this);
    }
    
    
    
    /**
     * Deletes the given trainer
     * @param student 
     */
    public static void delete(Trainer trainer) {
        data.delete(trainer);
    }

    
//  When we try to print a Trainer object, we return formatted the String we want to print. 
    @Override
    public String toString(){

        return Style.createFormat(new int[]{20,20,30},new String[]{this.firstName,this.lastName,this.subject});
    }
    
}
