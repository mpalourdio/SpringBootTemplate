/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamServiceAndOtherWtf {

    public final List<Application> applicationsList = new ArrayList<>();
    public Stream<Application> stream;

    public StreamServiceAndOtherWtf() {
        Application app1 = new Application();
        app1.setName("name1");
        app1.setIsVisible(true);

        Application app2 = new Application();
        app2.setName("name2");
        app2.setIsVisible(true);

        applicationsList.add(app1);
        applicationsList.add(app2);
        stream = applicationsList.stream();
    }

    public List<Application> getApplications() {
        return stream
                .map(a -> {
                    a.setName("modified");
                    return a;
                })
                .collect(Collectors.toList());
    }
}
