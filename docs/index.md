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

## Maven Repository

The following URL is used as the Maven repository for this library.

https://okamumu.github.io/JMTRandom/repository/

For example, in Gradle, we put the following codes in `gradle.build` to use this library.

```groovy
// Set URL to two properties; url and artifactUrls

repositories {
  maven {
    url "https://okamumu.github.io/JMTRandom/repository/"
  }.artifactUrls('https://okamumu.github.io/JMTRandom/repository/')
}

dependencies {
  compile 'com.github.okamumu:jmtrandom:0.1.0'
}
```

## Example

Generate a sequence of uniform random numbers.

```java
import com.github.okamumu.jmtrandom.*;

class Main {
  public static void main(String[] args) {
    long seed = 20180516;
    Random rnd = new Random(new MT64(seed));
    for (int i=0; i<100; i++) {
      System.out.println(rnd.nextUnif());
    }
  }
}
```
