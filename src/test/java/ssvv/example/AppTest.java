package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

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

    private boolean isFileEmpty(String file) {
        File openFile = new File(file);
        return openFile.length() == 0;
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

    private Service GetService() {
        StudentXMLRepository fileRepository1 = GetEmptyStudentsRepository();
        TemaXMLRepository fileRepository2 = GetEmptyHWRepository();
        NotaXMLRepository fileRepository3 = GetEmptyGradeRepository();

        return new Service(fileRepository1, fileRepository2, fileRepository3);
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

    @Test
    public void shouldWriteToFileValidStudent_StudentFileRepository() {
        String fileName = "studenti_test.txt";
        Validator<Student> studentValidator = new StudentValidator();
        DeleteFileContents(fileName, true);
        StudentFileRepository fileRepository = new StudentFileRepository(studentValidator, fileName);

        Student validStudent = new Student("200", "Bob", 200);
        fileRepository.save(validStudent);

        assertFalse(isFileEmpty(fileName));
    }

    @Test
    public void shouldNotWriteToFileInvalidStudent_StudentFileRepository() {
        Validator<Student> studentValidator = new StudentValidator();
        String fileName = "studenti_test.txt";
        DeleteFileContents(fileName, true);
        StudentFileRepository fileRepository = new StudentFileRepository(studentValidator, fileName);

        Student validStudent = new Student("200", "Alice", 1);
        fileRepository.save(validStudent);

        assertTrue(isFileEmpty(fileName));
    }

    @Test
    public void test_tc_1() {
        Service service = GetService();

        assertEquals(service.saveStudent("", "A", 225), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("", "A", 225)));
    }

    @Test
    public void test_tc_2() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "", 225), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "", 225)));
    }

    @Test
    public void test_tc_3() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 1), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 1)));
    }

    @Test
    public void test_tc_4() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 225), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 225)));
    }

    @Test
    public void test_tc_5() {
        Service service = GetService();
        service.saveStudent("11", "A", 226);

        assertEquals(service.saveStudent("11", "B", 226), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "B", 226)));
    }

    @Test
    public void test_tc_6() {
        Service service = GetService();

        assertEquals(service.saveStudent("12", "B", 226), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("12", "B", 226)));
    }

    @Test
    public void test_tc_7() {

    }

    @Test
    public void test_tc_8() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 110), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 110)));
    }

    @Test
    public void test_tc_9() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 111), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 111)));
    }

    @Test
    public void test_tc_10() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 112), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 112)));
    }

    @Test
    public void test_tc_11() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 936), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 936)));
    }

    @Test
    public void test_tc_12() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 937), 0);
        assertTrue(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 937)));
    }

    @Test
    public void test_tc_13() {
        Service service = GetService();

        assertEquals(service.saveStudent("11", "A", 938), 1);
        assertFalse(CheckIfStudentPresent(service.findAllStudents(), new Student("11", "A", 938)));
    }

    @Test
    public void test_wbt_tc_1() {
        Service service = GetService();

        assertEquals(service.saveTema(null, "Description", 4, 1), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema(null, "Description", 4, 1)));
    }

    @Test
    public void test_wbt_tc_2() {
        Service service = GetService();

        assertEquals(service.saveTema("", "Description", 4, 1), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("", "Description", 4, 1)));
    }

    @Test
    public void test_wbt_tc_3() {
        Service service = GetService();

        assertEquals(service.saveTema("1", null, 4, 1), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", null, 4, 1)));
    }

    @Test
    public void test_wbt_tc_4() {
        Service service = GetService();

        assertEquals(service.saveTema("1", "", 4, 1), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "", 4, 1)));
    }

    @Test
    public void test_wbt_tc_5() {
        Service service = GetService();

        assertEquals(service.saveTema("1", "Description", 0, -1), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Description", 0, -1)));
    }

    @Test
    public void test_wbt_tc_6() {
        Service service = GetService();

        assertEquals(service.saveTema("1", "Description", 15, 14), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Description", 15, 14)));
    }

    @Test
    public void test_wbt_tc_7() {
        Service service = GetService();

        assertEquals(service.saveTema("1", "Description", 1, 4), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Description", 1, 4)));
    }

    @Test
    public void test_wbt_tc_8() {
        Service service = GetService();

        assertEquals(service.saveTema("1", "Description", 4, 0), 1);
        assertFalse(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Description", 4, 0)));
    }

    @Test
    public void test_wbt_tc_9() { // Valid
        Service service = GetService();

        assertEquals(service.saveTema("1", "Description", 4, 1), 0);
        assertTrue(CheckIfAssignmentIsPresent(service.findAllTeme(), new Tema("1", "Description", 4, 1)));
    }
}
