# Automated Coverage Collection Tool

Use	ASM	byte-code	manipulation framework to	build	an	automated coverage	collection	tool	that can	capture	the	statement	coverage for the program	under	Junit test.	

* use Java Agent to perform	on-the-fly	code	instrumentation.


[Java	Agent](https://docs.oracle.com/javase/6/docs/api/java/lang/instrument/packagesummary.html)


### Build
Import project to Intellij Idea, then build artifact. The name of output artifact is "agent.jar"

### Usage
Copy agent.jar to anywhere you want.
Choose a project you want to analyze. This project must use Junit framework.
Before run Junit test, add VM arguement "-javaagent:<path to agent.jar>=<your project name>", then run test.
The result will show in "System.out".

### Example

#### VM arguement:

![Imgur](http://i.imgur.com/pRh7izt.png)

#### Result:

Output format:

class name:

one method in this class:

all statements in this method, shown as line number in source code:

all covered statement:

![Imgur](http://i.imgur.com/Eg2lKcX.png)
