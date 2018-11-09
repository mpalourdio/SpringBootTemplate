/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.geolocation;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DbIpDotComServiceTest {

    @Test
    public void testCanGeolocalizeSwissIp() {
        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(new RestTemplateBuilder().build());
        String ipAddress = "145.232.192.197";
        GeoLocationApiResponse geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertEquals("Suisse", geoLocationApiResponse.getCountryName());
        assertEquals("Renens", geoLocationApiResponse.getCity());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
        assertFalse(geoLocationApiResponse.isHasErrored());
    }

    @Test
    public void testFailsGracefullyIfIpIsFuckedUp() {
        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(new RestTemplateBuilder().build());
        String ipAddress = "666.666.666.666";
        GeoLocationApiResponse geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertTrue(geoLocationApiResponse.isHasErrored());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
        assertNotNull(geoLocationApiResponse.getError());
    }

    @Test
    public void testFailsGracefullyIfHttpException() {
        String ipAddress = "666.666.666.666";
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), any(), eq(ipAddress))).thenThrow(HttpClientErrorException.class);

        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(restTemplate);
        GeoLocationApiResponse geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertTrue(geoLocationApiResponse.isHasErrored());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
    }
}
