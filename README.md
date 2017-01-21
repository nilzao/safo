# safo
SAFO - Selenium Auto FOrm

You can get a XML file into a FORM using Selenium web driver.

follow the package br.com.nils.selenium.safo.example to see how to do that.

the @SafoComponent annotation will help

inside pom.xml

    <dependency>
      <groupId>br.com.nils.senelium.auto.form</groupId>
      <artifactId>safo</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>

    <repositories>
	    <repository>
	      <id>safo-repo</id>
	      <url>https://raw.github.com/nilzao/safo/mvn-repo/</url>
	      <snapshots>
	        <enabled>true</enabled>
	        <updatePolicy>always</updatePolicy>
	      </snapshots>
	    </repository>
    </repositories>