package org.wv.stepsovc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wv.stepsovc.tools.seed.SeedLoader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SeedLoadController {

    @Autowired
    SeedLoader seedLoader;

    @RequestMapping("/load_seeds")
    public void loadSeeds(HttpServletResponse response) throws IOException {
        seedLoader.load();
        response.getWriter().println("Seed load successful");
    }
}
