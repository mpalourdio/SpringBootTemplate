/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.events;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

public class AsyncLogger {

    private final ApplicationEventPublisher eventPublisher;

    public AsyncLogger(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void write(Class ownerClass, Level logLevel, String logMessage) {
        eventPublisher.publishEvent(new LogEvent(ownerClass, logLevel, logMessage));
    }

    public static void log(Class ownerClass, Level logLevel, String logMessage) {
        Logger logger = LoggerFactory.getLogger(ownerClass);

        switch (logLevel.levelInt) {
            case Level.TRACE_INT:
                logger.trace(logMessage);
                break;
            case Level.DEBUG_INT:
                logger.debug(logMessage);
                break;
            case Level.INFO_INT:
                logger.info(logMessage);
                break;
            case Level.WARN_INT:
                logger.warn(logMessage);
                break;
            case Level.ERROR_INT:
                logger.error(logMessage);
                break;
            default:
                logger.info(logMessage);
                break;
        }
    }
}
