dropwizard-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika` and
clones of the `readability` text extraction libraries using the `dropwizard`
framework.

It only sort of works at the moment. I am not a Java person so I am still trying
to fumble my way around this foreign land.

	$> cd dropwizard-extruder
	$> make exec
	mvn compile exec:java
	...
	INFO  [2013-08-30 12:49:12,184] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
	INFO  [2013-08-30 12:49:12,189] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081
  
	$> curl 'http://localhost:8080/boilerpipe?url=SOME_URL'

	$> curl 'http://localhost:8080/java-readability?url=SOME_URL'

	$> curl 'http://localhost:8080/tika?url=SOME_URL_DOT_PDF'

Also, local file uploads:

	$> curl -v -X POST -F "file=SOME_FILE.html" http://localhost:8080/boilerpipe
  
	$> curl -v -X POST -F "file=SOME_FILE.pdf" http://localhost:8080/tika

Notes
--

* There is also a [separate branch](https://github.com/straup/dropwizard-extruder/tree/snacktory) that uses the `snacktory` readability clone
but it has not been merged in to master yet.

Open questions
--

Should this:

* Always return plain text
* Always return HTML
* Always return JSON blobs containing "paragraphs" 

See also
--

* http://code.google.com/p/boilerpipe/
* https://tika.apache.org/
* https://github.com/basis-technology-corp/Java-readability
* https://github.com/karussell/snacktory
