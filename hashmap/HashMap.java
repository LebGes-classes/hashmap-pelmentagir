public class HashMap<K,V> implements Hash<K,V>{
    Node<K,V>[] table;
    Node<K,V>[] newTable;
    int size;
    float DEFAULT_LOAD_FACTOR = 0.75f;
    HashMap(){
        this.table = new Node[4];
        this.size = 0;
    }

    public void put(K key, V value){
        int hash = key.hashCode();
        if (hash == 0) {
            throw new IllegalArgumentException("Key must have a non-zero hash code.");
        }
        int tableIndex = hash % table.length;
        Node<K,V> current = table[tableIndex];
        int count = 0;
        // проверочка на коолизию
        while(current != null){
            if(hash == current.getKey().hashCode()){ // проверка на хэшкод ёпта
                if(key.equals(current.getKey())){ //проверка на значение ёпта
                    current.setValue(value);
                    return;
                }
            }
            count++;
            current = current.next;
        }
        if(count >= table.length){
            resize(table.length*2);
        }
        // вставка на первую позицию со сдвигом
        Node<K,V> newNode = new Node<>(key,value,hash);
        newNode.next = table[tableIndex];
        table[tableIndex] = newNode;
        size++;

    }
    private void resize(int newLength){
        newTable = new Node[newLength];
        for(Node<K,V> sus : table){
            //Тут как бы надо все поменять емае
            while(sus != null){
                int tempIndex = sus.getKey().hashCode() % newLength;
                newTable[tempIndex] = sus;
                sus = sus.next;
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key){
        int hash = key.hashCode();
        int tableIndex = hash % table.length;

        Node<K, V> current = table[tableIndex];
        while (current != null){
            if(current.getKey().hashCode() == hash){
                return current.getValue();
            }
            current = current.next;
        }
        return null;
    }
    public boolean contains(K key){
        int hash = key.hashCode();
        int tableIndex = hash % table.length;

        Node<K,V> current = table[tableIndex];
        while (current != null){
            if(current.getKey().hashCode() == hash){
                return true;
            }
            current = current.next;
        }
        return false;
    }
    @Override
    public boolean containsValue(V value){
        Node<K ,V>[] current = table;
        for (Node<K, V> node : current) {
            while (node != null) {
                if (node.getValue().equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V remove(K key) {
        int hash = key.hashCode();
        int indexTable = hash % table.length;
        Node<K, V> current = table[indexTable];
        Node<K, V> prev = null;
        int count = 0;
        while (current != null) {
            if (current.getKey().hashCode() == hash) {
                if (prev == null) {//если первый элемент
                    table[indexTable] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.getValue();
            }
            prev = current;
            count++;
            current = current.next;
        }
        if(DEFAULT_LOAD_FACTOR*table.length > size){
            resize((int)(table.length/1.5));
        }
        // Если ключ не найден
        return null;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void clear() {
        table = new Node[4];
        size = 0;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Node<K, V> node : table) {
            while (node != null) {
                stringBuilder.append(node.getKey()).append("=").append(node.getValue());
                if (node.next != null) {
                    stringBuilder.append(", ");
                }
                node = node.next;
            }
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
