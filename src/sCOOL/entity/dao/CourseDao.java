/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.Trainer;

/**
 *
 * @author tsepe
 */
public class CourseDao implements CrudInterface<Course>{

    
    private static final String FINDBYID = "SELECT * FROM courses WHERE id = ?";
    private static final String FINDBYMAXID = "SELECT * FROM courses WHERE id = (SELECT max(id) FROM courses)";
    private static final String EXISTS = "SELECT * FROM courses WHERE title = ? and stream = ? and type = ? and start_date = ? and end_date = ? ";
    private static final String FINDALL = "SELECT * FROM courses";
    private static final String INSERT = " insert into courses (title, stream, type, start_date, end_date) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = " UPDATE courses SET title = ?, stream = ?, type = ?, start_date = ?, end_date = ? WHERE id = ?";
    private static final String DELETE = " DELETE FROM courses WHERE id = ?";
    private static final String GET_COURSES_ARE_ACTIVE_IN = "SELECT * FROM courses WHERE start_date < ? AND end_date > ?";
    private static final String DELETE_STUDENTS_FROM_COURSE = "DELETE FROM courses_students WHERE course_id = ?";
    private static final String DELETE_TRAINERS_FROM_COURSE = "DELETE FROM courses_trainers WHERE course_id = ?";
    private static final String DELETE_ASSIGNMENTS_FROM_COURSE = "DELETE FROM courses_assignments WHERE course_id = ?";
    private static final String ADD_STUDENT_IN_COURSE = " insert into courses_students (course_id, student_id) values (?, ?)";
    private static final String REMOVE_STUDENT_FROM_COURSE = " DELETE FROM courses_students WHERE course_id = ? and student_id = ?";
    private static final String ADD_TRAINER_IN_COURSE = " insert into courses_trainers (course_id, trainer_id) values (?, ?)";
    private static final String REMOVE_TRAINER_FROM_COURSE = " DELETE FROM courses_trainers WHERE course_id = ? and trainer_id = ?";
    private static final String DELETE_STUDENTGRADES_FROM_COURSE = "DELETE FROM studentgrades WHERE course_id = ?";
    private static final String GET_ASSIGNMENTS_FOR_COURSE = "SELECT * FROM assignments C INNER JOIN courses_assignments R ON C.id =R.assignment_id " +
                                                             "INNER JOIN courses T ON R.course_id = T.id WHERE T.id = ?";
    private static final String GET_SUDENTS_FOR_COURSE = "SELECT * FROM students S INNER JOIN courses_students R ON S.id =R.student_id " +
                                                         "INNER JOIN courses C ON R.course_id = C.id WHERE C.id = ?";
    private static final String GET_SUDENTS_NOT_IN_COURSE = "SELECT * FROM students S WHERE S.id NOT IN (SELECT student_id " +
                                                            "FROM  courses_students C WHERE C.course_id = ?)";
    private static final String GET_TRAINERS_FOR_COURSE = "SELECT * FROM trainers T INNER JOIN courses_trainers R ON T.id =R.trainer_id " +
                                                          "INNER JOIN courses C ON R.course_id = C.id WHERE C.id = ?";
    private static final String GET_TRAINERS_NOT_IN_COURSE = "SELECT * FROM trainers T WHERE T.id NOT IN (SELECT trainer_id " +
                                                             "FROM  courses_trainers C WHERE C.course_id = ?)";
    
    private GenericDao data = GenericDao.getInstance();
    
    
    
    /**
     * Returns an ArrayList with Courses
     * @return ArrayList with Courses
     */
    @Override
    public ArrayList<Course> getAll() {
        ArrayList<Course> allCourses = new ArrayList();
        String statement = FINDALL;
        ResultSet rs = data.makeStatement(statement);
        try {
            while(rs.next()){
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                allCourses.add(course);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getAll()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        return allCourses;
    }

    
    
    /**
     * Returns the course by it's id
     * @param id
     * @return Course
     */
    @Override
    public Course getById(int id) {
        Course result = null;
        String statement = FINDBYID;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)id});
        try {
            while(rs.next()){
                result = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getById()");
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
    public Course getByMaxId() {
        Course result = null;
        String statement = FINDBYMAXID;
        ResultSet rs = data.makeStatement(statement);
        try {
            while(rs.next()){
                result = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getByMaxId()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        return result;
    }
    

    /**
     * Saves the course into the dataBase,and sets
     * @param course
     */
    @Override
    public void create(Course t) {
        String statement = INSERT;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getStream(), (Object)t.getType(),
                                                (Object)t.getStart_date(),(Object)t.getEnd_date()});
        Course lastCreatedCourse = getByMaxId();                                //after the course creation, we get back the last course with the id
        t.setId(lastCreatedCourse.getId());                                     //and set the id to the given course
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Updates the course's parameters in database.
     * User must first edit the course.
     * @param updatedCourse
     */
    @Override
    public void update(Course t) {
        String statement = UPDATE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getStream(),(Object)t.getType(),
                                                (Object)t.getStart_date(),(Object)t.getEnd_date(),(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Deletes the given course from the database
     * @param course
     */
    @Override
    public void delete(Course t) {
        String statement = DELETE_STUDENTGRADES_FROM_COURSE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        statement = DELETE_STUDENTS_FROM_COURSE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        statement = DELETE_TRAINERS_FROM_COURSE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        statement = DELETE_ASSIGNMENTS_FROM_COURSE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        statement = DELETE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
    }

    
    /**
     * Checks if the given course exists
     * @param course
     * @return
     */
    @Override
    public boolean exists(Course t) {
        boolean exists = true;
        
        String statement = EXISTS;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)t.getTitle(),(Object)t.getStream(),
                                            (Object)t.getType(),(Object)t.getStart_date(),(Object)t.getEnd_date()});
        try {
            if (rs.next() == false) {
                exists = false;
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.exists()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }

        return exists;
    }
    
    
    /**
     * Returns all the courses which are active in the given date
     * @param date
     * @return ArrayList with Courses
     */
    public ArrayList<Course> getCoursesAreActiveIn(LocalDate date){
        ArrayList<Course> allCourses = new ArrayList();
        String statement = GET_COURSES_ARE_ACTIVE_IN;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object) date,(Object) date});
        try {
            while(rs.next()){
                Course course = new Course(rs.getInt("id"),rs.getString("title"),rs.getString("stream"),
                        rs.getString("type"),rs.getDate("start_date").toLocalDate(),rs.getDate("end_date").toLocalDate());
                allCourses.add(course);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getCoursesAreActiveIn()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return allCourses;
    }
    
    
    
    /**
     * Get all the course's assignments
     * @param course
     * @return ArrayList of assignments
     */
    public ArrayList<Assignment> getAssignmentsForCourse(Course course){
        ArrayList<Assignment> result = new ArrayList();
        
        String statement = GET_ASSIGNMENTS_FOR_COURSE;

        // execute the preparedstatement
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)course.getId()});
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                result.add(assignment);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getAssignmentsForCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    /**
     * Get all the course's students
     * @param course
     * @return ArrayList of students
     */
    public ArrayList<Student> getStudentsForCourse(Course course){
        ArrayList<Student> result = new ArrayList();
        
        String statement = GET_SUDENTS_FOR_COURSE;
        
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)course.getId()});
        try {
            while(rs.next()){
                Student student = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
                result.add(student);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getStudentsForCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    /**
     * Get all the students who are not in course
     * @param course
     * @return ArrayList of students
     */
    public ArrayList<Student> getStudentsNotInCourse(Course course){
        ArrayList<Student> result = new ArrayList();
        
        
        String statement =  GET_SUDENTS_NOT_IN_COURSE;

        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)course.getId()});
        try {
            while(rs.next()){
                Student student = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
                result.add(student);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getStudentsNotInCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return result;
    }
    
    
    /**
     * Get all the course's trainers
     * @param course
     * @return ArrayList of trainers
     */
    public ArrayList<Trainer> getTrainersForCourse(Course course){
        ArrayList<Trainer> result = new ArrayList();
        
        String statement = GET_TRAINERS_FOR_COURSE;

        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)course.getId()});
        try {
            while(rs.next()){
                Trainer trainer = new Trainer(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getString("subject"));
                result.add(trainer);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getTrainersForCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
      
        return result;
    }
    
    
    /**
     * Get all the trainers who are not in course
     * @param course
     * @return ArrayList of trainers
     */
    public ArrayList<Trainer> getTrainersNotInCourse(Course course){
        ArrayList<Trainer> result = new ArrayList();
        
        String statement =  GET_TRAINERS_NOT_IN_COURSE;
        
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)course.getId()});
        try {
            while(rs.next()){
                Trainer trainer = new Trainer(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getString("subject"));
                result.add(trainer);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with CourseDao.getTrainersNotInCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    
    /**
     * Adds a student in a course
     * @param course
     * @param student
     */
    public void addStudentInCourse(Course course, Student student){
        
      String statement = ADD_STUDENT_IN_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)student.getId()});
      data.closeConnections(data.ps, data.conn);
    }
    
    
    /**
     * removes a student from a course
     * @param course
     * @param student
     */
    public void removeStudentFromCourse(Course course, Student student){
        
      String statement = REMOVE_STUDENT_FROM_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)student.getId()});
      data.closeConnections(data.ps, data.conn);
    }
    
    
    /**
     * Adds a trainer in a course
     * @param course
     * @param trainer
     */
    public void addTrainerInCourse(Course course, Trainer trainer){
        
      String statement = ADD_TRAINER_IN_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)trainer.getId()});
      data.closeConnections(data.ps, data.conn);
    }
    
    
    /**
     * removes a trainer from a course
     * @param course
     * @param trainer
     */
    public void removeTrainerFromCourse(Course course, Trainer trainer){
        
      String statement = REMOVE_TRAINER_FROM_COURSE;
      data.makeUpdateStatement(statement,new Object[]{(Object)course.getId(),(Object)trainer.getId()});
      data.closeConnections(data.ps, data.conn);
    }

    
    
}
