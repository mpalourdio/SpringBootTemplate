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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Running a GET request here with ACCEPT header = application/vnd.api.v1+json;q=0.9, application/json
 * will receive response from processV2.
 *
 * The same thing will happen if no ACCEPT header is specified
 *
 * @link https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
 * @link http://allegro.tech/2015/01/Content-headers-or-how-to-version-api.html
 */


@RestController
@RequestMapping("/http")
public class HttpVersionedApiController {


    @GetMapping(value = "/test", produces = MediaType.APPLICATION_VND_API_V1_VALUE)
    public String processV1() {
        return "v1";
    }

    @GetMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_VND_API_V2_VALUE})
    public String processV2() {
        return "v2";
    }

    @PostMapping(
            value = "/test",
            consumes = MediaType.APPLICATION_VND_API_V1_VALUE,
            produces = MediaType.APPLICATION_VND_API_V1_VALUE
    )
    public String processV1Post() {
        return "v1-post";
    }

    @PostMapping(
            value = "/test",
            consumes = MediaType.APPLICATION_VND_API_V2_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_VND_API_V2_VALUE}
    )
    public String processV2Post() {
        return "v2-post";
    }

    @PostMapping(value = "/test", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_VND_API_V1_VALUE)
    public String processV1PostConsumeAll() {
        return "v1-post-consumes-all";
    }

    @PostMapping(
            value = "/test",
            consumes = MediaType.ALL_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_VND_API_V2_VALUE}
    )
    public String processV2PostConsumeAll() {
        return "v2-post-consumes-all";
    }
}


