/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.properties.MarvelProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
@EnableConfigurationProperties(MarvelProperties.class)
public class ReactiveController {

    private final MarvelProperties marvelProperties;

    public ReactiveController(MarvelProperties marvelProperties) {
        this.marvelProperties = marvelProperties;
    }

    @GetMapping(value = "/reactive", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<String> get() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String hash = DigestUtils.md5Hex(timestamp + marvelProperties.getPrivatekey() + marvelProperties.getPublickey());
        String uri = "http://gateway.marvel.com/v1/public/characters/1009220?apikey="
                + marvelProperties.getPublickey()
                + "&ts="
                + timestamp
                + "&hash=" + hash;

        WebClient webClient = WebClient.create(uri);

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
