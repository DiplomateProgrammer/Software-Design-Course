import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

class EventsStatisticImplTest {
    private MockableClock clock;
    private EventsStatistic eventsStatistic;

    @BeforeEach
    void setup() {
        clock = new MockableClock(Instant.now());
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    void testStatisticByNonExistingName() {
        assertThat(eventsStatistic.getEventStatisticByName("HahaTestIdontExist")).isZero();
    }

    @Test
    void testStatisticByName() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("1");

        assertThat(eventsStatistic.getEventStatisticByName("1")).isEqualTo(2.0 / 60);
    }

    @Test
    void testOverdueEvent() {
        eventsStatistic.incEvent("1");
        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        assertThat(eventsStatistic.getEventStatisticByName("1")).isZero();
    }

    @Test
    void testStatisticByNameComplex() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        eventsStatistic.incEvent("1");

        assertThat(eventsStatistic.getEventStatisticByName("1")).isEqualTo(1.0 / 60);
    }

    @Test
    void testAllStatistic() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");

        clock.setNow(Instant.now().plus(30, ChronoUnit.MINUTES));
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("5");
        eventsStatistic.incEvent("2");

        clock.setNow(Instant.now().plus(60, ChronoUnit.MINUTES));

        eventsStatistic.incEvent("3");
        eventsStatistic.incEvent("4");

        Map<String, Double> stats = eventsStatistic.getAllEventStatistic();
        assertThat(stats).containsEntry("1", 1.0 / 60);
        assertThat(stats).containsEntry("2", 2.0 / 60);
        assertThat(stats).containsEntry("3", 1.0 / 60);
    }

    @Test
    void testAllStatisticOverdue() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");

        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        eventsStatistic.incEvent("3");

        Map<String, Double> stats = eventsStatistic.getAllEventStatistic();
        assertThat(stats).doesNotContainKey("1");
        assertThat(stats).containsEntry("3", 1.0 / 60);
    }
}
