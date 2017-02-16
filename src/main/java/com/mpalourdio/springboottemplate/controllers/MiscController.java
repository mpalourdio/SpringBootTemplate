/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/misc")
@RestController
public class MiscController {

    private final String serverPort;
    private final String username;
    private final String password;


    public MiscController(
            @Value("${server.port}") final String serverPort,
            @Value("${admin.username}") final String username,
            @Value("${admin.password}") final String password
    ) {
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
    }

    @GetMapping("/basicauth")
    public String restTemplateWithBasicAuth() {
        final RestTemplate rt = new RestTemplateBuilder().basicAuthorization(username, password).build();
        return rt.getForObject("http://localhost:" + serverPort + "/basicauth", String.class);
    }
}
