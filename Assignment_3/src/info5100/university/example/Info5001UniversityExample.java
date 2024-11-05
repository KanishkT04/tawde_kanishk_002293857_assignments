/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kal bugrara
 */
public class Info5001UniversityExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          // Initialize the Department
        Department department = new Department("Information Systems");

        // Course Catalog Management
        CourseCatalog courseCatalog = department.getCourseCatalog();
        Course coreCourse = courseCatalog.newCourse("Application Engineering", "INFO5100", 4);
        Course elective1 = courseCatalog.newCourse("Web Design", "INFO6200", 4);

        // Faculty Directory Management
        FacultyDirectory facultyDirectory = department.getFacultyDirectory();
        FacultyProfile faculty1 = facultyDirectory.newFacultyProfile(new Person("Prof. Ruben Almorim"));
        FacultyProfile faculty2 = facultyDirectory.newFacultyProfile(new Person("Prof. Carlo Ancelotti"));

        // Course Schedule Management for Fall 2023 Semester
        CourseSchedule courseSchedule = department.newCourseSchedule("Fall2023");
        createCourseOffers(courseSchedule, coreCourse, elective1, faculty1, faculty2);

        // Student Directory and Registration
        Map<String, StudentProfile> students = registerStudents(department, courseSchedule, coreCourse, elective1);

        // Set Faculty Ratings
        setFacultyRatings(courseSchedule);

        // Generate Semester Report
        generateSemesterReport(students, courseSchedule);

        // Calculate and display total department revenue
        int totalRevenue = department.calculateRevenuesBySemester("Fall2023");
        System.out.println("Total Department Revenue for Fall 2023: $" + totalRevenue);
    }

 private static void createCourseOffers(CourseSchedule courseSchedule, Course coreCourse,
                                        Course elective1, FacultyProfile faculty1,
                                        FacultyProfile faculty2) {
    // Create course offers and assign faculty members
    CourseOffer offerCore = courseSchedule.newCourseOffer(coreCourse.getCOurseNumber());
    offerCore.AssignAsTeacher(faculty1);
    offerCore.generatSeats(10); // Generate 10 seats for the core course

    CourseOffer offerElective1 = courseSchedule.newCourseOffer(elective1.getCOurseNumber());
    offerElective1.AssignAsTeacher(faculty2);
    offerElective1.generatSeats(10); // Generate 10 seats for the elective course

    // Assuming you need to keep a list of course offers in CourseSchedule
    courseSchedule.addCourseOffer(offerCore);
    courseSchedule.addCourseOffer(offerElective1);
}
 
    private static Map<String, StudentProfile> registerStudents(Department department, CourseSchedule courseSchedule, 
                                                            Course coreCourse, Course elective1) {
    PersonDirectory personDirectory = department.getPersonDirectory();
    StudentDirectory studentDirectory = department.getStudentDirectory();
    Map<String, StudentProfile> students = new HashMap<>();

    for (int i = 1; i <= 10; i++) {
        Person person = personDirectory.newPerson("01123" + i);
        StudentProfile student = studentDirectory.newStudentProfile(person);
        students.put("Student" + i, student);

        // Course load for Fall 2023 semester
        CourseLoad courseLoad = student.newCourseLoad("Fall2023");
        
        // Use the correct method to get course offers by number
        courseLoad.newSeatAssignment(courseSchedule.getCourseOfferByNumber(coreCourse.getCOurseNumber()));
        courseLoad.newSeatAssignment(courseSchedule.getCourseOfferByNumber(elective1.getCOurseNumber()));
    }
    return students;
}

    private static void setFacultyRatings(CourseSchedule courseSchedule) {
    CourseOffer[] courseOffers = courseSchedule.getAllCourseOffers();

    // Debugging information
    System.out.println("Total Course Offers: " + courseOffers.length);

    if (courseOffers.length > 0 && courseOffers[0].getFacultyProfile() != null) {
        FacultyAssignment assignment1 = courseOffers[0].getFacultyProfile().getFacultyAssignment();
        if (assignment1 != null) {
            assignment1.setProfRating(4.5); // For core course
        }
    }

    if (courseOffers.length > 1 && courseOffers[1].getFacultyProfile() != null) {
        FacultyAssignment assignment2 = courseOffers[1].getFacultyProfile().getFacultyAssignment();
        if (assignment2 != null) {
            assignment2.setProfRating(3.8); // For elective course
        }
    } else {
        System.out.println("Not enough course offers available.");
    }
}

    private static void generateSemesterReport(Map<String, StudentProfile> students, CourseSchedule courseSchedule) {
        System.out.println("=== Fall 2023 Semester Report ===");

        for (StudentProfile student : students.values()) {
            System.out.println("Student ID: " + student.getPerson().getPersonId());
            System.out.println("Courses Registered:");
            CourseLoad courseLoad = student.getCourseLoadBySemester("Fall2023");
            double totalCredits = 0.0;
            double totalGradePoints = 0.0;

            // Calculate grades and GPA
            for (CourseOffer courseOffer : courseSchedule.getAllCourseOffers()) {
                System.out.println(" - " + courseOffer.getSubjectCourse().getCourseName());
                System.out.println("   Faculty: " + courseOffer.getFacultyProfile());

                // Simulating grade calculation
                double grade = Math.random() * 4; // Random grade between 0 and 4
                System.out.println("   Grade: " + grade);
                totalCredits += courseOffer.getSubjectCourse().getCredits();
                totalGradePoints += grade * courseOffer.getSubjectCourse().getCredits();
            }

            double gpa = totalCredits == 0 ? 0 : totalGradePoints / totalCredits;
            System.out.println("Average GPA for Semester: " + gpa);

            // Calculate Tuition Fees (Assume $1000 per credit)
            double tuitionFees = totalCredits * 1000;
            System.out.println("Total Tuition Fees: $" + tuitionFees);
            System.out.println("----------------------------");
        }
    }
}
