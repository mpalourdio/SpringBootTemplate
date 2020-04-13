package com.mpalourdio.springboottemplate.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamServiceAndOtherWtfTest {

    private StreamServiceAndOtherWtf streamServiceAndOtherWtf;

    @BeforeEach
    void setUp() {
        streamServiceAndOtherWtf = new StreamServiceAndOtherWtf();
    }

    @Test
    void tetsCollectorAndMap() {
        var applications = streamServiceAndOtherWtf.getApplications();

        assertEquals("modified", applications.get(0).getName());
        assertEquals("modified", applications.get(1).getName());
    }

    @Test
    void testStreamDoesNotModifyValuesIfNotCollected() {
        var stream = streamServiceAndOtherWtf.stream;
        var newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        });

        assertNotEquals(newName, streamServiceAndOtherWtf.applicationsList.get(0).getName());
    }

    @Test
    void testStreamModifiesValuesIfCollected() {
        var stream = streamServiceAndOtherWtf.stream;
        var newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        }).collect(Collectors.toList());

        assertEquals(newName, streamServiceAndOtherWtf.applicationsList.get(0).getName());
    }

    @Test()
    void testCollectedObjectsAreTheSameInstance() {
        var applications = streamServiceAndOtherWtf.getApplications();
        var collect = applications.stream().collect(Collectors.toList());
        assertEquals(collect.get(0), applications.get(0));
    }

    @Test
    void iHaveNoIdeaWhatIAmDoing() {
        var applicationsList = streamServiceAndOtherWtf.applicationsList;
        var preferences = streamServiceAndOtherWtf.preferencesList;
        List<Aggregate> aggregates = new ArrayList<>();

        applicationsList.stream()
                .map(a -> {
                    var aggregate = new Aggregate();
                    preferences.forEach(p -> {
                        if (p.getAppId() == a.getId()) {
                            aggregate.setApplication(a);
                            aggregate.setIsFavorite(p.getIsFavorite());
                        }
                    });
                    return aggregate;
                })
                .filter(ag -> ag.getApplication() != null)
                .collect(Collectors.toCollection(() -> aggregates));

        assertEquals(2, aggregates.size());
        assertEquals("name1", aggregates.get(0).getApplication().getName());
        assertFalse(aggregates.get(0).getIsFavorite());
    }
}
