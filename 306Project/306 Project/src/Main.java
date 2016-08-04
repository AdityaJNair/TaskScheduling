import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.stream.file.FileSourceFactory;

import graph306.CustomGraphReader;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.print("Enter file path: "); // Use args[0] in the real program		
		String filePath = reader.nextLine();
		
		CustomGraphReader graphReader = new CustomGraphReader(args);
		
	}

}
