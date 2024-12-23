package com.mpalourdio.springboottemplate.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StreamServiceAndOtherWtfTest {

    private StreamServiceAndOtherWtf streamServiceAndOtherWtf;

    @BeforeEach
    void setUp() {
        streamServiceAndOtherWtf = new StreamServiceAndOtherWtf();
    }

    @Test
    void tetsCollectorAndMap() {
        var applications = streamServiceAndOtherWtf.getApplications();

        assertThat(applications.get(0).getName()).isEqualTo("modified");
        assertThat(applications.get(1).getName()).isEqualTo("modified");
    }

    @Test
    void testStreamDoesNotModifyValuesIfNotCollected() {
        var stream = streamServiceAndOtherWtf.stream;
        var newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        });

        assertThat(streamServiceAndOtherWtf.applicationsList.get(0).getName()).isNotEqualTo(newName);
    }

    @Test
    void testStreamModifiesValuesIfCollected() {
        var stream = streamServiceAndOtherWtf.stream;
        var newName = "modified and not collected";
        stream.map(a -> {
            a.setName(newName);
            return a;
        }).collect(Collectors.toList());

        assertThat(streamServiceAndOtherWtf.applicationsList.get(0).getName()).isEqualTo(newName);
    }

    @Test()
    void testCollectedObjectsAreTheSameInstance() {
        var applications = streamServiceAndOtherWtf.getApplications();
        var collect = applications.stream().collect(Collectors.toList());
        assertThat(collect.get(0)).isEqualTo(applications.get(0));
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

        assertThat(aggregates).hasSize(2);
        assertThat(aggregates.get(0).getApplication().getName()).isEqualTo("name1");
        assertThat(aggregates.get(0).getIsFavorite()).isFalse();
    }
}
