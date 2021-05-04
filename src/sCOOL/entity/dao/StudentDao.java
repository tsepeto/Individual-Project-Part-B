/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;

/**
 *
 * @author tsepe
 */
public class StudentDao implements CrudInterface<Student>{
    
    private static final String FINDBYID = "SELECT * FROM students WHERE id = ?";
    private static final String FINDBYMAXID = "SELECT * FROM students WHERE id = (SELECT max(id) FROM students)";
    private static final String GET_STUDENTS_WHEN_LESSONS_MORE_THAN = "SELECT * FROM Students S INNER JOIN courses_students C ON S.id = C.student_id GROUP BY S.id HAVING COUNT(*) > ?;";
    private static final String STUDENTEXISTS = "SELECT * FROM students WHERE first_name = ? and last_name = ? and birthday = ? and tuition_fees = ?";
    private static final String FINDALL = "SELECT * FROM students";
    private static final String INSERT = "insert into students (first_name, last_name, birthday, tuition_fees) values (?, ?, ?, ?)";
    private static final String UPDATE = " UPDATE students SET first_name = ?, last_name = ?, birthday = ?, tuition_fees = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM students WHERE id = ?";
    private static final String REMOVE_STUDENT_FROM_COURSES = "DELETE FROM courses_students WHERE student_id = ?";
    private static final String REMOVE_STUDENTGRADES_FOR_STUDENT_WITH_ID = "DELETE FROM studentgrades WHERE student_id = ?";
    private static final String GET_COURSES_FOR_STUDENT = "SELECT * FROM courses C INNER JOIN courses_students R ON C.id =R.course_id" +
                                                          " INNER JOIN students T ON R.student_id = T.id WHERE T.id = ?";
    private static final String GET_ASSIGNMENTS_FOR_STUDENT =   "SELECT * FROM assignments A INNER JOIN courses_assignments R ON A.id =R.assignment_id " +
                                                                "INNER JOIN courses C ON R.course_id = C.id INNER JOIN courses_students X ON C.id = X.course_id " +
                                                                "INNER JOIN students S ON X.student_id = S.id WHERE S.id = ?";
    private static final String GET_RATED_ASSIGNMENTS_FROM_STUDENT_COURSE =   "SELECT * FROM assignments S WHERE S.id IN (SELECT assignment_id " +
                                                                              "FROM  studentgrades C WHERE C.student_id = ? AND C.course_id = ?)";
    private static final String GET_UNRATED_ASSIGNMENTS_FROM_STUDENT_COURSE =   "select * from assignments A join courses_assignments CA on A.id = CA.assignment_id join courses C on CA.course_id = C.id " +
                                                                                "join courses_students CS on C.id = CS.course_id join students S on CS.student_id = S.id where S.id = ?  and C.id = ? and A.id not in " +
                                                                                "(select SG.assignment_id from studentgrades SG where SG.student_id = ? and SG.course_id = ?);";
    
    
    private GenericDao data = GenericDao.getInstance();
    
    
    
    
    
    
    
    /**
     * Returns an ArrayList with Students
     * @return ArrayList with Students
     */
    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> allStudents = new ArrayList();
        String statementQ = FINDALL;
        ResultSet rs = null;
        try {
            rs = data.makeStatement(statementQ);
            while(rs.next()){
                Student student = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
                allStudents.add(student);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getAll()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        
        return allStudents;
    }

    
    
    /**
     * Returns the student by his id
     * @param id
     * @return Student
     */
    @Override
    public Student getById(int id) {
        Student result = null;
        String statement = FINDBYID;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)id});
        try {
            while(rs.next()){
                result = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getById()");
        }
        finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    /**
     * Returns the created student 
     * @return Student
     */
    @Override
    public Student getByMaxId() {
        Student result = null;
        String statement = FINDBYMAXID;
        ResultSet rs = data.makeStatement(statement);
        try {
            while(rs.next()){
                result = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getByMaxId()");
        }
        finally{
            data.closeConnections(rs,data.statement,data.conn);
        }
        return result;
    }

    
    /**
     * Saves the student into the dataBase
     * @param student
     */
    @Override
    public void create(Student t) {
        String statement = INSERT;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getFirstName(),(Object)t.getLastName(),
                                                (Object)t.getDateOfBirth(),(Object)t.getTuitionFees()});
        Student lastCreatedStudent = getByMaxId();                              //after the student creation, we get back the last student with the id
        t.setId(lastCreatedStudent.getId());                                    //and set the id to the given student
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Updates the student's parameters in database.
     * User must first edit the student.
     * @param updatedStudent
     */
    @Override
    public void update(Student t) {
        String statement = UPDATE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getFirstName(),(Object)t.getLastName(),
                                                (Object)t.getDateOfBirth(),(Object)t.getTuitionFees(),(Object)t.getId()});
        
        data.closeConnections(data.ps, data.conn);
        
    }

    
    
    /**
     * Deletes the given student from the database
     * @param student
     */
    @Override
    public void delete(Student t) {
        
        String statement = REMOVE_STUDENT_FROM_COURSES;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
              
        statement = REMOVE_STUDENTGRADES_FOR_STUDENT_WITH_ID;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
        
        statement = DELETE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getId()});
        data.closeConnections(data.ps, data.conn);
        
    }
    
    
    /**
     * Returns all students that are registered to more courses than the given 
     * number
     * @param num
     * @return ArrayList with Students
     */
    public ArrayList<Student> getStudentsWhenLessonsMoreThan(int num){
        ArrayList<Student> allStudents = new ArrayList();
        String statement =  GET_STUDENTS_WHEN_LESSONS_MORE_THAN;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)num});
        try {
            while(rs.next()){
                Student student = new Student(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),
                        rs.getDate("birthday").toLocalDate(),rs.getDouble("tuition_fees"));
                allStudents.add(student);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getStudentsWhenLessonsMoreThan()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return allStudents;
    }
    
    
    /**
     * Checks if the given student exists
     * @param student
     * @return
     */
    @Override
    public boolean exists(Student student) {
        
        boolean exists = true;
        
        String statement = STUDENTEXISTS;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)student.getFirstName(),(Object)student.getLastName(),(Object)student.getDateOfBirth(),(Object)student.getTuitionFees()});
        try {
            if (rs.next() == false) {
                exists = false;
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.studentExists()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }

        return exists;
    }
    
    
    /**
     * Get all the student's courses
     * @param student
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCoursesForStudent(Student student){
        ArrayList<Course> resultCourses = new ArrayList();
        
        String statement = GET_COURSES_FOR_STUDENT;

        ResultSet result = data.makePreparedStatement(statement,new Object[]{(Object)student.getId()});
        try {
            while(result.next()){
                Course course = new Course(result.getInt("id"),result.getString("title"),result.getString("stream"),
                        result.getString("type"),result.getDate("start_date").toLocalDate(),result.getDate("end_date").toLocalDate());
                resultCourses.add(course);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getCoursesForStudent()");
        }finally{
            data.closeConnections(result,data.ps, data.conn);
        }
        return resultCourses;
    }
    
    
    /**
     * Get all the student's assignments
     * @param student
     * @return ArrayList of assignments
     */
    public ArrayList<Assignment> getAssignmentsForStudent(Student student) {
        ArrayList<Assignment> result = new ArrayList();
        String statement  = GET_ASSIGNMENTS_FOR_STUDENT;
        
        // execute the preparedstatement
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)student.getId()});
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                result.add(assignment);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getAssignmentsForStudent()");
        }
        finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        
        return result;
    }
    
    
    /**
     * Returns the rated Assignments by the student_id and course_id
     * @param student
     * @param course
     * @return ArrayList of Assignments
     */
    public ArrayList<Assignment> getRatedAssignmentsFromStudentCourse(Student student,Course course){
        ArrayList<Assignment> result = new ArrayList();
        String statement =  GET_RATED_ASSIGNMENTS_FROM_STUDENT_COURSE;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)student.getId(),(Object)course.getId()});
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                
                result.add(assignment);
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getRatedAssignmentsFromStudentCourse()");
        }
        finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return result;
    }
    
    
    /**
     * Returns the Unrated Assignments by the student_id and course_id
     * @param student
     * @param course
     */
    public ArrayList<Assignment> getUnratedAssignmentsFromStudentCourse(Student student, Course course) {
        ArrayList<Assignment> result = new ArrayList();
        String statement = GET_UNRATED_ASSIGNMENTS_FROM_STUDENT_COURSE;
        
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)student.getId(),(Object)course.getId(),(Object)student.getId(),(Object)course.getId()});
        try {
            while(rs.next()){
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("title"),rs.getString("description"),
                        rs.getDate("sub_dateTime").toLocalDate(),rs.getInt("oral_mark"),rs.getInt("total_mark"));
                result.add(assignment);
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentDao.getUnratedAssignmentsFromStudentCourse()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        

        return result;
    }

    
    
    
}
