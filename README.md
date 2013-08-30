dropwizard-extruder
==

This is a meant to be a simple HTTP Pony to wrap the `boilerpipe` and `Tika`
text extraction libraries using the `dropwizard` framework. Ideally the `readbility` algorithm too.

It only sort of works at the moment. I am not a Java person so I am still trying to fumble my
way around this foreign land.

  $> cd dropwizard-extractor
  $> make exec
  mvn compile exec:java
  ...
  INFO  [2013-08-30 12:49:12,184] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
  INFO  [2013-08-30 12:49:12,189] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081
  
  $> curl 'http://localhost:8080/boilerpipe?url=SOME_URL'
  
Open questions
--

Should this:

* Always return plain text
* Always return HTML
* Always return JSON blobs containing "paragraphs" 

See also
--

* https://github.com/basis-technology-corp/Java-readability
* https://github.com/karussell/snacktory
