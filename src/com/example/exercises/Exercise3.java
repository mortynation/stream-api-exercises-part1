package com.example.exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.domain.Director;
import com.example.domain.Genre;
import com.example.domain.Movie;
import com.example.service.InMemoryMovieService;
import com.example.service.MovieService;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise3 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Find the number of genres of each director's movies

		Map<String, Long> noOfGenresOfEachDirectorsMovies = movieService.findAllDirectors().stream().
				collect(Collectors.toMap(
                        Director::getName,
						director -> movieService.findAllMoviesByDirectorId(director.getId())
								.stream().map(Movie::getGenres).flatMap(Collection::stream)
								.distinct().count()
				));

		Map<Director, Map<Genre, Long>> expanded = movieService.findAllDirectors()
						.stream()
								.collect(Collectors.toMap(
										director -> director,
										director -> movieService.findAllMoviesByDirectorId(director.getId())
												.stream().map(Movie::getGenres).flatMap(Collection::stream)
												.collect(Collectors.groupingBy(genre -> genre, Collectors.counting()))
								));

		var directorGenreNumbers =
				movieService.findAllMovies()
						.stream()
						.map( movie -> movie.getDirectors().stream().map(director -> new DirectorGenresPair(director,movie.getGenres())).toList())
						.flatMap(Collection::stream)
						.map( directorGenres -> directorGenres.genres().stream().map( genre -> new DirectorGenrePair(directorGenres.director(),genre) ).toList())
						.flatMap(Collection::stream)
						.collect(Collectors.groupingBy(DirectorGenrePair::director,Collectors.groupingBy(DirectorGenrePair::genre,Collectors.counting())));

		expanded.forEach((key, val) ->
				System.out.println(key + " " + val));


	}

}