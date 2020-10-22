package pl.piwonski.covid19stats.country;

import org.springframework.stereotype.Service;
import pl.piwonski.covid19stats.model.Country;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country getCountryByName(String slug) {
        return countryRepository.findBySlug(slug);
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<Country> addCountries(List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteAll() {
        countryRepository.deleteAll();
    }


}
