package jaxb.helloworld;

public class HelloTest {
    
    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.make("Hello", "en");
        hello.make("Good Morning", "en");
        hello.make("Good Afternoon", "en");
        hello.marshal();
    }
}
