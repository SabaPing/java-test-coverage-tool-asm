# Automated Coverage Collection Tool

Use	ASM	byte-code	manipulation framework to	build	an	automated coverage	collection	tool	that can	capture	the	statement	coverage for the program	under	test.	Then,	apply	your	tool	to	10 real-world	Java	projects (>1000	lines	of	code)	with	JUnit	tests	(>50	tests)	from	GitHub/SourceForge to	collect	the	statement coverage	for	its	JUnit	tests.	Note	that	your	tool should,

1. use Java Agent to perform	on-the-fly	code	instrumentation, and 

2. be integrated with	the	Maven	build	system so	that	your	tool	can	be	triggered	by	simply changing	the	pom.xml	file	of	the	project	under	test. You	are	also	highly recommended	to	trace	coverage	for	each	single	JUnit	test	by	capturing the test	start/end	events.

[Java	Agent](https://docs.oracle.com/javase/6/docs/api/java/lang/instrument/packagesummary.html)

[Maven](https://maven.apache.org/)
