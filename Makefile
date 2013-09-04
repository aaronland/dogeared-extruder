exec:
	mvn compile exec:java

package:
	mvn package

todo:
	echo "# Generated automatically at" `date` > TODO.txt
	echo "" >> TODO.txt
	grep -n -r -e TODO ./src >> TODO.txt
	grep -n -r -e TO\ DO ./src >> TODO.txt
