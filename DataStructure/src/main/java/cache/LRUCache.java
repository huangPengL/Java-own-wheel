package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huangpenglong
 * @Date: 2023/8/26 10:46
 */
class LRUCache {
    class DLinkedNode{
        int key;
        int val;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode(){}
        public DLinkedNode(int key, int val){
            this.key = key;
            this.val = val;
        }
    }

    class DLinkedList{
        private DLinkedNode head, tail;

        public DLinkedList(){
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }
        public int removeTail(){
            int removeKey = tail.prev.key;
            tail.prev = tail.prev.prev;
            tail.prev.next = tail;

            return removeKey;
        }
        public void remove(DLinkedNode cur){
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }

        public void addHead(DLinkedNode node){
            head.next.prev = node;
            node.next = head.next;
            node.prev = head;
            head.next = node;
        }
        public void moveToHead(DLinkedNode cur){
            this.remove(cur);
            this.addHead(cur);
        }
    }

    private Map<Integer, DLinkedNode> cache;
    private int capacity;
    private DLinkedList dLinkedList;
    public LRUCache(int capacity) {
        cache = new HashMap<>();
        this.capacity = capacity;
        dLinkedList = new DLinkedList();
    }

    public int get(int key) {
        if(!cache.containsKey(key)){
            return -1;
        }

        DLinkedNode cur = cache.get(key);
        dLinkedList.moveToHead(cur);
        return cache.get(key).val;
    }

    public void put(int key, int value) {
        DLinkedNode node;
        if(!cache.containsKey(key)){
            node = new DLinkedNode(key, value);
            cache.put(key, node);
            dLinkedList.addHead(node);
        }
        else{
            node = cache.get(key);
            node.val = value;
            dLinkedList.moveToHead(node);
        }


        if(cache.size() > this.capacity){
            int k = dLinkedList.removeTail();
            cache.remove(k);
        }
    }
}
