/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.json.Account;
import com.mpalourdio.springboottemplate.json.AccountDecorator;
import com.mpalourdio.springboottemplate.json.AccountInterface;
import com.mpalourdio.springboottemplate.json.Context;
import com.mpalourdio.springboottemplate.mediatype.MediaType;
import com.mpalourdio.springboottemplate.properties.CredentialsProperties;
import com.mpalourdio.springboottemplate.service.ToSerialize;
import com.mpalourdio.springboottemplate.service.UselessBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/misc")
@RestController
public class MiscController {

    private final String serverPort;
    private final String[] propertiesList;
    private final CredentialsProperties credentialsProperties;
    private final UselessBean uselessBean;

    public MiscController(
            @Value("${server.port}") String serverPort,
            @Value("${list}") String[] propertiesList,
            CredentialsProperties credentialsProperties,
            UselessBean uselessBean
    ) {
        this.serverPort = serverPort;
        this.propertiesList = propertiesList;
        this.credentialsProperties = credentialsProperties;
        this.uselessBean = uselessBean;
    }

    @GetMapping("/basicauth")
    public String restTemplateWithBasicAuth() {
        var rt = new RestTemplateBuilder()
                .basicAuthentication(credentialsProperties.getUsername(), credentialsProperties.getPassword()).build();
        return rt.getForObject("http://localhost:" + serverPort + "/basicauth", String.class);
    }

    @PostMapping(value = "/serialization", produces = MediaType.APPLICATION_JSON_VALUE)
    public ToSerialize testSerialization(@RequestBody ToSerialize toSerialize) {
        uselessBean.testSerialization(toSerialize);

        return toSerialize;
    }

    @PostMapping(value = "/jsonunwrapped", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInterface jsonUnwrapped(@RequestBody Account account) {

        var accountDecorator = new AccountDecorator(account);
        var context = new Context();
        context.ref = "ref";
        accountDecorator.context = context;

        return accountDecorator;
    }

    @PostMapping(value = "/jsonraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInterface jsonraw(@RequestBody Account account) {

        return account;
    }

    @GetMapping(value = "/jsonunwrappedget", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInterface jsonUnwrappedGet() {
        var restTemplate = new RestTemplate();

        var account = new Account();
        account.firstName = "firstName";
        account.lastName = "lastname";

        var httpEntity = new HttpEntity<>(account);
        var exchange = restTemplate.exchange(
                "http://localhost:8080/misc/jsonunwrapped/",
                HttpMethod.POST,
                httpEntity,
                AccountDecorator.class);

        return exchange.getBody();
    }

    @GetMapping(value = "/propertieslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Integer> getList() {
        return Arrays.stream(propertiesList)
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());
    }
}
