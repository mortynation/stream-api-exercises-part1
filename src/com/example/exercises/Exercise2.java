package com.example.exercises;

import java.util.*;
import java.util.stream.Collectors;

import com.example.dao.CountryDao;
import com.example.dao.InMemoryWorldDao;
import com.example.domain.City;
import com.example.domain.Country;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise2 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Find the most populated city of each continent

		Map<String, String> mostPopulatedCityOfEachContinent = new HashMap<>();
		for(String continent : countryDao.getAllContinents()) {
			List<Country> countries = countryDao.findCountriesByContinent(continent);
			List<City> listOfCities = countries.stream()
					.flatMap(country -> country.getCities().stream())
					.toList();
			int currPopulation = 0;
			City maxPopCity = new City();
			for (City city : listOfCities) {
				if(city.getPopulation() > currPopulation) {
					currPopulation = city.getPopulation();
					maxPopCity = city;
				}
			}
			mostPopulatedCityOfEachContinent.put(continent, maxPopCity.getName());
		}

		Map<String, String> mostPopulatedCityOfEachContinentNew =
				countryDao.getAllContinents().stream()
						.collect(Collectors.toMap(
								continent -> continent,
								continent -> countryDao.findCountriesByContinent(continent).stream()
										.flatMap(country -> country.getCities().stream())
										.max(Comparator.comparingInt(City::getPopulation))
										.map(City::getName)
										.orElse("")
						));

		var highPopulatedCityOfEachContinent =
				countryDao.findAllCountries()
						.stream()
						.map( country -> country.getCities().stream().map(city -> new ContinentCityPair(country.getContinent(),city)).toList())
						.flatMap(Collection::stream)
						.collect(Collectors.groupingBy(ContinentCityPair::continent,Collectors.maxBy( ContinentCityPair::compareTo )));

		highPopulatedCityOfEachContinent.forEach(ContinentCityPair::printEntry);


		highPopulatedCityOfEachContinent.forEach((key, value) -> System.out.println(key + " " + value));
	}



}