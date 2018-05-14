package dobrowol.styloweplywanie.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentAchievementTest {

    StudentAchievement sut;
    @Test
    public void setTime() {
        sut = new StudentAchievement();
        String time = "1:00.000";
        sut.setTime(time);
        assertEquals("60000", sut.time);
        time = "1:00";
        sut.setTime(time);
        assertEquals("60000", sut.time);
        time = "1.20";
        sut.setTime(time);
        assertEquals("1200", sut.time);
        time = "1.2";
        sut.setTime(time);
        assertEquals("1200", sut.time);

    }

    @Test
    public void time2seconds() {
    }
}