# VersionSpaceLearning

Note: .pom file has to be edited before running the project, we have created local system library for SPAB project to be used as dependencies

Please add the following in your .pom file:

========================================================================================================================================================
<dependency>
			<groupId>org.dice-research</groupId>
			<artifactId>SPAB</artifactId>
			<version>0.1.0-SNAPSHOT</version>
			<scope>system</scope>
			<systemPath>"YOUR SYSTEM PATH"</systemPath>
		</dependency>
		
		
		<dependency>
			<groupId>org.dice-research</groupId>
			<artifactId>SPAB-jar-with-dependencies</artifactId>
			<version>0.1.0-SNAPSHOT</version>
			<scope>system</scope>
			<systemPath>"YOUR SYSTEM PATH"</systemPath>
		</dependency>
		
========================================================================================================================================================

How to create a jar file with Maven for existing project, please follow the link below:
https://www.youtube.com/watch?v=vGtGxKZQ-l8&t