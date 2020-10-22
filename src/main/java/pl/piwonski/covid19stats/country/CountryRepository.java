package pl.piwonski.covid19stats.country;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.covid19stats.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findBySlug(String name);
}
