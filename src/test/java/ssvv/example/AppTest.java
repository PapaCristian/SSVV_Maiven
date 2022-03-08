package ssvv.example;

import domain.Student;
import org.junit.Test;
import repository.StudentFileRepository;
import repository.StudentXMLRepository;
import validation.StudentValidator;
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
}
