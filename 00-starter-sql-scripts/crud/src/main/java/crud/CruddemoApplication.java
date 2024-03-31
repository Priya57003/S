package crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.crudapi.cruddemo.StudentDAOImpl;
import com.example.Student;
import com.example.StudentDAO;

@SpringBootApplication
public class CruddemoApplication {
	public static void main(String args[])
	{
		SpringApplication.run(CruddemoApplication.class,args);
	}
	

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAOImpl studentDAO) {

		return runner -> {
			// createStudent(studentDAO);

			// createMultipleStudents(studentDAO);

			readStudent(studentDAO);

		};
	}

	private void readStudent(StudentDAOImpl studentDAO) {

		// create  a student object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Aditya", "G", "cogo@luv2code.com");

		// save the student
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		// display id of the saved student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id: " + theId);

		// retrieve student based on the id: primary key
		System.out.println("Retrieving student with id: " + theId);
		com.crudapi.cruddemo.Student myStudent = studentDAO.findById(theId);

		// display student
		System.out.println("Found the student: " + myStudent);
	}

	private void createMultipleStudents(StudentDAOImpl studentDAO) {

		// create multiple students
		System.out.println("Creating 3 student objects ...");
		Student tempStudent1 = new Student("Priya", "Doe", "john@code.com");
		Student tempStudent2 = new Student("Myl", "Public", "mary@luvcode.com");
		Student tempStudent3 = new Student("Sush", "Applebum", "bonita@luv2code.com");

		// save the student objects
		System.out.println("Saving the students ...");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);
	}

	private void createStudent(StudentDAO studentDAO) {

		// create the student object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Paul", "Doe", "paul@luv2code.com");

		// save the student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		// display id of the saved student
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}
}