package trie;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: huangpenglong
 * @Date: 2023/9/3 11:29
 */
public class SearchSysAutoCompletion {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("广州", "广州大学", "广州塔", "广西", "Guangzhou", "Guangxi");

        Trie trie = new Trie();

        trie.insertList(words);

        Scanner scanner = new Scanner(System.in);
        String cur = scanner.next();
        while(!cur.equals("exit")) {
            List<String> strings = trie.prefixSearch(cur);
            strings.forEach(System.out::println);
            cur = scanner.next();
        }
    }
}
