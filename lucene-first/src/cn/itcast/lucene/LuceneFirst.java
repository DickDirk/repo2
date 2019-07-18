package cn.itcast.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

/**
 * @program: Lucene
 * @description
 * @author: liumengke
 * @create: 2019-07-17 12:52
 **/
public class LuceneFirst {
    @Test
    public void test() throws Exception {
        //指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:\\Develop\\JAVA\\Lucene\\index").toPath());
        // 第二步：创建一个indexwriter对象。
        // 2）指定一个IndexWriterConfig对象。
        IndexWriter iw = new IndexWriter(directory, new IndexWriterConfig());
        //
        File file = new File("F:\\javaSE黑马48期就业班资料\\javaEE就业班预习资料\\46加密项目阶段\\008 A0.lucene2018\\02.参考资料\\searchsource");
        File[] files = file.listFiles();
        for (File f : files) {
            //读取文件名
            String filename = f.getName();
            //文件的路径
            String filepath = f.getPath();
            //文件的内容
            String fileContent = FileUtils.readFileToString(f, "utf-8");
            //文件的大小
            long filesize = FileUtils.sizeOf(f);
            //创建filed
            /*  参数一.
                参数二.
                参数三.

             */
            Field fieldName = new TextField("name", filename, Field.Store.YES);
            Field fieldPath = new TextField("path", filepath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", filesize + "", Field.Store.YES);
            //创建文档对象
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
            //把文档对象写入索引库
            iw.addDocument(document);
            //关闭索引库
            iw.close();
        }

    }
    @Test
    public void test1() throws Exception {
        //指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:\\Develop\\JAVA\\Lucene\\index").toPath());


    }
    @Test
    public void test2() throws Exception {
            //
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("","");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        tokenStream.close();
    }
}
