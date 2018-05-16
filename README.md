# JMTRandom

A Java library for Mersenne twister and SMFT.

## Build

This library is build by Gradle. To obtain the jar file, please type the following command
```
gradle jar
```
The compiled jar file is found in `build/libs` directory.

## API Document

API Document is available in https://okamumu.github.io/JMTRandom/javadoc/

## Example

Generate a sequence of uniform random numbers.

```java
package com.github.okamumu.jmtrandom.*;

...

long seed = 20180516;
Random rnd = new Random(new MT64(seed));
for (int i=0; i<100; i++) {
  System.out.println(rnd.nextUnif());
}
```
