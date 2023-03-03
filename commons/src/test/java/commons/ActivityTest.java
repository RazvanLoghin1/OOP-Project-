package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActivityTest {
    @Test
    public void emptyConstructorTest() {
        Activity a = new Activity();
        assertNotNull(a);
    }

    @Test
    public void fullConstructorTest() {
        Activity a = new Activity("0", "0", "0", 0, "0");
        assertNotNull(a);
    }

    @Test
    public void allGettersTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals("38-hairdryer", a.getId());
        assertEquals("Using hairdyer for an hour", a.getTitle());
        assertEquals(100, a.getConsumption());
        assertEquals("google.com", a.getSource());
    }

    @Test
    public void allSettersTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        a.setId("id");
        a.setTitle("title");
        a.setConsumption(50);
        a.setSource("bing.com");
        assertEquals("id", a.getId());
        assertEquals("title", a.getTitle());
        assertEquals(50, a.getConsumption());
        assertEquals("bing.com", a.getSource());
    }

    @Test
    public void sameObjectEqualsTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertTrue(a.equals(a));
    }

    @Test
    public void sameAttributesObjectsEqualsTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertTrue(a.equals(b));
    }

    @Test
    public void differentObjectsEqualsTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = new Activity("60-electriccar", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertFalse(a.equals(b));
    }

    @Test
    public void nullObjectEqualsTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = null;
        assertFalse(a.equals(b));
    }

    @Test
    public void hashCodeTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertNotNull(a.hashCode());
    }

    @Test
    public void hashCodeCorrectnessTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals(272090574, a.hashCode());
    }

    @Test
    public void sameAttributesObjectsHashCodeTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        System.out.println(a.hashCode());
        Activity b = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void differentObjectsHashCodeTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = new Activity("60-electriccar", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toStringTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals("Activity{id=38-hairdryer, title='Using hairdyer for an hour', "
                + "consumption=100, source='google.com'}", a.toString());
        System.out.println(a.toString());
    }

    @Test
    public void sameAttributesObjectsToStringTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals(a.toString(), b.toString());
    }

    @Test
    public void differentObjectsToStringTest() {
        Activity a = new Activity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity b = new Activity("60-electriccar", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertNotEquals(a.toString(), b.toString());
    }
}
