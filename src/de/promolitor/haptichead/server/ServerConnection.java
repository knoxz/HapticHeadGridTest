package de.promolitor.haptichead.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ServerConnection {

	private static int motorCount = 24;
	public static int[] intensities = new int[motorCount];
	private byte[] intensitiesByteArray = new byte[motorCount << 2];

	private Socket s;

	public ServerConnection() {

	}

	public boolean connect() {

		try {
			s = new Socket("192.168.1.26", 55006);

			System.out.println(s.isConnected());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void byteUpdate() {
		for (int i = 0; i < motorCount; i++) {
			int x = intensities[i];
			int j = i << 2;
			intensitiesByteArray[j++] = (byte) ((x >>> 0) & 0xff);
			intensitiesByteArray[j++] = (byte) ((x >>> 8) & 0xff);
			intensitiesByteArray[j++] = (byte) ((x >>> 16) & 0xff);
			intensitiesByteArray[j++] = (byte) ((x >>> 24) & 0xff);
		}
	}

	public void test() throws IOException {
		for (int i = 0; i < intensities.length; i++) {
			intensities[i] = i;
		}
		byteUpdate();
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.write(intensitiesByteArray);
		dos.flush();
	}

	public void send() {
		byteUpdate();
		try {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.write(intensitiesByteArray);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws InterruptedException, IOException {
		ServerConnection server = new ServerConnection();
		server.connect();

		while (true) {
			Thread.sleep(100);
			server.byteUpdate();
			server.test();
		}

	}
}
