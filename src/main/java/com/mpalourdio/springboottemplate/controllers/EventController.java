/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import ch.qos.logback.classic.Level;
import com.mpalourdio.springboottemplate.events.AsyncEvent;
import com.mpalourdio.springboottemplate.events.AsyncLogger;
import com.mpalourdio.springboottemplate.events.MyEvent;
import com.mpalourdio.springboottemplate.mediatype.MediaType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    private final ApplicationEventPublisher eventPublisher;
    private final AsyncLogger logger;

    public EventController(ApplicationEventPublisher eventPublisher, AsyncLogger asyncLogger) {
        this.eventPublisher = eventPublisher;
        logger = asyncLogger;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String publishAction() {
        AsyncEvent asyncEvent = new AsyncEvent();
        eventPublisher.publishEvent(asyncEvent);

        MyEvent event = new MyEvent("\uD83D\uDE0A");
        eventPublisher.publishEvent(event);

        return event.getMessage();
    }

    @GetMapping(value = "/log", produces = MediaType.APPLICATION_JSON_VALUE)
    public String logAction() {
        logger.write(getClass(), Level.TRACE, "TRACE");
        logger.write(getClass(), Level.ERROR, "ERROR");
        logger.write(getClass(), Level.DEBUG, "DEBUG");
        logger.write(getClass(), Level.WARN, "WARN");
        logger.write(getClass(), Level.INFO, "INFO");
        logger.write(getClass(), Level.OFF, "OFF");

        return "things logged (async)";
    }
}
