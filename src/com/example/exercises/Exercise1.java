package com.example.exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.example.domain.Director;
import com.example.domain.Movie;
import com.example.service.InMemoryMovieService;
import com.example.service.MovieService;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise1 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Find the number of movies of each director
		Map<String, Long> numberOfMoviesByDirector = movieService.findAllDirectors().stream()
				.collect(Collectors.toMap(Director::getName,
						director -> (long) movieService.findAllMoviesByDirectorId(director.getId()).size()
                ));

		Map<String, Long> dirMovCounts = movieService.findAllMovies().stream().map(Movie::getDirectors)
				.flatMap(Collection::stream).collect(Collectors.groupingBy(Director::getName, Collectors.counting()));

		numberOfMoviesByDirector.forEach((name, count) -> System.out.println(name + " " + count));
	}

}
