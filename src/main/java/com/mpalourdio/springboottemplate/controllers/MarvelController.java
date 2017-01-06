/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    private final String marvelPublicKey;
    private final String marvelPrivateKey;

    public MarvelController(
            @Value("${marvel.public.key}") final String marvelPublicKey,
            @Value("${marvel.private.key}") final String marvelPrivateKey
    ) {
        this.marvelPublicKey = marvelPublicKey;
        this.marvelPrivateKey = marvelPrivateKey;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allCharacters() {
        final RestTemplate restTemplate = new RestTemplate();
        final String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        final String hash = DigestUtils.md5Hex(timestamp + marvelPrivateKey + marvelPublicKey);
        final String uri = "http://gateway.marvel.com/v1/public/characters/1009220?apikey=" + marvelPublicKey + "&ts=" + timestamp + "&hash=" + hash;

        return restTemplate.getForEntity(uri, String.class);
    }
}
