package com.example.javafxproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuBarController {
    public void showCategoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("category.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Categories!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();

    } public void showNewCategoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("newcategory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Add new Category!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showItemScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("item.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Items!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showNewItemScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("newitem.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Add new Item!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showFactoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("factory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Factories!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showNewFactoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("newfactory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Add new Factory!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showStoreScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("store.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Stores!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }

    public void showNewStoreScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("newstore.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Add new Store!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }
    public void showAddressScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("address.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Address!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }
    public void showNewAddressScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("newaddress.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginApplication.getMainStage().setTitle("Create new Address!");
        LoginApplication.getMainStage().setScene(scene);
        LoginApplication.getMainStage().show();
    }
}