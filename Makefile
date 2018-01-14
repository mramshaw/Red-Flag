all:	build run

build:
	javac -cp src/ src/Driver.java

run:
	java -cp src/ Driver

keywords:
	java -cp src/ KeywordManager

emails:
	java -cp src/ EmailManager

clean:
	rm src/*.class
