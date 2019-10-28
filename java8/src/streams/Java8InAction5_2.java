package streams;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class Java8InAction5_2 {
    public static void main(String[] args) {
        squares();
        pairs();
        dividePairs();
    }

    private static void squares() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(toList());
        System.out.println(squares);
    }

    private static void pairs(){
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j})
                ).collect(toList());
        System.out.println(pairs);
    }

    private static void dividePairs() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})
                ).collect(toList());
        System.out.println(pairs);
    }
}
