import org.junit.Assert;
import org.junit.Test;


public class LRUCacheTest {

    @Test
    public void testOneElem(){
        LRUCache<Integer, Integer> cache = new LRUCache<>(4);
        cache.put(1, 2);
        Assert.assertEquals(2, cache.get(1).intValue());
    }

    @Test
    public void testSmallOverflow(){
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(3, 4);
        Assert.assertNull(cache.get(1));
        Assert.assertEquals(3, cache.get(2).intValue());
        Assert.assertEquals(4, cache.get(3).intValue());
    }

    @Test
    public void testMoveFront(){
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(3, 4);
        Assert.assertEquals(cache.get(1).intValue(), 2);
        cache.put(4, 5);
        Assert.assertEquals(cache.get(1).intValue(), 2);
        Assert.assertEquals(4, cache.get(3).intValue());
        Assert.assertEquals( 5, cache.get(4).intValue());
        Assert.assertNull( cache.get(2));
    }
    @Test
    public void testTwoMoveFront(){
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 1);
        cache.put(2, 2);
        Assert.assertEquals(1, cache.get(1).intValue());
        Assert.assertEquals(2, cache.get(2).intValue());
        cache.put(3, 3);
        Assert.assertNull(cache.get(1));
        Assert.assertEquals(2, cache.get(2).intValue());
        Assert.assertEquals(3, cache.get(3).intValue());
    }
    @Test
    public void testComplex(){
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        Assert.assertEquals(1, cache.get(1).intValue());
        Assert.assertEquals(2, cache.get(2).intValue());
        cache.put(4, 4);
        Assert.assertEquals(2, cache.get(2).intValue());
        Assert.assertEquals(1, cache.get(1).intValue());
        Assert.assertEquals(1, cache.get(1).intValue());
        Assert.assertNull(cache.get(3));
        cache.put(3, 3);
        Assert.assertNull(cache.get(4));
        cache.put(5, 5);
        Assert.assertNull(cache.get(2));
    }
}
