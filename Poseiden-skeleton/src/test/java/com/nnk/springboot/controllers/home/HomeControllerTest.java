package com.nnk.springboot.controllers.home;

import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    @Test
    public void testHomePage() {
        // Créer une instance du contrôleur
        HomeController homeController = new HomeController();

        // Appeler la méthode home
        String viewName = homeController.home();

        // Vérifier que le nom de la vue est "home"
        assertEquals("home", viewName);
    }
}