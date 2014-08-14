Camunda Cockpit Plugins
=======================

This repository contains a collection of plugins for Camunda Cockpit, which have been developed by the Camunda BPM community and Camunda's consulting team.


How to use a Cockpit plugin
---------------------------

1. Build the plugin using Maven with `mvn clean install`

2. Integrate the JAR in your `camunda-*.war` file and there under WEB-INF/lib

After that the plugin will be shown in the Cockpit section that it extends.


Alternatively, add the plugin as a dependency to the Cockpit `pom.xml` and rebuild the Camunda web application.

    <dependencies>
      ...
      <dependency>
        <groupId>org.camunda.bpm.cockpit.plugin</groupId>
        <artifactId>cockpit-sample-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>runtime</scope>
      </dependency>


More information
----------------

Read the [plugin development how to](http://docs.camunda.org/latest/real-life/how-to/#cockpit-how-to-develop-a-cockpit-plugin).


License
-------

All plugins in this repository are released under terms of the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
