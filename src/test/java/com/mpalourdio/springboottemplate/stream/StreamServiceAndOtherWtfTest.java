package com.mpalourdio.springboottemplate.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StreamServiceAndOtherWtfTest {

    private StreamServiceAndOtherWtf streamServiceAndOtherWtf;

    @Before
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
}
