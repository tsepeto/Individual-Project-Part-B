/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sCOOL.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import sCOOL.entity.Assignment;
import sCOOL.entity.Course;
import sCOOL.entity.Student;
import sCOOL.entity.StudentGrade;

/**
 *
 * @author tsepe
 */
public class StudentGradeDao{
    
    private static final String FINDBYID = "SELECT * FROM studentgrades WHERE assignment_id = ? AND course_id = ? AND student_id = ?";
    private static final String INSERT = " insert into studentgrades (assignment_id, course_id, student_id, oral_mark, total_mark) values (?, ?, ?, ?, ?)";
    private static final String EXISTS = "SELECT * FROM studentgrades WHERE assignment_id = ? and course_id = ? and student_id = ?";
    private static final String UPDATE = " UPDATE studentgrades SET  oral_mark = ?, total_mark = ? WHERE assignment_id = ? AND course_id = ? AND student_id = ?";
    
    StudentDao studentDao = new StudentDao();
    AssignmentDao assignmentDao = new AssignmentDao();
    CourseDao courseDao = new CourseDao();
    
    private GenericDao data = GenericDao.getInstance();
    
    
    /**
     * Returns a specific StudentGrade
     * @param student
     * @param course
     * @param assignment
     * @return StudentGrade
     */
    public StudentGrade getStudentGrade(Student student, Course course, Assignment assignment){
        StudentGrade studentGrade = null;
        String statement = FINDBYID;
        ResultSet rs = data.makePreparedStatement(statement, new Object[]{(Object)assignment.getId(),(Object)course.getId(),(Object)student.getId()});
        try {
            while(rs.next()){
                studentGrade = new StudentGrade(studentDao.getById(rs.getInt("student_id")),assignmentDao.getById(rs.getInt("assignment_id")),courseDao.getById(rs.getInt("course_id")),
                        rs.getInt("oral_mark"),rs.getInt("total_mark"));
                
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentGradeDao.getStudentGrade()");
        }finally{
            data.closeConnections(rs,data.ps, data.conn);
        }
        return studentGrade;
    }
    
    
    /**
     * Saves the StudentGrade into the dataBase
     * @param StudentGrade
     */
    public void create(StudentGrade t) {
      String statement = INSERT;
      data.makeUpdateStatement(statement,new Object[]{(Object)t.getAssignment().getId(),(Object)t.getCourse().getId(),
                                                (Object)t.getStudent().getId(),(Object)t.getOralMark(),(Object)t.getTotalMark()});
      data.closeConnections(data.ps, data.conn);
        
    }
    
    
    /**
     * Checks if the StudentGrade
     * @param Student
     * @param Course
     * @param Assignment
     * @return booleans
     */
    public boolean exists(Student student, Course course, Assignment assignment){
        
        boolean exists = true;
        
        String statement = EXISTS;
        ResultSet rs = data.makePreparedStatement(statement,new Object[]{(Object)assignment.getId(),(Object)course.getId(),
                                            (Object)student.getId()});
        try {
            if (rs.next() == false) {
                exists = false;
            }
        } catch (SQLException ex) {
            System.out.println("Problem with StudentGradeDao.exists()");
        }finally{
            data.closeConnections(data.ps, data.conn);
        }

        return exists;
    }
    
    
    /**
     * Updates the studentGrade's parameters in database.
     * User must first edit the studentGrade.
     * @param updatedCourse
     */
    public void update(StudentGrade t){
      String statement = UPDATE;
        data.makeUpdateStatement(statement,new Object[]{(Object)t.getOralMark(),(Object)t.getTotalMark(),(Object)t.getAssignment().getId(),
                                                (Object)t.getCourse().getId(),(Object)t.getStudent().getId()});
        data.closeConnections(data.ps, data.conn);
        
    }
}
