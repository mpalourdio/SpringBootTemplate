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

public class LogEvent {

    private final Class ownerClass;
    private final Level logLevel;
    private final String logMessage;

    public LogEvent(Class ownerClass, Level logLevel, String logMessage) {
        this.ownerClass = ownerClass;
        this.logLevel = logLevel;
        this.logMessage = logMessage;
    }

    public void triggerLogging() {
        AsyncLogger.log(ownerClass, logLevel, logMessage);
    }
}
