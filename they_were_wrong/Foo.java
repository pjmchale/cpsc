public class Foo {
	public void print(){
		System.out.print("FOO");
	}
	public void print(char c){
		System.out.print(c);
		print();
	}
}