package io.myzoe.system_design;

import java.util.Arrays;

public class RepeatedNumbers {
    public static int withMutation(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = Math.abs(arr[i]);
            if (arr[idx] > 0) {
                arr[idx] *= -1;
            }
            else {
                return idx;
            }
        }
        return -1;
    }

    public static int withoutMutation(int[] arr) {
        int slow = arr[0];
        int fast = arr[slow];
        while (slow != fast) {
            slow = arr[slow];
            fast = arr[arr[fast]];
        }
        slow = 0;
        while (slow != fast) {
            slow = arr[slow];
            fast = arr[fast];
        }

        return slow;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 3};
        System.out.println(withoutMutation(arr));
        System.out.println(withMutation(arr));
        System.out.println(Arrays.toString(arr));
    }
}
