/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.comparators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparatorServiceTest {

    private static final String FIRST_LABEL = "aA";
    private static final String SECOND_LABEL = "Bb";
    private static final String THIRD_LABEL = "a";
    private static final String LAST_LABEL = "B";
    private final List<Application> applicationList = new ArrayList<>();
    private ComparatorService comparatorService;

    @BeforeEach
    void setUp() {
        //should come last
        var application1 = new Application();
        application1.setLabel(LAST_LABEL);
        application1.setIsFavorite(false);

        //should come third
        var application2 = new Application();
        application2.setLabel(THIRD_LABEL);
        application2.setIsFavorite(false);

        //should come second
        var application3 = new Application();
        application3.setLabel(SECOND_LABEL);
        application3.setIsFavorite(true);

        //should come first
        var application4 = new Application();
        application4.setLabel(FIRST_LABEL);
        application4.setIsFavorite(true);

        applicationList.add(application1);
        applicationList.add(application2);
        applicationList.add(application3);
        applicationList.add(application4);

        comparatorService = new ComparatorService();
    }

    @Test
    void testCanOrderApplicationsByMultipleComparators() {
        var sortedApps =
                comparatorService.sortApplicationsByFavoriteThenLabel(applicationList);

        assertEquals(FIRST_LABEL, sortedApps.get(0).getLabel());
        assertEquals(SECOND_LABEL, sortedApps.get(1).getLabel());
        assertEquals(THIRD_LABEL, sortedApps.get(2).getLabel());
        assertEquals(LAST_LABEL, sortedApps.get(3).getLabel());
    }
}
