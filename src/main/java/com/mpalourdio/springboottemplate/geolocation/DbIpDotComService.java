/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.geolocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

/**
 * @link http://api.db-ip.com/v2/free/145.232.192.197
 * @link https://stackoverflow.com/questions/44190906/translate-country-name-into-other-language
 */
@Slf4j
public final class DbIpDotComService implements IpGeolocationInterface {

    private static final String SERVICE_URL = "http://api.db-ip.com/v2/free/{ipAddress}";
    private final RestTemplate restTemplate;

    public DbIpDotComService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GeoLocationApiResponse geoLocateIp(String ipAddress) {
        GeoLocationApiResponse finalResponse = new GeoLocationApiResponse();

        try {
            GeoLocationApiResponse response = restTemplate.getForObject(SERVICE_URL, GeoLocationApiResponse.class, ipAddress);

            if (response != null) {
                finalResponse = handleResponse(response);
            }
        } catch (HttpClientErrorException e) {
            log.error("Geolocation API call error", e);
        }

        finalResponse.setIp(ipAddress);
        return finalResponse;
    }

    private GeoLocationApiResponse handleResponse(GeoLocationApiResponse response) {
        if (response.getError() != null) {
            log.error("Unable to get geolocation. Error : {}", response.getError());

            return response;
        }

        response.setHasErrored(false);

        return enrichResponseWithFrenchCountryName(response);
    }

    private GeoLocationApiResponse enrichResponseWithFrenchCountryName(GeoLocationApiResponse response) {
        Locale locale = new Locale.Builder().setRegion(response.getCountryCode()).build();
        String displayCountry = locale.getDisplayCountry(Locale.FRENCH);
        response.setCountryName(displayCountry);

        return response;
    }
}
