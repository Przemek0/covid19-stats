package pl.piwonski.covid19stats;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.piwonski.covid19stats.api.data.provider.ApiDataProvider;
import pl.piwonski.covid19stats.country.CountryService;


@Component
public class StartUpController {
    private final CountryService countryService;
    private final ApiDataProvider apiDataProvider;

    public StartUpController(CountryService countryService, ApiDataProvider apiDataProvider) {
        this.countryService = countryService;
        this.apiDataProvider = apiDataProvider;
    }

    @EventListener
    public void onStartUp(ContextRefreshedEvent event) {
        countryService.deleteAll();
        apiDataProvider.getApiData();
    }

}
