import java.util.HashMap;

public class LRUCache<K, V> {
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be a positive number!");
        }
        this.capacity = capacity;
        list = new CacheList();
        map = new HashMap<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        list.moveToFront(map.get(key));
        assert list.head == map.get(key) : "CacheList moveToFront is incorrect!";
        return map.get(key).value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            list.moveToFront(node);
            assert list.head == node : "CacheList is incorrect!";
            return;
        }
        Node node = new Node(key, value);
        map.put(key, node);
        if (map.size() > capacity) {
            popLast();
        }
        list.pushFront(node);
        assert list.head == node : "CacheList is incorrect!";
    }

    private void popLast() {
        Node node = list.popBack();
        map.remove(node.key);
    }


    private class Node {
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K key;
        V value;
        Node next;
        Node prev;
    }

    private class CacheList {
        public CacheList() {
            this.head = this.tail = null;
        }

        public void moveToFront(Node node) {
            if (node == head) {
                return;
            }
            if (node == tail) {
                tail = node.prev;
            }
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            head.prev = node;
            node.prev = null;
            node.next = head;
            head = node;
        }

        public Node popBack() {
            assert tail != null : "Attempting to pop from empty list!";
            Node last = tail;
            if (tail.prev == null) {
                assert head == tail : "Head or prev nodes are incorrect!";
                head = tail = null;
                return last;
            }
            tail.prev.next = null;
            tail = tail.prev;
            return last;
        }

        public void pushFront(Node node) {
            node.prev = null;
            if (head != null) {
                head.prev = node;
                node.next = head;
            } else {
                assert tail == null : "Non-null tail in empty list!";
                node.next = null;
                tail = node;
            }
            head = node;
        }

        private Node head;
        private Node tail;
    }

    private int capacity;
    CacheList list;
    HashMap<K, Node> map;
}
