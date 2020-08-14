package com.gitee.linzl.composite.pattern;

import com.gitee.linzl.composite.pattern.demo2.BranchCompany;
import com.gitee.linzl.composite.pattern.demo2.Company;
import com.gitee.linzl.composite.pattern.demo2.FinanceDepartment;
import com.gitee.linzl.composite.pattern.demo2.HRDepartment;

public class CompositeTest2 {
	public static void main(String[] args) {
		Company headquarters = new BranchCompany("广州部门");
		headquarters.add(new FinanceDepartment("总部财务处"));
		headquarters.add(new HRDepartment("总部人力资源部"));

		Company beijing = new BranchCompany("北京分公司");
		beijing.add(new FinanceDepartment("北京财务处"));
		beijing.add(new HRDepartment("北京人力资源部"));

		headquarters.add(beijing);
		System.out.println("----公司结构图----");
		headquarters.display(1);

		System.out.println("\n----职责图----");
		headquarters.fulfilDuty();

	}
}
