package com.gitee.linzl.file;

import java.io.IOException;
import org.apache.poi.ss.formula.functions.Irr;

/**
 * @author linzhenlie-jk
 * @date 2021/1/23
 */
public class IrrUtil {
    public static void main(String[] args) throws IOException {
        double dd = Irr.irr(new double[]{-220.93d, 75.69d, 75.69d, 75.7d},0.1d);

        System.out.println(dd);
    }
}
