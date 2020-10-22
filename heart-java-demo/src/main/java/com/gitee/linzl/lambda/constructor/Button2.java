package com.gitee.linzl.lambda.constructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Button2 {
    public class Button {
        private String text;

        public Button(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public void test() {
        List<String> labels = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        // ClassName::new
        Stream<Button> buttonStream = labels.stream().map(Button::new);

        Button[] buttons1 = buttonStream.toArray(Button[]::new);
        for (Button button : buttons1) {
            System.out.println(button.getText());
        }
    }

    public void test1() {
        List<Integer> list = this.asList(ArrayList::new, 1, 2, 3, 4, 5);
        list.forEach(System.out::println);
    }

    public <T> List<T> asList(MyList<List<T>> creator, T... a) {
        List<T> list = creator.create();
        for (T t : a) {
            list.add(t);
        }
        return list;
    }

    public static void main(String[] args) {
        Button2 button2 = new Button2();
        button2.test();
        button2.test1();
    }
}