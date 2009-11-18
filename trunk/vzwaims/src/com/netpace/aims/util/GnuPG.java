package com.netpace.aims.util;

import java.io.*;

public class GnuPG {
	private Process p;

	private String gpg_result;
	private String gpg_err;

	public GnuPG() {
	}

	public static void main(String[] args) {
		// System.out.println("START");
		if (args.length != 1) {
			System.out.println("Usage: GnuPG filename_to_sign");
			System.exit(1);
		}

		// System.out.println(args[0]);
		// System.out.println(args[1]);
		GnuPG objGPG = new GnuPG();
		// objGPG.verifySign (args[0],args[1]);
		objGPG.sign(args[0]);
	}

	public void sign(String originalFile) {

		System.out.print("Signing...");

		try {
			// p = Runtime.getRuntime().exec("cat passphrase.txt | gpg
			// --passphrase-fd 0 --verbose --output ~/GPG/Adnan.gpg --sign " +
			// originalFile);
			// p = Runtime.getRuntime().exec("gpg --passphrase-fd 0 --batch
			// --default-key 'Adnan Makda' --sign " + originalFile);
			p = Runtime.getRuntime().exec("gpg --passphrase-fd 0 --batch --local-user Netpace_Inc_Motricity_Content_ZIP --sign "+ originalFile);
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		try {
			// out.write("\n");
			out.write("Secret_Netpace_Motricity");
			out.close();
		} catch (IOException io) {
			io.printStackTrace();
		}

		try {
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}

	public void verifySign(String signedFile, String originalFile) {

		System.out.print("Verifying...");

		try {
			// p = Runtime.getRuntime().exec("gpg --output "+originalFile+"
			// --decrypt --yes --verbose " + signedFile);
			// p = Runtime.getRuntime().exec("gpg --verify " + signedFile);
			p = Runtime.getRuntime().exec("gpg --output " + originalFile + " " + signedFile);
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		System.out.print("Verifying...1");
		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		System.out.print("Verifying...2");
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		System.out.print("Verifying...3");
		try {
//			out.write("\n");
			out.write("Secret_Netpace_Motricity");
			out.close();
		} catch (IOException io) {
			System.out.print("Verifying...4");
		}

		try {
			p.waitFor();

			System.out.print("Verifying...5");
			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
			System.out.print("Verifying...6");
		}

		System.out.print("Verifying...7");
		gpg_result = psr_stdout.getString();
		System.out.print("Verifying...8");
		gpg_err = psr_stdout.getString();
		System.out.print("Verifying...9");

		System.out.println("Done.");
	}

	public void encrypt(String str, String rcpt) {
		System.out.print("Encrypting... ");

		try {
			p = Runtime.getRuntime().exec("gpg --armor --batch --encrypt -r " + rcpt);
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		try {
			out.write(str);
			out.close();
		} catch (IOException io) {
		}

		try {
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}

	public void decrypt(String str, String passphrase) {
		File f = null;

		try {
			f = File.createTempFile("gpg-decrypt", null);
			FileWriter fw = new FileWriter(f);
			fw.write(str);
			fw.flush();
		} catch (IOException io) {
		}

		System.out.print("Decrypting from: " + f.getAbsolutePath());

		try {
			p = Runtime.getRuntime().exec("gpg --passphrase-fd 0 --batch --decrypt " + f.getAbsolutePath());
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		try {
			out.write(passphrase);
			out.close();
		} catch (IOException io) {
		}

		try {
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}

	public String getResult() {
		return gpg_result;
	}

	public String getError() {
		return gpg_err;
	}
}

class ProcessStreamReader extends Thread {
	String name;
	StringBuffer stream;
	InputStreamReader in;

	final static int BUFFER_SIZE = 256;

	ProcessStreamReader(String name, InputStream in) {
		super();

		this.name = name;
		this.in = new InputStreamReader(in);

		this.stream = new StringBuffer();
	}

	public void run() {
		try {
			int read;
			char[] c = new char[BUFFER_SIZE];

			while ((read = in.read(c, 0, BUFFER_SIZE - 1)) > 0) {
				stream.append(c, 0, read);
				if (read < BUFFER_SIZE - 1)
					break;
			}
		} catch (IOException io) {
		}
	}

	String getString() {
		return stream.toString();
	}
}
