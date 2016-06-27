package runner;

import java.io.FileNotFoundException;

import rmi.RemoteHelper;

public class ServerRunner {
	
	public ServerRunner() throws FileNotFoundException {
		new RemoteHelper();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new ServerRunner();
	}
}
