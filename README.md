dogeared-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika` and
clones of the `readability` text extraction libraries using the `dropwizard`
framework.

Version "2"
--

This package was not updated between May 2014 and February 2022.

In February 2022 "version 2" was released which introduces no new user-facing features but update the internal code, where necessary, to account for changes to its dependencies and to address known security vulnerabilities.

Quick start
--

To start the server:
   
	$> cd dogeared-extruder
	$> make build
	... JAVA STUFF ...
	$> java -jar target/extruder-2.0.jar server
	... MOAR JAVA STUFF ...
	INFO  [2013-08-30 12:49:12,184] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
	INFO  [2013-08-30 12:49:12,189] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081

And then you can pass it URLs as `GET` parameters:
  
	$> curl 'http://localhost:8080/boilerpipe?url=SOME_URL'

	$> curl 'http://localhost:8080/java-readability?url=SOME_URL'

	$> curl 'http://localhost:8080/tika?url=SOME_URL_DOT_PDF'

It also supports local files via `POST` uploads:

	$> curl -X POST -F "file=@SOME_FILE.html" http://localhost:8080/boilerpipe

	$> curl -X POST -F "file=@SOME_FILE.html" http://localhost:8080/java-readability

	$> curl -X POST -F "file=@SOME_FILE.pdf" http://localhost:8080/tika 

By default the server will return HTML but if you pass an `Accept:
application/json` header you'll get a big old blob of JSON instead.

	$> curl -H 'Accept:application/json' 'http://localhost:8080/boilerpipe?url=SOME_URL'

Notes
--

* It works but I am not a Java person so I am still fumbling my way around this foreign land.

* The text/content extraction is pretty heavy-handed and relies on the
  underlying libraries to do the right thing. Currently everything returns
  blocks of plain text so things like lists and code samples will probably be
  mangled. This is not ideal but that stuff is meant to be handled going forward.

* If you look carefully at the URLs above and the actual classes that define the
  functionality they all look basically the same save for the names of the
  extraction tools. For the time being I think the classes (and URLs) should
  remain separate if only to keep things simple(r) while everything else is
  sorted out.

* You can also type `make exec` to recompile the code and launch the server in
  foreground mode, which is useful for debugging things.

Dependencies
--

* You will need to have [maven](https://maven.apache.org/what-is-maven.html) installed to manage the build process. Or you can use the `Dockerfile` included with this package (assuming you have Docker installed).

Docker
--

```
$> docker build -t dogeared-extruder .

$> docker run -it -p 8080:8080 dogeared-extruder java -jar /usr/local/jar/dogeared-extruder.jar server
INFO  [2022-02-16 02:09:14,077] com.yammer.dropwizard.cli.ServerCommand: Starting extruder
INFO  [2022-02-16 02:09:14,079] org.eclipse.jetty.server.Server: jetty-8.y.z-SNAPSHOT
INFO  [2022-02-16 02:09:14,148] com.sun.jersey.server.impl.application.WebApplicationImpl: Initiating Jersey application, version 'Jersey: 1.17.1 02/28/2013 12:47 PM'
INFO  [2022-02-16 02:09:14,182] com.yammer.dropwizard.config.Environment: The following paths were found for the configured resources:

    GET     /boilerpipe (info.aaronland.extruder.BoilerpipeResource)
    POST    /boilerpipe (info.aaronland.extruder.BoilerpipeResource)
    GET     /tika (info.aaronland.extruder.TikaResource)
    POST    /tika (info.aaronland.extruder.TikaResource)
    GET     /java-readability (info.aaronland.extruder.JavaReadabilityResource)
    POST    /java-readability (info.aaronland.extruder.JavaReadabilityResource)

INFO  [2022-02-16 02:09:14,182] com.yammer.dropwizard.config.Environment: tasks = 

    POST    /tasks/gc (com.yammer.dropwizard.tasks.GarbageCollectionTask)

INFO  [2022-02-16 02:09:14,325] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
INFO  [2022-02-16 02:09:14,328] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081
```

To do
--

Aside from stuff listed in the [TODO.txt](TODO.txt) file:

* Try to be smarter about extracting or generating a page title for HTML
 output. Currently the code does not try to parse HTML input for title and
 simply parrots the basename of the input URL and/or relies on Tika's internal
 metadata parser.
* A resource endpoint that calls the `readability.com` API

See also
--

* http://code.google.com/p/boilerpipe/
* https://tika.apache.org/
* https://github.com/basis-technology-corp/Java-readability
