package ch01;

import java.util.Arrays;

public class Q3 {

    public static void removeDuplicates(char[] str) {
        if (str == null) {
            return;
        }
        int len = str.length;
        if (len < 2) {
            return;
        }
        int tail = 1;

        for (int i = 1; i < len; ++i) {
            
            int j;
            for (j = 0; j < tail; ++j) {
                System.out.print(j+",");
                if (str[i] == str[j]) {
                    break;
                }
            }
            System.out.println();
            System.out.println("i="+i);
            System.out.println("j="+j);
            System.out.println("tail="+tail);
            System.out.println(Arrays.toString(str));
            if (j == tail) {
                str[tail] = str[i];
                System.out.println("str["+tail+"] = "+str[i]);
                ++tail;
            }
            System.out.println("tail="+tail);
            System.out.println(Arrays.toString(str));
            System.out.println("=======================================");
        }
        str[tail] = 0;
    }

    public static void removeDuplicatesEff(char[] str) {
        if (str == null) {
            return;
        }
        int len = str.length;
        if (len < 2) {
            return;
        }

        boolean[] hit = new boolean[256];
        for (int i = 0; i < 256; ++i) {
            hit[i] = false;
        }
        hit[str[0]] = true;

        int tail = 1;
        for (int i = 1; i < len; ++i) {
            if (!hit[str[i]]) {
                str[tail] = str[i];
                ++tail;
                hit[str[i]] = true;
            }
        }
        str[tail] = 0;
    }

    public static void main(String[] args) {
        String s = "abab";
        char[] arr = s.toCharArray();
        removeDuplicates(arr);
        System.out.println(Arrays.toString(arr));
    }
}
