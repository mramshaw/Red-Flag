all:	build run

build:
	javac -cp src/ src/Driver.java

deprecated:
	javac -cp src/ -Xlint:deprecation src/Driver.java

unchecked:
	javac -cp src/ -Xlint:unchecked src/Driver.java

run:
	java -cp src/ Driver

parallel:
	java -cp src/ Driver -parallel

keywords:
	java -cp src/ KeywordManager

postscript:
	java -cp src/ PostScriptManager

emails:
	java -cp src/ EmailManager

clean:
	rm src/*.class
