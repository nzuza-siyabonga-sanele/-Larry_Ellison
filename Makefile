.PHONY: start-server

start-server: clean

clean: myServer_CommandLineWithObstacles
	test -f myServer_CommandLineWithObstacles && kill -9 `cat myServer_CommandLineWithObstacles` && rm myServer_CommandLineWithObstacles || true


myServer_noCommandLine.pid:
	mvn test -Dtest="za/co/wethinkcode/robotworlds/world/ReadConfigFileTest.java"
	java -jar target/Brown_Fields-1.1-jar-with-dependencies.jar & echo $$! > $@
	mvn compile
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World1x1/ForwardRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World1x1/LaunchRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World1x1/StateRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World1x1/LookRobotTests.java"


myServer_CommandLineWithObstacles: myServer_noCommandLine.pid
	test -f myServer_noCommandLine.pid && kill -9 `cat myServer_noCommandLine.pid` && rm -f myServer_noCommandLine.pid || true
	mvn compile
	java -jar target/Brown_Fields-1.1-jar-with-dependencies.jar -p 5000 -s 2 -o 1,1 & echo $$! > $@
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World2x2/ForwardRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World2x2/LaunchRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World2x2/StateRobotTests.java"
	mvn test -Dtest="za/co/wethinkcode/robotworlds/World2x2/LookRobotTests.java"
	mvn package


