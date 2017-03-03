# manifest-validator-maven-plugin

[![Build Status](https://travis-ci.org/velo/manifest-validator-maven-plugin.svg?branch=master)](https://travis-ci.org/velo/manifest-validator-maven-plugin?branch=master) 
[![Coverage Status](https://coveralls.io/repos/github/velo/manifest-validator-maven-plugin/badge.svg?branch=master)](https://coveralls.io/github/velo/manifest-validator-maven-plugin?branch=master) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/manifest-validator-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/manifest-validator-maven-plugin/) 
[![Issues](https://img.shields.io/github/issues/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/issues) 
[![Forks](https://img.shields.io/github/forks/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/network) 
[![Stars](https://img.shields.io/github/stars/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/stargazers)

maven.boilerplate contains a set of configuration files for CI(travis, appveyor), coverage(coveralls+jacoco), code format(eclipse+maven), licensing, other maven plugins and all other things I think I will need accross multiple projects

# Why?
![image](https://cloud.githubusercontent.com/assets/136590/14231013/3752c72a-f9c6-11e5-9372-af23df11d18d.png)


I wanna all my projects share what I think makes life easier without having to remember copy and pasting all.

This way, a script can remember to copy and paste for me.

As a preparation step, it's really wise to run
````
$ mvn com.github.ekryd.sortpom:sortpom-maven-plugin:2.4.0:sort -Dsort.keepBlankLines=true "-Dsort.lineSeparator=\n" -Dsort.predefinedSortOrder=custom_1 -Dsort.createBackupFile=false
````
