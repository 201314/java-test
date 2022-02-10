package com.gitee.linzl.file;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileFromUrlTest {
    public static void main(String[] args) {
        //将两个pdf合并成一个
        concatenateSummary();
    }

    public static void concatenateSummary() {
        List<String> args = new ArrayList<>();
        args.add("D:\\BaiduNetdiskDownload\\Spring\\Spring笔记.pdf");
        args.add("D:\\BaiduNetdiskDownload\\Spring高手系列\\Spring系列【公众号：路人甲Java】(下).pdf");
        String finalFile = "D:\\Spring笔记.pdf";
        try {
            int pageOffset = 0;
            ArrayList master = new ArrayList();
            int f = 0;
            String outFile = finalFile;
            Document document = null;
            PdfCopy writer = null;
            while (f < args.size()) {
                // create a reader for a certain document
                PdfReader reader = new PdfReader(args.get(f));
                reader.consolidateNamedDestinations();
                // retrieve the total number of pages
                int n = reader.getNumberOfPages();
                List bookmarks = SimpleBookmark.getBookmark(reader);
                if (bookmarks != null) {
                    if (pageOffset != 0)
                        SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                    master.addAll(bookmarks);
                }
                pageOffset += n;
                if (f == 0) {
                    // step 1: creation of a document-object
                    document = new Document(reader.getPageSizeWithRotation(1));
                    // step 2: create a writer that listens to the document
                    writer = new PdfCopy(document, new FileOutputStream(outFile));
                    // step 3: open the document
                    document.open();
                }
                // step 4: add content
                PdfImportedPage page;
                for (int i = 0; i < n; ) {
                    ++i;
                    page = writer.getImportedPage(reader, i);
                    writer.addPage(page);
                }
                f++;
            }
            if (!master.isEmpty())
                writer.setOutlines(master);
            // step 5: close the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
