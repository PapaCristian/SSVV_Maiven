package ssvv.example;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.PrintWriter;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    Service service;

    private void DeleteFileContents(String file, boolean full) {
        try {
            PrintWriter writer = new PrintWriter(file);
            if (full) {
                writer.print("");
            } else {
                writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<Entitati>\n" +
                        "</Entitati>\n");
            }
            writer.close();
        } catch (Exception e) {}
    }

    private StudentXMLRepository GetEmptyStudentsRepository() {
        Validator<Student> studentValidator = new StudentValidator();
        String fileName = "studenti_test.xml";
        DeleteFileContents(fileName, false);
        return new StudentXMLRepository(studentValidator, fileName);
    }

    private TemaXMLRepository GetEmptyHWRepository() {
        Validator<Tema> validator = new TemaValidator();
        String fileName = "hw_test.xml";
        DeleteFileContents(fileName, false);
        return new TemaXMLRepository(validator, fileName);
    }

    private NotaXMLRepository GetEmptyGradeRepository() {
        Validator<Nota> validator = new NotaValidator();
        String fileName = "grade_test.xml";
        DeleteFileContents(fileName, false);
        return new NotaXMLRepository(validator, fileName);
    }

    private boolean CheckIfStudentPresent(Iterable<Student> iterator, Student student) {
        for (Student value : iterator) {
            if (value.getID().equals(student.getID()) && value.getNume().equals(student.getNume()) && value.getGrupa() == student.getGrupa()) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckIfAssignmentIsPresent(Iterable<Tema> iterator, Tema tema) {
        for (Tema value : iterator) {
            if (value.getID().equals(tema.getID()) && value.getDescriere().equals(tema.getDescriere()) && value.getStartline() == tema.getStartline() && value.getDeadline() == tema.getDeadline()) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckIfGradeIsPresent(Iterable<Nota> iterator, Nota grade) {
        for (Nota value : iterator) {
            if (value.getID().equals(grade.getID()) && value.getNota() == grade.getNota() && value.getSaptamanaPredare() == grade.getSaptamanaPredare() && value.getFeedback().equals(grade.getFeedback())) {
                return true;
            }
        }
        return false;
    }

    @Before
    public void SetService() {
        StudentXMLRepository fileRepository1 = GetEmptyStudentsRepository();
        TemaXMLRepository fileRepository2 = GetEmptyHWRepository();
        NotaXMLRepository fileRepository3 = GetEmptyGradeRepository();

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void test_i_1() { // Add student
        assertEquals(service.saveStudent("1", "Alice", 935), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("1", "Alice", 935)));
    }

    @Test
    public void test_i_2() { // Add assignment
        assertEquals(service.saveTema("1", "Homework", 5, 3), 0);
        assertTrue(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Homework", 5, 3)));
    }

    @Test
    public void test_i_3() { // Add grade
        assertEquals(-1, service.saveNota("1", "1", 9.6, 3, "OK"));
        assertFalse(CheckIfGradeIsPresent(service.findAllNote(), new Nota(new Pair<String, String>("1", "1"), 9.6, 3, "OK")));
    }

    @Test
    public void test_i_4() { // Add all
        assertEquals(service.saveStudent("1", "Alice", 935), 0);
        assertEquals(service.saveTema("1", "Homework", 5, 3), 0);
        assertEquals(service.saveNota("1", "1", 9.6, 5, "OK"), 0);
        assertTrue(CheckIfGradeIsPresent(service.findAllNote(), new Nota(new Pair<String, String>("1", "1"), 9.6, 5, "OK")));
    }

    @Test
    public void test_i_5() { // Grade before deadline
        assertEquals(service.saveStudent("1", "Alice", 935), 0);
        assertEquals(service.saveTema("1", "Homework", 5, 3), 0);
        assertEquals(service.saveNota("1", "1", 9.6, 4, "OK"), 0);
        assertTrue(CheckIfGradeIsPresent(service.findAllNote(), new Nota(new Pair<String, String>("1", "1"), 9.6, 4, "OK")));
    }

    @Test
    public void test_i_6() { // Grade after deadline
        assertEquals(service.saveStudent("1", "Alice", 935), 0);
        assertEquals(service.saveTema("1", "Homework", 5, 3), 0);
        assertEquals(service.saveNota("1", "1", 9.6, 6, "OK"), 0);
        assertTrue(CheckIfGradeIsPresent(service.findAllNote(), new Nota(new Pair<String, String>("1", "1"), 7.1, 6, "OK")));
    }

    @Test
    public void test_i_7() { // Grade after deadline
        assertEquals(service.saveStudent("1", "Alice", 935), 0);
        assertEquals(service.saveTema("1", "Homework", 5, 3), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("1", "Alice", 935)));
        assertTrue(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Homework", 5, 3)));
    }
}
