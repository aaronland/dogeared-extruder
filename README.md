dropwizard-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika` and
clones of the `readability` text extraction libraries using the `dropwizard`
framework.

To start the server:
   
	$> cd dropwizard-extruder
	$> make build
	... JAVA STUFF ...
	$> java -jar target/extruder-1.0-SNAPSHOT.jar server
	... MOAR JAVA STUFF ...
	INFO  [2013-08-30 12:49:12,184] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
	INFO  [2013-08-30 12:49:12,189] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081

And then you can pass it URLs as `GET` parameters:
  
	$> curl 'http://localhost:8080/boilerpipe?url=SOME_URL'

	$> curl 'http://localhost:8080/java-readability?url=SOME_URL'

	$> curl 'http://localhost:8080/tika?url=SOME_URL_DOT_PDF'

By default the server will return HTML but if you pass an `Accept:
application/json` header you'll get a big old blob of JSON instead.

	$> curl -H 'Accept:application/json' 'http://localhost:8080/boilerpipe?url=SOME_URL'

Notes
--

* It basically works at the moment but I am not a Java person so I am still trying
  to fumble my way around this foreign land. For example, the correct `mvn`
  commands for building a standalone server that runs in the background.

* You can also type `make exec` to recompile the code and launch the server in
  foreground mode, which is useful for debugging things.

* The text/content extraction is pretty heavy-handed and relies on the
  underlying libraries to do the right thing. Currently everything returns
  blocks of plain text so things like lists and code samples will probably be
  mangled. This is not ideal but that stuff is meant to be handled going forward.

* There is a separate branch called `local-files` which allows you to upload
  files as HTTP `POST` blobs. Currently it works fine when the server is started
  using `make exec` (see above) but fails completely when run as a stand-alone
  jar file. This appears to be a `maven-shade` thing but ... uh, Java?

* If you look carefully at the URLs above and the actual classes that define the
  functionality they all look basically the same save for the names of the
  extraction tools. For the time being I think the classes (and URLs) should
  remain separate if only to keep things simple(r) while everything else is
  sorted out.

* There is also a [separate
branch](https://github.com/straup/dropwizard-extruder/tree/snacktory) that uses
the `snacktory` readability clone but it has not been merged in to master yet. I
can't remember why except that I was having trouble getting it to work and
decided to try the `java-readability` library instead.

To do
--

Aside from stuff listed in the [TODO.txt](TODO.txt) file:

* Health checks
* A resource endpoint that calls the actual Readability API

See also
--

* http://code.google.com/p/boilerpipe/
* https://tika.apache.org/
* https://github.com/basis-technology-corp/Java-readability
* https://github.com/karussell/snacktory
