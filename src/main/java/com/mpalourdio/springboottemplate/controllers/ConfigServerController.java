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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/configserver", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConfigServerController {

    public static final String SEPARATOR = " - ";
    private final String username;
    private final String password;
    private final String common1;
    private final String common2;

    public ConfigServerController(
            @Value("${username}") String username,
            @Value("${password}") String password,
            @Value("${common1}") String common1,
            @Value("${common2}") String common2
    ) {
        this.username = username;
        this.password = password;
        this.common1 = common1;
        this.common2 = common2;
    }

    @GetMapping
    public String getPropertyFromConfigServer() {
        return common1 + SEPARATOR + common2 + " // " + username + SEPARATOR + password;
    }
}
