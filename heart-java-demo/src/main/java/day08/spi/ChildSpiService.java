package day08.spi;

public class ChildSpiService implements SpiService {

	@Override
	public void test() {
		System.out.println("test");
	}

}
