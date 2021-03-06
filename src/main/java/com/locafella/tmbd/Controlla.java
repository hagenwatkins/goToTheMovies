package com.locafella.tmbd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class Controlla {
    @RequestMapping(path = "/")
    public String homePage() {
        return "home";
    }

    @RequestMapping(path = "/now-playing")
    public String nowPlaying(Model model) {
        model.addAttribute("movies", getMovies("now-playing"));


        return "now-playing";
    }

    @RequestMapping(path = "/medium-popular-long-name")
    public String mediumPage(Model model) {

        List<Movie> filteredMovies = getMovies("now-playing")
                .stream()
                .filter(movie -> movie.getPopularity() >= 30 && movie.getPopularity() <= 80 && movie.getTitle().length() >= 10)
                .collect(Collectors.toList());
        model.addAttribute("movies", filteredMovies);
        return "medium-popular-long-name";

    }


    public static List<Movie> getMovies(String route) {
        RestTemplate restTemplate = new RestTemplate();
        ResultsPage movies = restTemplate.getForObject("https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb", ResultsPage.class);

        return movies.results;

    }
}


