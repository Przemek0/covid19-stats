package pl.piwonski.covid19stats.api.data.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.piwonski.covid19stats.country.CountryService;
import pl.piwonski.covid19stats.model.Country;
import pl.piwonski.covid19stats.model.Data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiDataProvider {
    private URL countriesUrl;
    private URL dataUrl;
    private final CountryService countryService;

    public ApiDataProvider(CountryService countryService) {
        this.countryService = countryService;
        try {
            this.countriesUrl = new URL("https://api.covid19api.com/countries");
            this.dataUrl = new URL("https://api.covid19api.com/total/dayone/country/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private List<Data> getData(String countrySlug) {
        List<Data> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            dataList = Arrays.asList(mapper.readValue(new URL(dataUrl.toString() + countrySlug), Data[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            countries = Arrays.asList(mapper.readValue(this.countriesUrl, Country[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public List<Country> getApiData() {
        List<Country> countries = getCountries();
        Thread thread = new Thread(() -> {
            countries.forEach(country -> {
                List<Data> dataList = getData(country.getSlug());
                country.getDataList().addAll(dataList);
                countryService.addCountry(country);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        thread.start();
        return countries;
    }
}
