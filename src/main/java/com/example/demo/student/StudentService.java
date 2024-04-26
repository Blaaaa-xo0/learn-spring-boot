package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //funcion que trae a todos los estudiantes de la base de datos
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    //funcion que agrega nuevos estudiantes a la base de datos
    public void addNewStudent(Student student) {

        //verifica si el email ya esta en uso
        Optional<Student> studentOptional = studentRepository.
                findStudentByEmail(student.getEmail());

        //si el email ya esta en uso lanza una excepcion
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        //si no esta en uso guarda al estudiante
        studentRepository.save(student);
    }

    //funcion que elimina estudiantes de la base de datos
    public void deleteStudent(Long studentId) {

        //verifica si el estudiante existe
        boolean exists = studentRepository.existsById(studentId);

        //si no existe lanza una excepcion
        if (!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + " does not exists"
            );
        }

        //elimina al estudiante
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email) {

        //verifica si el estudiante existe
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exists"
                ));

        //si el nombre no es nulo y no es igual al que ya tiene lo actualiza
        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        //si el email no es nulo y no es igual al que ya tiene lo actualiza
        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)){

            //verifica si el email ya esta en uso
            Optional<Student> studentOptional = studentRepository.
                    findStudentByEmail(email);

            //si el email ya esta en uso lanza una excepcion
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }
}
