/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.geolocation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbIpDotComServiceTest {

    @Test
    void testCanGeolocalizeSwissIp() {
        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(new RestTemplateBuilder().build());
        var ipAddress = "145.232.192.197";
        var geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertEquals("Suisse", geoLocationApiResponse.getCountryName());
        assertEquals("Renens", geoLocationApiResponse.getCity());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
        assertFalse(geoLocationApiResponse.isHasErrored());
    }

    @Test
    void testFailsGracefullyIfIpIsFuckedUp() {
        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(new RestTemplateBuilder().build());
        var ipAddress = "666.666.666.666";
        var geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertTrue(geoLocationApiResponse.isHasErrored());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
        assertNotNull(geoLocationApiResponse.getError());
    }

    @Test
    void testFailsGracefullyIfHttpException() {
        var ipAddress = "666.666.666.666";
        var restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), any(), eq(ipAddress))).thenThrow(HttpClientErrorException.class);

        IpGeolocationInterface ipGeolocationService = new DbIpDotComService(restTemplate);
        var geoLocationApiResponse = ipGeolocationService.geoLocateIp(ipAddress);

        assertTrue(geoLocationApiResponse.isHasErrored());
        assertEquals(ipAddress, geoLocationApiResponse.getIp());
    }
}
