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

        // 外层循环控制排序轮数
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // 内层循环进行相邻元素比较和交换
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // 如果某一轮没有发生交换，说明数组已经有序，提前结束
            if (!swapped) {
                break;
            }
        }
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

    /**
     * 主方法 - 测试示例
     */
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("排序前:");
        printArray(arr);

        bubbleSort(arr);

        System.out.println("排序后:");
        printArray(arr);
    }
}
