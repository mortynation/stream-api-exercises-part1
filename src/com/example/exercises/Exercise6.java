package com.example.exercises;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.dao.CountryDao;
import com.example.dao.InMemoryWorldDao;
import com.example.domain.Country;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise6 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Sort the countries by number of their cities in descending order
		Map<Country, Integer> lstOfCountries = countryDao.findAllCountries().stream().sorted(new Comparator<Country>() {
					@Override
					public int compare(Country o1, Country o2) {
						return Integer.compare(o2.getCities().size(), o1.getCities().size());
					}
				})
				.collect(Collectors.toMap(country -> country, country -> country.getCities().size()));




//		lstOfCountries.forEach((key, val) -> System.out.println(key + " " + val));


	}

}
