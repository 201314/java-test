package day11.nio.files;

import java.io.IOException;

import org.junit.Test;

import com.gitee.linzl.nio.file.FilesDemo;

public class FilesDemoTest {

	@Test
	public void test1() {
		FilesDemo.isTest();
	}

	@Test
	public void lines() {
		FilesDemo.lines();
	}

	@Test
	public void list() {
		FilesDemo.list();
	}

	@Test
	public void newBuffer() {
		try {
			FilesDemo.newBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void newByte() {
		try {
			FilesDemo.newByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void newDirectory() {
		try {
			FilesDemo.newDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void newStream() {
		try {
			FilesDemo.newStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
