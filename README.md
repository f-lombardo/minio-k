# Minio-k: an example Kotlin client for MinIO

This is a simple example of using [MinIO](https://min.io/) client libraries in Kotlin

This program uploads a file into an S3 compatible bucket. The host configuration can be provided in two ways:
1. Using the environment variables endpoint, accessKey, secretKey
2. Through the mc/config.json (it's the same that mc uses) and passing a configuration name as first parameter to the program

These are the command line parameters:

 `[configurationName] bucketName objectName localFileName`
 
Here are the steps to run the program:

* Create the full jar with 

`./gradelw fatJar`

* Create a `USER_HOME/mc/config.json` with the connections configurations 
(you can use [minio client mc](https://docs.min.io/docs/minio-client-quickstart-guide.html))

* Tu upload `/my/Local/File.Name` to a S3 compatible bucket, run the program with: 

`java -jar minio-k-all.jar myServerConfiguration myBucketName myObjectName /my/Local/File.Name`


