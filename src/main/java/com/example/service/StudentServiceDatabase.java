package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }


    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }


    @Override
    public void addStudent (StudentModel student)
    {
    	log.info("student " + student.getNpm() + " added");
        studentMapper.addStudent (student);
    }


    @Override
    public void deleteStudent (StudentModel student)
    {
    	log.info("student " + student.getNpm() + " deleted");
    	studentMapper.deleteStudent(student);
    }

    @Override
    public void updateStudent (StudentModel student) {
    	log.info("student " + student.getNpm() + " updated");
    	studentMapper.updateStudent(student);
    }
}
