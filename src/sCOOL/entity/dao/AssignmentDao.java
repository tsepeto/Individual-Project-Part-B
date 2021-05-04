/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sCOOL.entity.Assignment;
import sCOOL.entity.Course;

/**
 *
 * @author tsepe
 */
public class AssignmentDao implements CrudInterface<Assignment>{

    private static final String FINDBYID = "SELECT * FROM assignments WHERE id = ?";
    private static final String FINDBYMAXID = "SELECT * FROM assignments WHERE id = (SELECT max(id) FROM assignments)";
    private static final String EXISTS = "SELECT * FROM assignments WHERE title = ? and description = ? and sub_dateTime = ? and oral_mark = ? and total_mark = ? ";
    private static final String FINDALL = "SELECT * FROM assignments";
    private static final String INSERT = " insert into assignments (title, description, sub_dateTime, oral_mark, total_mark) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = " UPDATE assignments SET title = ?, description = ?, sub_dateTime = ?, oral_mark = ?, total_mark = ? WHERE id = ?";
    private static final String DELETE = " DELETE FROM assignments WHERE id = ?";
    private static final String REMOVE_FROM_COURSES = "DELETE FROM courses_assignments WHERE assignment_id = ?";
    private static final String DELETE_STUDENT_GRADES = "DELETE FROM studentgrades WHERE assignment_id = ?";
    private static final String GET_COURSES_FOR_ASSIGNMENT = "SELECT * FROM courses C INNER JOIN courses_assignments R ON C.id =R.course_id " +
                                                             "INNER JOIN assignments A ON R.assignment_id = A.id WHERE A.id = ?";
    private static final String GET_COURSES_NOT_IN_ASSIGNMENT = "SELECT * FROM courses C WHERE C.start_date< ? AND C.end_date > ? AND C.id NOT IN (SELECT course_id " +
                                                                "FROM courses_assignments R WHERE R.assignment_id = ?)";
    private static final String GET_ASSIGNMENTS_FOR_WEEK = "SELECT * FROM assignments A WHERE WEEK(A.sub_dateTime,1) = ?";
    private static final String ADD_COURSE = " insert into courses_assignments (course_id, assignment_id) values (?, ?)";
    private static final String REMOVE_COURSE = " DELETE FROM courses_assignments WHERE course_id = ? and assignment_id = ?";
    
    
    private GenericDao data = GenericDao.getInstance();
    
    
    /**
     * Returns an ArrayList with Assignments
     * @return ArrayList with Assignments
     */
    @Override
    public ArrayList<Assignment> getAll() {
        ArrayList<Assignment> allAssignments = new ArrayList();
        String statement = FINDALL;
        ResultSet rs = data.makeStatement(statement);
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                allAssignments.add(assignment);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getAll()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        return allAssignments;
    }

    
    /**
     * Returns the Assignment by it's id
     * @param id
     * @return Assignment
     */
    @Override
    public Assignment getById(int id) {
        Assignment result = null;
        String statement = FINDBYID;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)id});
        try {
            while(rs.next()){
                result = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getById()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return result;
    }
    
    
    /**
     * Returns the created course 
     * @return Course
     */
    @Override
    public Assignment getByMaxId() {
        Assignment result = null;
        String statement = FINDBYMAXID;
        ResultSet rs = data.makeStatement(statement);
        try {
            while(rs.next()){
                result = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getByMaxId()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        return result;
    }

    
    
    /**
     * Saves the assignment into the dataBase
     * @param assignment
     */
    @Override
    public void create(Assignment t) {
        String statement = INSERT;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getDescription(),
                                                (Object)t.getSubDateTime(),(Object)t.getOralMark(),(Object)t.getTotalMark()});
        Assignment lastCreatedAssignment = getByMaxId();                        //after the assignment creation, we get back the last assignment with the id
        t.setId(lastCreatedAssignment.getId());                                 //and set the id to the given assignment
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Updates the Assignment's parameters in database.
     * User must first edit the assignment.
     * @param updatedAssignment
     */
    @Override
    public void update(Assignment t) {
        String statement = UPDATE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getDescription(),(Object)t.getSubDateTime(),
                                                                     (Object)t.getOralMark(),(Object)t.getTotalMark(),(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Deletes the given assignment from the database
     * @param assignment 
     */
    @Override
    public void delete(Assignment t) {
        String statement = DELETE_STUDENT_GRADES;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        
        statement = REMOVE_FROM_COURSES;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        statement = DELETE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
    }

    
    
    /**
     * Checks if the given assignment exists
     * @param Assignment
     * @return boolean
     */
    @Override
    public boolean exists(Assignment t) {
        boolean exists = true;
        
        String statement = EXISTS;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getDescription(),
                                            (Object)t.getSubDateTime(),(Object)t.getOralMark(),(Object)t.getTotalMark()});
        try {
            if (rs.next() == false) {
                exists = false;
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.exists()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }

        return exists;
    }
    
    
    /**
     * Get all the assignment's courses
     * @param assignment
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCoursesForAssignment(Assignment assignment){
        ArrayList<Course> result = new ArrayList();
        
        String statement = GET_COURSES_FOR_ASSIGNMENT;

        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)assignment.getId()});
        try {
            while(rs.next()){
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                result.add(course);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getCoursesForAssignment()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return result;
    }
    
    
    /**
     * Get all the courses which are not in Assignment
     * And are active in assignment's deadLine!!!
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCoursesNotInAssignment(Assignment assignment){
        ArrayList<Course> result = new ArrayList();
        
        String statement =  GET_COURSES_NOT_IN_ASSIGNMENT;

        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)assignment.getSubDateTime(),(Object)assignment.getSubDateTime(),(Object)assignment.getId()});
        try {
            while(rs.next()){
                
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                result.add(course);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getCoursesNotInAssignment()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    /**
     * Returns the assignments are in the same week's number as the given number
     * @param number
     * @return ArrayList of Assignment
     */
    public ArrayList<Assignment> getAssignmentsForWeek(int number){
        ArrayList<Assignment> result = new ArrayList();
        
        String statement =  GET_ASSIGNMENTS_FOR_WEEK;

        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)number});
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                result.add(assignment);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with AssignmentDao.getAssignmentsForWeek()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return result;
    }
    
    
    /**
     * Adds a course in an assignment
     * @param assignment
     * @param course
     */
    public void addCourseInAssignment(Assignment assignment,Course course){
     
      String statement = ADD_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)assignment.getId()});
      data.closeConnections(data.ps, data.conn);
      
    }
    
    
    /**
     * removes a course from an assignment
     * @param assignment
     * @param course
     */
    public void removeCourseFromAssignment(Assignment assignment,Course course){
      
      String statement = REMOVE_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)assignment.getId()});
      
      data.closeConnections(data.ps, data.conn);
      
    }

    
}
