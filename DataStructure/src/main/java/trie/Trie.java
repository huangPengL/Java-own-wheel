package trie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: huangpenglong
 * @Date: 2023/9/3 10:40
 */
public class Trie {

    /**
     * Trie树节点
     */
    private static class Node{
        Map<Character, Node> children;
        boolean isEnd;

        public Node(){
            children = new ConcurrentHashMap<>();
            isEnd = false;
        }
    }

    /**
     * 初始化Trie
     */
    private final Node root;
    public Trie(){
        root = new Node();
    }

    /**
     * 将元素插入到Trie中
     * @param word
     */
    public void insert(String word){
        if(word == null || word.isEmpty()){
            return;
        }
        Node cur = this.root;
        char[] ch = word.toCharArray();
        for (char curCh : ch) {
            if (!cur.children.containsKey(curCh)) {
                cur.children.put(curCh, new Node());
            }
            cur = cur.children.get(curCh);
        }
        cur.isEnd = true;
    }

    /**
     * 批量插入元素到Trie中
     * @param words
     */
    public void insertList(List<String> words){
        if(words == null || words.isEmpty()){
            return;
        }

        words.forEach(this::insert);
    }

    /**
     * 在Trie中搜索包含前缀target的所有元素，用列表返回
     * @param target
     * @return
     */
    public List<String> prefixSearch(String target){
        // 在Trie中查找目标字符串
        Node cur = startWithTargetNode(target);

        // 查找以target为前缀的所有元素
        List<String> ans = new ArrayList<>();
        dfs(cur, new StringBuilder(target), ans);
        return ans;
    }

    /**
     * 在Trie中寻找包含target子串的所有元素，用列表返回
     * @param target
     * @return
     */
    public List<String> matchItems(String target){
        char[] ch = target.toCharArray();
        int n = ch.length;

        List<String> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            int j = i;
            StringBuilder s = new StringBuilder();
            Node cur = this.root;
            while(j < n){
                if(!cur.children.containsKey(ch[j])){
                    break;
                }
                s.append(ch[j]);
                cur = cur.children.get(ch[j]);
                if(cur.isEnd){
                    ans.add(s.toString());
                }
                j++;
            }
        }

        return ans;
    }

    private Node startWithTargetNode(String target){
        Node cur = this.root;

        char[] ch = target.toCharArray();
        for (char curCh : ch) {
            Node next = cur.children.get(curCh);
            // Trie中不包含该字符串，则停止搜索
            if (next == null) {
                return null;
            }
            cur = next;
        }
        return cur;
    }

    private void dfs(Node cur, StringBuilder s, List<String> ans) {
        if(cur == null){
            return;
        }

        if(cur.isEnd){
            ans.add(s.toString());
            if(cur.children.size() == 0){
                return;
            }
        }

        for(Map.Entry<Character, Node> entry: cur.children.entrySet()){
            s.append(entry.getKey());
            dfs(entry.getValue(), s, ans);
            s.deleteCharAt(s.length()-1);
        }
    }
}
