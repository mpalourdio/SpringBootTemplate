/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.comparators;

import java.util.Comparator;
import java.util.List;

public class ComparatorService {

    public void sortApplicationsByFavoriteThenLabel(List<Application> applicationList) {
        Comparator<Application> combinedAppComparator = Comparator
                .comparing(Application::getIsFavorite).reversed()
                .thenComparing(Application::getLabel, String.CASE_INSENSITIVE_ORDER);

        applicationList.sort(combinedAppComparator);
    }
}
