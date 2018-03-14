
public class Test {
	public static void main(String[] args) {
		Foo f1 = new FooBar();
		Bar b = new Bar();
		Foo f2 = new Foo();

		f1.print('f');
		b.print();
		f2.print('f');
	}
}