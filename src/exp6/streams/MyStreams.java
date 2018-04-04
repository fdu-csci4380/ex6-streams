/**
 * 
 */
package exp6.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ilker
 *
 */
public class MyStreams {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		myStreams_1();
		slide_8a_1();
	}
	
	public static void myStreams_1() {
		// For loop
		List<Student> students = Arrays.asList(new Student[]{new Student("Veli"),new Student("Ali"),new Student("Abba"), new Student("Bob")});
		List<String> names =new ArrayList<>();
		for(Student student : students){
		    if(student.getName().startsWith("A")){
		        names.add(student.getName());
		    }
		}
		
		// Stream doing above with java8 streams
		List<String> names2a = students.stream()
		    .map(Student::getName)
		    .filter(name->name.startsWith("A"))
		    .collect(Collectors.toList());  

		// Stream
		List<String> names2b = students.stream()
		    .map(Student::getName)
		    .filter(name->name.startsWith("A"))
		    .limit(10)
		    .collect(Collectors.toList());    
		
		// Sequential and parallel streams
		Arrays.asList("one","two","three").stream().forEach(System.out::println);
		Arrays.asList("one","two","three").stream().parallel().forEach(System.out::println);
		Arrays.asList("one","two","three").parallelStream().forEach(System.out::println);

		// Building Streams
		//Creating Stream of hardcoded Strings and printing each String
		Stream.of("This", "is", "Java8", "Stream").forEach(System.out::println);
		Arrays.asList("This", "is", "Java8", "Stream").stream().forEach(System.out::println);
		
		//Creating stream of arrays
		String[] stringArray = new String[]{"Streams", "can", "be", "created", "from", "arrays"};
		Arrays.stream(stringArray).forEach(System.out::println);	       

		//Creating BufferedReader for a file
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(Paths.get("File2readAsStream.txt"), StandardCharsets.UTF_8);
			//BufferedReader's lines methods returns a stream of all lines
			reader.lines().forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ways to use "lambda expressions" in streams0
		Arrays.asList("one","two","three").stream().forEach(System.out::println);
		Arrays.asList("one","two","three").stream().forEach(str -> System.out.println(str));
		Arrays.asList("one","two","three").stream().forEach((String str) -> System.out.println(str));
		Arrays.asList("one","two","three").stream().forEach((String str) -> { System.out.println(str);});
		Arrays.asList("one","two","three").stream().forEach((String str) -> {
		    System.out.println(str);
		    System.out.println("bla bla");
		});
		
		// forEach
		Stream.of("a","ab","abc","abcd").forEach(System.out::println);
		
		// map
		Arrays.asList("a","ab","abc","abcd").stream().map(str -> str.length()).forEach(System.out::println);
		Arrays.asList("a","ab","abc","abcd").stream().map(String::length).forEach(System.out::println);
		
		// filter
		Arrays.asList("a","ab","abc","abcd").stream().filter(str -> str.length() >= 2).forEach(System.out::println);
				
		// collect
		List<Integer> strLengths = Arrays.asList("a","ab","abc","abcd").stream().map(str -> str.length()).collect(Collectors.toList());
		
		// T reduce(T identity, BinaryOperator<T> accumulator) 	
		// identity;initial value of reduce), accumulator; a function for combining 2 values 	
		// accumulator takes 2 inputs; 1st one return value of previous accumulator, 2nd one current stream element
		int maxInt1 =  Stream.of(12,3,8,9).reduce(Integer.MIN_VALUE, Math::max);
		int maxInt2 =  Stream.of(12,3,8,9).reduce(Integer.MIN_VALUE, (maxVal, currentVal) -> Math.max(maxVal, currentVal));
		int maxInt3 =  Stream.of(12,3,8,9).max(Integer::compare).get();
		int sumInt1 =  Stream.of(11,3,8,9).reduce(0, (sumVal, currentVal) -> Integer.sum(sumVal, currentVal));
		int sumInt2 =  Stream.of(11,3,8,9).mapToInt(Integer::intValue).sum();

		// Sorting
		Arrays.asList("a2","a1","b3","a3","b2","b1","c4","c2")
		.stream()
		.map(String::toUpperCase)
		.sorted()
		.forEach(System.out::println);
		
		List<BigDecimal> list = Arrays.asList(new BigDecimal("31.31"),new BigDecimal("21.21"),new BigDecimal("11.11"),new BigDecimal("41.41"));
		final List<BigDecimal> sortedList = list.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

		// findFirst , findAny
		Optional<String> optionalResult = Arrays.asList("a","ab","abc","abcd", "xyz1", "xyz2","xyz3", "xy", "xyz").stream()
		.filter(str -> str.startsWith("xy"))
		.findFirst();
		System.out.println( optionalResult.isPresent() ? optionalResult.get() : "notPresent" );
		
		Arrays.asList("a","ab","abc","abcd", "xyz1", "xyz2","xyz3", "xy", "xyz").stream()
		.filter(str -> str.startsWith("xy"))
		.findFirst()
		.ifPresent(System.out::println);
		
		String result = Arrays.asList("a","ab","abc","abcd", "xyz1", "xyz2","xyz3", "xy", "xyz").stream()
		.filter(str -> str.startsWith("xy"))
		.findFirst()
		.orElse("Nothing starts with 'xy'");
		System.out.println(result);
		
		String result2 = Arrays.asList("a","ab","abc","abcd", "xyz1", "xyz2","xyz3", "xy", "xyz").stream()
		.filter(str -> str.startsWith("xy"))
		.findAny()
		.orElse("Nothing starts with 'xy'");
		System.out.println(result2);
		
		// Numerical Ranges; IntStream, DoubleStream, LongStream
		IntStream.rangeClosed(1, 10).forEach(num -> System.out.print(num));  // ->12345678910
		IntStream.range(1, 10).forEach(num -> System.out.print(num));        // ->123456789
		
		// mapToInt, mapToLong, mapToDouble, mapToObj
		Stream.of("ilker21","ilker31","ilker11","ilker41")
		.map(str -> str.substring(5))
		.mapToInt(Integer::parseInt)
		.max()
		.ifPresent(System.out::println);
	}

	public Integer sumDeleteMe() {
		List<Integer> ages = Arrays.asList(10,15,20,24);
		Integer sumOfAges = 0;
		for (Integer age : ages) {
			sumOfAges = Integer.sum(sumOfAges, age);
		}
		return sumOfAges;		
	}
	
	public void toStringEquivalentOfForEach(){
		List<String> strings = Arrays.asList("ilker", "kiris");
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	public static class Student {
		private String name;
		public String getName() { return name; }
		public Student(String _name) { this.name = _name; }
		public String toString() { return "name:" + name; }
	}
	
	public enum Gender { MALE, FEMALE }
	
	public static class Author {
		private String name;
		private int age;
		private Gender gender;
		public Author(String _name, int _age, Gender _gender) {
			this.name = _name; 
			this.age = _age;
			this.gender = _gender;
		}
		public String getName() { return name; }
		public int getAge() { return age; }
		public Gender getGender() {	return gender; }	
	}
	
	public static class Book {
		private Author author;
		private String title;
		public Book(Author _author, String _title) {
			this.author = _author;
			this.title = _title;
		}
		public Author getAuthor() {	return author;	}
		public String getTitle() { return title; }
	}
	
	public static List<Book> initializeBooks(){
		List<Book> books = Arrays.asList(
				new Book(new Author("ilker", 20, Gender.MALE), "Idiots_1"),
				new Book(new Author("Amy", 21, Gender.FEMALE), "Idiots_2"),
				new Book(new Author("Bob", 51, Gender.MALE), "Idiots_3"),
				new Book(new Author("Gerturde", 56, Gender.FEMALE), "Idiots_4"),
				new Book(new Author("Allen", 61, Gender.MALE), "Idiots_5"),
				new Book(new Author("McSusan", 51, Gender.FEMALE), "Idiots_6")	
		);
		return books;
	}
	
	public static void slide_8a_1(){
		List<Book> books = MyStreams.initializeBooks();
		int sumOfFemaleAuthorsOfAgeLessThan25 =
		 books.stream()
//		.map((Book book) -> book.getAuthor())
//		.map(book -> book.getAuthor())
		.map(Book::getAuthor)
		.filter(author -> author.getGender() == Gender.FEMALE)
		.map(Author::getAge)
		.filter(age -> age < 25)
//		.peek(age -> System.out.println(age))
		.peek(System.out::println)
//		.reduce(0, (sumValue, currentAge) -> Integer.sum(sumValue, currentAge));
		.reduce(0, Integer::sum);
		
		System.out.println(sumOfFemaleAuthorsOfAgeLessThan25);
	}
}
