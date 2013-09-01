dropwizard-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika` and
clones of the `readability` text extraction libraries using the `dropwizard`
framework.

It basically works at the moment but I am not a Java person so I am still trying
to fumble my way around this foreign land.

To start the server:

	$> cd dropwizard-extruder
	$> make exec
	mvn compile exec:java
	...
	INFO  [2013-08-30 12:49:12,184] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
	INFO  [2013-08-30 12:49:12,189] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081

And then you can pass it URLs as `GET` parameters:
  
	$> curl 'http://localhost:8080/boilerpipe?url=SOME_URL'

	$> curl 'http://localhost:8080/java-readability?url=SOME_URL'

	$> curl 'http://localhost:8080/tika?url=SOME_URL_DOT_PDF'

It also supports local files via `POST` uploads:

	$> curl -X POST -F "file=SOME_FILE.html" http://localhost:8080/boilerpipe

	$> curl -X POST -F "file=SOME_FILE.html" http://localhost:8080/java-readability
  
	$> curl -X POST -F "file=SOME_FILE.pdf" http://localhost:8080/tika

Notes
--

* If you look carefully at the URLs above and the actual classes that define the
  functionality they all look basically the same save for the names of the
  extraction tools. For the time being I think the classes (and URLs) should
  remain separate if only to keep things simple(r) while everything else is
  sorted out.

* There is also a [separate
branch](https://github.com/straup/dropwizard-extruder/tree/snacktory) that uses
the `snacktory` readability clone but it has not been merged in to master yet.

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
