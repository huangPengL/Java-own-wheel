package trie;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: huangpenglong
 * @Date: 2023/9/3 11:29
 */
public class SensitiveWordMatch {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("白痴", "色色", "脑残", "PORN","porN", "sb", "sm");

        Trie trie = new Trie();

        trie.insertList(words);

        Scanner scanner = new Scanner(System.in);
        String cur = scanner.next();
        while(!cur.equals("exit")) {

            Set<String> matched = trie.matchItems(cur);
            System.out.println("测试敏感词匹配：");
            matched.forEach(System.out::println);
            System.out.println();
            cur = scanner.next();
        }
    }
}
