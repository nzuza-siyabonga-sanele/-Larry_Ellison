.PHONY: start-server


start-server: clean

clean: server_ser.pid
	test -f server_ser.pid && kill -9 `cat server_ser,pid` && rn server_ser.pid || true

server.pid:
	mvn compile
	java -jar libs/reference-server-0.1.0.jar -s 1 & echo $$! > $@
	mvn test
	mvn package

server_ser.pid: server.pid
	test -f server_ser.pid && kill -9 `cat server_ser,pid` && rn server_ser.pid || true
	java -jar target/src-1.0-SNAPSHOT-jar-with-dependencies.jar
	mvn test


server_ser2.pid:
	mvn compile
	java -jar libs/reference-server-0.2.3.jar -s 1  & echo $$! > $@
	mvn test
	mvn package

server_ser3.pid:
	mvn compile
	java -jar libs/reference-server-0.2.3.jar -s 2 -o 1,1  & echo $$! > $@
	mvn test
	mvn package



#VERSION := $(shell cat package.json | awk '/"version":/ {gsub(/("|",)/,"",$$2);print $$2};')
#
#tag:
#	git tag -f $(VERSION)
#
#version-list:
#	git tag --list


#ifeq ($(VERSION),)
#     VERSION:=$(shell git describe --tags --abbrev=0 | awk -F .   '{OFS="."; $$NF+=1; print}')
#endif:
#
#tag:
#	git checkout integration
#	echo $(VERSION)
#	git tag $(VERSION)
#	#git push --tags
#
#endif