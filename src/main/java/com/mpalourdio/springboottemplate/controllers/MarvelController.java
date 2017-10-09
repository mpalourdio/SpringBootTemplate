/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.mediatype.MediaType;
import com.mpalourdio.springboottemplate.properties.MarvelProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/marvel")
@EnableConfigurationProperties(MarvelProperties.class)
public class MarvelController {

    private final MarvelProperties marvelProperties;
    private final HttpHeaders addonHeaders = new HttpHeaders();

    public MarvelController(MarvelProperties marvelProperties) {
        this.marvelProperties = marvelProperties;
        buildAddonsHeaders(marvelProperties);
    }

    private void buildAddonsHeaders(MarvelProperties marvelProperties) {
        String unkownProperty = marvelProperties.getUnknownProperty();

        if (StringUtils.isNotBlank(unkownProperty)) {
            Arrays.asList(unkownProperty.split(",")).forEach(h -> {
                String[] split = h.split("=");
                addonHeaders.add(split[0], split[1]);
            });
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allCharacters() {
        RestTemplate restTemplate = new RestTemplate();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String hash = DigestUtils.md5Hex(timestamp + marvelProperties.getPrivatekey() + marvelProperties.getPublickey());
        String uri = "http://gateway.marvel.com/v1/public/characters/1009220?apikey="
                + marvelProperties.getPublickey()
                + "&ts="
                + timestamp
                + "&hash="
                + hash;

        return restTemplate.getForEntity(uri, String.class);
    }
}
