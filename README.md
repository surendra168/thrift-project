### Steps to run the project

<ol>
  <li>First, set up mysql server in your local machine. Run the code given in sql/setup.sql to create a database named person_db and a table named persons in it .</li>
  
  <li>Run "mvn clean package" to compile the project and package the compiled code along with any resources into a JAR file within the target directory</li>
  <li>Run "java -cp target/test-thrift-1.0-SNAPSHOT-jar-with-dependencies.jar PersonServiceServer
" to instantiate the server</li>
  <li>Run "java -cp target/test-thrift-1.0-SNAPSHOT-jar-with-dependencies.jar PersonServiceClient to run the client.
</li>
</ol>