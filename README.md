# manifest-validator-maven-plugin

[![Build Status](https://travis-ci.org/velo/manifest-validator-maven-plugin.svg?branch=master)](https://travis-ci.org/velo/manifest-validator-maven-plugin?branch=master) 
[![Coverage Status](https://coveralls.io/repos/github/velo/manifest-validator-maven-plugin/badge.svg?branch=master)](https://coveralls.io/github/velo/manifest-validator-maven-plugin?branch=master) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/manifest-validator-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/manifest-validator-maven-plugin/) 
[![Issues](https://img.shields.io/github/issues/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/issues) 
[![Forks](https://img.shields.io/github/forks/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/network) 
[![Stars](https://img.shields.io/github/stars/velo/manifest-validator-maven-plugin.svg)](https://github.com/velo/manifest-validator-maven-plugin/stargazers)

Checks MANIFEST.MF of a given jar

# Why?

Nordays executable jars became the new black.

Still, https://github.com/apache/maven-plugins/tree/trunk/maven-jar-plugin[maven-jar-plugin] provides no validation for the MANIFEST.MF.


# How?

````
           <plugin>
                <groupId>com.marvinformatics</groupId>
                <artifactId>manifest-validator-maven-plugin</artifactId>
                <configuration>
                    <entries>
                        <entry>Main-Class:PRESENT,VALID_CLASS</entry>
                    </entries>
                </configuration>
                <executions>
                    <execution>
                        <phase>verify</phase>
                        <goals>
                            <goal>manifest-validator</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
````
