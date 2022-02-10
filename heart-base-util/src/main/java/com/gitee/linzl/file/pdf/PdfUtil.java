package com.gitee.linzl.file.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.SimpleBookmark;
import org.apache.commons.compress.utils.IOUtils;

/**
 * pdf工具
 *
 * @author linzhenlie-jk
 * @date 2022/2/10
 */
public class PdfUtil {
    public static boolean merge(List<File> list, File targetPdf) {
        int pageOffset = 0;
        List allBookMark = new ArrayList();
        int index = 0;
        //默认A4大小
        Document document = null;
        PdfCopy writer = null;

        try {
            while (index < list.size()) {
                // create a reader for a certain document
                PdfReader reader = new PdfReader(list.get(index).getPath());
                reader.consolidateNamedDestinations();
                // retrieve the total number of pages
                int num = reader.getNumberOfPages();

                List bookmarks = SimpleBookmark.getBookmark(reader);
                if (bookmarks != null) {
                    if (pageOffset != 0) {
                        SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                    }
                    allBookMark.addAll(bookmarks);
                }
                pageOffset += num;

                if (index == 0) {
                    // step 1: creation of a document-object
                    document = new Document(reader.getPageSizeWithRotation(1));
                    // step 2: create a writer that listens to the document
                    writer = new PdfCopy(document, new FileOutputStream(targetPdf));
                    // step 3: open the document
                    document.open();
                }
                // step 4: add content
                for (int i = 1; i < num; i++) {
                    document.newPage();
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    writer.addPage(page);
                }
                index++;
            }
            if (!allBookMark.isEmpty()) {
                writer.setOutlines(allBookMark);
            }

            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(document)) {
                // step 5: close the document
                document.close();
            }
        }
        return Boolean.FALSE;
    }

    public static void generate(Map<String, String> data, File pdfTemplate, File targetPdf) {
        FileOutputStream fos = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(pdfTemplate.getPath());
            fos = new FileOutputStream(targetPdf);
            stamper = new PdfStamper(reader, fos);
            stamper.setFormFlattening(Boolean.TRUE);

            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            AcroFields form = stamper.getAcroFields();
            /**
             * 设置全局字段
             */
            form.addSubstitutionFont(base);

            for (String key : form.getFields().keySet()) {
                /**
                 * 每个域的字体
                 */
                form.setFieldProperty(key, "textfont", base, null);
                form.setFieldProperty(key, "textsize", new Float(9), null);
            }

            if (data != null && !data.isEmpty()) {
                for (String fieldName : data.keySet()) {

                    if (fieldName.startsWith("base64")) {
                        addImage(stamper, form, fieldName, data.get(fieldName));
                        continue;
                    }
                    form.setField(fieldName, data.get(fieldName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
            IOUtils.closeQuietly(fos);
        }
    }

    public static void addImage(PdfStamper stamper, AcroFields form, String fieldName, String imagePath) {
        try {
            List<AcroFields.FieldPosition> photograph = form.getFieldPositions(fieldName);
            if (photograph != null && photograph.size() > 0) {
                Rectangle rect = photograph.get(0).position;
                Image img = Image.getInstance(imagePath);
                img.scaleToFit(rect.getWidth(), rect.getHeight());
                img.setBorder(2);
                img.setAbsolutePosition(
                    rect.getLeft() + rect.getWidth() - img.getScaledWidth()
                    , rect.getTop() - rect.getHeight());
                PdfContentByte cb = stamper.getOverContent(photograph.get(0).page);
                cb.addImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
