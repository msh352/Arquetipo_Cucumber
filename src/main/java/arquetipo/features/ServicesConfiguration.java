package arquetipo.features;

import org.junit.Assert;

public class ServicesConfiguration {

    public static final String DUCKDUCKGO = "https://api.duckduckgo.com";
    public static final String WIKIPEDIA = "https://en.wikipedia.org";
    public static final String REQRES = "https://reqres.in";

    //global variables
    public static final String SERVICE = "SERVICE";

    private ServicesConfiguration() {
    }

    public static String buildEndpoint(String service) {
        String baseHost = System.getProperty("env");
        String urlService = service;
        switch (service) {
            case "DUCKDUCKGO":
                urlService = ServicesConfiguration.DUCKDUCKGO;
                break;
            case "REQRES-SERVICE":
                urlService = ServicesConfiguration.REQRES;
                break;
            case "WIKIPEDIA":
                urlService = ServicesConfiguration.WIKIPEDIA;
                break;
            default:
                Assert.fail("Specify a service");
                break;
        }

        switch (baseHost) {
            case "DEV":
                if (urlService.equalsIgnoreCase(ServicesConfiguration.DUCKDUCKGO)) {
                    urlService = urlService + "dev";
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.REQRES)) {
                    urlService = urlService + "dev";
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.WIKIPEDIA)) {
                    urlService = urlService + "dev";
                }
                break;
            case "PRE":
                if (urlService.equalsIgnoreCase(ServicesConfiguration.DUCKDUCKGO)) {
                    urlService = urlService + "pre";
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.REQRES)) {
                    urlService = urlService + "pre";
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.WIKIPEDIA)) {
                    urlService = urlService + "pre";
                }
                break;
            case "PRO":
                if (urlService.equalsIgnoreCase(ServicesConfiguration.DUCKDUCKGO)) {
                    urlService = ServicesConfiguration.DUCKDUCKGO;
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.REQRES)) {
                    urlService = ServicesConfiguration.REQRES;
                }
                if (urlService.equalsIgnoreCase(ServicesConfiguration.WIKIPEDIA)) {
                    urlService = ServicesConfiguration.WIKIPEDIA;
                }
                break;
            default:
                Assert.fail("Specify an environment");
                break;
        }

        return urlService;
    }
}
