package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;//DB connection ko store krne k liye variable bnaya
    private Scanner scanner;//Scanner naam ka ek variable banata hai future me use krne k liye
    public Patient(Connection connection, Scanner scanner){//Constructor ka main kaam hota hai object ko initialize karna (ready banana)
        this.connection=connection;
        this.scanner=scanner;//Ab scanner ready ho gaya use karne ke liye (scanner obj assign ho gya)
    }
    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();
        // jb bhi database k sath connection interface ka use krte huye connect krte  h toh ek exception aata h jisko hum bolte h SQLException.usko catch krne k liye try catch block ka use krte h

        try {
            String query="INSERT INTO patients(name,age,gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);//same query me alag-alag data daal sakte ho/security/Performance fast
            //Dynamic values dalne ke liye
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            //preparedStatement object ke andar jo method hai (executeUpdate()), usko call kar rahe ho
            //executeUpdate()->INSERT / UPDATE / DELETE query run karna
            int affectedRows=preparedStatement.executeUpdate();
            //executeUpdate() database se number of affected rows return karta hai, aur tum us value ko affectedRows variable me store kar leti ho
            if(affectedRows>0){
                System.out.println("Patient Added Successfully!!");
            }else{
                System.out.println("Failed to add Patient!!");
            }

        }catch(SQLException e){
            e.printStackTrace();//jo bhi particulae exception ka data aa rha hoga usko 1 1 krk print kr denge
        }
    }
    public void viewPatients() {
        //database se sara data laynge or use select krenge
        String query = "select * from patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients : ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age       | Gender      |");
            System.out.println("+------------+--------------------+----------+------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n",id,name,age,gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
            }catch(SQLException e){//jb bhi statement ka use krenge to sql exception aayega
                e.printStackTrace();
            }
        }
         public boolean getPatientById(int id) {
             String query = "SELECT * FROM Patients WHERE id=?";
             try {
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 preparedStatement.setInt(1, id);
                 ResultSet resultSet = preparedStatement.executeQuery();
                 if (resultSet.next()) {
                     return true;
                 } else {
                     return false;
                 }
             } catch (SQLException e) {//jb bhi statement ka use krenge to sql exception aayega
                 e.printStackTrace();
             }
             return false;
         }



}
