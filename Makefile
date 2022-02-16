exec:
	mvn clean
	mvn compile exec:java

build: todo
	mvn clean
	mvn install

run:
	java -jar target/extruder-1.0.jar server

todo:
	echo "# Generated automatically at" `date` > TODO.txt
	echo "" >> TODO.txt
	grep -n -r -e TODO ./src >> TODO.txt
	grep -n -r -e TO\ DO ./src >> TODO.txt

docker:
ifdef NOCACHE
	docker build --no-cache=true -t dogeared-extruder .
else
	docker build -t dogeared-extruder .
endif

docker-run:
	docker run -it -p 8080:8080 dogeared-extruder java -jar /usr/local/jar/dogeared-extruder.jar server
