/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class MyEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MyEvent myEvent;

    @EventListener
    public AnotherEvent publishMyEvent(MyEvent myEvent) {
        this.myEvent = myEvent;
        myEvent.setMessage(myEvent.getMessage() + " -> now I have been published");

        return new AnotherEvent();
    }

    @EventListener
    public void publishAnotherEvent(AnotherEvent myOtherEvent) {
        myEvent.setMessage(myEvent.getMessage() + "\n" + myOtherEvent.publishMeToo());
    }

    @Async
    @EventListener
    public void publishMeAsynchronously(AsyncEvent event) throws InterruptedException {
        Thread.sleep(5000);
        logger.debug("I've been fired first in EventController#publishAction, but displayed last");
    }

    @Async
    @EventListener
    public void loggerEventListener(LogEvent event) throws InterruptedException {
        Thread.sleep(5000);
        event.triggerLogging();
    }
}
