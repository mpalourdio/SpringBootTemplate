package com.mpalourdio.springboottemplate.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamServiceAndOtherWtfTest {

    private StreamServiceAndOtherWtf streamServiceAndOtherWtf;

    @BeforeEach
    public void setUp() {
        streamServiceAndOtherWtf = new StreamServiceAndOtherWtf();
    }

    @Test
    public void tetsCollectorAndMap() {
        List<Application> applications = streamServiceAndOtherWtf.getApplications();

        assertEquals("modified", applications.get(0).getName());
        assertEquals("modified", applications.get(1).getName());
    }

    @Test
    public void testStreamDoesNotModifyValuesIfNotCollected() {
        Stream<Application> stream = streamServiceAndOtherWtf.stream;
        String newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        });

        assertNotEquals(newName, streamServiceAndOtherWtf.applicationsList.get(0).getName());
    }

    @Test
    public void testStreamModifiesValuesIfCollected() {
        Stream<Application> stream = streamServiceAndOtherWtf.stream;
        String newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        }).collect(Collectors.toList());

        assertEquals(newName, streamServiceAndOtherWtf.applicationsList.get(0).getName());
    }

    @Test()
    public void testCollectedObjectsAreTheSameInstance() {
        List<Application> applications = streamServiceAndOtherWtf.getApplications();
        List<Application> collect = applications.stream().collect(Collectors.toList());
        assertEquals(collect.get(0), applications.get(0));
    }

    @Test
    public void iHaveNoIdeaWhatIAmDoing() {
        List<Application> applicationsList = streamServiceAndOtherWtf.applicationsList;
        List<Preferences> preferences = streamServiceAndOtherWtf.preferencesList;
        List<Aggregate> aggregates = new ArrayList<>();

        applicationsList.stream()
                .map(a -> {
                    Aggregate aggregate = new Aggregate();
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
