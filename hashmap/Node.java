import java.util.HashMap;

public class Node<K,V> {
    private int hash;
    private V value;
    private K key;
    Node<K,V> next;

    Node(K key, V value, int hash){
        this.key = key;
        this.value = value;
        this.hash = hash;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
    public void remove(Node<K,V> table, int count){
        Node<K,V> _current = table;
        int counter = 0;

        while(counter < count-1 && _current != null){
            _current = _current.next;
            counter++;
        }
        _current.next = _current.next.next;
    }
}
