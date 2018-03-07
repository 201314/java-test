package day08.spi;

public class ChildSPIService implements SPIService {

	@Override
	public void test() {
		System.out.println("test");
	}

}
