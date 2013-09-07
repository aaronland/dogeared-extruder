exec:
	mvn compile exec:java

build:
	mvn clean
	mvn install

todo:
	echo "# Generated automatically at" `date` > TODO.txt
	echo "" >> TODO.txt
	grep -n -r -e TODO ./src >> TODO.txt
	grep -n -r -e TO\ DO ./src >> TODO.txt
