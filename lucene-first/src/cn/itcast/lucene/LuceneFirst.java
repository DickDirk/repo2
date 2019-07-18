package cn.itcast.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
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
    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        //指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:\\Develop\\JAVA\\Lucene\\index").toPath());
        // 第二步：创建一个indexwriter对象。
        // 2）指定一个IndexWriterConfig对象。
        IndexWriter iw = new IndexWriter(directory, new IndexWriterConfig());
        //读取磁盘上的文件 对应的每个文件拆创建一个文档对象
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
            /*  参数一:域的名称
                参数二:域的内容
                参数三:是否存储

             */
            Field fieldName = new TextField("name", filename, Field.Store.YES);
            Field fieldPath = new TextField("path", filepath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", filesize + "", Field.Store.YES);
            //创建文档对象
            Document document = new Document();
            //向文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
            //把文档对象写入索引库
            iw.addDocument(document);
            //关闭索引库
            // iw.close();
        }

    }

    @Test
    public void test1() throws Exception {
        //指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:\\Develop\\JAVA\\Lucene\\index").toPath());
        //2.创建一个indexreader对象,指定索引 位置
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建一个indexsearcher 对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建一个query对象,termquery
        Query query = new TermQuery(new Term("content", "spring"));
        //5.执行查询,得到一个topdocs对象
        //参数一:查询的对象 参数二:查询返回的最大记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //6.去查询结果的总记录数
        System.out.println("查询的总记录数: " + topDocs.totalHits);
        //7.去文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //8.打印文档中的内容
        for (ScoreDoc scoreDoc : scoreDocs) {
            //取文档id
            int docid = scoreDoc.doc;
            //根据id取文档对象
            Document document = indexSearcher.doc(docid);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("content"));
            System.out.println(document.get("size"));
            System.out.println("==========================");
        }
        //9.关闭indexreader对象
        indexReader.close();

    }

    @Test
    public void test2() throws Exception {
        //
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("", "");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }
        tokenStream.close();
    }
}
