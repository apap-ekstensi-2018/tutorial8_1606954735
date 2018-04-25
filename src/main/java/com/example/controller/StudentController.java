package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/index")
    public String index (Model model)
    {
    	model.addAttribute("title", "Home");
        return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute("title", "Menambah Mahasiswa");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (Model model,
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);
        model.addAttribute("title", "Sukses Menambah Mahasiswa");

        return "success-add";
    }


//    @RequestMapping("/student/view")
//    public String view (Model model,
//            @RequestParam(value = "npm", required = false) String npm)
//    {
//        StudentModel student = studentDAO.selectStudent (npm);
//
//        if (student != null) {
//            model.addAttribute ("student", student);
//            model.addAttribute("title", "Melihat Data Mahasiswa");
//            return "view";
//        } else {
//            model.addAttribute ("npm", npm);
//            model.addAttribute("title", "Data Mahasiswa Tidak Ada");
//            return "not-found";
//        }
//    }
    
    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
    	return "redirect:/student/view/" + npm;
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            model.addAttribute("title", "Melihat Data Mahasiswa");
            return "view";
        } else {
        	model.addAttribute("title", "Data Mahasiswa Tidak Ada");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
    	model.addAttribute("title", "Daftar Mahasiswa");
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
    	if (student != null) {
    		model.addAttribute("title", "Sukses Menghapus Mahasiswa");
    		studentDAO.deleteStudent (student);
    		return "delete";
    	} else {
    		model.addAttribute("title", "Gagal Menghapus Mahasiswa");
    		model.addAttribute ("npm", npm);
            return "not-found";
    	}        
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
        	model.addAttribute("title", "Memperbarui Data Mahasiswa");
            model.addAttribute ("student", student);
            return "form-update";
        } else {
        	model.addAttribute("title", "Data Mahasiswa Tidak Ada");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit(Model model, @RequestParam(value = "npm", required = false) String npm,
    		@RequestParam(value = "name", required = true) String name,
    		@RequestParam(value = "gpa", required = true) Double gpa) {
    	StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.updateStudent(student);
        model.addAttribute("title", "Sukses Memperbarui Data Mahasiswa");
        return "success-update";
    }
    
    @GetMapping("/student/updateForm/{npm}")
    public String updateForm(Model model, @PathVariable(value = "npm") String npm) {
    	StudentModel student = studentDAO.selectStudent(npm);
    	
    	if (student != null) {
    		model.addAttribute("title", "Memperbarui Data Mahasiswa");
            model.addAttribute ("student", student);
            return "form-update2";
        } else {
        	model.addAttribute("title", "Data Mahasiswa Tidak Ada");
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @PostMapping("/student/updateForm/submit")
    public String updateFormSubmit(Model model, @ModelAttribute StudentModel student) {
    	studentDAO.updateStudent(student);
    	model.addAttribute("title", "Sukses Memperbarui Data Mahasiswa");
    	return "success-update";
    }
}
