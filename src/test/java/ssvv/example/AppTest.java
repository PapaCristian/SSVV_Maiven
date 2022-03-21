package ssvv.example;

import domain.Student;
import org.junit.Test;
import repository.StudentFileRepository;
import validation.StudentValidator;
import validation.ValidationException;
import validation.Validator;

import java.io.File;
import java.io.PrintWriter;

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

    private void DeleteFileContents(String file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (Exception e) {}
    }

    private boolean isFileEmpty(String file) {
        File openFile = new File(file);
        return openFile.length() == 0;
    }

    private StudentFileRepository GetEmptyStudentsRepository() {
        Validator<Student> studentValidator = new StudentValidator();
        String fileName = "studenti_test.txt";
        DeleteFileContents(fileName);
        return new StudentFileRepository(studentValidator, fileName);
    }

    @Test
    public void shouldWriteToFileValidStudent_StudentFileRepository() {
        String fileName = "studenti_test.txt";
        Validator<Student> studentValidator = new StudentValidator();
        DeleteFileContents(fileName);
        StudentFileRepository fileRepository = new StudentFileRepository(studentValidator, fileName);

        Student validStudent = new Student("200", "Bob", 200);
        fileRepository.save(validStudent);

        assertFalse(isFileEmpty(fileName));
    }

    @Test
    public void shouldNotWriteToFileInvalidStudent_StudentFileRepository() {
        Validator<Student> studentValidator = new StudentValidator();
        String fileName = "studenti_test.txt";
        DeleteFileContents(fileName);
        StudentFileRepository fileRepository = new StudentFileRepository(studentValidator, fileName);

        Student validStudent = new Student("200", "Alice", 1);
        fileRepository.save(validStudent);

        assertTrue(isFileEmpty(fileName));
    }

    @Test
    public void test_tc_1() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("", "A", 225);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) { }

        try {
            repository.findOne("11");
            fail();
        } catch (Exception ignored) { }
    }

    @Test
    public void test_tc_2() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "", 225);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) { }

        try {
            repository.findOne("11");
            fail();
        } catch (Exception ignored) { }
    }

    @Test
    public void test_tc_3() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 1);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) { }

        try {
            repository.findOne("11");
            fail();
        } catch (Exception ignored) { }
    }

    @Test
    public void test_tc_4() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 225);

        try {
            repository.save(student);
        } catch (ValidationException ignored) {
            fail();
        }

        try {
            repository.findOne("11");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_tc_5() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 225);
        repository.save(student);
        student = new Student("11", "B", 226);


        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        if (repository.findOne("11").getNume().equals("B")) {
            fail();
        }
    }

    @Test
    public void test_tc_6() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 225);
        repository.save(student);
        student = new Student("12", "B", 226);


        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("12");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_tc_7() {

    }

    @Test
    public void test_tc_8() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 110);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
            fail();
        } catch (Exception ignored) { }
    }

    @Test
    public void test_tc_9() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 111);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    public void test_tc_10() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 112);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    public void test_tc_11() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 936);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    public void test_tc_12() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 937);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    public void test_tc_13() {
        StudentFileRepository repository = GetEmptyStudentsRepository();

        Student student = new Student("11", "A", 938);

        try {
            repository.save(student);
            fail();
        } catch (ValidationException ignored) {
        }

        try {
            repository.findOne("11");
            fail();
        } catch (Exception ignored) { }
    }
}
