
public class Main {
    public static void main(String[] args) {
        SomeBean sb = new SomeBean();
        sb = new Injector().inject(sb);
        sb.foo();
    }
}