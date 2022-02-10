import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Irr;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author linzhenlie-jk
 * @date 2021/1/23
 */
public class FileUtil {
    public static void main(String[] args) throws IOException {
        double dd = Irr.irr(new double[]{-220.93d, 75.69d, 75.69d, 75.7d},0.1d);

        System.out.println(dd);
    }
}
