package org.legion.storage;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.legion.util.Utilities;
import java.io.*;
import java.net.URLConnection;
import java.util.List;

public class StorageService implements Serializable {
    MinioClient minioClient;


    public StorageService() throws Exception {
        Utilities util = new Utilities();
        minioClient =
                MinioClient.builder()
                        .endpoint(util.getConfigProperty("minio-server-host"))
                        .credentials(util.getConfigProperty("minio-user"),
                                util.getConfigProperty("minio-password"))
                        .build();
    }

    public List<Bucket> fetchBucketList() throws Exception {
        List<Bucket> bucketList = minioClient.listBuckets();
        return bucketList;
    }


    public void createBucket(String name) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
    }

    public void deleteBucket(String name) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(name).build());
    }


    public Iterable<Result<Item>> fetchObjects(String bucketName) throws Exception {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }

    public String saveObject(String bucketName, String name, byte[] content) throws Exception {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
        String contentType = URLConnection.guessContentTypeFromStream(is);

        if (contentType == null || contentType.isEmpty()) {
            throw new Exception("cannot fetch content type");
        }

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(name)
                .stream(new ByteArrayInputStream(content), -1, 10485760)
                .contentType(contentType)
                .build());
        Utilities util = new Utilities();
        return util.getConfigProperty("archive-storage.external-public-ip") + "/" + bucketName + "/" + name;

    }


}
