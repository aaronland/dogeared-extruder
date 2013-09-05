dropwizard-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika` and
clones of the `readability` text extraction libraries using the `dropwizard`
framework.

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

By default the server will return HTML but if you pass an `Accept:
application/json` header you'll get a big old blob of JSON instead.

Notes
--

* It basically works at the moment but I am not a Java person so I am still trying
  to fumble my way around this foreign land. For example, the correct `mvn`
  commands for building a standalone server that runs in the background.

* The text/content extraction is pretty heavy-handed and relies on the
  underlying libraries to do the right thing. Currently everything returns
  blocks of plain text so things like lists and code samples will probably be
  mangled. This is not ideal but that stuff is meant to be handled going forward.

* If you look carefully at the URLs above and the actual classes that define the
  functionality they all look basically the same save for the names of the
  extraction tools. For the time being I think the classes (and URLs) should
  remain separate if only to keep things simple(r) while everything else is
  sorted out.

* There is also a [separate
branch](https://github.com/straup/dropwizard-extruder/tree/snacktory) that uses
the `snacktory` readability clone but it has not been merged in to master yet.

To do
--

Aside from stuff listed in the [TODO.txt](TODO.txt} file:

* UTF-8 headers

* Health checks

See also
--

* http://code.google.com/p/boilerpipe/
* https://tika.apache.org/
* https://github.com/basis-technology-corp/Java-readability
* https://github.com/karussell/snacktory
