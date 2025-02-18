package com.example.javafxproject.production.Files;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.javafxproject.production.main.Main;
import com.example.javafxproject.production.model.Admin;
import com.example.javafxproject.production.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String LOGIN_FILE_NAME="txtfiles/logins.txt";
    public static Long getNextUserID()
    {
        List<User> userList=getUsersFromFile();
        Long lastUserID=userList.stream().map(u ->u.getId()).max((u1,u2) ->u1.compareTo(u2)).get();
        return lastUserID+1;
    }
    public static List<User> getUsersFromFile()
    {
        List<User> userList=new ArrayList<>();
        Optional<User> newUser=Optional.empty();
        File userFile=new File(LOGIN_FILE_NAME);
        try (BufferedReader reader=new BufferedReader(new FileReader(userFile)))
        {
            Optional<String> userIDOptional=Optional.empty();

            while ((userIDOptional=Optional.ofNullable(reader.readLine())).isPresent())
            {
                Long userID=Long.parseLong(userIDOptional.get());
                String usernameString= reader.readLine();
                String passwordString= reader.readLine();
                Integer userType=Integer.parseInt(reader.readLine());
                if (userType.equals(1))
                {
                    Admin admin=new Admin(userID,usernameString,passwordString);
                    userList.add(admin);
                }
                else if (userType.equals(2))
                {
                    newUser=Optional.of(new User(userID,usernameString,passwordString));
                    userList.add(newUser.get());
                }
            }
        }
        catch (IOException exception)
        {
            System.out.println("Dogodila se pogreška kod čitanja datoteke!");
            logger.error("Dogodila se pogreška kod čitanja datoteke! " + exception);
        }
        return userList;
    }
    public static void saveUsersToFile(List<User> userList)
    {
        File userFile=new File(LOGIN_FILE_NAME);
        try (PrintWriter printWriter=new PrintWriter(userFile))
        {
            for (User user:userList)
            {
                printWriter.println(user.getId());
                printWriter.println(user.getUsername());
                printWriter.println(user.getPassword());
                if (user instanceof Admin)
                {
                    printWriter.println(1);
                }
                else printWriter.println(2);
            }
        }
        catch (IOException exception)
        {
            System.out.println("Dogodila se pogreška kod zapisivanja podataka o korisnicima u datoteku.");
            logger.error("Dogodila se pogreška kod zapisivanja podataka o korisnicima u datoteku. "+exception);
        }
    }
}
