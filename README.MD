# Automated Coverage Collection Tool

Use	ASM	byte-code	manipulation framework to	build	an	automated coverage	collection	tool	that can	capture	the	statement	coverage for the program	under	test.	Then,	apply	your	tool	to	10 real-world	Java	projects (>1000	lines	of	code)	with	JUnit	tests	(>50	tests)	from	GitHub/SourceForge to	collect	the	statement coverage	for	its	JUnit	tests.	Note	that	your	tool should,

1. use Java Agent to perform	on-the-fly	code	instrumentation, and 

2. be integrated with	the	Maven	build	system so	that	your	tool	can	be	triggered	by	simply changing	the	pom.xml	file	of	the	project	under	test. You	are	also	highly recommended	to	trace	coverage	for	each	single	JUnit	test	by	capturing the test	start/end	events.

[Java	Agent](https://docs.oracle.com/javase/6/docs/api/java/lang/instrument/packagesummary.html)

[Maven](https://maven.apache.org/)

### Build
Import project to Intellij Idea, then build artifact. The name of output artifact is "agent.jar"

### Usage
Copy agent.jar to anywhere you want.
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
