package pyankov.springcourse.librarywebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationConfroller {
    @GetMapping()
    public String getNavigationPage() {
        return "/navigation/navigationPage";
    }
}
