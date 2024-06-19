package com.example.exercises;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.dao.CityDao;
import com.example.dao.CountryDao;
import com.example.dao.InMemoryWorldDao;
import com.example.domain.City;
import com.example.domain.Country;

import javax.swing.text.html.Option;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise5 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();
	private static final CityDao cityDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Find the highest populated capital city of each continent
		Map<String, Optional<City>> result = countryDao.getAllContinents().stream().filter(Objects::nonNull)
				.collect(Collectors.toMap(continent -> continent,
						continent -> countryDao.findCountriesByContinent(continent).stream()
								.map(Country::getCapital).map(cityDao::findCityById)
								.filter(Objects::nonNull)
								.max(Comparator.comparingInt(City::getPopulation))
						));

		result.forEach((key, value) -> System.out.println(key + " " + value));

	}

}