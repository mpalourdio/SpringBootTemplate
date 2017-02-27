/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.properties.CredentialsProperties;
import com.mpalourdio.springboottemplate.service.ToSerialize;
import com.mpalourdio.springboottemplate.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/misc")
@RestController
public class MiscController {

    private final String serverPort;
    private final CredentialsProperties credentialsProperties;
    private final UselessBean uselessBean;

    public MiscController(
            @Value("${server.port}") final String serverPort,
            final CredentialsProperties credentialsProperties,
            final UselessBean uselessBean
    ) {
        this.serverPort = serverPort;
        this.credentialsProperties = credentialsProperties;
        this.uselessBean = uselessBean;
    }

    @GetMapping("/basicauth")
    public String restTemplateWithBasicAuth() {
        final RestTemplate rt = new RestTemplateBuilder()
                .basicAuthorization(credentialsProperties.getUsername(), credentialsProperties.getPassword()).build();
        return rt.getForObject("http://localhost:" + serverPort + "/basicauth", String.class);
    }

    @PostMapping(value = "/serialization", produces = MediaType.APPLICATION_JSON_VALUE)
    public ToSerialize testSerialization(@RequestBody final ToSerialize toSerialize) {
        uselessBean.testSerialization(toSerialize);

        return toSerialize;
    }
}
