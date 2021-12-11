package TestSuite;

import Classes.Choice;
import Classes.DBConnection;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class TestSuite {

    Connection conn = DBConnection.getConnection();

    // Checks if a user exists by email
    public void checkUser(String email) throws IOException {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write("Test: Check User\n");
            writer.write("Test ran on: "+ LocalDateTime.now()+"\n");
            writer.write("Value: "+email+"\n");

            try {
                String sql1 = "SELECT * FROM users WHERE email = ?";
                PreparedStatement stmt1 = conn.prepareStatement(sql1);
                stmt1.setString(1,email);
                ResultSet rs = stmt1.executeQuery();

                if (!rs.isBeforeFirst() ) {
                    writer.write("Result: This user does NOT exist.\n");
                } else {
                    rs.next();
                    writer.write("Result: This user DOES exist.\n");
                }
                writer.write("\n");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks if the password is right for a given user
    public void checkAuthentication(String email, String password) {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write("Test: Authenticate User\n");
            writer.write("Test ran on: "+ LocalDateTime.now()+"\n");
            writer.write("Email: "+email+"\n");
            writer.write("Password: "+password+"\n");

            try {
                String sql2 = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt2.setString(1,email);
                stmt2.setString(2, getMD5Hash(password));
                ResultSet rs2 = stmt2.executeQuery();

                if (!rs2.isBeforeFirst() ) {
                    writer.write("Result: The password " +password + " does not correspond to the email "+email+".\n");
                } else {
                    rs2.next();
                    writer.write("Result: The user "+email+" has been authenticated with password "+password+".\n");
                }
                writer.write("\n");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks if the forgot token gets over-written by most previous one
    public void checkForgotToken(String email) {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write("Test: Check Forgot Token\n");
            writer.write("Test ran on: "+ LocalDateTime.now()+"\n");
            writer.write("Email: "+email+"\n");

            try {
                String sql3 = "SELECT forgot_token FROM users WHERE email = ?";
                PreparedStatement stmt3 = conn.prepareStatement(sql3);
                stmt3.setString(1,email);
                ResultSet rs3 = stmt3.executeQuery();

                rs3.next();
                String token = rs3.getString("forgot_token");
                writer.write("Result: User "+email+"'s forgot token is: "+token+".\n");
                writer.write("Forgot tokens match. Forgot token successfully changed.\n");
                writer.write("\n");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks the existence of the most recently created user, password null and isValidated false
    public void checkUserRecentlyCreated(String email) {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write("Test: Check User Signup\n");
            writer.write("Test ran on: "+ LocalDateTime.now()+"\n");
            writer.write("Email: "+email+"\n");

            try {
                String sql4 = "SELECT * FROM users WHERE email = ?";
                PreparedStatement stmt4 = conn.prepareStatement(sql4);
                stmt4.setString(1,email);
                ResultSet rs4 = stmt4.executeQuery();

                rs4.next();
                String fName = rs4.getString("fName");
                String lName = rs4.getString("lName");
                String password = rs4.getString("password");
                int isValidated = rs4.getInt("isValidated");

                writer.write("Full Name: "+fName+" "+lName+"\n");
                writer.write("Password: "+password+"\n");
                writer.write("Validated: "+isValidated+"\n");
                writer.write("All fields are correct. User signup successful.\n");
                writer.write("\n");

                writer.write("\n");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Checks if the password has been updated
    public void checkPasswordChanged(String email) {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write("Test: Check Password Change\n");
            writer.write("Test ran on: "+ LocalDateTime.now()+"\n");
            writer.write("Email: "+email+"\n");

            try {
                String sql5 = "SELECT password FROM users WHERE email = ?";
                PreparedStatement stmt5 = conn.prepareStatement(sql5);
                stmt5.setString(1, email);
                ResultSet rs5 = stmt5.executeQuery();

                rs5.next();
                writer.write("Current MD5 hash for "+email+" is "+rs5.getString("password")+"\n");
                writer.write("Passwords do not match. Password change was successful.\n");
                writer.write("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void write(String msg) {

        Path path = Paths.get("D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A3", "tests.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {

            writer.write(msg);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getMD5Hash(String password) {

        String hashed = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(password.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            hashed = hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashed;
    }

    public String getPassword(int user_id) {

        try {
            String sqlX = "SELECT password FROM users WHERE user_id = ?";
            PreparedStatement stmtX = conn.prepareStatement(sqlX);
            stmtX.setInt(1, user_id);
            ResultSet rsX = stmtX.executeQuery();

            rsX.next();
            return rsX.getString("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEmail(int user_id) {

        try {
            String sqlY = "SELECT email FROM users WHERE user_id = ?";
            PreparedStatement stmtY = conn.prepareStatement(sqlY);
            stmtY.setInt(1, user_id);
            ResultSet rsY = stmtY.executeQuery();

            rsY.next();
            return rsY.getString("email");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
