package com.gitee.linzl.lambda.collection;/*
package com.gitee.linzl.lambda.collection;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import jdk.nashorn.internal.ir.Terminal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.*;1.接口的默认方法
    Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展方法  
    //Formula表示一个设计 计算公式 的接口   
    public interface Formula {  
        //计算  
        double calculate(int a);  
          
        //开方  
        default double sqrt(int a){  
            return Math.sqrt(a);  
        }  
    }  
  
    main:  
    Formula f = new Formula() {  
        @Override  
        public double calculate(int a) {  
            return a+1;  
        }  
    };  
    System.out.println(f.calculate(4));  
    System.out.println(f.sqrt(8));  
  
    注意:现在接口还可以存在静态方法，  
    可以使用 接口名.静态方法名 的形式直接调用  
  
  
2.Lambda 表达式  
    2.1 认识Lambda表达式  
    例如:  
    public class LambdaTest1 {  
      
        public static void main(String[] args) {  
              
            //假如一个list集合中的元素要排序  
            List<String> list = Arrays.asList
            ("hello","tom","apple","bbc");  
            //之前的排序我们可以这样写  
            Collections.sort(list, new Comparator<String>(){
                @Override  
                public int compare(String o1, String o2) {  
                    return -o1.compareTo(o2);  
                }  
            });  
              
            //使用Lambda表达式  
            Collections.sort(list,(String s1,String s2)->{  
                return s1.compareTo(s2);  
            });  
              
            //可以简写为  
            //1.大括号里面就一句代码  
            //2.编译器可以自动推导出参数类型  
            Collections.sort(list,(s1,s2)->s1.compareTo(s2));  
              
            System.out.println(list);  
              
        }  
          
    }  
  
    2.2 Functional接口  
    “函数式接口”是指仅仅只包含一个抽象方法的接口，每一个该类型的lambda表达式都会被匹配到这个抽象方法。因为 默认方法 不算抽象方法，所以你也可以给你的函数式接口添加默认方法。  
    我们可以将lambda表达式当作任意只包含一个抽象方法的接口类型，确保你的接口一定达到这个要求，你只需要给你的接口添加 @FunctionalInterface 注解，编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。  
    例如:  
    public class LambdaTest2 {  
      
        public static void main(String[] args) {  
              
            LambdaTest2 t = new LambdaTest2();  
    //      也可以先创建对象  
    //      Action1 a1 = ()->System.out.println("hello");  
              
            t.test1(()->System.out.println("hello"));  
              
            //Action2<String,Integer> a2 = (f)->"这个数字是:"+f;  
            //如果参数就一个,那么还可以这样简写 去掉小括号  
            Action2<String,Integer> a2 = f->"这个数字是:"+f;  
            t.test2(a2);  
        }  
        public void test1(Action1 a){  
            a.run();  
        }  
        public void test2(Action2<String,Integer> a){  
            System.out.println(a.run(3));  
        }  
          
    }  
    //这个注解不加也可以,加上只是为了让编译器检查  
    @FunctionalInterface  
    interface Action1{  
        public void run();  
    }  
      
    //这个注解不加也可以,加上只是为了让编译器检查  
    @FunctionalInterface  
    interface Action2<T,F>{  
        public T run(F f);  
    }  
      
  
    注意:lambda表达式无法访问接口的默认方法  
  
    2.3 方法与构造函数引用  
    Java 8 允许你使用 :: 关键字来传递方法(静态方法和非静态方法)  
    例如:  
    public class LambdaTest3 {  
        public static void main(String[] args) {  
              
            LambdaTest3 t = new LambdaTest3();  
            //使用Lambda引用类的静态方法  
            //能引用Integer类中的静态方法toBinaryString的原因是:  
            //Action3接口中只有一个方法且方法的参数类型和返回值类型  
            //与Integer类中的静态方法toBinaryString的参数类型、返回类型是一致的  
            Action3 a3 = Integer::toBinaryString;  
            System.out.println(a3.run(4));  
              
            //使用Lambda引用对象的非静态方法  
            //能引用对象t中的非静态方法test的原因是和上面的描述是一致的  
            Action3 aa3 = t::test;  
            System.out.println(aa3.run(4));  
        }  
          
        public String test(int i){  
            return "i="+i;  
        }  
    }  
  
    @FunctionalInterface  
    interface Action3{  
        public String run(int Integer);  
    }  
  
  
    下面是一个接口中带泛型的时候特殊例子: 可以使用  类名::非静态方法  的形式引用方法  
    public class LambdaTest6 {  
      
        public static void main(String[] args) {  
              
            Model m = new Model();  
              
            //方法有一个参数，然后没返回类型,这里参数类型会自动识别  
            Action<Model> a1 = (s)->System.out.println("hello");  
            a1.run(m);  
              
            //注意:如果这里泛型类型不是Model 那么就不能引用Model中的方法  
            //可以引用Model类中任意方法 只要满足一点:该方法没有参数  
            //将来run方法中就会调用Model类型对象m的此处引用的方法  
            Action<Model> a2 = Model::test3;  
            a2.run(m);  
              
            //引用对象m中的test2方法  
            //因为test2方法的参数和返回类型和Action接口的方法完全一致  
            Action<Model> a3 = m::test2;  
            a3.run(m);  
        }  
          
    }  
  
    interface Action<T>{  
        public void run(T t);  
    }  
  
    class Model{  
          
        public void test1(){  
            System.out.println("test1");  
        }  
        public void test2(Model a){  
            System.out.println("test2");  
        }  
        public int test3(){  
            System.out.println("test3");  
            return 1;  
        }  
    }  
      
  
    Java 8 允许你使用 :: 关键字来引用构造函数  
    public class LambdaTest4 {  
          
        public static void main(String[] args) {  
            //Lambda表达式引用构造函数  
            //根据构造器的参数来自动匹配使用哪一个构造器  
            Action4Creater creater = Action4::new;  
            Action4 a4 = creater.create("zhangsan");  
            a4.say();   
        }  
      }  
  
    class Action4{  
        private String name;  
        public Action4() {  
              
        }  
        public Action4(String name) {  
            this.name = name;  
        }  
        public void say(){  
            System.out.println("name = "+name);  
        }  
    }  
  
    interface Action4Creater{  
        public Action4 create(String name);  
    }  
  
    2.4 lambda表达式中的变量访问  
    public class LambdaTest5 {  
        private static int j;  
        private int k;  
        public static void main(String[] args) {  
            LambdaTest5 t = new LambdaTest5();  
            t.test();  
        }  
          
        public void test(){  
            int num = 10;  
            j = 20;  
            k = 30;  
              
            //lambda表达式中可以访问成员变量也可以方法局部变量  
            Action5 a5 = (i)->System.out.println("操作后:i="+(i+num+j+k));  
            a5.run(1);  
              
            //但是这个被访问的变量默认变为final修饰的 不可再改变 否则编译不通过  
            //num = 60;  
            j = 50;  
            k = 70;  
        }  
          
    }  
  
    interface Action5{  
        public void run(int i);  
    }  
      
      
    2.5 Predicate接口和lambda表达式  
    java.util.function.Predicate接口是用来支持java函数式编程新增的一个接口,使用这个接口和lamb表达式就可以以更少的代码为API方法添加更多的动态行为。   
    public class LambdaTest6 {  
        public static void main(String[] args) {  
            List<String> languages = Arrays.asList("Java", "html5","JavaScript", "C++", "hibernate", "PHP");  
              
            //开头是J的语言  
            filter(languages,(name)->name.startsWith("J"));  
            //5结尾的  
            filter(languages,(name)->name.endsWith("5"));  
            //所有的语言  
            filter(languages,(name)->true);  
            //一个都不显示  
            filter(languages,(name)->false);  
            //显示名字长度大于4  
            filter(languages,(name)->name.length()>4);  
            System.out.println("-----------------------");  
            //名字以J开头并且长度大于4的  
            Predicate<String> c1 = (name)->name.startsWith("J");
            Predicate<String> c2 = (name)->name.length()>4;  
            filter(languages,c1.and(c2));  
              
            //名字不是以J开头  
            Predicate<String> c3 = (name)->name.startsWith("J");  
            filter(languages,c3.negate());  
              
            //名字以J开头或者长度小于4的  
            Predicate<String> c4 = (name)->name.startsWith("J");  
            Predicate<String> c5 = (name)->name.length()<4;  
            filter(languages,c4.or(c5));  
              
            //名字为Java的  
            filter(languages,Predicate.isEqual("Java"));  
              
            //判断俩个字符串是否相等  
            boolean test = Predicate.isEqual("hello").test("world");  
            System.out.println(test);  
        }  
        public static void filter(List<String> languages, Predicate<String> condition) {    
            for(String name: languages) {    
                if(condition.test(name)) {    
                    System.out.println(name + " ");    
                }    
            }    
        }    
          
    }  
      
    2.6 Function 接口  
         Function有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法  
         compose方法表示在某个方法之前执行  
         andThen方法表示在某个方法之后执行  
         注意：compose和andThen方法调用之后都会把对象自己本身返回，这可以方便链式编程  
         default <V> Function<T,V> andThen(Function<? super R,? extends V> after) 返回一个先执行当前函数对象apply方法再执行after函数对象apply方法的函数对象。  
  
        default <V> Function<T,V> compose(Function<? super V,? extends T> before)返回一个先执行before函数对象apply方法再执行当前函数对象apply方法的函数对象。  
  
        static <T> Function<T,T> identity() 返回一个执行了apply()方法之后只会返回输入参数的函数对象。  
    public interface Function<T, R> {  
  
        R apply(T t);  
  
        default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {  
            Objects.requireNonNull(before);
            return (V v) -> apply(before.apply(v));  
        }  
  
        default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {  
            Objects.requireNonNull(after);  
            return (T t) -> after.apply(apply(t));  
        }  
          
        //注意: t->t是(t)->t的简写  
        //t->t是作为方法identity的返回值的,也就是Function类型对象  
        //类似于这样的写法:Function<Object, Object> f = t->t;  
        //那么f.apply("test") 返回字符串"test"  
        //传入什么则返回什么  
        static <T> Function<T, T> identity() {  
            return t -> t;  
        }  
    }  
  
    例如:  
    public class LambdaTest7 {  
        //静态内部类  
        private static class Student{  
            private String name;  
            public Student(String name){  
                this.name = name;  
            }  
            public String getName() {  
                return name;  
            }  
              
        }  
        public static void main(String[] args) {  
            */
/*用户注册输入一个名字tom*//*

            String name = "tom";  
              
            */
/*使用用户的输入的名字创建一个对象*//*

            Function<String, Student> f1 =(s)->new Student(s);  
            //注意上面的代码也可以写出这样，引用类中的构造器  
            //Function<String, Student> f1 =Student::new;  
            Student stu1 = f1.apply(name);  
            System.out.println(stu1.getName());  
              
            */
/*需求改变,使用name创建Student对象之前需要给name加一个前缀*//*

            Function<String,String> before = (s)->"briup_"+s;  
            //表示f1调用之前先执行before对象的方法,把before对象的方法返回结果作为f1对象方法的参数  
            Student stu2 = f1.compose(before).apply(name);  
            System.out.println(stu2.getName());  
              
            */
/*获得创建好的对象中的名字的长度*//*

            Function<Student,Integer> after = (stu)->stu.getName().length();  
            //before先调用方法,结果作为参数传给f1来调用方法,结果再作为参数传给after,结果就是我们接收的数据  
            int len = f1.compose(before).andThen(after).apply(name);  
            System.out.println(len);  
              
        }  
          
    }          
 
    2.7 Supplier接口  
    Supplier接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数  
    public interface Supplier<T> {  
        T get();  
    }  
    例如:  
    public class LambdaTest8 {  
        public static void main(String[] args) {  
            //生成一个八位的随机字符串  
            Supplier<String> f = ()->{  
                String base = "abcdefghijklmnopqrstuvwxyz0123456789";       
                Random random = new Random();       
                StringBuffer sb = new StringBuffer();       
                for (int i = 0; i < 8; i++) {    
                    //生成[0,base.length)之间的随机数  
                    int number = random.nextInt(base.length());       
                    sb.append(base.charAt(number));       
                }       
                return sb.toString();     
            };  
            System.out.println(f.get());  
        }  
          
    }  
 
    2.8 Consumer接口  
    Consumer接口接收一个任意范型的值，和Function接口不同的是该接口没有任何值  
    public interface Consumer<T> {  
  
        void accept(T t);  
  
        default Consumer<T> andThen(Consumer<? super T> after) {  
            Objects.requireNonNull(after);  
            return (T t) -> { accept(t); after.accept(t); };  
        }  
    }  
    例如:  
    public class LambdaTest9 {  
        //静态内部类  
        private static class Student{  
            private String name;  
  
            public String getName() {  
                return name;  
            }  
  
            public void setName(String name) {  
                this.name = name;  
            }  
        }  
          
        public static void main(String[] args) {  
            Student s = new Student();  
            s.setName("tom");  
              
            Consumer<Student> c =   
            stu->System.out.println("hello!"+stu.getName());  
            c.accept(s);  
              
        }  
          
    }  
  
    总结:  
        Function<T, R>  接口   R apply(T t);       有参数有返回值  
        Supplier<T>       接口   T get();         没参数有返回值  
        Consumer<T>    接口   void accept(T t); 有参数没返回值  
  
        另外需要注意的接口: 其用法和上面介绍的接口使用方式类同  
        BinaryOperator<T>接口    T apply(T t, T t)  将两个T作为输入，返回一个T作为输出  
        BiFunction<T, U, R>接口  R apply(T t, U u)  将一个T和一个U输入，返回一个R作为输出  
        BinaryOperator接口继承了BiFunction接口  
        public interface BinaryOperator<T> extends BiFunction<T,T,T>
  
        BiConsumer<T, U>接口  void accept(T t, U u) 将俩个参数传入，没有返回值  
 
    2.9 Optional类  
    Optional 不是接口而是一个类，这是个用来防止NullPointerException异常的辅助类型  
    Optional 被定义为一个简单的容器，其值可能是null或者不是null。  
    在Java8之前一般某个函数应该返回非空对象但是偶尔却可能返回了null，而在Java 8中，不推荐你返回null而是返回Optional。  
    这是一个可以为null的容器对象。  
    如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。  
    public class Optotion {  
      
    public static void main(String[] args) {  
          
        */
/*of方法 为非null的值创建一个Optional*//*

        //of方法通过工厂方法创建Optional类。  
        //需要注意的是，创建对象时传入的参数不能为null。  
        //如果传入参数为null，则抛出NullPointerException 。  
        Optional<String> op1 = Optional.of("hello");  
          
        */
/*ofNullable方法 为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional。*//*

        //ofNullable与of方法相似，唯一的区别是可以接受参数为null的情况  
        Optional<String> op2 = Optional.ofNullable(null);  
          
        */
/*isPresent方法 如果值存在返回true，否则返回false。*//*

        */
/*get方法 如果Optional有值则将其返回，否则抛出NoSuchElementException。*//*

        if(op1.isPresent()){  
            System.out.println(op1.get());  
        }  
        if(op2.isPresent()){  
            System.out.println(op2.get());  
        }  
          
        */
/*ifPresent方法 如果Optional实例有值则为其调用consumer，否则不做处理*//*

        //consumer接口中的方法只有参数没有返回值  
        op1.ifPresent(str->System.out.println(str));  
        op2.ifPresent(str->System.out.println(str));//这个不执行 因为op2里面的值是null  
          
          
        */
/*orElse方法 如果有值则将其返回，否则返回指定的其它值。*//*

        System.out.println(op1.orElse("如果op1中的值为null则返回这句话,否则返回这个值"));  
        System.out.println(op2.orElse("如果op2中的值为null则返回这句话,否则返回这个值"));  
          
          
        */
/*orElseGet方法 orElseGet与orElse方法类似，区别在于得到的默认值。orElse方法将传入的字符串作为默认值，orElseGet方法可以接受Supplier接口的实现用来生成默认值。*//*

        //Supplier接口中的方法没有参数但是有返回值  
        System.out.println(op1.orElseGet(()->"自己定义的返回值"));  
        System.out.println(op2.orElseGet(()->"自己定义的返回值"));  
          
          
        */
/*orElseThrow方法 如果有值则将其返回，否则抛出supplier接口创建的异常。*//*

        //在orElseThrow中我们可以传入一个lambda表达式或方法，如果值不存在来抛出异常。  
        //orElseThrow方法的声明如下 所有只能返回一个Throwable类型对象  
        //public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X  
        try {  
            System.out.println(op1.orElseThrow(Exception::new));;  
            //System.out.println(op2.orElseThrow(Exception::new));;这个会抛出异常  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
          
        */
/*map方法 如果有值，则对其执行调用mapper函数得到返回值。*//*

        //返回值并且依然Optional包裹起来,其泛型和你返回值的类型一致  
        //public<U> Optional<U> map(Function<? super T, ? extends U> mapper)  
        Optional<Integer> map1 = op1.map(str->1);  
        System.out.println(map1.get());  
        Optional<Double> map2 = op2.map(str->1.2);  
        System.out.println(map2.orElse(0.0));  
          
          
        */
/*flatMap方法 如果有值，为其执行mapper函数返回Optional类型返回值，否则返回空Optional。*//*

        //flatMap与map方法类似，区别在于flatMap中的mapper返回值必须是Optional。调用结束时，flatMap不会对结果用Optional封装。  
        //需要我们自己把返回值封装为Optional  
        //public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)   
        System.out.println(op1.flatMap(str->Optional.of(str+"_briup")).get());  
        //op1.flatMap(str->"");编译出错  
          
          
        */
/*filter方法 如果有值并且满足断言条件返回包含该值的Optional，否则返回空Optional。*//*

        //public Optional<T> filter(Predicate<? super T> predicate)   
        op1 = op1.filter(str->str.length()<10);  
        System.out.println(op1.orElse("值为null"));  
        op1 = op1.filter(str->str.length()>10);  
        System.out.println(op1.orElse("值为null"));  
    }  
  
    2.10 java.util.stream.Stream 接口
    java.util.Stream 表示能应用在一组元素上一次执行的操作序列。  
    Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，  
    而中间操作返回Stream本身，这样你就可以将多个操作依次串起来(链式编程)。  
    Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set， Map不支持。  
    Stream的操作可以串行执行或者并行执行。  
    Stream 作为 Java 8 的一大亮点，它与 java.io 包里的 InputStream 和 OutputStream 是完全不同的概念。  
    Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、  
    高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。  
    Stream API 借助于同样新出现的Lambda表达式，极大的提高编程效率和程序可读性。  
    同时它提供串行和并行两种模式进行汇聚操作  
  
    2.10.1 Stream对象的构建:  
    // 1.使用值构建  
    Stream<String> stream = Stream.of("a", "b", "c");  
    // 2. 使用数组构建  
    String[] strArray = new String[] {"a", "b", "c"};  
    Stream<String> stream = Stream.of(strArray);  
    Stream<String> stream = Arrays.stream(strArray);  
    // 3. 利用集合构建(不支持Map集合)  
    List<String> list = Arrays.asList(strArray);  
    stream = list.stream();  
  
    对于基本数值型，目前有三种对应的包装类型 Stream：IntStream、LongStream、DoubleStream。
    当然我们也可以用 Stream<Integer>、Stream<Long> 、Stream<Double>，但是 自动拆箱装箱会很耗时，所以特别为这三种基本数值型提供了对应的 Stream。  
    Java 8 中还没有提供其它基本类型数值的Stream  
  
    2.10.2 数值Stream的构建:  
    IntStream stream1 = IntStream.of(new int[]{1, 2, 3});  
    //[1,3)  
    IntStream stream2 = IntStream.range(1, 3);  
    //[1,3]  
    IntStream stream3 = IntStream.rangeClosed(1, 3);  
  
    2.10.3 Stream转换为其它类型:  
    Stream<String> stream = Stream.of("hello","world","tom");  
    // 1. 转换为Array  
    String[] strArray  = stream.toArray(String[]::new);  
    // 2. 转换为Collection  
    List<String> list1 = stream.collect(Collectors.toList());
    List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));  
    Set<String> set3 = stream.collect(Collectors.toSet());  
    Set<String> set4 = stream.collect(Collectors.toCollection(HashSet::new));  
    // 3. 转换为String  
    String str = stream.collect(Collectors.joining()).toString();  
  
    特别注意 : 一个 Stream 只可以使用一次，上面的代码为了简洁而重复使用了多次。  
    这个代码直接运行会抛出异常的:  
    java.lang.IllegalStateException: stream has already been operated upon or closed  
  
  
    2.10.4 Stream操作  
    当把一个数据结构包装成Stream后，就要开始对里面的元素进行各类操作了。常见的操作可以归类如下。  
  
    Intermediate：中间操作  
    map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered  
  
    Terminal： 最终操作
    forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator  
  
    Short-circuiting： 短路操作  
    anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit  
  
  
    map/flatMap映射 把 Stream中 的每一个元素，映射成另外一个元素。  
    例子:  
    转换大写  
    Stream<String> wordList = Stream.of("hello","world","tom");  
    List<String> output = wordList.  
                    map(String::toUpperCase).  
                    collect(Collectors.toList());  
    //也可以直接使用forEach循环输出  
    wordList.map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);  
  
    例子:  
    计算平方数  
    List<Integer> nums = Arrays.asList(1, 2, 3, 4);  
    List<Integer> squareNums =   
                nums.stream().  
                map(n -> n * n).  
                collect(Collectors.toList());  
    map生成的是个1:1映射，每个输入元素，都按照规则转换成为另外一个元素。还有一些场景，是一对多映射关系的，这时需要 flatMap。  
    map和flatMap的方法声明是不一样的  
    <R> Stream<R>      map(Function<? super T, ? extends R> mapper);  
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);  
  
    例子:  
    //stream1中的每个元素都是一个List集合对象  
    Stream<List<Integer>> stream1 = Stream.of(  
                     Arrays.asList(1),  
                     Arrays.asList(2, 3),  
                     Arrays.asList(4, 5, 6)  
                 );  
                Stream<Integer> stream2 = stream1.  
                flatMap((e) -> e.stream());  
                  
    stream2.forEach(e->System.out.println(e));//输出1 2 3 4 5 6  
    flatMap 把 stream1 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终新的 stream2 里面已经没有 List 了，都是直接的数字。  
  
    例子:  
    Stream<String> stream1 = Stream.of("tom.Li","lucy.Liu");  
    //flatMap方法把stream1中的每一个字符串都用[.]分割成了俩个字符串  
    //最后返回了一个包含4个字符串的stream2  
    Stream<String> stream2 = stream1.flatMap(s->Stream.of(s.split("[.]")));  
    stream2.forEach(System.out::println);  
    输出结果:  
        tom  
        Li  
        lucy  
        Liu  
  
  
  
    forEach 遍历 接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。  
    forEach 是 terminal 操作，执行完stream就不能再用了  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    list.stream().forEach(System.out::println);  
  
  
    filter 过滤 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。  
    通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作，所以我们可以在过滤后的结果来应用其他Stream操作（比如forEach）。forEach需要一个函数来对过滤后的元素依次执行。forEach是一个最终操作，所以我们不能在forEach之后来执行其他Stream操作  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    list.stream().filter(s->s.length()>4).forEach(System.out::println);  
    注意:System.out::println 这个是lambda表达式中对静态方法的引用  
  
  
    peek 对每个元素执行操作并返回一个新的 Stream  
    注意:调用peek之后,一定要有一个最终操作  
    peek是一个intermediate 操作  
    例子:  
    List<String> list = Arrays.asList("one", "two", "three", "four");  
    List<String> list2 = list.stream()  
                 .filter(e -> e.length() > 3)  
                 .peek(e -> System.out.println("第一次符合条件的值为: " + e))  
                 .filter(e->e.length()>4)  
                 .peek(e -> System.out.println("第二次符合条件的值为: " + e))  
                 .collect(Collectors.toList());  
    System.out.println(list2.size());//打印结果为 1  
    最后list2中就存放的筛选出来的元素  
  
  
    findFirst 总是返回 Stream 的第一个元素，或者空，返回值类型：Optional。  
    如果集中什么都没有,那么list.stream().findFirst()返回一个Optional<String>对象,  
        但是里面封装的是一个null。  
    例子:  
    List<String> list = Arrays.asList("test","hello","world");  
    Optional<String> first = list.stream().findFirst();  
    System.out.println(first.orElse("值为null"));  
  
  
    sort 排序  
    排序是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。  
    对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip 甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    list.stream().sorted().filter(s->s.startsWith("j")).forEach(System.out::println);  
    //按照字符串的长短排序  
    list.stream().sorted((s1,s2)->s1.length()-s2.length()).forEach(System.out::println);  
    需要注意的是，排序只创建了一个排列好后的Stream，而不会影响原有的数据源，排序之后原数据list是不会被修改的：  
  
  
    Map 映射  
    中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象，  
        下面的示例展示了将字符串转换为大写字符串。  
        你也可以通过map来讲对象转换成其他类型，  
        map返回的Stream类型是根据你map传递进去的函数的返回值决定的。  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    list.stream().map(s->s.toUpperCase()).forEach(System.out::println);  
  
  
    Match 匹配
    Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。  
    所有的匹配操作都是最终操作，并返回一个boolean类型的值。  
    //所有元素匹配成功才返回true 否则返回false  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    boolean allMatch = list.stream().allMatch((s)->s.startsWith("j"));  
    System.out.println(allMatch);  
  
    //任意一个匹配成功就返回true 否则返回false  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    boolean anyMatch = list.stream().anyMatch((s)->s.startsWith("j"));  
    System.out.println(anyMatch);  
  
    //没有一个匹配的就返回true 否则返回false  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    boolean noneMatch = list.stream().noneMatch((s)->s.startsWith("j"));  
    System.out.println(noneMatch);  
  
  
    Count 计数  
    计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。  
    例子:  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    long count = list.stream().filter(s->s.startsWith("j")).count();  
    System.out.println(count);  
  
  
    Reduce 规约/合并  
    这是一个最终操作，允许通过指定的函数来将stream中的多个元素规约合并为一个元素.  
    它提供一个起始值（种子），然后依照运算规则（BinaryOperator），  
        和前面 Stream 的第一个、第二个、第 n 个元素组合。Stream.reduce，  
        常用的方法有average, sum, min, max, and count，返回单个的结果值，  
        并且reduce操作每处理一个元素总是创建一个新值.  
        从这个意义上说，字符串拼接、数值的 sum、min、max等都是特殊的 reduce。  
        例如 Stream 的 sum 就相当于  
    IntStream integers = IntStream.range(1, 10);  
    Integer sum = integers.reduce(0, (a, b) -> a+b); 或  
    Integer sum = integers.reduce(0, Integer::sum);  
    也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。  
    OptionalInt min = integers.reduce((a, b) -> a<b?a:b);  
    // 字符串连接，concat = "ABCD"  
    String concat        = Stream.of("A", "B", "C", "D").reduce("", String::concat);   
    Optional<String> opStr = Stream.of("A", "B", "C", "D").reduce(String::concat);   
  
    例子:  
    List<String> list = Arrays.asList("test","javap","hello","world","java","tom","C","javascript");  
    Optional<String> reduce = list.stream().sorted((s1,s2)->s2.length()-s1.length()).filter(s->s.startsWith("j")).map(s->s+"_briup").reduce((s1,s2)->s1+"|"+s2);  
    System.out.println(reduce.orElse("值为空"));//打印结果为: javascript_briup|javap_briup|java_briup  
    整个代码有点长，可以换行看下:  
    Optional<String> reduce    =  list.stream()  
                      .sorted((s1,s2)->s2.length()-s1.length())  
                      .filter(s->s.startsWith("j"))  
                      .map(s->s+"_briup")  
                      .reduce((s1,s2)->s1+"|"+s2);  
        1.先调用stream方法  
        2.再排序，按照字符串的长度进行排序，长的在前短的再后  
        3.再过滤，字符串必须是以字符'j'开头的  
        4.再进行映射，把每个字符串后面拼接上"_briup"  
        5.再调用reduce进行合并数据,使用"|"连接字符串  
        6.最后返回Optional<String>类型数据，处理好的字符串数据就封装在这个对象中                 
  
    limit/skip   
    limit 返回 Stream 的前面 n 个元素；skip 则是跳过前 n 个元素只要后面的元素  
    例子:  
    List<String> list = Arrays.asList("test","javap","hello","world","java","tom","C","javascript");  
    list.stream().limit(5).forEach(System.out::println);  
    list.stream().skip(5).forEach(System.out::println);  
  
  
  
  
    min/max/distinct  
    例子:  
    找出字符文件中字符字符最长的一行  
    BufferedReader br = new BufferedReader(new FileReader("src/com/briup/test/a.txt"));
    int maxLen = br.lines().  
            mapToInt(String::length).  
            max().  
            getAsInt();  
  
    System.out.println(maxLen);    
    注意:lines方法把文件中所有行都返回并且转换为一个Stream<String>类型对象,因为每行读出的String类型数据,同时String::length是使用方法引用的特殊方式(因为泛型的缘故),上面的笔记中已经介绍过了,max()方法执行后返回的时候OptionalInt类型对象,所以接着调用了getAsInt方法来获得这次运行结果的int值  
  
    例子:  
    找出全文的单词，转小写，去掉空字符,去除重复单词并排序  
    BufferedReader br = new BufferedReader(new FileReader("src/com/briup/test4/day17.txt"));  
    br.lines().  
       flatMap(s->Stream.of(s.split(" "))).  
       filter(s->s.length()>0).  
       map(s->s.toLowerCase()).  
       distinct().  
       sorted().  
       forEach(System.out::println);  
      
  
  
    Stream.generate  
    通过Supplier接口，可以自己来控制Stream的生成。这种情形通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的 Stream。把 Supplier 实例传递给 Stream.generate() 生成的 Stream，由于它是无限的，在管道中，必须利用limit之类的操作限制Stream大小。可以使用此方式制造出海量的测试数据  
    public static<T> Stream<T> generate(Supplier<T> s);  
    例子:  
    生成100个随机数并由此创建出Stream实例  
    Stream.generate(()->(int)(Math.random()*100)).limit(100).forEach(System.out::println);  
          
  
  
    Stream.iterate  
    iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（假设是 f）。  
        然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，  
        f(f(f(seed))) 第四个,以此类推。  
    该方法的声明为:  
    public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)  
  
    UnaryOperator接口继承了Function接口:  
    public interface UnaryOperator<T> extends Function<T, T>  
    例子:  
    生成一个等差数列  
    Stream.iterate(0, n -> n + 3).  
                limit(10).   
                forEach(x -> System.out.print(x + " "));  
    打印结果:  
    0 3 6 9 12 15 18 21 24 27   
  
  
  
  
    Collectors   
    java.util.stream.Collectors 类的主要作用就是辅助进行各类有用的操作。  
    例如把Stream转变输出为 Collection，或者把 Stream 元素进行分组。  
    例子:  
    把Stream中的元素进行过滤然后再转为List集合  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    List<String> result = list.stream().filter(s->s.length()>4).collect(Collectors.toList());  
  
    //分组:按照字符串的长度分组  
    List<String> list = Arrays.asList("test","hello","world","java","tom","C","javascript");  
    //相同长度的字符串放到一个List集合中作为Map的value,字符串的长度作为Map的Key  
    Map<Integer, List<String>> collect = list.stream().collect(Collectors.groupingBy(String::length));  
    //注意下面写法可能写到s->s.length()的时候Eclipse里面有可能不会代码提示，这个要看你先的是=号的哪一边  
    //最终原因还是泛型的事情  
    Map<Integer, List<String>> collect = list.stream().collect(Collectors.groupingBy(s->s.length()));  
          
    //分割:按照字符串是否包含java进行划分  partitioning分割划分的意思  
    Map<Boolean, List<String>> collect =   
            list.stream().collect(Collectors.partitioningBy(s->s.indexOf("java")!=-1));  
    for(Boolean b:collect.keySet()){  
        System.out.println(b+" : "+collect.get(b).size());  
    }  
  
  
    2.11 并行Streams  
    Stream有串行和并行两种，串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。  
    例子:  
    public class LambdaTest12 {  
      
        public static void main(String[] args) {  
              
            //生成100万个不同的字符串放到集合中  
            int max = 1000000;  
            List<String> values = new ArrayList<String>(max);  
            for (int i = 0; i < max; i++) {  
                UUID uuid = UUID.randomUUID();  
                values.add(uuid.toString());  
            }  
  
  
            //1纳秒*10^9=1秒   
            long t0 = System.nanoTime();  
            //串行stream   
            long count = values.stream().sorted().count();  
            //并行stream  
            //long count = values.parallelStream().sorted().count();  
            long t1 = System.nanoTime();  
  
            long time = t1 - t0;  
            System.out.println(count);  
            System.out.println(time);  
        }  
          
    }  
  
    结论:对100万个字符串进行排序和计数操作，串行和并行运算的用时差别还是很明显的  
  
  
  
    2.12 Map集合  
    Map类型不支持stream，不过Map提供了一些新的有用的方法来处理一些日常任务。  
    Java8为Map新增的方法：  
  
    Object compute(Object key, BiFunction remappingFunction):该方法使用remappingFunction根据原key-value对计算一个新的value。只要新的value不为null，就使用新的value覆盖原value；如果新的value为null，则删除原key-value对；  
  
    Object computeIfAbsent(Object key, Function mappingFunction):如果传入的key参数在Map中对应的value为null，该方法将使用mappingFunction根据原key、value计算一个新的结果，则用该计算结果覆盖原value；如果传入的key参数在Map中对应的value为null，则该方法不做任何事情；如果原Map原来不包括该key，该方法可能会添加一组key-value对。  
  
    Object computeIfPresent(Object key, BiFunction remappingFunction):如果传给该方法的key参数在Map中对应的value不为null，该方法将使用remappingFunction根据原key、value计算一个新结果，并且该计算结果不为null，则使用该结果覆盖原来的value；如果计算结果为null，则删除原key-value对。  
  
    void forEach(BiConsumer action):该方法是Java8为Map新增的一个遍历key-value对的方法。
  
    Object getOrDefault(Object key, V defaultValue):获取指定的key对应的value。如果该key不存在，则返回defaultValue。  
  
    Object merge(Object key, Object value, BiFunction remappingFunction):该方法会先根据key参数获取该Map中对应的value。如果获取的value为null，则直接使用传入的value覆盖原value（在这种情况下，可能会添加一组key-value）；如果获取的value不为null，则使用remappingFunction函数根据原value、新value计算一个新的结果，并用新的结果去覆盖原有的value。  
  
    Object putIfAbsent(Object key, Object value):该方法会自动检测指定的key对应的value是否为null，如果该key对应的value为null，则使用传入的新value代替原来的null。如果该key对应的value不是null，那么该方法不做任何事情。  
  
    Object replace(Object key, Object value):将Map中指定key对应的value替换成新value并把被替换掉的旧值返回。如果key在Map中不存在，该方法不会添加key-value对，而是返回null。  
  
    Boolean replace(K key, V oldValue, V newValue):将Map中指定的key-value对的原value替换成新value。如果在Map中找到指定的key-value对，则执行替换并返回true，否则返回false。  
  
    replaceAll(BiFunction function):该方法使用function对原key-value对执行计算，并将计算结果作为key-value对的value值  */
