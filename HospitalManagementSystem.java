package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    //Ye values DB connect karne ke liye use hoti hain
    private static final String url = "jdbc:mysql://localhost:3306/hospital";//sql workbench se path copy kiya
    private static final String username = "root";//database ka nam
    private static final String password = "shradha";

    public static void main(String[] args) {
        //drivers load krenge jo ki database k sath connect krne k liye neccesary h
        try {//Ye line MySQL driver load karti hai
            Class.forName("com.mysql.cj.jdbc.Driver");//ye class ek exception throw krta h
        } catch (ClassNotFoundException e) {//Agar driver nahi mila → exception
            e.printStackTrace();

        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);//Java aur MySQL ka connection ban gaya
            //Dependency injection means class ke required objects ko andar create na karke bahar se pass karna(connection,scanner)
            Patient patient = new Patient(connection, scanner);
            Doctors doctors = new Doctors(connection);
            while (true) {//tbtk true rhega jbtk iss particular fn se exit nhi kr jate
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Pateint");
                System.out.println("2. View Pateint");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choise: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view pateint
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        //view doctors
                        doctors.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book appointment
                        bookAppointment(patient,doctors,connection,scanner);
                        System.out.println();
                        break;

                    case 5:
                        System.out.println("Thank you for using hospital management system ");
                        return;
                    default:
                        System.out.println("Enter Valid Choice!!");
                        break;

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctors doctors, Connection connection, Scanner scanner) {
        //User se details li
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD):");
        String appointmentDate = scanner.next();
        // patient obj k help se getPatientById method jo batayega  iss particular patientId pr koi patient exist krta h ya nhi usko inwoke kr rhe h .yha se boolean value aayega T or F. ab appointment krne k liye patient or doctor dono available chahiye ,database k andar
        if (patient.getPatientById(patientId) && doctors.getDoctorById(doctorId)) {//patient exist krta h ya nhi AND doctor exist krta h ya nhi
            if (checkDoctorAvailablity(doctorId, appointmentDate, connection)) {//us date pe doctor free hai ya nahi
                String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date)VALUES(?,?,?)";//Appointment table me data insert
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();//Kitni rows insert hui
                    if (rowsAffected > 0) {//Agar insert ho gaya → success message
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to book appointment");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor not available on this date!!");
            }

        } else {
            System.out.println("Either doctor or patient doesn't exist!!");
        }
    }

    public static boolean checkDoctorAvailablity(int doctorId, String appointmentDate, Connection connection) {
        //kitni appointments already hain us doctor ki us date pe
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";//ye no of rows dega .agar koi rows aa rha h mtlb doctor ka appointment already booked h or agar rows 0 h mtlb abhi us doctor ka koi appointment book nhi h
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //COUNT(*) matching rows ko ginta hai aur ek single number return karta hai, isliye first column me wahi number hota hai
                int count = resultSet.getInt(1);//generally colm ya field ka name pass krte h lekin agar int value pass krte h toh wo colm ka index batata h
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}





