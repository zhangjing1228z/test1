public class BubbleSort {

    /**
     * 冒泡排序方法
     * @param arr 待排序的整数数组
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            // 最后 i 个元素已经有序，不需要再比较
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // 如果某一趟没有发生交换，说明数组已经有序，提前结束
            if (!swapped) {
                break;
            }
        }
    }

    // 测试示例
    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("排序前:");
        printArray(array);

        bubbleSort(array);

        System.out.println("排序后:");
        printArray(array);
    }

    /**
     * 打印数组方法
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
