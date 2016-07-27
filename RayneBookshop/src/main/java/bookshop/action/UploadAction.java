package bookshop.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.bson.BsonDocument;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.net.UnknownHostException;  
import java.util.Iterator;  
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB; 
import com.mongodb.MongoClient; 
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;  
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;  


public class UploadAction extends ActionSupport{
    
    private File image; //上传的文件
    private String imageFileName; //文件名称
    private String imageContentType; //文件类型
    byte[] buffer = null;

    @SuppressWarnings("deprecation")
	public String execute() throws Exception {
        //String realpath = ServletActionContext.getActionContext().getRealPath("/images");
        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
    	//String realpath = ServletActionContext.getPageContext().getContextPath(); 
    	String realpath="F:\\Apache Software Foundation\\Tomcat 8.0\\webapps\\RayneBookshop\\resources\\images";
        System.out.println("realpath: "+realpath);
        if (image != null) {
            File savefile = new File(new File(realpath), imageFileName);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(image, savefile);
            ActionContext.getContext().put("message", "文件上传成功");
        }
        
        /*以上是将图片上传至了服务器，接下来要将它存储在mongodb里面去*/
        /*Mongo mg = new Mongo("127.0.0.1", 27017);
        DB db = mg.getDB("test");*/
        MongoClient mongoClient = new MongoClient();
        
        //查询所有Database
        for (String name : mongoClient.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }
        
        DB db = mongoClient.getDB("test");
        
        //查询所有集合名
        for (String name : db.getCollectionNames()) {
            System.out.println("collectionName: " + name);
        }
        
        DBCollection users = db.getCollection("person");
        //查询所有数据
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
        
        //将file转化成byte数组
        FileInputStream fis = new FileInputStream(image);  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] b = new byte[3096];  
        int n;  
        while ((n = fis.read(b)) != -1)  
        {  
            bos.write(b, 0, n);  
        }  
        fis.close();  
        bos.close();  
        buffer = bos.toByteArray();
        
        //存入mongodb
        BasicDBObject doc = new BasicDBObject();
        
        doc.put("ID", 1);
        doc.put("img", buffer);

        users.insert(doc);
        
        //寻找ID为1的元素
        BasicDBObject query = new BasicDBObject();  
        query.put("ID", 1);  
        DBCursor cursor = users.find(query);
        DBObject obj = cursor.next();
        obj.put("ID",2);
        users.save(obj);
        System.out.println(obj.get("ID"));
        while(cursor.hasNext()) {  
            System.out.println(cursor.next());  
        }  
        return SUCCESS;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    
}
