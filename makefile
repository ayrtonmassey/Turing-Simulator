JAVAC=javac
BUILD=build
SOURCE=src
DIST=dist

all: build jar

build:
	mkdir -p $(BUILD)/turing/gui/img/
	find -wholename "*.java" | xargs $(JAVAC) -d $(BUILD)/
	cp $(SOURCE)/turing/gui/img/* $(BUILD)/turing/gui/img/

jar:
	mkdir -p $(DIST)/
	jar -cvfe $(DIST)/turing.jar turing.Main -C $(BUILD)/ .

.PHONY: clean

clean: clean_build clean_dist tidy

clean_build:
	rm -rf build/

clean_dist:
	rm -rf dist/

tidy:
	rm -rf *~
