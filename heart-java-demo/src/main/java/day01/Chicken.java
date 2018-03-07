package day01;

public class Chicken {
	int money;
	double hen = 3;
	double rooster = 4;
	double chick = 0.5;

	public void howToMany(int money) {
		for (int i = 0; i <= money / hen; i++)
			for (int j = 0; j <= money / rooster; j++)
				for (int k = 0; k <= 100 - i - j; k++)
					if ((3 * i + 4 * j + 0.5 * k == money) && (i + j + k == 100)) {
						System.out.println("母鸡有" + i + "只,公鸡" + j + "只,小鸡" + k + "只");
					}

		// int count=0;
		// for(int i=0;i<=33;i++)
		// for(int j=0;j<=25;j++)
		// for(int k=0;k<=200;k++)
		// if((3*i+4*j+0.5*k==money)){
		// System.out.println("母鸡有"+i+"只,公鸡"+j+"只,小鸡"+k+"只");
		// count++;
		// }
		// System.out.println(count);
	}

	public static void main(String args[]) {
		Chicken chicken = new Chicken();
		chicken.howToMany(100);
	}
}
